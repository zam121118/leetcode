//BeatBox客户端最终版。能连接到MusicServer上以便传送和接收其他客户端的节拍样式
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.sound.midi.*;

import java.util.*;

public class BeatBoxFinal {

	JFrame theFrame;
	JPanel mainPanel;
	JList incomingList;//接收消息列表
	JTextField userMessage;
	ArrayList<JCheckBox> checkboxList;
	int nextNum;
	Vector<String> listVector=new Vector<String>();//Vector 类可以实现可增长的对象数组。与数组一样，它包含可以使用整数索引进行访问的组件。
	                                               //但是，Vector 的大小可以根据需要增大或缩小，以适应创建 Vector 后进行添加或移除项的操作。
	String userName;
	ObjectOutputStream out;
	ObjectInputStream in;
	HashMap<String,boolean[]> otherSeqsMap=new HashMap<String,boolean[]>();
	
	Sequencer sequencer;
	Sequence sequence;
	Sequence mySequence=null;
	Track track;
	
	
	String[] instrumentNames={"Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare",
			"Crash Cymbal","Hand Clap","High Tom","Hi Bongo","Maracas","Whistle","Low Conga",
			"Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"};
	
	int[] instruments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
	
	public void startUp(String name) {
		userName=name;
		//建立到服务器的连接
		try{
			Socket sock=new Socket("127.0.0.1",4242);
			out=new ObjectOutputStream(sock.getOutputStream());
			in=new ObjectInputStream(sock.getInputStream());
			Thread remote=new Thread(new RemoteReader());
			remote.start();
		}catch(Exception ex){
			System.err.println("couldn't connect - you'll have to play alone.");
		}
		setUpMidi();//设置音乐设备
		buildGUI();
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
		
		JButton sendIt=new JButton("sendIt");
		sendIt.addActionListener(new MySendListener());//监听downTempo按钮有没有被点击
		buttonBox.add(sendIt);
		
		userMessage=new JTextField();
		buttonBox.add(userMessage);
		//会显示收到信息的组件
		incomingList=new JList();
		incomingList.addListSelectionListener(new MyListSelectionListener());
		incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane theList=new JScrollPane(incomingList);
		buttonBox.add(theList);
		incomingList.setListData(listVector);//no data to start with
		
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
		
		theFrame.setBounds(50,50,300,300);
		theFrame.pack();//调整此窗口的大小，以适合其子组件的首选大小和布局
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
		ArrayList<Integer> trackList=null;//用来保存每一个轨道的乐器
		sequence.deleteTrack(track);//清空Sequence中所有的轨道
		track=sequence.createTrack();//重新创建轨道
		
		for(int i=0;i<16;i++){//循环16行
			trackList =new ArrayList<Integer>();//创建16个轨道
			
			for(int j=0;j<16;j++){//每行循环16列
				
				JCheckBox jc=(JCheckBox) checkboxList.get(j+(16*i));
				if (jc.isSelected()){
					int key=instruments[i];//查找所有复选框，并将当前行的乐器传入其所在的列轨道
					trackList.add(new Integer(key));
				} else{
					trackList.add(null);//该位置的轨道为空
				}
			}
			
			makeTracks(trackList);//对每行设置每个列的轨道的note on和note off
			//track.add(makeEvent(176,1,127,0,16));//
		}
		
		track.add(makeEvent(192,9,1,0,15));//so we always go to full 16 beats
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
		
		public class MySendListener implements ActionListener{
			public void actionPerformed(ActionEvent a){
				//一个存储checkboxes状态的arraylist
				boolean[] checkboxState=new boolean[256];
				for(int i=0;i<256;i++){
					JCheckBox check=(JCheckBox) checkboxList.get(i);
					if(check.isSelected()){
						checkboxState[i]=true;
					}
				}
				String messageToSend=null;
				try{
					out.writeObject(userName+nextNum++ +":"+userMessage.getText());
					out.writeObject(checkboxState);
				}catch(Exception ex){
					System.out.println("Sorry dude,Could not send it to the server.");
				}
				userMessage.setText("");
			}
		}
		
		//用户点选信息时会马上加载节拍样式并开始播放
		public class MyListSelectionListener implements ListSelectionListener{
			public void valueChanged(ListSelectionEvent le){
				if(!le.getValueIsAdjusting()){
					String selected=(String) incomingList.getSelectedValue();
					if(selected!=null){
						//now go to the map,and change the sequence
						boolean[] selectedState=(boolean[]) otherSeqsMap.get(selected);
						changeSequence(selectedState);
						sequencer.stop();
						buildTrackAndStart();
					}
				}
			}
		}
		
		
		public class RemoteReader implements Runnable{
			boolean[] checkboxState=null;
			String nameToShow=null;
			Object obj=null;
			public void run(){
				try{
					while((obj=in.readObject())!=null){
						System.out.println("got an object from server");
						System.out.println(obj.getClass());
						String nameToShow=(String) obj;
						checkboxState=(boolean[]) in.readObject();
						otherSeqsMap.put(nameToShow, checkboxState);
						listVector.add(nameToShow);
						incomingList.setListData(listVector);
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		
		public class MyPlayMineListener implements ActionListener{
			public void actionPerformed(ActionEvent a){
				if(mySequence!=null){
					sequence=mySequence;//restore to my original
				}
			}
		}
		
		public void changeSequence(boolean[] checkboxState){
			for(int i=0;i<256;i++){
				JCheckBox check=(JCheckBox) checkboxList.get(i);
				if(checkboxState[i]){
					check.setSelected(true);
				}else{
					check.setSelected(false);
				}
			}
		}
		
		
		
		
		//将MidiEvent时间设置到track中
		public void makeTracks(ArrayList list){
			Iterator it=list.iterator();
			for(int i=0;i<16;i++){
				Integer num=(Integer) it.next();
				if(num!=null){
					int numKey=num.intValue();
					track.add(makeEvent(144,9,numKey,100,i));
					track.add(makeEvent(128,9,numKey,100,i+1));
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

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BeatBoxFinal().startUp(args[0]);//args[0] is your user ID/screen name
	}

}
