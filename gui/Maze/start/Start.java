package Maze.start;
import java.io.File;

import Maze.data.*;
import Maze.view.*;
public class Start {
   public static void main(String []args) {
       MazeMaker mazeMaker = new MazeByFile(new File("ÎÄ¼þ/ÃÔ¹¬.txt"));
       Point [][] point= mazeMaker.initMaze();
       IntegrationView integrationView = new IntegrationView();
       MazeView mazeView  = new MazeView(point);
       integrationView.addMazeView(mazeView);
   }
}