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
    public Point [][] point;        //��Ҫ��ͼ���Թ�
    Rectangle2D [][] block;        //�Թ���·��ǽ����ͼ
    int width = 22;                //·��ǽ�Ŀ��
    int height =22;
    int leftX = 80;                //���ƫ������
    int leftY = 50;
    static PersonInMaze peopleWalker;     //���Թ���
    HandleMove  handleMove;        //����������
    JButton again;
    JButton Set_view;
    JButton Select_pic;
    JButton Select_maze;
    Toolkit tool;
	JRadioButton jradio_choseprint;
	JRadioButton jradio_choseprintpic;
	String file_maze ="�ļ�/�Թ�.txt";
    String pic_wall ="�ļ�/wall.png";
    String pic_avenue ="�ļ�/avenue.png";
    String pic_exit ="�ļ�/exit.png";
    
    Bool bool;//�ж�ѡ����ļ��Ƿ�ΪͼƬ���ı�
    
    Color[] string = new Color[]{Color.pink,Color.blue,Color.pink,Color.white,Color.green};//0 1 ɽ 2 ǽ�߿� 3 4 ����
    
    public MazeView(Point[][] p){
    	 tool=getToolkit();
       point = p; 
       peopleWalker = new PersonInMaze();
       handleMove = new HandleMove();
       initPointXY();  //������ͼ�������õ������
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
       again = new JButton("����");
       again.setSize(80,30);
       again.setLocation(1,leftY);
       again.addActionListener(this);
       add(again);
       
       jradio_choseprint = new JRadioButton("��ɫ��ͼ");
       jradio_choseprintpic = new JRadioButton("ͼƬ��ͼ");
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
       JButton checkButton = new JButton("ȷ���޸�");
       checkButton.setSize(100, 30);
       checkButton.setLocation(600,50);
       checkButton.addActionListener(this);
       add(checkButton);
       
       Set_view = new JButton("�޸����");
       Set_view.setSize(100, 30);
       Set_view.setLocation(500,100);
       Set_view.addActionListener(this);
       add(Set_view);
       
       Select_pic = new JButton("�޸�ͷ��");
       Select_pic.setSize(100, 30);
       Select_pic.setLocation(500,150);
       Select_pic.addActionListener(this);
       add(Select_pic);
       
       Select_maze = new JButton("�޸��Թ�");
       Select_maze.setSize(100, 30);
       Select_maze.setLocation(500,200);
       Select_maze.addActionListener(this);
       add(Select_maze);
       
}
   public void initPointXY(){ //������ͼ�������õ������
       for(int i=0;i<point.length;i++) {
         for(int j=0;j<point[i].length;j++){
           point[i][j].setX(j*width+leftX);   //�������ϵԭ�������Ͻǣ�������x-�ᣬ������y-��
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
             //���Ƶľ��ε����Ͻ������ǣ�x*width+leftX,y*height+leftY��
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
                          g_2d.setColor(string[3]);//���ڿ������ɫ
                          g_2d.fill(block[i][j]);
                          g_2d.setColor(string[4]);//����������ɫ
                          g_2d.setFont(new Font("",Font.BOLD,10));
                          g_2d.drawString("����",point[i][j].getX(),point[i][j].getY()+4*height/5);
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
       g_2d.setColor(Color.black);;//������ɫ
       String mess2 ="����굥�����Թ��ߣ�Ȼ�󰴷��������";
       int toLeftDis =handleMove.getBounds().width+leftX;
       g_2d.setFont(new Font("",Font.PLAIN,14));
       
       g_2d.drawString(mess2,leftX,(point.length+1)*height+leftY);
   }
   public Point getEnterPoint(Point [][] point){ //�õ���ڵ�
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
			//������ѡͼ���ػ�
			peopleWalker.repaint_pic(createUI());

		}else if (e.getSource() == Select_maze) {
			//������ѡͼ���ػ�
			file_maze = createUI();
			again(file_maze);
			       
		}
		repaint();
   }
	public void create_choseJButton() {
		JFrame f=new JFrame("�޸�ͼƬ");
		f.setSize(300, 300);
	    f.setLocation(300, 200);
	    f.setLayout(null);
	    JButton wall = new JButton("ǽ");
	    wall.setLocation(75, 0);
	    wall.setSize(150, 50);
	    JButton road = new JButton("·");
	    road.setSize(150, 50);
	    road.setLocation(75, 100);
	    JButton exit = new JButton("����");
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
    	JFrame f=new JFrame("�޸���ɫ");
        f.setSize(200, 150);
        f.setLocation(300, 200);
        f.setLayout(new FlowLayout());
        JMenuBar menuBar = new JMenuBar();
        f.setJMenuBar(menuBar);
        JMenu menu = new JMenu("ǽ");// �����˵�����
        menuBar.add(menu);// ���˵�������ӵ��˵���������
        
        
        JMenu Wall_2 = new JMenu("�߿�");// �����˵����Ӳ˵�����
        menu.add(Wall_2);// ���Ӳ˵�������ӵ��ϼ��˵�������
        
        JMenuItem Wall_2_1 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Wall_2_1.setActionCommand("Wall_rim_green");
        Wall_2_1.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        JMenuItem Wall_2_2 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Wall_2_2.setActionCommand("Wall_rim_blue");
        Wall_2_2.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        JMenuItem Wall_2_3 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Wall_2_3.setActionCommand("Wall_rim_pink");
        Wall_2_3.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        Wall_2.add(Wall_2_1);// ���Ӳ˵��Ĳ˵��������ӵ��Ӳ˵�������
        Wall_2.add(Wall_2_2);
        Wall_2.add(Wall_2_3);
        
        JMenu Moun = new JMenu("·");// �����˵�����
        menuBar.add(Moun);// ���˵�������ӵ��˵���������
        
        JMenu Moun_1 = new JMenu("���");// �����˵������
        //Wall_1.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        Moun.add(Moun_1);// ���˵��������ӵ��˵�������
        
        JMenuItem Moun_1_1 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Moun_1_1.setActionCommand("Moun_bg_green");
        Moun_1_1.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        JMenuItem Moun_1_2 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Moun_1_2.setActionCommand("Moun_bg_blue");
        Moun_1_2.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        JMenuItem Moun_1_3 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Moun_1_3.setActionCommand("Moun_bg_pink");
        Moun_1_3.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        Moun_1.add(Moun_1_1);// ���Ӳ˵��Ĳ˵��������ӵ��Ӳ˵�������
        Moun_1.add(Moun_1_2);
        Moun_1.add(Moun_1_3);
        
        
        JMenu Moun_2 = new JMenu("�߿�");// �����˵����Ӳ˵�����
        Moun.add(Moun_2);// ���Ӳ˵�������ӵ��ϼ��˵�������
        
        JMenuItem Moun_2_1 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Moun_2_1.setActionCommand("Moun_rim_green");
        Moun_2_1.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        JMenuItem Moun_2_2 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Moun_2_2.setActionCommand("Moun_rim_blue");
        Moun_2_2.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        JMenuItem Moun_2_3 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Moun_2_3.setActionCommand("Moun_rim_pink");
        Moun_2_3.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        Moun_2.add(Moun_2_1);// ���Ӳ˵��Ĳ˵��������ӵ��Ӳ˵�������
        Moun_2.add(Moun_2_2);
        Moun_2.add(Moun_2_3);
        
        JMenu Exit = new JMenu("����");// �����˵�����
        menuBar.add(Exit);// ���˵�������ӵ��˵���������
        
        JMenu Exit_1 = new JMenu("���");// �����˵������
        //Wall_1.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        Exit.add(Exit_1);// ���˵��������ӵ��˵�������
        
        JMenuItem Exit_1_1 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Exit_1_1.setActionCommand("exit_bg_green");
        Exit_1_1.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        JMenuItem Exit_1_2 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Exit_1_2.setActionCommand("exit_bg_blue");
        Exit_1_2.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        JMenuItem Exit_1_3 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Exit_1_3.setActionCommand("exit_bg_pink");
        Exit_1_3.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        Exit_1.add(Exit_1_1);// ���Ӳ˵��Ĳ˵��������ӵ��Ӳ˵�������
        Exit_1.add(Exit_1_2);
        Exit_1.add(Exit_1_3);
        
        
        JMenu Exit_2 = new JMenu("����");// �����˵����Ӳ˵�����
        Exit.add(Exit_2);// ���Ӳ˵�������ӵ��ϼ��˵�������
        
        JMenuItem Exit_2_1 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Exit_2_1.setActionCommand("exit_rim_green");
        Exit_2_1.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        JMenuItem Exit_2_2 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Exit_2_2.setActionCommand("exit_rim_blue");
        Exit_2_2.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        JMenuItem Exit_2_3 = new JMenuItem("��ɫ");// �����Ӳ˵��Ĳ˵������
        Exit_2_3.setActionCommand("exit_rim_pink");
        Exit_2_3.addActionListener(new ItemListener());// Ϊ�˵�������¼�������
        Exit_2.add(Exit_2_1);// ���Ӳ˵��Ĳ˵��������ӵ��Ӳ˵�������
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
                label.setText("������ȡ��");
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
