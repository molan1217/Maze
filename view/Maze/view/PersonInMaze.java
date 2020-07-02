package Maze.view;
import javax.imageio.ImageIO;
import javax.swing.*;

import Maze.data.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class PersonInMaze extends JTextField{
   Point point;  //所在的点
   Toolkit tool;
   String pic ="文件/person.gif";
   Bool bool;
   PersonInMaze(){
      tool=getToolkit();
      setEditable(false);
      setBorder(null);
      setOpaque(false);
      setToolTipText("单击我,然后按键盘方向键");
     
      requestFocusInWindow();
   }
   
   public void setAtMazePoint(Point p){
      point = p;
   }
   public Point getAtMazePoint(){
      return point;
   }
   public void repaint_pic(String a) {
	   bool = new Bool(a);
	if (bool.isImage()) {
		pic = a;
		repaint();
		System.out.println("11");
	}else {
		
	}
	
}

   public void paintComponent(Graphics g){
      super.paintComponent(g);
      int w=getBounds().width;
      int h=getBounds().height; 
      Image image=tool.getImage(pic);  
      g.drawImage(image,0,0,w,h,this);
  } 
}