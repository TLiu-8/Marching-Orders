/** 
  * StartingFrame.java
  * Version 1.0
  * Author: Theo Liu
  * Date: Dec 20 2018
  * Description: draws graphics for starting screen
**/

//Import statements
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//done
class StartingFrame extends JFrame { 
  
  // class variable (the frame being drawn)
  private JFrame thisFrame;
  
  //Constructor 
  StartingFrame() { 
    super("Start Screen");
    this.thisFrame = this; 
    
    //configure the window
    this.setSize(1080,1080);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
         
    //Create a Panel for stuff
    JPanel decPanel = new DecoratedPanel();
    decPanel.setBorder(new EmptyBorder(768-240*2, 68, 68, 68));
    
    //listens for keyboard clicks
    MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);
    
    //add the main panel to the frame
    this.add(decPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //INNER CLASS - Overide Paint Component for JPANEL
  private class DecoratedPanel extends JPanel {
    
    DecoratedPanel() {
      this.setBackground(new Color(0,0,0,0));
    }
    
    public void paintComponent(Graphics g) { 
      super.paintComponent(g);     
      g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
      g.setColor(Color.black);
      Image pic = new ImageIcon( "Pictures/titlescreen.png" ).getImage();  //draws image and title
      g.drawImage(pic,0,0,null); 
      Image title = new ImageIcon( "Pictures/title.png" ).getImage();
      g.drawImage(title, 250, 460, null);
      g.drawString("Click any button to start!",450,650);
    } 
  }
  
  //INNER CLASS - checks for key presses
  private class MyKeyListener implements KeyListener {
    public void keyTyped(KeyEvent e) {  
    }
     
    public void keyReleased(KeyEvent e) {
    }
     
    public void keyPressed(KeyEvent e) { 
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
          System.out.println("YIKES ESCAPE KEY!"); //close frame & quit
          System.exit(0);
      } else {
        thisFrame.dispose();
        new MenuFrame(); 
      }    
    }
    
  }
  
  //Main method starts this application
  public static void main(String[] args) { 
    new StartingFrame();
  }
  
}