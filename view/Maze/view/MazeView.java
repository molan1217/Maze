package Maze.view;
import javax.imageio.ImageIO;
import javax.swing.*;

import Maze.data.MazeByFile;
import Maze.data.MazeMaker;
import Maze.data.Point;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
public class MazeView extends JPanel implements ActionListener {
    public Point [][] point;        //需要视图的迷宫
    Rectangle2D [][] block;        //迷宫中路或墙的视图
    int width = 22;                //路或墙的宽度
    int height =22;
    int leftX = 80;                //起点偏移坐标
    int leftY = 50;
    static PersonInMaze peopleWalker;     //走迷宫者
    HandleMove  handleMove;        //负责处理行走
    JButton again;
    JButton Set_view;
    JButton Select_pic;
    JButton Select_maze;
    Toolkit tool;
	JRadioButton jradio_choseprint;
	JRadioButton jradio_choseprintpic;
	String file_maze ="文件/迷宫.txt";
    String pic_wall ="文件/wall.png";
    String pic_avenue ="文件/avenue.png";
    String pic_exit ="文件/exit.png";
    
    Bool bool;//判断选择的文件是否为图片或文本
    
    Color[] string = new Color[]{Color.pink,Color.blue,Color.pink,Color.white,Color.green};//0 1 山 2 墙边框 3 4 出口
    
    public MazeView(Point[][] p){
    	 tool=getToolkit();
       point = p; 
       peopleWalker = new PersonInMaze();
       handleMove = new HandleMove();
       initPointXY();  //依据视图重新设置点的坐标
       handleMove.setMazePoint(point);
       block = new Rectangle2D[point.length][point[0].length];
       setLayout(null);
       //JPanel pNorth = new JPanel();
       add(handleMove);
       add(peopleWalker);
      
       handleMove.setSize(120,30);
       handleMove.setLocation(leftX,leftY/3);
       peopleWalker.setSize(width,height);
       peopleWalker.setAtMazePoint(getEnterPoint(point));
       peopleWalker.setLocation(getEnterPoint(point).getX(),getEnterPoint(point).getY()); 
       initView();
       registerListener();
       creat_button();

   }
   public void creat_button() {
       again = new JButton("重走");
       again.setSize(80,30);
       again.setLocation(1,leftY);
       again.addActionListener(this);
       add(again);
       
       jradio_choseprint = new JRadioButton("颜色绘图");
       jradio_choseprintpic = new JRadioButton("图片绘图");
       ButtonGroup group=new ButtonGroup();
       group.add(jradio_choseprint);
       group.add(jradio_choseprintpic);
       jradio_choseprint.setLocation(500, 40);
       jradio_choseprint.setSize(100,30);
       jradio_choseprintpic.setLocation(500, 70);
       jradio_choseprintpic.setSize(100,30);
       jradio_choseprint.setSelected(true);
       add(jradio_choseprint);
       add(jradio_choseprintpic);
       JButton checkButton = new JButton("确定修改");
       checkButton.setSize(100, 30);
       checkButton.setLocation(600,50);
       checkButton.addActionListener(this);
       add(checkButton);
       
       Set_view = new JButton("修改外观");
       Set_view.setSize(100, 30);
       Set_view.setLocation(500,100);
       Set_view.addActionListener(this);
       add(Set_view);
       
       Select_pic = new JButton("修改头像");
       Select_pic.setSize(100, 30);
       Select_pic.setLocation(500,150);
       Select_pic.addActionListener(this);
       add(Select_pic);
       
       Select_maze = new JButton("修改迷宫");
       Select_maze.setSize(100, 30);
       Select_maze.setLocation(500,200);
       Select_maze.addActionListener(this);
       add(Select_maze);
       
}
   public void initPointXY(){ //依据视图重新设置点的坐标
       for(int i=0;i<point.length;i++) {
         for(int j=0;j<point[i].length;j++){
           point[i][j].setX(j*width+leftX);   //组件坐标系原点是左上角，向右是x-轴，向下是y-轴
           point[i][j].setY(i*height+leftY);    
         }
       }
       peopleWalker.setAtMazePoint(getEnterPoint(point));
       peopleWalker.setLocation(getEnterPoint(point).getX(),getEnterPoint(point).getY()); 
       handleMove.setMazePoint(point); 
   }
   public void initView() {
      for(int i = 0;i<point.length;i++){
           for(int j = 0;j<point[i].length;j++) {
             int x = point[i][j].getX();
             int y = point[i][j].getY();
             //绘制的矩形的左上角坐标是（x*width+leftX,y*height+leftY）
             block[i][j]=new Rectangle2D.Double(x,y,width,height);
           }
      }
      repaint();
      handleMove.showTime.setText("0");
      peopleWalker.requestFocusInWindow();
      validate();
   }
   public void registerListener(){
      peopleWalker.addKeyListener(handleMove);
      handleMove.setMazePoint(point);
   }
   public void paintComponent(Graphics g){
       super.paintComponent(g);
       Graphics2D g_2d = (Graphics2D)g;
       BasicStroke bs=
                   new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
       Image image;
       if (jradio_choseprint.isSelected()) {
    	   for(int i = 0;i<point.length;i++){
               for(int j = 0;j<point[i].length;j++) {
                   if(!point[i][j].isRoad()){  
                       g_2d.setColor(string[2]);
                       g_2d.setStroke(bs); 
                       g_2d.draw(block[i][j]);      
                   }
                   else {
                       g_2d.setColor(string[0]);
                       g_2d.fill(block[i][j]);
                       g_2d.setColor(string[1]);
                       g_2d.setStroke(bs);
                       g_2d.draw(block[i][j]); 

                       if(point[i][j].isOut()){
                          g_2d.setColor(string[3]);//出口框填充颜色
                          g_2d.fill(block[i][j]);
                          g_2d.setColor(string[4]);//出口字体颜色
                          g_2d.setFont(new Font("",Font.BOLD,10));
                          g_2d.drawString("出口",point[i][j].getX(),point[i][j].getY()+4*height/5);
                       }
                   }  
               }
           }
	}else {
		for(int i = 0;i<point.length;i++){
	           for(int j = 0;j<point[i].length;j++) {
	               if(!point[i][j].isRoad()){  
	                   
	            	   image=tool.getImage(pic_wall);  
	                   g_2d.drawImage(image, point[i][j].getX(), point[i][j].getY(),width,height,this);
	                  
	               }
	               else {
	               
	                    image=tool.getImage(pic_avenue);  
	                   
	                  g_2d.drawImage(image, point[i][j].getX(), point[i][j].getY(),width,height,this);
	                
	                   if(point[i][j].isOut()){
	                	  image=tool.getImage(pic_exit); 
	                      g_2d.drawImage(image, point[i][j].getX(), point[i][j].getY(),width,height,this);
	  	                
	                   }
	               }  
	           }
	       }
	}
       
       int x = point[0][0].getX();
       int y = point[0][0].getY();
       x= x*width+leftX;
       y= y*height+leftY;
       Rectangle2D rect = 
       new Rectangle2D.Double(x,y,width*point[0].length,height*point.length);
       g_2d.draw(rect);
       g_2d.setColor(Color.black);;//字体颜色
       String mess2 ="用鼠标单击走迷宫者，然后按方向键行走";
       int toLeftDis =handleMove.getBounds().width+leftX;
       g_2d.setFont(new Font("",Font.PLAIN,14));
       
       g_2d.drawString(mess2,leftX,(point.length+1)*height+leftY);
   }
   public Point getEnterPoint(Point [][] point){ //得到入口点
       Point p =null;
       for(int i = 0;i<point.length;i++){
           for(int j = 0;j<point[i].length;j++) {
               if(point[i][j].isEnter()){
                    p = point[i][j];
                    break;
               }
           }
       }
       return p;
   }
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==again) {
			again(file_maze);
			
		}
		else if(e.getSource()==Set_view){
			if (jradio_choseprint.isSelected()) {
				create_chosemenu();
			}else {
				create_choseJButton();
			}

		}else if (e.getSource() == Select_pic) {
			//根据所选图标重绘
			peopleWalker.repaint_pic(createUI());

		}else if (e.getSource() == Select_maze) {
			//根据所选图标重绘
			file_maze = createUI();
			again(file_maze);
			       
		}
		repaint();
   }
	public void create_choseJButton() {
		JFrame f=new JFrame("修改图片");
		f.setSize(300, 300);
	    f.setLocation(300, 200);
	    f.setLayout(null);
	    JButton wall = new JButton("墙");
	    wall.setLocation(75, 0);
	    wall.setSize(150, 50);
	    JButton road = new JButton("路");
	    road.setSize(150, 50);
	    road.setLocation(75, 100);
	    JButton exit = new JButton("出口");
	    exit.setLocation(75, 200);
	    exit.setSize(150, 50);
	    f.add(wall);
	    f.add(road);
	    f.add(exit);
	    f.setVisible(true);
	    wall.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		        String file = createUI();
				bool = new Bool(file);
				if (bool.isImage()) {
					pic_wall = file;
					repaint();
				}
				
			}
		});
	    
	    road.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				   String file = createUI();
					bool = new Bool(file);
					if (bool.isImage()) {
						pic_avenue = file;
						repaint();
					}
			}
		});

	    exit.addActionListener(new ActionListener() {
	
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		// TODO Auto-generated method stub
	    		 String file = createUI();
					bool = new Bool(pic_wall);
					if (bool.isImage()) {
						pic_exit = file;
						repaint();
					}
			}
	    });
	}
	public void create_chosemenu() {
    	JFrame f=new JFrame("修改颜色");
        f.setSize(200, 150);
        f.setLocation(300, 200);
        f.setLayout(new FlowLayout());
        JMenuBar menuBar = new JMenuBar();
        f.setJMenuBar(menuBar);
        JMenu menu = new JMenu("墙");// 创建菜单对象
        menuBar.add(menu);// 将菜单对象添加到菜单栏对象中
        
        
        JMenu Wall_2 = new JMenu("边框");// 创建菜单的子菜单对象
        menu.add(Wall_2);// 将子菜单对象添加到上级菜单对象中
        
        JMenuItem Wall_2_1 = new JMenuItem("绿色");// 创建子菜单的菜单项对象
        Wall_2_1.setActionCommand("Wall_rim_green");
        Wall_2_1.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        JMenuItem Wall_2_2 = new JMenuItem("蓝色");// 创建子菜单的菜单项对象
        Wall_2_2.setActionCommand("Wall_rim_blue");
        Wall_2_2.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        JMenuItem Wall_2_3 = new JMenuItem("粉色");// 创建子菜单的菜单项对象
        Wall_2_3.setActionCommand("Wall_rim_pink");
        Wall_2_3.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        Wall_2.add(Wall_2_1);// 将子菜单的菜单项对象添加到子菜单对象中
        Wall_2.add(Wall_2_2);
        Wall_2.add(Wall_2_3);
        
        JMenu Moun = new JMenu("路");// 创建菜单对象
        menuBar.add(Moun);// 将菜单对象添加到菜单栏对象中
        
        JMenu Moun_1 = new JMenu("填充");// 创建菜单项对象
        //Wall_1.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        Moun.add(Moun_1);// 将菜单项对象添加到菜单对象中
        
        JMenuItem Moun_1_1 = new JMenuItem("绿色");// 创建子菜单的菜单项对象
        Moun_1_1.setActionCommand("Moun_bg_green");
        Moun_1_1.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        JMenuItem Moun_1_2 = new JMenuItem("蓝色");// 创建子菜单的菜单项对象
        Moun_1_2.setActionCommand("Moun_bg_blue");
        Moun_1_2.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        JMenuItem Moun_1_3 = new JMenuItem("粉色");// 创建子菜单的菜单项对象
        Moun_1_3.setActionCommand("Moun_bg_pink");
        Moun_1_3.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        Moun_1.add(Moun_1_1);// 将子菜单的菜单项对象添加到子菜单对象中
        Moun_1.add(Moun_1_2);
        Moun_1.add(Moun_1_3);
        
        
        JMenu Moun_2 = new JMenu("边框");// 创建菜单的子菜单对象
        Moun.add(Moun_2);// 将子菜单对象添加到上级菜单对象中
        
        JMenuItem Moun_2_1 = new JMenuItem("绿色");// 创建子菜单的菜单项对象
        Moun_2_1.setActionCommand("Moun_rim_green");
        Moun_2_1.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        JMenuItem Moun_2_2 = new JMenuItem("蓝色");// 创建子菜单的菜单项对象
        Moun_2_2.setActionCommand("Moun_rim_blue");
        Moun_2_2.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        JMenuItem Moun_2_3 = new JMenuItem("粉色");// 创建子菜单的菜单项对象
        Moun_2_3.setActionCommand("Moun_rim_pink");
        Moun_2_3.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        Moun_2.add(Moun_2_1);// 将子菜单的菜单项对象添加到子菜单对象中
        Moun_2.add(Moun_2_2);
        Moun_2.add(Moun_2_3);
        
        JMenu Exit = new JMenu("出口");// 创建菜单对象
        menuBar.add(Exit);// 将菜单对象添加到菜单栏对象中
        
        JMenu Exit_1 = new JMenu("填充");// 创建菜单项对象
        //Wall_1.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        Exit.add(Exit_1);// 将菜单项对象添加到菜单对象中
        
        JMenuItem Exit_1_1 = new JMenuItem("绿色");// 创建子菜单的菜单项对象
        Exit_1_1.setActionCommand("exit_bg_green");
        Exit_1_1.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        JMenuItem Exit_1_2 = new JMenuItem("蓝色");// 创建子菜单的菜单项对象
        Exit_1_2.setActionCommand("exit_bg_blue");
        Exit_1_2.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        JMenuItem Exit_1_3 = new JMenuItem("粉色");// 创建子菜单的菜单项对象
        Exit_1_3.setActionCommand("exit_bg_pink");
        Exit_1_3.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        Exit_1.add(Exit_1_1);// 将子菜单的菜单项对象添加到子菜单对象中
        Exit_1.add(Exit_1_2);
        Exit_1.add(Exit_1_3);
        
        
        JMenu Exit_2 = new JMenu("字体");// 创建菜单的子菜单对象
        Exit.add(Exit_2);// 将子菜单对象添加到上级菜单对象中
        
        JMenuItem Exit_2_1 = new JMenuItem("绿色");// 创建子菜单的菜单项对象
        Exit_2_1.setActionCommand("exit_rim_green");
        Exit_2_1.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        JMenuItem Exit_2_2 = new JMenuItem("蓝色");// 创建子菜单的菜单项对象
        Exit_2_2.setActionCommand("exit_rim_blue");
        Exit_2_2.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        JMenuItem Exit_2_3 = new JMenuItem("粉色");// 创建子菜单的菜单项对象
        Exit_2_3.setActionCommand("exit_rim_pink");
        Exit_2_3.addActionListener(new ItemListener());// 为菜单项添加事件监听器
        Exit_2.add(Exit_2_1);// 将子菜单的菜单项对象添加到子菜单对象中
        Exit_2.add(Exit_2_2);
        Exit_2.add(Exit_2_3);
        
        f.setVisible(true);
	}
	public void again(String a) {
		 bool = new Bool(a);
		if (bool.isText()) {
			int m =point.length;
			int n =point[0].length;
			MazeMaker mazeMaker = new MazeByFile(new File(a));
			point= mazeMaker.initMaze();
			initPointXY();   
			initView();
			handleMove.recordTime.stop();
			handleMove.spendTime = 0;
			handleMove.showTime.setText("0");
			handleMove.isLeave = false;
			repaint();
		}

	}
    private  String createUI() {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);
        File file = null;
        final JLabel label = new JLabel();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
               file = fileChooser.getSelectedFile();

         } else {
                label.setText("打开命令取消");
         }
            
        panel.add(label);
        return file.getParent()+ "\\"+ file.getName();
    }
    
    public void itemStateChanged(ItemEvent e) {
    	// TODO Auto-generated method stub
    	repaint();
    	}
    private class ItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String aString =  e.getActionCommand();
            
            switch (aString) {
			case "Wall_rim_green":
				string[2] = Color.green;
				break;
			case "Wall_rim_blue":
				string[2] = Color.blue;
				break;
			case "Wall_rim_pink":
				string[2] = Color.pink;
				break;
			case "Moun_bg_green":
				string[0] = Color.green;
				break;
			case "Moun_bg_blue":
				string[0] = Color.blue;
				break;
			case "Moun_bg_pink":
				string[0] = Color.pink;
				break;
			case "Moun_rim_green":
				string[1] = Color.green;
				break;
			case "Moun_rim_blue":
				string[1] = Color.blue;
				break;
			case "Moun_rim_pink":
				string[1] = Color.pink;
				break;

			case "exit_rim_green":
				string[4] = Color.green;
				break;
			case "exit_rim_blue":
				string[4] = Color.blue;
				break;
			case "exit_rim_pink":
				string[4] = Color.pink;
				break;

			case "exit_bg_green":
				string[3] = Color.green;
				break;
			case "exit_bg_blue":
				string[3] = Color.blue;
				break;
			case "exit_bg_pink":
				string[3] = Color.pink;
				break;

			default:
				break;
			}
            repaint();
        }
    }
}
