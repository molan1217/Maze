package Maze.data;
public class Point{
    int x,y;            //点的位置坐标
    int number;         //编号
    boolean isRoad;     //是否是路
    boolean isEnter;    //是否是入口点
    boolean isOut;      //是否是出口点
    boolean haveFlag;   //该点是否被标记有旗
    boolean isMountain; //该点是否是山
    boolean isCharge;   //该点是否收费 
    int chargeMoney;    //该点收取的费用
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean isEnter(){
        return isEnter;
    }
    public void setIsOut(boolean boo){
        isOut = boo;
    }
    public boolean isOut(){
        return isOut;
    }
    public void setIsEnter(boolean boo){
        isEnter = boo;
    }
    public boolean isMountain(){
        return isMountain;
    }
    public void setIsMountain(boolean boo){
        isMountain = boo;
    }
    public boolean isRoad(){
        return isRoad;
    }
    public void setIsRoad(boolean boo){
        isRoad = boo;
    }
    public void setHaveFlag(boolean boo){
        haveFlag = boo;
    }
    public boolean getHaveFlag(){
        return haveFlag;
    }
    public void setNumber(int n){
        number = n;
    }
    public int getNumber(){
        return number;
    }
    public void setIsCharge(boolean boo){
        isCharge = boo;
    }
    public boolean getIsCharge(){
        return isCharge;
    }
    public void setChargeMoney(int m){
        chargeMoney = m;
    }
    public int getChargeMoney(){
        return chargeMoney;
    }
    public boolean equals(Point p){
        if(p.getX()==this.getX()&&p.getY()==this.getY())
           return true;
        else
           return false; 
    }
    public int distanceTo(Point p){
         return Math.abs(this.getX()-p.getX())+Math.abs(this.getY()-p.getY());
    }
}
