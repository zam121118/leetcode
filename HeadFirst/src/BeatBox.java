//BeatBox程序表，带有启动、停止和改变节奏的按钮
//复杂了一些，增加存储创作的曲子和恢复并播放已创作曲子的功能
import javax.swing.*;
import javax.sound.midi.*;

import java.awt.Event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;//字符串标记生成器、随机数生成器和位数组

public class BeatBox implements Serializable{

	JPanel mainPanel;
	ArrayList<JCheckBox> checkboxList;
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	JFrame theFrame;
	
	String[] instrumentNames={"Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare",
			"Crash Cymbal","Hand Clap","High Tom","Hi Bongo","Maracas","Whistle","Low Conga",
			"Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"};
	
	int[] instruments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BeatBox().buildGUI();
	}
	
	public void buildGUI(){
		
		theFrame=new JFrame("Cyber BeatBox");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout=new BorderLayout();//布局管理器BorderLayout
		JPanel background=new JPanel(layout);//将面板的布局管理器换成BorderLayout？？为何改变layout：变成边框布局，分为5个区
		//JComponent的setBorder用来设置该组件的边框
		//如果希望在标准的 Swing 组件而不是 JPanel 或 JLabel 上设置边框时，建议将组件放入 JPanel 中，并在 JPanel 上设置边框
		//createEmptyBorder(int top, int left, int bottom, int right) 
        //创建一个占用空间但没有绘制的空边框，指定了顶线、底线、左边框线和右边框线的宽度
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));//
		
		checkboxList=new ArrayList<JCheckBox>();
		Box buttonBox=new Box(BoxLayout.Y_AXIS);//创建一个沿Y坐标轴显示其组件的 Box盒子，装button的Box
		
		JButton start=new JButton("Start");
		start.addActionListener(new MyStartListener());//监听Start按钮有没有被点击
		buttonBox.add(start);//将start按钮加入到Box盒子
		
		JButton stop=new JButton("Stop");
		stop.addActionListener(new MyStopListener());//监听Stop按钮有没有被点击
		buttonBox.add(stop);//将stop按钮加入到Box盒子
		
		JButton upTempo=new JButton("Tempo Up");
		upTempo.addActionListener(new MyUpTempoListener());//监听UpTempo按钮有没有被点击
		buttonBox.add(upTempo);//将upTempo按钮加入到Box盒子
		
		JButton downTempo=new JButton("Tempo Down");
		downTempo.addActionListener(new MyDownTempoListener());//监听downTempo按钮有没有被点击
		buttonBox.add(downTempo);//将downTempo按钮加入到Box盒子
		
		JButton restore=new JButton("Restore");
		stop.addActionListener(new MyReadInListener());
		buttonBox.add(restore);
		
		JButton save=new JButton("SerializeIt");
		stop.addActionListener(new MySendListener());
		buttonBox.add(save);
		
		Box nameBox=new Box(BoxLayout.Y_AXIS);//沿Y轴创建一个装有所有乐器名称Label的Box盒子
		for(int i=0;i<16;i++){
			nameBox.add(new Label(instrumentNames[i]));//Y轴依次添加乐器的名称
		}
		
		background.add(BorderLayout.EAST,buttonBox);//将装button的盒子放在背景Jpanel的东边
		background.add(BorderLayout.WEST,nameBox);//将装label的盒子放在背景Jpanel的西边
		
		theFrame.getContentPane().add(background);//将面板放置在框架上
		
		//GridLayout(int rows, int cols)创建具有指定行数和列数的网格布局
		//GridLayout 类是一个布局处理器，它以矩形网格形式对容器的组件进行布置
		GridLayout grid=new GridLayout(16,16);//???
		grid.setVgap(1);//将组件之间的垂直间距设置为指定值
		grid.setHgap(2);//将组件之间的水平间距设置为指定值
		
		mainPanel=new JPanel(grid);//将grid网格加到mainPanel上
		background.add(BorderLayout.CENTER,mainPanel);//将mainPanel加到背景Panel上
		
		for(int i=0;i<256;i++){ //添加256个复选框到mainpanel上
			JCheckBox c=new JCheckBox();
			c.setSelected(false);//初始化全部为未选中
			checkboxList.add(c);
			mainPanel.add(c);
		}

		setUpMidi();//设置音乐设备
		
		theFrame.setBounds(50,50,300,300);
		theFrame.pack();
		theFrame.setVisible(true);
	}
	
	public void setUpMidi(){
		
		try{
			sequencer=MidiSystem.getSequencer();//获取默认设备,并打开
			sequencer.open();
			sequence=new Sequence(Sequence.PPQ,4);
			track=sequence.createTrack();
			sequencer.setTempoInBPM(120);//设置速度，以每分钟的拍数为单位。
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void buildTrackAndStart(){//创建track并开始播放
		int[] trackList=null;//
		
		sequence.deleteTrack(track);//清空Sequence中所有的轨道
		track=sequence.createTrack();//重新创建轨道
		
		for(int i=0;i<16;i++){//循环16行
			trackList =new int[16];//创建16个轨道
			
			int key=instruments[i];//
			
			for(int j=0;j<16;j++){//每行循环16列
				
				JCheckBox jc=(JCheckBox) checkboxList.get(j+(16*i));
				if (jc.isSelected()){
					trackList[j]=key;//查找所有复选框，并将当前行的乐器传入其所在的列轨道
				} else{
					trackList[j]=0;
				}
			}
			
			makeTracks(trackList);//对每行设置每个列的轨道的note on和note off
			track.add(makeEvent(176,1,127,0,16));//
		}
		
		track.add(makeEvent(192,9,1,0,15));
		
		try{
			
			sequencer.setSequence(sequence);
			//设置循环回放的重复次数。LOOP_CONTINUOUSLY循环应无限继续而不是在执行完特定次数的循环后停止
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();//开始播放
			sequencer.setTempoInBPM(120);//设置速度，每分钟120拍
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//当监听到start按钮按下，调用MystartListener方法
	public class MyStartListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			buildTrackAndStart();
		}
	}
	
	//当stop按钮的调用方法
	public class MyStopListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			sequencer.stop();
		}
	}

	public class MyUpTempoListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			float tempoFactor=sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor*1.03));
		}
	}
	
	public class MyDownTempoListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			float tempoFactor=sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor*.97));
		}
	}
	
	
	
	
	//内部类；存储复选框状态的按钮监听方法的类
	public class MySendListener implements ActionListener{
		
		public void actionPerformed(ActionEvent a){
			boolean[] checkboxState=new boolean[256];//用来保存复选框状态的数组
			for(int i=0;i<256;i++){
				JCheckBox check=(JCheckBox) checkboxList.get(i);//逐个取得状态并加到数组中
				if(check.isSelected()){//选中为true
					checkboxState[i]=true;
				}
			}
			
			//java.io.File类实例化的对象代表磁盘上的文件或目录的路径名称，如：/Users/Kathy/GameFile.txt
			//但它并不能读取或代表文件中的数据（就像信封上的地址不是房子一样）
			try{
				FileOutputStream fileStream=new FileOutputStream(new File("Checkbox.ser"));
				ObjectOutputStream os=new ObjectOutputStream(fileStream);
				os.writeObject(checkboxState);//将boolean数组序列化
				os.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	
	//内部类：还原BeatBox节奏，当用户按下restore按钮时
	public class MyReadInListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			boolean[] checkboxState=null;
			try{
				FileInputStream fileIn=new FileInputStream(new File("Checkbox.ser"));
				ObjectInputStream is=new ObjectInputStream(fileIn);
				checkboxState=(boolean[]) is.readObject();
			} catch(Exception ex){
				ex.printStackTrace();
			}
			
			
			for(int i=0;i<256;i++){
				JCheckBox check=(JCheckBox) checkboxList.get(i);
				if(checkboxState[i]){
					check.setSelected(true);
				}else{
					check.setSelected(false);
				}
			}
			sequencer.stop();
			buildTrackAndStart();
		}
	}
	
	
	
	
	
	//将MidiEvent时间设置到track中
	public void makeTracks(int[] list){
		for(int i=0;i<16;i++){
			int key=list[i];
			
			if(key!=0){
				track.add(makeEvent(144,9,key,100,i));
				track.add(makeEvent(128,9,key,100,i+1));
			}
		}
	}
	
	
	//设置shortMesage制作成MidiEvent
	public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
		MidiEvent event=null;
		try{
			ShortMessage a=new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event=new MidiEvent(a,tick);
		} catch(Exception e){
			e.printStackTrace();
		}
		return event;
	}

}
