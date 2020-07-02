package Maze.data;
 //设置两点之间的路
public class SetRoad {
   static void setRoad(Point p1,Point p2,Point [][] p){ 
        int row = p.length;
        int column = p[0].length;
        int m1=0,n1=0,m2=0,n2=0;
        for(int i=0;i<row;i++) {
           for(int j=0;j<column;j++){ 
             if(p1 == p[i][j]) {
                m1 = i ;
                n1 = j;
             }
           }   
        } 
        for(int i=0;i<row;i++) {
           for(int j=0;j<column;j++){ 
             if(p2 == p[i][j]){ 
                m2 = i ;
                n2 = j;
             }
           }   
        }
       if(m1 >= m2) {
            for(int i = m1;i >= m2;i--)      //从p1出发向上设置路
                 p[i][n1].setIsRoad(true);
            if(n1<=n2){
                for(int j = n1;j <= n2;j++)   //再向右设置路
                   p[m2][j].setIsRoad(true);
            }
            else {
                for(int j = n1;j >= n2;j--)   //再向左设置路
                   p[m2][j].setIsRoad(true);
            }
        }
        else if(m1 < m2) {
            for(int i = m1;i <= m2;i++)       //从p1出发向下设置路
                p[i][n1].setIsRoad(true);
            if(n1<=n2){
                for(int j = n1;j <= n2;j++)   //再向右设置路
                   p[m2][j].setIsRoad(true);
            }
            else {
                for(int j = n1;j >= n2;j--)   //再向左设置路
                   p[m2][j].setIsRoad(true);
            }
        }
    }
}
 