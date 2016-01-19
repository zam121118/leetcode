package mechants_River;
/**
 * m 商人人数
 * s 随从 人数
 * dual 对象表示商人和随从的组成状态，也就是一次渡河方案。
 * add minus 状态变迁的计算
 * @author tina
 *
 */
public class Dual {
    int m, s;

    public Dual(int m, int s) {
        this.m = m;
        this.s = s;
    }

    Dual add(Dual e) {
        return new Dual(this.m + e.m, this.s + e.s);
    }

    Dual minus(Dual e) {
        return new Dual(this.m - e.m, this.s - e.s);
    }
    public boolean greaterOrEqual(Dual d){
        //System.out.println("is Valid"+this.m+" "+this.s+"----"+d.m+"  "+d.s);
        return (this.m>=d.m&&this.s>=d.s? true:false); 
    }
}
