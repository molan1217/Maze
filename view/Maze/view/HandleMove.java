package Maze.view;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import Maze.data.Point;
import Maze.view.PersonInMaze;
public class HandleMove extends JPanel implements KeyListener,ActionListener {  
    Point [][] p;
    int spendTime=0;
    javax.swing.Timer recordTime; //计时器
    JTextField showTime;     
    Toolkit tool;            //用来播放嘟嘟声音
    PersonInMaze person;
    boolean isLeave = false; //判断是否已经离开出口
    int out_i,out_j;         //存放出口点位置索引
    HandleMove(){
       recordTime=new javax.swing.Timer(1000,this);
       showTime=new JTextField("0",5);
       tool=getToolkit(); 
       showTime.setEditable(false);
       showTime.setHorizontalAlignment(JTextField.CENTER);
       add(new JLabel("计时器:"));
       add(showTime);
       setBackground(Color.cyan);
    } 
    public void setMazePoint(Point [][] point){
        p=point;
    }
    public void initSpendTime(){
       recordTime.stop();
       spendTime=0;
       showTime.setText(null);
    }
    public void keyPressed(KeyEvent e){
        recordTime.start();
        person=(PersonInMaze)e.getSource();
        int m=-1,n=-1;
        Point startPoint=person.getAtMazePoint();
        for(int i=0;i<p.length;i++){
            for(int j=0;j<p[i].length;j++){
               if(startPoint.equals(p[i][j])){
                  m=i;
                  n=j;
                  break;
               }
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){
           int k=Math.max(m-1,0);
           if(p[k][n].isRoad()){
             tool.beep(); //发出嘟的一声
             person.setAtMazePoint(p[k][n]);
             person.setLocation(p[k][n].getX(),p[k][n].getY());
           }
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN){
           int k=Math.min(m+1,p.length-1);
           if(p[k][n].isRoad()) {
             tool.beep();
             person.setAtMazePoint(p[k][n]);
             person.setLocation(p[k][n].getX(),p[k][n].getY());
           }
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT){
           int k=Math.max(n-1,0);
           if(p[m][k].isRoad()){
             tool.beep();
             person.setAtMazePoint(p[m][k]);
             person.setLocation(p[m][k].getX(),p[m][k].getY());
           }
        } 
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
           int k=Math.min(n+1,p[0].length-1);
           if(p[m][k].isRoad()){
             tool.beep();
             person.setAtMazePoint(p[m][k]);
             person.setLocation(p[m][k].getX(),p[m][k].getY());
           }
        }
    } 
    public void actionPerformed(ActionEvent e){
          spendTime++;
          showTime.setText("用时:"+spendTime+"秒");
    } 
    public void keyReleased(KeyEvent e){
        if(isLeave == true)
           return;
        PersonInMaze person=(PersonInMaze)e.getSource();
        int m=-1,n=-1;
        Point endPoint=person.getAtMazePoint();
        if(endPoint.isOut()){
           recordTime.stop();
           JOptionPane.showMessageDialog(this,"您可以离开出口","消息框",
                                       JOptionPane.INFORMATION_MESSAGE );
           int x=p[p.length-1][p[0].length-1].getX()+person.getBounds().width;
           int y=p[p.length-1][p[0].length-1].getY()+person.getBounds().height;
           person.setLocation(x,y);
           isLeave = true;

       }
    }

    public void keyTyped(KeyEvent e) {}
}