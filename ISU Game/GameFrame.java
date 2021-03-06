/**
* GameFrame.java
* Version 1.0
* Author: Theo Liu
* Date: Dec 20 2018
* Description: Runs main game loop for Marching Orders
**/

// imports

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon; 
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.Font;
import java.awt.Rectangle;
//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
//Other
import java.io.*;
import javax.imageio.*;
import java.util.ArrayList;
import javax.sound.sampled.*;

//done
class GameFrame extends JFrame {
  
  //class variables
  static GameAreaPanel gamePanel;
  
  // clock variables
  Clock clock;
  long lastTimeCheck = System.currentTimeMillis();
  int frameCount=0;
  String frameRate="0 fps";
  int deltaTime;
  
  // main objects
  Map land;
  House[] houses;
  House a, b, c;
  ArrayList<Robot> listOfRobots;
  Robot start;      //initializing robot
  
  // special events 
  BufferedImage dogHouse;
  double powerUpTime;
  PowerUp tempPowerUp;
  boolean dogCase;
  
  // determines change in speed, rates, points, etc
  int gameSpeed;
  int spawnRate;
  int totalPoints;
  int lives;
  int min, max;
  int boundary1, boundary2;
  double lastCheck;   //needed because game loop can run multiple times in one second, stops code from running too many times
 //Other
  boolean paused;
  File audioFile;
  
  // End of Class Variables
  
  //Constructor - this runs first
  GameFrame(int difficulty, int map)  {
    
    
    super("My Game");  
    // Set the frame dimensions
    this.setSize(1080,1080);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    this.setUndecorated(false);  //Set to true to remove title bar
    //make sure the frame has focus  
    this.requestFocusInWindow();  
    this.setVisible(true);
    
    //Set up the game panel (where we put our graphics)
    gamePanel = new GameAreaPanel();
    gamePanel.setBackground(new Color(87, 65, 200, 150));
    this.add(new GameAreaPanel());
    
    //Creates Land 
    land = new Map(1080,1080,map);
    
    //Creates house on random coordinates every time
    a = new House((int)(Math.random()*360)+260,0,2);
    b = new House(0,(int)(Math.random()*480)+240,1);
    c = new House(960,(int)(Math.random()*480)+240,0);
    houses = new House[3];
    houses[0] = a;
    houses[1] = b;
    houses[2] = c;
    
    // intial robot
    start = new Person(500,500);
    listOfRobots = new ArrayList<Robot>();
    listOfRobots.add(start);
    
    // special events (dog)
    try{
      dogHouse = ImageIO.read(new File("Pictures/doghouse.png"));
    } catch(Exception e) {System.out.println("error loading sheet");};
    dogCase = false;
    
    //Game Functions
    spawnRate = 1000;
    
    if (difficulty == 0){
      gameSpeed = 120;
    } else if (difficulty == 1){
      gameSpeed = 90;
    } else {
      gameSpeed = 60;
    }
    totalPoints = 0;
    lives = 5;
    min = 1;
    max = 2;
    boundary1 = 980;
    boundary2 = 100;
   
    //Other
    audioFile  = new File("Music/music.wav");
    lastCheck = 0;
    paused = false;
    
    //adding keyboard and mouse event listeners
    MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);

    MyMouseListener mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);
  
    //declares clock 
    clock = new Clock();

    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
    t.start();
   
  } //End of Constructor

  
  //THE GAME LOOP- this is where the game state is updated
  public void animate() {
    
    playMusic(); 
    
    while(lives > 0){
      if (paused == false){
        //SPAWNING
        int spawner = (int)(Math.random()*spawnRate); // generate random number
        if (spawner % 300 == 0 && tempPowerUp == null){
          powerUpTime = clock.getActualTime();
          tempPowerUp = new PowerUp((int)(Math.random()*360)+360,(int)(Math.random()*360)+360);
        }
        
        int count = listOfRobots.size();
        if ( ( (spawner % 20 == 0) || (count < min) )  && (count < max) ){
        
          int spawnX = 0;
          int spawnY = 0;
        
          if(listOfRobots.size() > 0 ){
            // spawn in range around 240 to 720
            spawnX = (int)(Math.random()*480)+240;
            spawnY = (int)(Math.random()*480)+240;
            for (int i = 0; i < listOfRobots.size(); i++){
            
              Robot d = listOfRobots.get(i);
              int oldX, oldY;
              oldX = d.getBoundingBox().x;
              oldY = d.getBoundingBox().y;
            
              while (Math.abs(spawnX-oldX) < 60 && Math.abs(spawnY-oldY) < 70){
                // if spawning si overlapping, than generate new coordinates and restart loop
                spawnX = (int)(Math.random()*480)+240;
                spawnY = (int)(Math.random()*480)+240;
                i = 0;
              }
            }
          } else {
            spawnX = 500;
            spawnY = 500;
          }
          
          // ocassionally spawner will produce a dog (special event)
          if (spawner % 400 == 0 && dogCase == false){
            DogRobot dog = new DogRobot( spawnX , spawnY);
            listOfRobots.add(dog);
            dogCase = true;
          }else{
            Robot spawned = new Person( spawnX, spawnY);
            listOfRobots.add(spawned);
          }
        }
        // END OF SPAWNING
        // INTERACIONS
        for (int i = 0; i < listOfRobots.size(); i++){
        
          Robot r = listOfRobots.get(i);
        
          // allows user to react to newly spawned robot by delaying movement through first four loops
          if (r.checkDelay() > 4){
            r.move();
          }
        
          for (int j = i+1; j < listOfRobots.size(); j++){
          
            Robot s = listOfRobots.get(j);
          
            // checks collisions of two robots
            if ((r.getBoundingBox()).intersects(s.getBoundingBox()) ){
            
              r.switchDirection();
              s.switchDirection();
            
              if (r.getDirection() == s.getDirection()){  // since dog is faster two can collide in same direction
                r.switchDirection();                      // in this case switch only one.
              }
              
              // find width and height.
              int width = Math.max(r.getBoxWidth(), s.getBoxWidth());
              int height = Math.max(r.getBoxHeight(), s.getBoxHeight());
              // find x distance needed to sperate collision / y distance
              int x = (width - Math.abs(r.getBoundingBox().x - s.getBoundingBox().x))/2;
              int y = (height - Math.abs(r.getBoundingBox().y - s.getBoundingBox().y))/2;
            
              int dr = r.getDirection();             
              int ds = s.getDirection();
              // moves back the robots based on how much they have collided
              if(dr == 0 || dr == 2){ // up/down use y
                for(int k = 0; k < y+1; k=k+4){
                  r.move();
                }
              } else {// right/left use x
                for(int k = 0; k < x+1; k=k+4){
                  r.move();
                }
              }
            
              if(ds == 0 || ds == 2){ // up/down use y
                for(int k = 0; k < y+1; k=k+4){
                  s.move();
                }
              }else{
                for(int k = 0; k < x+1; k=k+4){
                  s.move();
                }
              }
            }
          }  
        
          //check interactions between robots and houses
          for (int j = 0; j < 3; j++){
          
            // checks if any robots collide with house and add points
            if ( ( ( r.getBoundingBox() ).intersects( (houses[j] ).getBoundingBox() )) && (r.getType() == houses[j].getType()) ){
            
              totalPoints =  totalPoints +  houses[j].addPoints();
              listOfRobots.remove(r);
              r = null;
              j = 3; // leave loop (no need to check other collisions)
  
            } else if ( (r.getBoundingBox()).intersects(houses[j].getBoundingBox()) ) {
              if (r.getType() == 4){
                dogCase = false;
              }
              listOfRobots.remove(r);
              r= null;   
              houses[j].downgrade();
              j = 3; // leave loop (no need to check other collisions)
              
           // if houses are certain level robots may bounce off walls
          } else if (houses[j].getLevel() == 2){
            if ( (r.getBoundingBox() ).intersects( houses[j].getFirstWall() ) ){
              r.switchDirection();
            }
          } else if (houses[j].getLevel() == 3){
            if ( (r.getBoundingBox() ).intersects( houses[j].getFirstWall() ) || (r.getBoundingBox() ).intersects( houses[j].getSecondWall() ) ){
              r.switchDirection();
            }
          }
        }
        
        // if the robot has not collided with any other thing
        // check final collisions between power ups etc
        if (r != null){
          if (tempPowerUp != null){
            if ( (r.getBoundingBox()).intersects(tempPowerUp.getBoundingBox()) && tempPowerUp.getType() == 0){
              tempPowerUp = null;
              if (lives < 5){         // if collide with power up add lives
                lives++;
              } else {
                totalPoints = totalPoints + 100;         // if lives are already max add 100 points
              }
            }
          }
          // check if robots fall off board
          if ( r.getBoundingBox().x > boundary1 || r.getBoundingBox().x < boundary2 ||
                     r.getBoundingBox().y > boundary1 || r.getBoundingBox().y < boundary2){
            if (r.getType() != 4){
              lives--;                   // if not a dog lose lives
            } else {
              Rectangle dogHouse = new Rectangle(840,0,120,120);
              dogCase = false;
              if ( (r.getBoundingBox() ).intersects(dogHouse)){   // check if the dog collided with dog house
                totalPoints = totalPoints+100;
              }
            }
            listOfRobots.remove(r);
            r = null;
          }
        }
      }
      
      // update clock
      clock.update();
      
      //every 20 seconds change speed, min, max values etc
      if (clock.getActualTime() % 20 == 0 && lastCheck != clock.getActualTime()){
        
        if (gameSpeed > 50){
          gameSpeed = gameSpeed - 2;
        }
        if (spawnRate > 300){
          spawnRate = spawnRate - 2;
        }
        
        if (min < 4){
          min++;
        }
        if (max < 6){
          max++;
        }
        // makes sure this code doesn't run multiple times per second:
        lastCheck = clock.getActualTime();
      } 
      
      try{ Thread.sleep(gameSpeed);} catch (Exception exc){}  //delay 
      this.repaint();
      }
    }
    // once exited main game loop start game over frame
    try{
      new GameOverFrame( totalPoints );
      this.dispose();
    }catch(Exception E){};
  }
  
  //METHOD: plays music
  // @param: null @return: null
  public void playMusic(){
    try{
      
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
      DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
      Clip clip = (Clip) AudioSystem.getLine(info);
      clip.close();
      clip.open(audioStream);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      clip.start();
      
                
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  //METHOD: getFrameRate(), updates frame rate per second
  // @param: null @return:String
  public String getFrameRate(){
    long currentTime = System.currentTimeMillis();  //get the current time
    deltaTime += currentTime - lastTimeCheck; //add to the elapsed time
    lastTimeCheck = currentTime; //update the last time var
    frameCount++; // everytime this method is called it is a new frame
    if (deltaTime>=1000) { //when a second has passed, update the string message
      frameRate = frameCount + " fps" ;
      frameCount=0; //reset the number of frames since last update
      deltaTime=0;  //reset the elapsed time
    }
    return frameRate;
  }
  
  /** --------- INNER CLASSES ------------- **/
  
  // INNER CLASS - Draws on Screen
  private class GameAreaPanel extends JPanel {
    
    public void paintComponent(Graphics g) {   
      super.paintComponent(g);

      g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
      g.setColor(Color.white);
      
      // draws land
      land.draw(g);
      
      // draws dog house only if dog is on board
      if (dogCase == true){      
        g.drawImage(dogHouse, 880, 0 , null);
      }
      
      // draws houses
      for (int i = 0; i < 3; i++){
        House a = houses[i];
        a.draw(g);
      }
      
      // draws robots
      for (int i = 0; i <= listOfRobots.size()-1; i++){
        Robot r = listOfRobots.get(i);
        r.draw(g);
      }
      
      // draws hearts
      Image heart = new ImageIcon( "Pictures/heart.png" ).getImage();
      for (int i = 0; i < lives; i++){
        g.drawImage(heart,150 + 30*i,10,null);
      }
      // draws powerup for twenty seconds then deletes
      if (clock.getActualTime() - powerUpTime < 20 && tempPowerUp != null){
        tempPowerUp.draw(g);
      } else {
        tempPowerUp = null;
      }
      
      // draws scoreboard
      g.drawString("Score:",750,25);
      g.drawString(Integer.toString(totalPoints),825,25);
      // draws lives
      g.drawString("Lives:",100,25);
      // draws frame rates
      g.drawString(getFrameRate(),10,25);
      // draws clock
      clock.draw(g,1000,25);    
    }
  }

  // INNER CLASS - Detecting keyboard inputs
  private class MyKeyListener implements KeyListener {
    boolean keyPressedOnce = true;
    public void keyTyped(KeyEvent e) {  
    }
    
    public void keyPressed(KeyEvent e) {

      if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { //If ESC is pressed
        System.out.println("YIKES ESCAPE KEY!"); //close frame & quit
        System.exit(0);
      }
      // pausing conditions
      if (e.getKeyCode() == KeyEvent.VK_SPACE && keyPressedOnce == true) {
        paused = true;
        keyPressedOnce = false;
      } else if (e.getKeyCode() == KeyEvent.VK_SPACE && keyPressedOnce == false) {
        paused = false;
        keyPressedOnce = true;
      }
    
    }
    public void keyReleased(KeyEvent e){
    }
  } //end of keyboard listener
  
  // INNER CLASS - detects mouse clicks
  private class MyMouseListener implements MouseListener {
    
    public void mouseClicked(MouseEvent e) {
      // mouse clicks change directions
      int changeDirection = -1;
      
      if (e.getButton() == MouseEvent.BUTTON1){
        changeDirection = 0;
      }else if (e.getButton() == MouseEvent.BUTTON3){
        changeDirection = 1;
      }
      
      for (int i = 0; i <= listOfRobots.size()-1; i++){
        Robot r = listOfRobots.get(i);
        if (e.getX() > r.getX() && e.getX() < r.getX() + 64 &&
            e.getY() > r.getY() && e.getY() < r.getY() + 96){
          r.setDirection(changeDirection);
        }
      }
    }
    
    public void mousePressed(MouseEvent e){
    }
    
    public void mouseReleased(MouseEvent e){
    }
    
    public void mouseEntered(MouseEvent e){
    }
    
    public void mouseExited(MouseEvent e){
    }
  
  } //end of mouselistener
  
  // INNER CLASS - clock gets elapsed time and game time, can be drawn
  class Clock {
    // class variables
    long elapsedTime;
    long lastTimeCheck;
    double actualTime;
    // Constructor
    public Clock(){ 
      lastTimeCheck=System.nanoTime();
      elapsedTime=0;
      actualTime = 0;
    }
    
    public void update(){
      long currentTime = System.nanoTime();  //if the computer is fast you need more precision
      elapsedTime=currentTime - lastTimeCheck;
      lastTimeCheck=currentTime;
    }

    //return elapsed time in milliseconds
    public double getElapsedTime(){
      return elapsedTime/1.0E9;
    }
    
    public double getActualTime(){
      return Math.round(getElapsedTime() + actualTime);
    }
    
    public void draw(Graphics g, int x, int y){
      g.drawString("Clock:",940,25);
      g.drawString(Double.toString(getActualTime()),x,y);
      actualTime = getElapsedTime()+actualTime;
    }
  }
}
// End of Game Frame