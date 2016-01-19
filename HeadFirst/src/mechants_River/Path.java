package mechants_River;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
public class Path {

	Dual carryingSchema[]; // 小船可提供的载人方案
    Dual initStatus; // 初始状态
    Dual endStatus; // 终止状态
    List<Node> path = new ArrayList<Node>(); // 过河路径
    Set<Node> iNode = new TreeSet(); // 孤立结点

    public Path(int merchant, int servant, int carrying) {
        initStatus = new Dual(merchant, servant);
        endStatus = new Dual(0, 0);
        buildCarryingSchema(carrying);
        findPath();
        if (path.isEmpty()) {
            System.out.println("Can't solve the problem");
        } else {
            for (Node e : path) {
                System.out.println(e);
            }
        }
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Path p = new Path(5, 5, 2);
	}

	 public boolean isRepeat() {
	        return path.contains(this);
	    }

	    /**
	     * 构建渡河方案 根据小船的最大可载人数且小船不能空 计算可行方案 每个方案表示一个dual对象 并把保存在carryingSchema中。
	     * 在Node中，通过数组下标来引用小船的载人方案。 数组既可以正向遍历，也可以反向遍历，所有的载人方案，按总人数进行降序排列
	     * 
	     * @param carrying
	     */
	    public void buildCarryingSchema(int carrying) {
	        int size = (2 + carrying + 1) * carrying / 2; // 方案总数
	        carryingSchema = new Dual[size];
	        // 小船载人方案；按人数降序排列
	        for (int i = 0, total = carrying; total > 0; total--) {
	            for (int m = total; m >= 0; m--) {
	                carryingSchema[i++] = new Dual(m, total - m);
	            }
	        }
	    }

	    /**
	     * 使用穷举法，搜索一条即初始状态initStatus至终止状态endStatus的迁移路径 step表示渡河步骤，从0开始，step为偶数，表示前行
	     * 小船前行时，正向遍历表carrySchema
	     * 如果无法找到可行的方案，则当前结点为孤立结点，则从path中删除，置入孤立结点集合中，同时step回退一步
	     * ，继续搜索可行的渡河方案。当step<0时，则表示无法找到可行的渡河方案，path将为空。
	     */
	    public void findPath() {
	        Dual curStatus = initStatus;
	        int step = 0;
	        Node node = null, rnode = null;
	        while (step >= 0) {
	            int direction = (step % 2 == 0 ? Node.FORWARD : Node.BACKWAED);
	            int idx;
	            if (direction == Node.FORWARD) {
	                idx = 0;
	                // mode不空，表示发送路径回退。需要跳过该结点已尝试的方案
	                if (rnode != null) {
	                    idx = rnode.idx + 1;
	                    rnode = null;
	                }
	            } else {
	                // 方向为backword
	                idx = carryingSchema.length - 1;
	                // mode不空，表示发送路径回退．需要跳过结点
	                if (rnode != null) {
	                    idx = rnode.idx - 1;
	                    rnode = null;
	                }
	            }
	            boolean found = false;
	            while (!found && idx >= 0 && idx < carryingSchema.length) {

	                node = new Node(curStatus, direction, idx);
	                if (node.isValid() && // 渡河方案是否有效
	                        node.isSecure() && // 状态是否安全
	                        !node.isRepeat() && // 结点不能重复
	                        !node.isIsolated()) // 非孤立结点
	                {
	                    found = true;
	                } else {
	                    if (direction == Node.FORWARD)
	                        idx++; // 顺序搜索
	                    else
	                        idx--; // 逆序搜索
	                }
	            }

	            if (!found) { // 未找到可行的渡河方案
	                step--; // 回退一步, 并删除当前结点
	                if (step >= 0) {
	                    curStatus = path.get(step).status;
	                    rnode = path.remove(step);
	                    iNode.add(rnode); // 孤立结点
	                    
	                }
	                continue;
	            }

	            path.add(node); // 把当前结点加入路径中
	            if (node.isEnd())
	                break; // 是否到达终止状态
	            curStatus = node.nextStatus(); // 当前状态变迁

	            step++;
	            System.out
	                    .println(step + " " + node.status.m + " " + node.status.s);
	        }
	    }
	    /**
	     * status 节点方向
	     * direction 渡河方向
	     * idx 索引，指向小船的载人方案 carryingSchema[]
	     * @author tina
	     * Node 表示整个渡河方案的每个步骤，每个结点由结点状态（商人和随从的人数）、渡河方向和渡河方案组成
	     */
	    public class Node implements Comparable<Node> {
	        Dual status;
	        int direction;
	        int idx;
	        public static final int FORWARD = 0; // 前进
	        public static final int BACKWAED = 1; // 返回

	        public Node(Dual status, int direction, int idx) {
	            this.status = status;
	            this.direction = direction;
	            this.idx = idx;
	        }

	        @Override
	        public int compareTo(Node e) {
	            return this.toString().compareTo(e.toString());
	        }
	        @Override
	        public String toString() {
	            return "("+this.status.m+","+this.status.s+")" + (direction == FORWARD ? " ---> " : " <--- ")+"("+carryingSchema[this.idx].m+","+carryingSchema[this.idx].s+")";
	        }

	        public boolean isIsolated() {
	            // int direction = this.nextDirection();
	            Dual status = this.nextStatus();
	            for (Node e : iNode) {
	                if (direction == e.direction && status.equals(e.status)) {
	                    return true;
	                }
	            }
	            return false;
	        }

	        Dual nextStatus() {
	            return direction == FORWARD ? this.status
	                    .minus(carryingSchema[idx]) : this.status
	                    .add(carryingSchema[idx]);
	        }

	        public boolean equals(Object e) {
	            if (this == e)
	                return true;
	            if (!(e instanceof Node))
	                return false;
	            Node o = (Node) e;
	            return this.status.equals(o.status)
	                    && this.direction == o.direction && this.idx == o.idx;
	        }

	        /**
	         * 当向前渡河时，要求本岸商人和随从人数分别大于等于渡河方案指定的商人和随从人数；
	         * 当小船返回时，要求对岸商人和随从人数分别大于等于渡河方案指定的商人和随从人数
	         * 
	         * @return
	         */
	        public boolean isValid() {
	            return this.direction == FORWARD ? status
	                    .greaterOrEqual(carryingSchema[idx]) : initStatus.minus(
	                    status).greaterOrEqual(carryingSchema[idx]);
	        }
	        /**
	         * 判断结点的状态是否有效，这是实际问题的一具体约束，即要求结点状态中商人人数或者为0，或者不小于随从人数。
	         * @return
	         */
	        public boolean isSecure() {

	            Dual next = this.nextStatus();
	            
	            return (next.m == 0 || next.m >= next.s)
	                    && (initStatus.m - next.m == 0 || initStatus.m - next.m >= initStatus.s
	                            - next.s);
	        }

	        public boolean isRepeat() {
	            return iscontains(path);
	        }

	        public boolean iscontains(List<Node> p) {
	            for (int i = 0; i < p.size(); i++) {
	                Node o = p.get(i);
	                if (this.status.m == o.status.m
	                        && (this.status.s == o.status.s)
	                        && this.direction == o.direction && this.idx == o.idx) {
	                    System.out.println("equal");
	                    return true;
	                }
	            }
	            if(p.size()>=1&&this.idx==p.get(p.size()-1).idx){
	                return true;
	            }
	            return false;
	        }

	        public boolean isEnd() {
	            Dual next = this.nextStatus();
	            return next.m==endStatus.m&&next.s==endStatus.s;
	        }
	    }

	
}
