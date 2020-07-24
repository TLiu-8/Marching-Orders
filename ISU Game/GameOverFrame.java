/** 
 * GameOverFrame.java
 * Version 2.0
 * Author: Theo Liu
 * Date: Jan 15 2018
 * Description: displays graphics for the menu screen
**/

//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.util.Scanner;

//done
class GameOverFrame extends JFrame{
  
  // class variables
  private JFrame thisFrame;
  // for high score output
  private int score;
  private int[] scores;   
  private String[] names;
  private File file;
  private InputName tempFrame;
  
  //Constructor
  GameOverFrame(int score) { 
    super("Game Over");
    this.thisFrame = this;
    
    //configure the window
    this.setSize(1080,1080);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
   
    // set class variables
    this.score = score;   
    scores = new int[6];
    names = new String[6];
    file = new java.io.File("Highscores.txt");
    
    //read the high scores from the file & add current name and score to end of array
    try{
      readData();
    } catch(Exception e){};
    scores[5] = score;
    names[5] = "";
    
    //Create a Panel for stuff
    JPanel decPanel = new DecoratedPanel();
    
    JButton highScoreButton = new JButton("High Scores");
    highScoreButton.addActionListener(new ClickButtonListener());
    JButton replayButton = new JButton("Replay");
    replayButton.addActionListener(new RestartButtonListener() );
    decPanel.setBorder(new EmptyBorder(700,700,700,700));
    decPanel.add(highScoreButton);
    decPanel.add(replayButton);
    this.add(decPanel);
    this.setVisible(true);
    
    // create new window for input of new name
    bubbleSort();
    if (scores[5] != score){   //after sorting if the score is not the last element 
      tempFrame = new InputName();   //open window for input of new name
    }   
  }
  
  //METHOD: readData(), takes input from high score file stores into array, @param: null @return: null
  public void readData() throws Exception {
      Scanner input = new Scanner(file);
    
      int count1 = 0;
      int count2 = 0;
      for (int i =0; i < 10; i++){
        if (i % 2 == 0){                // even number lines are names, odd number lines are the scores
          names[count1] = input.next();
          count1++;
        } else {
          scores[count2] = input.nextInt();
          count2++;
        }
      }
      input.close();
  }
  
  // METHOD: bubbleSort(), using bubble sort method (switches the elements so the larger one is first)
  // @param: null @return:null
  public void bubbleSort(){
    
    int store;
    String nameStore;
    
    for (int j = 5; j > 0; j--){    // starts from last element and moves forward
      if (scores[j] > scores[j-1]){
        store = scores[j-1];
        scores[j-1] = scores[j];
        scores[j] = store;
        nameStore = names[j-1];
        names[j-1] = names[j];
        names[j] = nameStore;
      } else{
        j = 0;       // if last element is no longer larger, the rest will already be sorted
      }
    }
  }
  
  // METHOD: outputData(), takes array information on high scores and outputs into file
  // @param: null @return: null
  public void outputData() throws Exception {
    
    PrintWriter output = new PrintWriter(file);
    int count1 = 0;
    int count2 = 0;
    
    for (int i =0; i < 10; i++){
      if (i % 2 == 0){
        output.println(names[count1]);        //outputs data (odd number lines are scores, even are names)
        count1++;
      }else{
        output.println(scores[count2]);
        count2++;
      }
    }
    output.close();
  }
  
  //INNER CLASS - Overide Paint Component for JPANEL
  private class DecoratedPanel extends JPanel{
    
    // Constructor
    DecoratedPanel() {
      this.setBackground(new Color(0,0,0,0));
    }
    
    // main paint method outputs pictures and strings
    public void paintComponent(Graphics g){    
      super.paintComponent(g);     
      Image pic = new ImageIcon( "Pictures/gameoverscreen.png" ).getImage();   //draws main features of game over screen
      g.drawImage(pic,0,0,null);  
      g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
      g.setColor(Color.white);
      g.drawString("Total Score:" + score, 425, 800);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
      if (scores[5] != score){
        g.drawString("New High Score!",350,900);
      }
    } 
  }
  
  //INNER CLASS - Creates the input name window
  class InputName extends JFrame{
    private JTextField inputField;
    private JFrame myWindow;
    
    // Constructor
    InputName(){
      
      myWindow = new JFrame("New High Score"); //create a new window with a title
      myWindow.setSize(400,200);  // set the size of my window to 400 by 400 pixels
      myWindow.setResizable(true);  // set my window to allow the user to resize it
      myWindow.setLocationRelativeTo(null);
      JPanel mainPanel = new JPanel();
    
      mainPanel.setLayout(new FlowLayout());  // set this panel up with flowlayout
      JLabel messageLabel = new JLabel(" Input your name! ");
      
      // **** Create a text field of intital size 10 characters and some initial text
      inputField = new JTextField("",10);
     
      JButton button = new JButton("Enter");
      button.addActionListener(new ClickButtonListener());
     
      mainPanel.add(messageLabel);
      mainPanel.add(inputField);
      mainPanel.add(button);

      // **** Add the main panel to the frame
      myWindow.add(mainPanel);
      // **** finally, display the window on the screen
      myWindow.setVisible(true); // make the window visible on the screen
    }
   
     // METHOD: getName(), returns the user input of the text field
     //@param: null @return: String
     public String getName() {
       return inputField.getText();
     }
   
     //METHOD: destroy(), disposes of the current window
     //@param: null @param: null
     public void destroy(){
       myWindow.dispose();
     }
   
   }
 
   //INNER CLASS - Button Press for Input Name Button
   class ClickButtonListener implements ActionListener{ 
     public void actionPerformed(ActionEvent event){
       if (tempFrame != null){
         tempFrame.destroy();           // disposes the window and runs HighScores
        }
        new HighScores();
      }
   }
   
   //INNER CLASS - Button Press for Replay Button
   class RestartButtonListener implements ActionListener{
     public void actionPerformed(ActionEvent event){
       thisFrame.dispose();                             // disposes the window and runs starting frame
       new StartingFrame();
     }
   }
   
   // INNTER CLASS - displays high score window runs output
   class HighScores extends JFrame{
     private String name;
     
     //Constructor
     HighScores() {
       JFrame myWindow = new JFrame("High Scores"); //create a new window with a title
       myWindow.setSize(400,200);  // set the size of my window to 400 by 400 pixels
       myWindow.setResizable(true);  // set my window to allow the user to resize it
       myWindow.setLocationRelativeTo(null);
       JPanel mainPanel = new JPanel();
       GridLayout layout1 = new GridLayout(5,5);
       mainPanel.setLayout(layout1);  // set this panel up with flowlayout
     
       for (int i = 0; i < 5; i++){
         if (names[i] == ""){                      //user's inputted name is added to array
           names[i] = tempFrame.getName();
         }
         mainPanel.add(new JLabel(names[i]));
         mainPanel.add(new JLabel(Integer.toString(scores[i])));
       }

       // **** Add the main panel to the frame
       myWindow.add(mainPanel);
       // **** finally, display the window on the screen
       myWindow.setVisible(true); // make the window visible on the screen
     
       try{
         outputData();               //after modifying the array outputs into file the new high scores
       }catch (Exception E){};
     }
   }
   
}
//End of GameOverFrame