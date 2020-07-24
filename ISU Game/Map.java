/**
 * Map.java
 * Version 1.0
 * Author: Theo Liu
 * Date: Dec 28 2018
 * Description: uses a 2d array to draw tiles on a map
 */

// import statements
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
//done
class Map{
  
  // class variables
  private Tile worldMap[][];
  private int boxWidth, boxHeight;
  private int mapType;
  private int tileMap[][] = {  {0,0,0,0,0,0,0,0,0},
                               {0,1,1,1,1,1,1,1,0},
                               {0,1,1,1,1,1,1,1,0},
                               {0,1,1,1,1,1,1,1,0},
                               {0,1,1,1,1,1,1,1,0},
                               {0,1,1,1,1,1,1,1,0},
                               {0,1,1,1,1,1,1,1,0},
                               {0,1,1,1,1,1,1,1,0},
                               {0,0,0,0,0,0,0,0,0}};
  
  //Constructor
  Map(int xResolution, int yResolution, int type){
    boxWidth = xResolution/9;
    boxHeight = yResolution/9;
    mapType = type;
    createWorldMap();
  }
  
  //METHOD createWorldMap(): creates 2d tile array based on pictures
  // @param: null @return:null
  public void createWorldMap() {
    worldMap = new Tile[tileMap.length][tileMap[1].length];
    for (int j=0;j<worldMap.length;j++){
      for (int i=0;i<worldMap[1].length;i++) {
        worldMap[j][i]=new Tile(tileMap[j][i],i*boxWidth, j*boxHeight,boxWidth,boxHeight,mapType);
      }
    }
  }
  
  //METHOD draw(): iterates through and draws the tiles
  //@param: Graphics g, @return: null
  public void draw(Graphics g) {
    for (int j=0;j<tileMap.length;j++){
      for (int i=0;i<tileMap[1].length;i++) {
        worldMap[j][i].draw(g,i,j);
      }
    }
  }
  
  //INNER CLASS- Tile
  class Tile {
    // class variables
    private Image tileBackground;
    
    //Constructor
    public Tile(int background,int x, int y, int w, int h, int type) {
      // get images
      Image grass = Toolkit.getDefaultToolkit().getImage("Pictures/grass.png");
      Image ocean = Toolkit.getDefaultToolkit().getImage("Pictures/ocean.png");
      Image stone = Toolkit.getDefaultToolkit().getImage("Pictures/stone.png");
      Image lava = Toolkit.getDefaultToolkit().getImage("Pictures/lava.png");
      Image ice = Toolkit.getDefaultToolkit().getImage("Pictures/ice.png");
      Image space = Toolkit.getDefaultToolkit().getImage("Pictures/space.png");
      
      Image mainTile;
      Image surrondingTile;
      
      // based on type of map will draw different tiles
      if (type  == 0){
        mainTile = grass;
        surrondingTile = ocean;
      }else if ( type == 1){
        mainTile = stone;
        surrondingTile = lava;
      }else{
        mainTile = ice;
        surrondingTile = space;
      }
   
      if (background==0){
        tileBackground = surrondingTile;
      }else{
        tileBackground = mainTile;
      }
    }
    
    //METHOD draw(): draws the tile
    //@param: Graphics g, x and y values for drawing @return: null
    public void draw(Graphics g, int xScreen, int yScreen){
      g.drawImage(tileBackground,xScreen*120,yScreen*120,120,120,null);
    } 
  } // end of tile inner class
}// end of map class
