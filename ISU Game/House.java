/**
 * House.java
 * Version 1.0
 * Author: Theo Liu
 * Date: Jan 3 2019
 * Description: Template for house class including a hitbox, points, etc.
 */

//import statements
import java.awt.Rectangle;            
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.*;
import javax.imageio.*;
//done
class House {
  
  // class variables
  private Rectangle boundingBox;
  private Rectangle firstWall;
  private Rectangle secondWall;
  
  private BufferedImage drawing;
  private BufferedImage extensionFront;
  private BufferedImage extension1;
  private BufferedImage extension2;
  
  private final int width;
  private final int height;
  
  private int total;
  private int type;
  private int level;
  private int num;
  
  private int x;
  private int y;
  
  private int x1,x2,y1,y2,a1,a2,b1,b2;  // coordinates for wall extensions
  
  //Constructor
  House(int x, int y,int type) {
    width = 80;
    height = 80;
    total = 0;
    this.type = type;
    this.x = x;
    this.y = y;
    boundingBox = new Rectangle(x+20,y+20,width,height); 
    
    if (type == 1){           // sets the appropriate coordinates for the extension BOUNDING BOX (ab)
      a1 = x+20;              // ie: if red house extensions will be above and below 
      a2 = x+20;
      b1 = y+120;
      b2 = y-80;
      
      x1 = x;                      //x1 and y1 are for coordinates needed to draw extension
      x2 = x; 
      y1 = y+120;
      y2 = y-120;
      
    } else if (type == 2){
      a1 = x-100;
      a2 = x+140;
      b1 = y+20;
      b2 = y+20;
      
      x1 = x-120;
      x2 = x+120;
      y1 = y;
      y2 = y;
    } else {
      a1 = x+20;
      a2 = x+20;
      b1 = y - 80;
      b2 = y + 120;
      
      x1 = x;
      x2 = x;
      y1 = y -120;
      y2 = y + 120;
    }
    
    firstWall = new Rectangle(a1,b1,width,height);          //creates new rectangle for the extensions
    secondWall = new Rectangle(a2,b2,width,height);
    
    // initialize images
    try{
      level = 1;
      if (type == 1){
      
        drawing = ImageIO.read(new File("Pictures/redhouse.png"));
        extensionFront = ImageIO.read(new File("Pictures/redhouse2.png"));
        extension1 = ImageIO.read(new File("Pictures/redhouse1.png"));
        extension2 = ImageIO.read(new File("Pictures/redhouse3.png"));
      
      } else if (type == 2){
      
        drawing = ImageIO.read(new File("Pictures/bluehouse.png"));
        extensionFront = ImageIO.read(new File("Pictures/bluehouse2.png"));
        extension1 = ImageIO.read(new File("Pictures/bluehouse1.png"));
        extension2 = ImageIO.read(new File("Pictures/bluehouse3.png"));
      
      } else {
      
        drawing = ImageIO.read(new File("Pictures/yellowhouse.png"));
        extensionFront = ImageIO.read(new File("Pictures/yellowhouse2.png"));
        extension1 = ImageIO.read(new File("Pictures/yellowhouse1.png"));
        extension2 = ImageIO.read(new File("Pictures/yellowhouse3.png"));
      }
    }  catch(Exception e) {System.out.println("error loading sheet");};
  }
  
  //METHOD: draw(), draws all necessary images
  // @param: Graphics g, @return: null
  public void draw(Graphics g) {
    
    if (level == 2){
      g.drawImage(extensionFront,x,y,null);
      g.drawImage(extension1,x1,y1,null);
    } else if (level == 3){
      g.drawImage(extensionFront,x,y,null);
      g.drawImage(extension1,x1,y1,null);
      g.drawImage(extension2,x2,y2,null);
    } else {  //level = 1
      g.drawImage(drawing,x,y,null);
    }
    //g.drawRect(x+20,y+20,width,height);    //uncomment to see hitboxes drawn
   // g.drawRect(a1,b1,width,height);
   // g.drawRect(a2,b2,width,height);
  }
  
  //METHOD: addPoints(), based on level determines added, and increases number
  // @param: null @return: int (the amount added)
  public int addPoints(){
    int added;
    if (level == 1){
      added = 1;
    } else if (level == 2){
      added = 10;
    } else { // level 3
      added = 100;
    }
    num++;
    total = total+added;
    checkUpgrade();
    return added;
  }
  
  //METHOD: getPoints(), returns the total points in each house
  //@param: null @return: int
  public int getPoints(){
    return total;
  }
  
  //METHOD: checkUpgrade(), changes level based on number in each house
  // @param: null @return:null
  public void checkUpgrade(){
    if (num >= 6){
      level = 3;
    } else if (num >= 3){
      level = 2;
    } else {
      level = 1;
    }
  }
  
  //METHOD: downgrade(), sets level back to 1, num back to 0
  //@param: null @return: null
  public void downgrade(){
    level = 1;
    num = 0;
  }
  
  //METHOD: getType()
  //@param: null @return:int
  public int getType(){
    return type;
  }
  
  //METHOD: getLevel()
  //@param: null @return: int
  public int getLevel(){
    return level;
  }
  
  //METHOD: getBoundingBox(), returns the rectangle
  // @param: null @return: Rectangle
  public Rectangle getBoundingBox() {
    return boundingBox;
  }
  
  //METHOD: getFirstWall(), needed for collisions between the extensions
  //@param: null @return:Rectangle
  public Rectangle getFirstWall(){
    return firstWall;
  }
  
  //METHOD: getSecondWall(), need for collisions between the extensions
  //@param: null @return: Rectangle
  public Rectangle getSecondWall(){
    return secondWall;
  }
  
}