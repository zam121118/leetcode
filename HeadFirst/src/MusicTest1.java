import javax.sound.midi.*;
public class MusicTest1 {
	public void paly(){
		try{
			//查看JAVA API发现该静态方法会抛出MidiUnavailableExcepton异常
			Sequencer sequencer=MidiSystem.getSequencer();//把风险程序放到try块
			System.out.println("we got a swquencer");
		} catch(MidiUnavailableException ex){ //用catch块摆放异常的处理程序
		System.out.println("Bummer");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MusicTest1 mt=new MusicTest1();
		mt.paly();
	}

}
