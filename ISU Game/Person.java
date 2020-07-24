/**
 * Robot.java
 * Version 1.0
 * Author: Theo LIu
 * Date: Dec 18 2018
 */

// import statements
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.Graphics;

class Person extends Robot{
  
  // class variables
  
  // sprites
  private BufferedImage[] sprites;
  private int currentSprite;
  private int type;
  
  //location
  private int width;
  private int height;
  
  // movement
  private int direction;
  private int delay;
  private int speed;

    
  // Constructor
  public Person(int x, int y) {
    super(x,y,(int)(Math.random() * 3),64,96,44,56);
    width = 64;
    height = 96;
    loadSprites();
    currentSprite = 0;
    speed = 4;
  }
  
   //METHOD loadSprites() gets spritesheet and stores in array
  // @param: null @return: null
  public void loadSprites(){
    
    try{
      BufferedImage sheet = ImageIO.read(new File("Pictures/spritesheet.png"));
      final int rows = 4;
      final int cols = 4;
      
      
      int multiplier;
      sprites = new BufferedImage[rows * cols];
      if (getType() == 1){
        multiplier = 0;
      } else if (getType() == 2){
        multiplier = width * 4;   // spritesheet also holds different coloured sprites
      } else {
        multiplier = width * 8;
      }
      
      for (int j = 0; j < rows; j++){
        for (int i = 0; i < cols; i++){                    // based on multiplier sprites will be different colours
          sprites[ (j*cols) + i] = sheet.getSubimage(i * width + multiplier, j * height, width, height);
        }
      }
    } catch(Exception e) {System.out.println("error loading sheet");};
  }
  
  //METHOD draw()
  // @param: Graphics g, @return: null
  
  public void draw(Graphics g){
    g.drawImage(sprites[currentSprite], getX(), getY(), null);
    //g.drawRect(x+10,y+30,width-20,height-40);  uncomment to see draw hitbox
  }
  
   // METHOD: move() changes x and y based on direction loops through array of sprites
  // @param: null @return: null
  public void move(){
    
    currentSprite++; //change sprite
    int direction = getDirection();
    // up movement
    if (direction == 0){
      
      if (currentSprite >= 8 || currentSprite < 4){
        currentSprite = 4;
      }
      setY(getY()-speed);
      setBoundingBox(getBoundingBoxX(),getBoundingBoxY()-speed);
      
     // right movement
    } else if (direction == 1){
    
      if (currentSprite >= 16 || currentSprite < 12){
        currentSprite = 12;
      }
      setX(getX()+speed);
      setBoundingBox(getBoundingBoxX()+speed,getBoundingBoxY());
      
    //down movement
    } else if (direction == 2){
      if (currentSprite >= 4){
        currentSprite = 0;
      }
      setY(getY()+speed);
      setBoundingBox(getBoundingBoxX(),getBoundingBoxY()+speed);
      
    // left movement
    } else if (direction == 3){
      
      if (currentSprite >= 12 || currentSprite < 8){
        currentSprite = 8;
      }
      setX(getX()-speed);
      setBoundingBox(getBoundingBoxX()-speed,getBoundingBoxY());
    }
  }
  
  // METHOD: checkDelay() after 4 turns the robot will move
  // @param: null @return: int
  public int checkDelay() {
    delay++;
    return delay;
  }

}
  
  
  