
 /** The "Minesweeper" class.
 * Description: Minesweeper is a single-player game. The object of the game is to clear an
 * abstract minefield without detonating a mine. The game is played by revealing squares of the
 * grid, typically by clicking them with a mouse. If a square containing a mine is revealed, 
 * the player loses the game. Otherwise, a digit is revealed in the square, indicating the number 
 * of adjacent squares that contains mines.if this number is zero then the square appears blank, 
 * and the surrounding squares are automatically also revealed.
 * 
 *
 * @author Peyman Gardideh
 * @version 06, 19, 2013 
 **/ 
      
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class MineSweeper implements MouseListener
{
      
      ArrayList <JButton> buttons;
      //the game grid buttons
      ImageIcon mine = new ImageIcon(getClass().getResource("mine.jpg")), flag = new ImageIcon(getClass().getResource("flag.jpg"));
      //icons for the mines and flags
      static int gridx = 9, gridy = 9, numMines = 10;
      //used to set how big the grid is
      static int[][] minesXY, mines, grid;
      static int[] minesList;
      //used to place the mines
      static boolean[] flagList;
      //which boxes are flagged
      boolean didNotLose = true; 
      //used to find out if the player has won or not
      JFrame window = new JFrame("MineSweeper");
      //game window
      JPanel minePanel = new JPanel(), menuBar = new JPanel(), mainMenu = new JPanel();
      //game panels
      
      
      public MineSweeper(){ //called in the main method to start the program
            //initializes window
            window.setSize(gridx*25, gridy*25+15);
            window.setLocation(650, 10);
            window.setResizable(false);
            window.setLayout(new BorderLayout());
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            menu();
            
            window.setVisible(true);
      }
      
      public void mouseClicked (MouseEvent e){ //mouse listener is added to the buttons arraylist. each time one is clicked, the program enters this method
            if (e.getButton() == MouseEvent.BUTTON1){ //if the action was a left clcik
                  if(!flagList[buttons.indexOf(e.getSource())] && buttons.get(buttons.indexOf(e.getSource())).isEnabled())
                        //if the button is enabled and is not set to a flag call leftClick()
                        leftClick(e);
            }
            else if (e.getButton() == MouseEvent.BUTTON3){
                  //if the button is a rightclick call rightClick()
                  rightClick(e);
            }
      }
      //these methods are needed when the class implements mouseListener however in this case they are not used
      public void mousePressed (MouseEvent e){}
      public void mouseReleased (MouseEvent e){}
      public void mouseEntered(MouseEvent e){}
      public void mouseExited (MouseEvent e){}
      
      public void leftClick (MouseEvent click){ //if one of the grid buttons are leftClicked
            
            int mineX, mineY; //coordinates for button pressed
            
            int whichButton = buttons.indexOf(click.getSource()); //which button was presssed
            buttons.get(whichButton).setIcon(null); //set the icon for that button to nothing
            //find the coordinates of button pressed
            mineX = whichButton % gridx; 
            mineY = (whichButton - mineX+1) / gridx;
            
            if(minesList[whichButton] == 1){ //if the button pressed was a mine
                  buttons.get(whichButton).setBackground(new Color(250, 0, 0));
                  for(int i = 0; i < minesList.length; i++){ //reveal the rest of the mines
                        if(minesList[i] == 1){
                              if(flagList[i] == false){
                                    buttons.get(i).setIcon(mine); 
                                    buttons.get(i).setOpaque(true);
                              }
                        }
                  }
                  for(int i = 0; i < gridx*gridy; i++) //disable all buttons
                        buttons.get(i).setEnabled(false);
                  
                  didNotLose = false; 
                  JOptionPane.showMessageDialog(new JFrame(), "You Lose!"); //display losing message
            }
            else{ //if the button pressed was not a mine call reveal()
                  reveal(mineY, mineX); 
            }
            
            if(isWin(didNotLose)) //finds out if the player won
                  JOptionPane.showMessageDialog(new JFrame(), "You Win!"); //display winning message
      }

      public void rightClick (MouseEvent click){ //if one of the grid buttons was right clicked
            
            int whichButton = buttons.indexOf(click.getSource()); //which button was pressed 
            flagList[whichButton] = !flagList[whichButton]; //sets the the box to flagged if not flagged and not flagged if flagged
            
            if(flagList[whichButton] && buttons.get(whichButton).isEnabled()){ //if the there isnt a flag and the button is enabled
                  buttons.get(whichButton).setIcon(flag); //place a flag
            }
            else if (buttons.get(whichButton).isEnabled()) {
                  buttons.get(whichButton).setIcon(null); //if the flag is set and the button is enabled 
            }
            
            if(isWin(true)) //if the player won
                  JOptionPane.showMessageDialog(new JFrame(), "You Win!"); //display win message 
      }
      public boolean isWin(boolean win){ //checks if player won
            
            for(int i = 0; i < minesList.length; i++){ 
                  if(minesList[i] == 1){
                        if(flagList[i] == true)
                              win &= true;
                  }
                  else if(!buttons.get(i).isEnabled()){x
                        win &= true;
                  }
                  else{
                        win = false;
                  }
            }
            return win;
      }
      
      public void menu(){ //creats the main menue
            
            //creates buttons
            mainMenu.removeAll();
            JButton instructions = new JButton("Instructions");
            JButton grid9by9 = new JButton("9 x 9, 10 mines"); 
            JButton grid16by16 = new JButton("16 x 16, 40 mines"); 
            JButton grid16by30 = new JButton("16 x 30, 99 mines");
            
            window.getContentPane().removeAll();
            instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
            grid9by9.setAlignmentX(Component.CENTER_ALIGNMENT);
            grid16by16.setAlignmentX(Component.CENTER_ALIGNMENT);
            grid16by30.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            //sets what each button does
            instructions.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e){
                        instructions();
                  }
            });
            
            grid9by9.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e){
                        gridx = 9;
                        gridy = 9;
                        numMines = 10;
                        startGame();
                  }
            });
            
            grid16by16.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e){
                        gridx = 16;
                        gridy = 16;
                        numMines = 40;
                        startGame();
                  }
            });
            
            grid16by30.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e){
                        gridx = 30;
                        gridy = 16;
                        numMines = 99;
                        startGame();
                  }
            });
            //adds buttons to panel. and adds panel to the window
            mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.Y_AXIS));
            
            mainMenu.add(instructions);
            mainMenu.add(grid9by9);
            mainMenu.add(grid16by16);
            mainMenu.add(grid16by30);
            
            window.setSize(9*25, 9*25+20);
            window.add(new JPanel(), BorderLayout.NORTH);
            window.add(mainMenu, BorderLayout.CENTER);
            window.setVisible(false);
            window.setVisible(true);
            
      }
      public void startGame(){ //starts the game
            didNotLose = true;
            buttons = new ArrayList <JButton> (gridx * gridy);
            //resets window
            window.getContentPane().removeAll();
            menuBar.removeAll();
            minePanel.removeAll();
            initMines();
            
            buttons.clear();
            for(int i = 0; i < gridx*gridy; i++){ //initializes buttons 
                  JButton button = new JButton();
                  button.addMouseListener(this);
                  button.setBackground(new Color(105, 105, 105));
                  buttons.add(button);
                  minePanel.add(button);
            }
            
            for(int i = 0; i < gridx*gridy; i++){ //resets buttons
                  buttons.get(i).setIcon(null);
                  buttons.get(i).setText(null);
            }
            //creates the menu bar items
            JButton backToMenu = new JButton("Menu");
            JButton newGame = new JButton ("New Game");
            
            //sets what each button does
            newGame.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                        startGame();
                  }
            });
            
            backToMenu.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e){
                        menu();
                  }
            });
            //adds buttons to window
            menuBar.add(newGame);
            menuBar.add(backToMenu);
            //creats window again
            minePanel.setLayout(new GridLayout(gridy, gridx));       
            window.getContentPane().setLayout(new BorderLayout());
            window.getContentPane().add(menuBar, BorderLayout.NORTH);
            window.getContentPane().add(minePanel, BorderLayout.CENTER);
            window.setSize(gridx*25, gridy*25+20);
            window.setVisible(false);
            window.setVisible(true);
      }
      public void instructions(){
            //resets window and creats button and creates the instructions
            mainMenu.removeAll();
            window.getContentPane().removeAll();
            JButton back = new JButton("<-- Back");
            JLabel instructionsLine1 = new JLabel(" Minesweeper is a single-player game. The object of the game is to clear an");
            JLabel instructionsLine2 = new JLabel(" abstract minefield without detonating a mine. The game is played by revealing squares of the");
            JLabel instructionsLine3 = new JLabel(" grid, typically by clicking them with a mouse. If a square containing a mine is revealed,");
            JLabel instructionsLine4 = new JLabel(" the player loses the game. Otherwise, a digit is revealed in the square, indicating the number");
            JLabel instructionsLine5 = new JLabel(" of adjacent squares that contains mines.if this number is zero then the square appears blank,");
            JLabel instructionsLine6 = new JLabel(" and the surrounding squares are automatically also revealed.");
            //if the back button is pressed go back to menu
            back.addActionListener(new ActionListener() {
                  public void actionPerformed (ActionEvent e){
                        menu();
                  }
            });
            //recreates window with instructions
            window.setSize(600, 150);
            mainMenu.add(back);
            mainMenu.add(instructionsLine1);
            mainMenu.add(instructionsLine2);
            mainMenu.add(instructionsLine3);
            mainMenu.add(instructionsLine4);
            mainMenu.add(instructionsLine5);
            mainMenu.add(instructionsLine6);
            window.add(mainMenu);
            window.setVisible(false);
            window.setVisible(true);
            
      }
      public static void initMines(){ //initializes arrays for mines
            
            boolean repeated = false;
            int[][] minesLocation =  new int[2][numMines];
            
            for(int i = 0; i < numMines; i++){ //randomizes mines coordinates
                  repeated = false;
                  minesLocation[0][i] = (int)(Math.random()*gridx) + 1;
                  minesLocation[1][i] = (int)(Math.random()*gridy) + 1;
                  for(int j = 0; j < i; j++)
                        repeated |= (minesLocation[0][i] == minesLocation[0][j] && minesLocation[1][i] == minesLocation[1][j]);
                  if(repeated)
                        i--;
            }
            minesXY = minesLocation;
            //creates a list of where the mines are
            int[] minesArray = new int[gridy * gridx];
            for(int i = 0; i < gridx*gridy; i++)
                  minesArray[i] = 0;
            
            for(int i = 0; i < numMines; i++)
                  minesArray[(minesXY[0][i]-1) + (minesXY[1][i]-1) * gridx] += 1;
            minesList = minesArray;
            
            
            int[][] minesGrid = new int[gridy+2][gridx+2]; 
            //creates a grid of where the miens are
            for(int i = 0; i < gridy+2; i++)
                  for(int j = 0; j < gridx+2; j++)
                  minesGrid [i][j] = 0;
            
            for(int i = 0; i < numMines; i++)
                  minesGrid[minesXY[1][i]][minesXY[0][i]] += 1;
            
            mines =  minesGrid;
            //creates the grid of numbers that shows how many mines are around each box
            int[][] howManyMines = new int[gridy][gridx];
            for(int a = 0; a < gridy; a++){
                  for(int b = 0; b < gridx; b++){
                        for(int i = -1; i < 2; i++){
                              for(int j = -1; j < 2; j++){
                                    if(i == 0 && j == 0)
                                          j++;
                                    howManyMines[a][b] += mines[a + 1 + i][b + 1 + j];
                              }
                        }
                  }
            }
            grid = howManyMines;
            //initializes flags grid to false for every box
            boolean[] flags = new boolean[gridx * gridy];
            for(int i = 0; i < gridx*gridy; i++)
                  flags[i] = false;
            flagList = flags;
            
      }
      
      public void reveal(int mineY1, int mineX1){ //recursive function to reveal boxes. recurres when you hit a zero
            
            buttons.get(mineY1 * gridx + mineX1).setEnabled(false); //disables  the current box
            
            if(grid[mineY1][mineX1] != 0){ //if the current button is 0
                  buttons.get(mineY1 * gridx + mineX1).setText(grid[mineY1][mineX1]+"");
                  buttons.get(mineY1 * gridx + mineX1).setOpaque(true);
            }
            else{
                  for(int i = -1; i <= 1; i++){ //looks around the button and checks if any of the buttons have a sum of 0
                        for(int j = -1; j <= 1; j++){
                              if(i == 0 && j == 0)
                                    j++;
                              if(mineY1 + i < gridy && mineX1 + j < gridx && mineY1 + i >= 0 && mineX1 + j >= 0){ 
                                    //makes sure the fuction doesnt check outside the screen
                                    if(grid[mineY1 + i][mineX1 + j] != 0){ //if the current box doesnt equal 0, reveal it
                                          buttons.get((mineY1 + i) *gridx + (mineX1 + j)).setText(grid[mineY1 + i][mineX1 + j]+"");
                                          buttons.get ((mineY1 + i) *gridx + (mineX1 + j)).setEnabled(false);
                                          buttons.get((mineY1 + i) *gridx + (mineX1 + j)).setOpaque(true);
                                    }
                                    else{
                                          if(buttons.get((mineY1 + i) * gridx + (mineX1 + j)).isEnabled() && 
                                             !flagList[(mineY1 + i) * gridx + (mineX1 + j)]) //if the current box is 0, reccurse
                                                reveal(mineY1 + i, mineX1 + j);
                                    }
                              }
                        }
                  }
            }
      }
      public static void main(String[] args) throws InterruptedException {
            
            new MineSweeper();
            
      }
}