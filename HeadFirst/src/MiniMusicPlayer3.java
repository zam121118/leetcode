//使用Java随机产生的圆形来跟着节奏起舞

import javax.sound.midi.*;
//import java.io.*;
import javax.swing.*;
import java.awt.*;
public class MiniMusicPlayer3 {

	static JFrame f=new JFrame("My Frist Music Video"); //创建框架
	static MyDrawPanel ml;  //创建绘图面板
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiniMusicPlayer3 mini=new MiniMusicPlayer3();
		mini.go();
	}
	
	public void setUpGui(){
		ml=new MyDrawPanel();
		f.setContentPane(ml);// 设置 contentPane 属性
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(30,30,300,300);//设置窗口位置
		f.setVisible(true);//显示可见
	}
	
	public void go(){
		setUpGui();//调用方法
		
		try{
			Sequencer player=MidiSystem.getSequencer();
			player.open();
			//在Sequencer对象注册监听者
			//注册MyDrawPanel控件的事件监听器，当传入控制参数MIDI号127，就调用监听到事件的应对方法
			player.addControllerEventListener(ml,new int[] {127});//其中Controller参数127代表 复音模式
			
			Sequence seq=new Sequence(Sequence.PPQ,4);
			Track track=seq.createTrack();
			
			int r=0;
			for (int i=0;i<60;i+=4){//连续发出15个钢琴按键声
				
				r=(int)((Math.random()*100)+1);//r是随机生成的音符
				
				track.add(makeEvent(144,1,r,100,i));//此处最后一个参数i是信息的时间戳
				track.add(makeEvent(176,1,127,0,i));//即176与144同时执行
				track.add(makeEvent(128,1,r,100,i+2));//结束128比144加2,即每个音长为2
			}
			
			player.setSequence(seq);//设置Sequence
			player.setTempoInBPM(120); //setTempoInBPM(float bpm)设置速度，以每分钟的拍数为单位。
			player.start();//开始播放
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){//设置ShortMessage并生成MidiEvent
		MidiEvent event=null;
		try{
			ShortMessage a=new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event=new MidiEvent(a,tick);//tick为音轨的长度
		}catch(Exception e){}
		
		return event;
	}
	
	
	class MyDrawPanel extends JPanel implements ControllerEventListener{
		
		boolean msg=false;
		
		//当 Sequencer遇到并处理了一个此侦听器需要的控制更改事件时调用。传入的事件是一个 ShortMessage，其第一个数据字节指示控件号，第二个数据字节是为控件设置的值
		public void controlChange(ShortMessage event){//
			msg=true;
			//f.repaint();//会自己调用paintComponent()
			repaint();//还是有区别的！
		}
		
		public void paintComponent(Graphics g){//伴随每个节拍，随机生成不同颜色、大小的的矩形
			if(msg){
				Graphics2D g2=(Graphics2D) g;
				 int r=(int)(Math.random()*250);
				 int gr=(int)(Math.random()*250);
				 int b=(int)(Math.random()*250);

				 g.setColor(new Color(r,gr,b));
				 
				 int ht=(int)((Math.random()*120)+10);
				 int width=(int)((Math.random()*120)+10);
				 
				 int x=(int)((Math.random()*40)+10);
				 int y=(int)((Math.random()*40)+10);

				 g.fillRect(x, y, width, ht);
			}
			
		}
	}

}
