package Maze.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bool {
	static String string;
	public Bool(String stringfile) {
		this.string = stringfile;
		
	}
	
	public static boolean isText() {
		File file  = new File(string);
		boolean isText = true;
		try {
			FileInputStream fin = new FileInputStream(file);
			long len = file.length();
			for (int j = 0; j < (int) len; j++) {
				int t = fin.read();
				if (t < 32 && t != 9 && t != 10 && t != 13) {
					isText = false;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isText;
	}
	
	public static boolean isImage(){ //ÅÐ¶ÏÊÇ·ñÎªÍ¼Æ¬
	   File f = new File(string);    
    boolean flag = false;  
    try  
    {  
        BufferedImage bufreader = ImageIO.read(f);  
        int width = bufreader.getWidth();  
        int height = bufreader.getHeight();  
        if(width==0 || height==0){  
            flag = false;  
        }else {  
            flag = true;  
        }  
    }  
    catch (IOException e)  
    {  
        flag = false;  
    }catch (Exception e) {  
        flag = false;  
    }  
    return flag;  
	}  
	}
