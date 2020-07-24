/**
* DogRobot.java
* Version 1.0
* Author: Theo Liu
* Date: Jan 15 2018
* Description: template for dog robot
**/

// import statements

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.Graphics;


class DogRobot extends Robot {
  
  // class variables
  private int currentSprite;
  private int speed;
  private int width;
  private int height;
  private BufferedImage[] sprites;
  
  //Constructor
  DogRobot(int x, int y){
    super(x,y,4,82,82,42,42);   // x , y, type, width, height,  boxWidth, boxHeight
    width = 82;
    height = 82;
    loadSprites();
    currentSprite = 0;
    speed = 6;
  }
  
  
  //METHOD draw()
  // @param: Graphics g, @return: null
   public void draw(Graphics g){
    g.drawImage(sprites[currentSprite], getX(), getY(), null);
    //g.drawRect(x+10,y+30,width-40,height-40);  uncomment to see draw hitbox
  }
  
  //METHOD loadSprites() gets spritesheet and stores in array
  // @param: null @return: null
  public void loadSprites(){
    
    try{
      BufferedImage sheet = ImageIO.read(new File("Pictures/dogspritesheet.png"));
      final int rows = 4;
      final int cols = 4;
      
      sprites = new BufferedImage[rows * cols];
      
      for (int j = 0; j < rows; j++){
        for (int i = 0; i < cols; i++){
          sprites[ (j*cols) + i] = sheet.getSubimage(i * width, j * height, width, height);
        }
      }
    } catch(Exception e) {System.out.println("error loading sheet");};
  }
  
  // METHOD: move() changes x and y based on direction loops through array of sprites
  // @param: null @return: null
  public void move(){
    
    currentSprite++; //change sprite
    int direction = getDirection();
    // up movement
    if (direction == 0){
      
      if (currentSprite >= 12 || currentSprite < 8){
        currentSprite = 8;
      }
      setY(getY()-speed);
      setBoundingBox(getBoundingBoxX(),getBoundingBoxY()-speed);
      
     // right movement
    } else if (direction == 1){
    
      if (currentSprite >= 8 || currentSprite < 4){
        currentSprite = 4;
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
      
      if (currentSprite >= 16 || currentSprite < 12){
        currentSprite = 12;
      }
      setX(getX()-speed);
      setBoundingBox(getBoundingBoxX()-speed,getBoundingBoxY());
    }
  }

}