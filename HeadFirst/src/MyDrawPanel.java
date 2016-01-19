import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;

public class MyDrawPanel extends JPanel {//创建JPanel的子类
		
//	public void paintComponent(Graphics g){//参数要有系统电泳
//		
//		g.setColor(Color.orange);
//			
//		g.fillRect(20,50,100,100);
//	}


// 显示JPEG
// public void paintComponent(Graphics g){
//  	Image image=new ImageIcon("catzilla.jpg").getImage();
//   g.drawImage(image,3,4,this);//
//  }
//
//	
//黑色背景画上随机色彩的圆圈
//  public void paintComponent(Graphics g){
//  	g.fillRect(0,0,this.getWidth(),this.getHeight());
//  	
//  	int red=(int)(Math.random()*255);
//    int green=(int)(Math.random()*255);
//  	int blue=(int)(Math.random()*255);
//
//	Color randomColor=new Color(red,green,blue);
//	g.setColor(randomColor);
//	g.fillOval(70,70,100,100);//填满参数指定的椭圆型区域
//  }
//
//	
//	
//随机渐变颜色
  public void paintComponent(Graphics g){
  	
  	Graphics2D g2d=(Graphics2D) g;
  
   int red=(int)(Math.random()*255);
   int green=(int)(Math.random()*255);
   int blue=(int)(Math.random()*255);

	Color startColor=new Color(red,green,blue);
	
	red=(int)(Math.random()*255);
    green=(int)(Math.random()*255);
  	blue=(int)(Math.random()*255);

	Color endColor=new Color(red,green,blue);
   
   GradientPaint gradient=new GradientPaint(70,70,startColor,150,150,endColor);
   g2d.setPaint(gradient);
   g2d.fillOval(70,70,50,50);
  }

}
