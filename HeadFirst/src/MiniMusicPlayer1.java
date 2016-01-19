//如何使用静态的makeEvent()方法--15个攀升的音阶组成的队列
import javax.sound.midi.*;

public class MiniMusicPlayer1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			//创建并打开队列
			Sequencer player=MidiSystem.getSequencer();
			player.open();
			//创建队列并track
			Sequence seq=new Sequence(Sequence.PPQ,4);
			Track track=seq.createTrack();
			//创建一堆连续的音符事件
			for(int i=5;i<61;i+=4){
				//调用makeEvent()来产生信息和事件然后把它们加到track上
				track.add(makeEvent(144,1,i,100,i));
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
