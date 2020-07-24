/** 
 * MenuFrame.java
 * Version 1.0
 * Author: Theo Liu
 * Date: Dec 20 2018
 * Description: displays graphics for the menu screen
**/

//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
// done
class MenuFrame extends JFrame { 

  private JFrame thisFrame;
  private int difficulty;
  private int map;
  
  //Constructor - this runs first
  MenuFrame() { 
    super("Start Screen");
    this.thisFrame = this; //lol  
    difficulty = 0;  // automatically sets difficulty to easy and map to grass
    map = 0;
    
    //configure the window
    this.setSize(1080, 1080);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
         
    //Create a Panel for stuff
    JPanel decPanel = new DecoratedPanel();
    decPanel.setBorder(new EmptyBorder(288, 68, 68, 68));
    
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setBackground(new Color(0, 0, 0, 0));
    mainPanel.setPreferredSize(new Dimension(640, 480));  
    
    //JBUTTONS
    
    //start button
    ImageIcon sb =new ImageIcon("Pictures/startbutton.png");
    JButton startButton = new JButton(sb);
    startButton.setBackground(new Color(0, 0, 0, 0));
    startButton.setRolloverIcon(new ImageIcon("Pictures/startbuttonpressed.png"));
    startButton.setBorder(BorderFactory.createEmptyBorder());
    startButton.setFocusPainted(false);
    startButton.addActionListener(new StartButtonListener());
    
    //easybutton
    JButton easyButton = new JButton("Easy");
    easyButton.setBackground(Color.RED);
    //mediumbutton
    JButton mediumButton = new JButton("Medium");
    mediumButton.setBackground(Color.RED);
    //hardbutton
    JButton hardButton = new JButton("Hard");
    hardButton.setBackground(Color.RED);
    
    //oceanbutton
    JButton oceanButton = new JButton("Ocean");
    oceanButton.setBackground(Color.WHITE);
    //lavabutton
    JButton lavaButton = new JButton("Lava");
    lavaButton.setBackground(Color.WHITE);
    //spacebutton
    JButton spaceButton = new JButton("Space");
    spaceButton.setBackground(Color.WHITE);
    
    //adding action listeners
    easyButton.addActionListener(new EasyButtonListener());
    mediumButton.addActionListener(new MediumButtonListener());
    hardButton.addActionListener(new HardButtonListener());
    oceanButton.addActionListener(new OceanButtonListener());
    lavaButton.addActionListener(new LavaButtonListener());
    spaceButton.addActionListener(new SpaceButtonListener());
    
    // create and add buttons to bottom panel
    JPanel bottomPanel = new JPanel();
    bottomPanel.setBackground(new Color(0, 0, 0, 0));
    bottomPanel.add(easyButton);
    bottomPanel.add(mediumButton);
    bottomPanel.add(hardButton);
    bottomPanel.add(oceanButton);
    bottomPanel.add(lavaButton);
    bottomPanel.add(spaceButton);
    bottomPanel.add(startButton);
    
    //Add all panels to the mainPanel according to border layout
    mainPanel.add(bottomPanel,BorderLayout.SOUTH);
    
    decPanel.add(mainPanel);
    //add the main panel to the frame
    this.add(decPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //Main method starts this application
  public static void main(String[] args) throws Exception { 
    new StartingFrame();
  }
  
  //INNER CLASS - Overide Paint Component for JPANEL
  private class DecoratedPanel extends JPanel {
    
    // Constructor
    DecoratedPanel() {
      this.setBackground(new Color(0, 0, 0, 0));
    }
    
    public void paintComponent(Graphics g) { 
      
      super.paintComponent(g);     
      // draw background
      Image pic = new ImageIcon( "Pictures/titlescreen.png" ).getImage();
      g.drawImage(pic,0,0,null);
      
      // rectangular strips
      g.setColor(Color.white);
      g.fillRect(0,50,1080,420);
      g.setColor(new Color(0,76,153));
      g.fillRect(0,500,1080,280);
      
      //title font
      g.setColor(Color.black);
      g.setFont(new Font("TimesRoman", Font.BOLD, 40));
      g.drawString("RULES:",200,100);
      
      //main text font
      g.setFont(new Font("TimesRoman", Font.BOLD, 20));
      
      // drawing rules
      g.drawString("1. Direct the robots into their house (red robot goes into red house)",200,150);
      g.drawString("2. Right click to make the robot turn clockwise (relative to the direction they are facing)",200,180);
      g.drawString("Left click to make the robot turn counterclockwise",220,210);
      g.drawString("3. Get points for correctly putting the robots into their house",200,240);
      g.drawString("4. Houses will upgrade and can give you even more points!",200,270);
      g.drawString("   However place the wrong robot into the wrong house and you will downgrade",200,300);
      g.drawString("5. Keep playing until you run out of lives, more and more robots will begin to appear",200,330);
      g.drawString("   and the speed will increase",200,360); 
      g.drawString("6. There are heart power ups you can get and lookout for special event dogs! Direct them into",200,390);
      g.drawString("   the dog house. You won't lose lives for missing them but they are worth 100 points.",200,420);
      g.drawString("7. To pause click the space button, to exit click the escape",200,450);
    }
  }
  
 //INNER CLASS - disposes frame starts new game
 class StartButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event) {  
      thisFrame.dispose();
      new GameFrame(difficulty,map); //passes on difficulty and map values chosen by user
    }
  }
 //INNER CLASS - changes difficulty setting to easy
 class EasyButtonListener implements ActionListener {
   public void actionPerformed(ActionEvent event){
     difficulty = 0;
   }
 }
 //INNER CLASS - changes difficulty setting to medium
 class MediumButtonListener implements ActionListener {
   public void actionPerformed(ActionEvent event){
     difficulty = 1;
   }
 }
 //INNER CLASS - changes difficulty setting to hard
 class HardButtonListener implements ActionListener {
   public void actionPerformed(ActionEvent event){
     difficulty = 2;
   }
 }
 //INNER CLASS - changes background to space
 class SpaceButtonListener implements ActionListener {
   public void actionPerformed(ActionEvent event){
     map = 2;
   }
 }
 //INNER CLASS - changes background to ocean
 class OceanButtonListener implements ActionListener {
   public void actionPerformed(ActionEvent event){
     map = 0;
   }
 }
  //INNER CLASS - changes background to lava
 class LavaButtonListener implements ActionListener {
   public void actionPerformed(ActionEvent event){
     map = 1;
   }
 }
}
// end of MenuFrame