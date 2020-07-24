/**
 * PowerUp.java
 * Version 1.0
 * Author: Theo LIu
 * Date: Dec 18 2018
 */

// import statements
import java.io.*;
import javax.imageio.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
//done
class PowerUp {
  
  // class variables
  private Rectangle BoundingBox;
  private int x,y,side,type;
  private BufferedImage display;
  
  // Constructor - creates bounding box and pictures
  PowerUp(int x, int y){
    
    this.x = x;
    this.y = y;
    side = 30;
    BoundingBox = new Rectangle(x,y,side,side);
    try{
      display = ImageIO.read(new File("Pictures/heart.png"));
    }  catch(Exception e) {System.out.println("error loading sheet");};
  }
  
  // METHOD: draw()
  // @param: Graphics g, @return: null
  public void draw(Graphics g){
    g.drawImage(display,x,y,null);
    //g.drawRect(x,y,side,side);  uncomment to see hitbox on screen
  }
  
  //METHOD: getType()
  //@param: null @return: int
  public int getType(){
    return type;
  }
  
  //METHOD:getBoundingBox() need for collisions
  //@param: null @return: Rectangle
  public Rectangle getBoundingBox(){
    return BoundingBox;
  }
  
}