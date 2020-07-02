package Maze.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
public class IntegrationView extends JFrame{
    JTabbedPane tabbedPane; //MazeView ”Õº

    public IntegrationView(){
    	// tabbedPane= new JTabbedPane(JTabbedPane.LEFT);//ø®‘⁄◊Û≤‡ 
         //tabbedPane.validate();
        // add(tabbedPane,BorderLayout.CENTER); 
         //setBounds(5,5,1500,720);
        // setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // setVisible(true);

        setBounds(5,5,1500,720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public void addMazeView(MazeView view){
    	
		add(view);
        validate();
    }
}