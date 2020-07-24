/**
 * Robot.java
 * Version 1.0
 * Author: Theo LIu
 * Date: Dec 18 2018
 */

// import statements
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

abstract class Robot {
  
  // class variables
  
  // sprites
  private BufferedImage[] sprites;
  private int currentSprite;
  private int type;
  
  //location
  private int x, y;
  // movement
  private int direction;
  private int delay;
  private int boxWidth;
  private int boxHeight;
  private Rectangle boundingBox;
    
  // Constructor
  public Robot(int x, int y, int type,int width, int height, int boxWidth, int boxHeight) {
    direction = (int)(Math.random()*4);
    this.type = type;
    this.x = x;
    this.y = y;
    delay = 0;   //sets delay to 0 (needs to run through 4 game loops before beginning to move)
    this.boxWidth = boxWidth;
    this.boxHeight = boxHeight;
    boundingBox = new Rectangle ((int)x+30, (int)y + 30, boxWidth, boxHeight);
  }
  
  //METHOD loadSprites() gets spritesheet and stores in array
  // @param: null @return: null
  abstract void loadSprites();
  
  // METHOD: move() changes x and y based on direction loops through array of sprites
  // @param: null @return: null
  abstract void move();
  
  // METHOD switches direction
  // @param: null @return: null
  public void switchDirection(){
    if (direction == 0 || direction == 1){
      direction = direction + 2;
    } else {
      direction = direction - 2;
    }
  }
  

    //METHOD draw()
  // @param: Graphics g, @return: null
  abstract void draw(Graphics g);

//  public void draw(Graphics g){
//    g.drawImage(sprites[currentSprite], x, y, null);
//    //g.drawRect(x+10,y+30,width-20,height-40);  uncomment to see draw hitbox
//  }
  
  // METHOD setDirection() used for rotating robots on clicks
  // @param: int turn @return: null
  public void setDirection(int turn){
    
    if (turn == 0){
      direction --;
      if (direction < 0){
        direction = 3;
      }
    } else if (turn == 1) {
      direction++;
      if (direction > 3){
        direction = 0;
      }
    }
  }
  
  // METHOD: checkDelay() after 4 turns the robot will move
  // @param: null @return: int
  public int checkDelay() {
    delay++;
    return delay;
  }
  
  //GETTERS AND SETTERS
 
  // METHOD getDirection()
  // @param: null @return: int
  public int getDirection(){
    return direction;
  }
  
  //METHOD getType()
  //@param: null @return: int
  public int getType(){
    return type;
  }
  
  //METHOD getX()
  //@param: int @return: int
  public int getX(){
    return x;
  }
  
  //METHOD getType()
  //@param: int @return: int
  public int getY(){
    return y;
  }
  
  //METHOD setX()
  //@param: int @return: int
  public void setX(int x){
    this.x = x; 
  }
  
  //METHOD setY()
  //@param: int @return: int
  public void setY(int y){
    this.y = y;
  }
  
  // METHOD getBoundingBox()
  // @param: null @return: Rectangle
  public Rectangle getBoundingBox(){
    return boundingBox;
  }
  
  public void setBoundingBox(int x, int y){
    boundingBox.x = x;
    boundingBox.y = y;
  }
  
  public int getBoundingBoxX(){
    return boundingBox.x;
  }
  
  public int getBoundingBoxY(){
    return boundingBox.y;
  }
  
  
  //METHOD getBoxWidth()
  // @param: null @return: int
  public int getBoxWidth(){
    return boxWidth;
    
  }
  //METHOD getBoxHeight()
  // @param: null @return: int
  public int getBoxHeight(){
    return boxHeight;
  }
  
  
}