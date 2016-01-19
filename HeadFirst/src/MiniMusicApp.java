import javax.sound.midi.*;
public class MiniMusicApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiniMusicApp mini=new MiniMusicApp();
		mini.play();
	}
	public void play(){
		try{
			//取得Sequencer并将其打开
			Sequencer player=MidiSystem.getSequencer();
			player.open();
			
			//创建新的Sequence
			Sequence seq=new Sequence(Sequence.PPQ,4);
			
			//要求取得Track
			Track track=seq.createTrack();
			
			//对Track加入几个MidiEvent,注意setMessage()的参数，以及MidiEvent的constructor
			ShortMessage a=new ShortMessage();
			a.setMessage(144,1,41,100);//信息类型、频道、音符、音道
			MidiEvent noteOn=new MidiEvent(a,1);
			track.add(noteOn);
			
			ShortMessage b=new ShortMessage();//创建message
			b.setMessage(128,1,41,100);//置入指令
			MidiEvent noteOff=new MidiEvent(b,22);//用message创建MidiEvent,参数列表第二个数字控制音长
			track.add(noteOff);//将MidiEvent加到Track中
			/*Track带有全部的MidiEvent对象，Sequence会根据事件组织它们。然后Sequencer会根据此顺序来播放，同一时间可以执行多个操作*/
			
			//将Sequence送到Sequencer上
			player.setSequence(seq);
			//开始播放
			player.start();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

}
