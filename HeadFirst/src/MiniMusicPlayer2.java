//注册并监听事件，但没有图形。从命令栏对每一拍输出一个信息。
//确保对每个note on事件都有对应的ControllerEvent事件会在同一拍上面触发，把它加到track上。
import javax.sound.midi.*;

public class MiniMusicPlayer2 implements ControllerEventListener{//要监听ControllerEvent,必须实现这个接口

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiniMusicPlayer2 mini=new MiniMusicPlayer2();
		mini.go();
	}
	public void go(){
		
		try{
			//创建并打开队列
			Sequencer player=MidiSystem.getSequencer();
			player.open();
			//创建队列并track
			Sequence seq=new Sequence(Sequence.PPQ,4);
			Track track=seq.createTrack();
			
			//向sequencer注册事件，注册的方法取用代表想要监听的时间的int数组，我们只需要127.
			//javax.sound.midi类sequence方法addControllerEventListener(ControllerEventListener listener, int[] controllers)
			// 注册一个控件事件侦听器，以便在 sequencer 处理所请求的一种或多种类型的控制更改事件时接收通知
			// controllers 参数使用一个 MIDI 号的数组，与侦听器将不再接收其更改通知的控件对应
			int[] eventIWant={127};
			player.addControllerEventListener(this, eventIWant);
			
			
			//创建一堆连续的音符事件
			for(int i=5;i<61;i+=4){
				//调用makeEvent()来产生信息和事件然后把它们加到track上
				track.add(makeEvent(144,1,i,100,i));
			//插入事件编号为127的自定义ControllerEvent(176),不会做任何事情，只是让我们知道有音符被播放，因为它的tick跟note on同时进行的
				track.add(makeEvent(176,1,127,0,i));
				
				track.add(makeEvent(128,1,i,100,i+2));

			}
			//开始播放
			player.setSequence(seq);
			player.setTempoInBPM(220);
			player.start();
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void controlChange(ShortMessage event){//监听到事件时在命令行打印出字符串的事件处理程序
		System.out.println("la");
	}
	
	
	
	public static MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
		MidiEvent event=null;
		try{
			ShortMessage a=new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event=new MidiEvent(a,tick);
		} catch (Exception ex){}
		return event;
	}

}
