import ZombieSim.Entities.Soldier;
import ZombieSim.Entities.Zombie;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class xLifeModel extends JPanel {
    private char[][] grid;
    private int rows;
    private int cols;
    private int cellSize = 20;
    private ArrayList<xSoldier> soldierArrayList = new ArrayList<>();

    // for read from file we just take
    public xLifeModel(String fileName) throws FileNotFoundException {

        Scanner input = new Scanner(new File(fileName));
        this.rows = input.nextInt();
        this.cols = input.nextInt();
        grid = new char[rows][cols];

        int i = 0;
        while (input.hasNextLine() && i <= rows ) {
            String line = input.nextLine();
            if(!line.isEmpty() && line.charAt(0)!='#'){
                for(int j = 0; j< cols;j++){
                    if(line.charAt(j) == 'S'){
                        soldierArrayList.add(new xSoldier(i,j)) ;
                        //grid[i][j] = '-'; // remove S from the map
                                          // map just save Wall, Weapon and SafeZone
                    }
                    grid[i][j] = line.charAt(j);
                }
                i++;
            }
        }

        //this.zombie = new xZombie(rows,cols,grid);
        this.setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
    }
    public void setCell(int r, int c, char value) { grid[r][c] = value; }

    public void updateMap(int x, int y, char bef, char aft){

    }

    public boolean canMove(int x, int y){
        //System.out.println(x + " - " + y);
        if(y>=cols || y < 0){ return false; }
        if(x<0 || x>=rows){return false;}
        if(grid[x][y] == 'X'){return false;} // Wall
        return true;
    }
    // Using update
    public void update() {
        // get new move of solier

        for (int i = 0; i< soldierArrayList.size();i++){
            xSoldier target = soldierArrayList.get(i);
            int x = target.getX(); // rows
            int y = target.getY(); // cols
            boolean isMove = false;
            // Remove current position from map
            grid[x][y] = '-';
            int x1 = 0;
            int y1 = 0;
            // Check bound + wall
            while (!isMove) {
                xEntity.Direction nextDir = target.move();
                // Calculate
                if (nextDir.equals(xEntity.Direction.SOUTH)) {

                    x1 = x-1;
                    y1 = y;
                } else if (nextDir.equals(xEntity.Direction.NORTH)) {
                    x1 = x+1;
                    y1 = y;
                } else if (nextDir.equals(xEntity.Direction.WEST)) {
                    x1 = x;
                    y1 = y-1;
                } else {
                    x1 = x;
                    y1 = y+1;
                }
                isMove = canMove(x1,y1);
            }
            grid[x1][y1] = 'S';
            // update arraylist
            soldierArrayList.get(i).setX(x1);
            soldierArrayList.get(i).setY(y1);

        }

        /*
        char[][] nextGen = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Need to check move all object
                // Then update nextGen

                // Zombies
                if (grid[i][j] == 'Z') {
                    //zombie.search(i,j);
                }
            }
        }

        grid = nextGen;
        */

    }

    // for saving current Map and final MAP
    @Override
    public String toString() {
        String retStr = "";

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                retStr+= grid[r][c];
            }
            // Add a newline character at the end of each row
            retStr+="\n";
        }
        return retStr;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Wall
                if (grid[r][c]=='X') {
                    g.setColor(Color.GRAY);
                    g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
                }
                // Weapon
                if (grid[r][c]=='W') {
                    g.setColor(Color.ORANGE);
                    g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
                }
                // SafeZone
                if (grid[r][c]=='O') {
                    g.setColor(Color.CYAN);
                    g.fillRect(c * cellSize+1, r * cellSize+1, cellSize+1, cellSize+1);
                }
                // Zombies
                if (grid[r][c]=='Z') {
                    g.setColor(Color.RED);
                    g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
                }
                // Human
                if (grid[r][c]=='H') {
                    g.setColor(Color.GREEN);
                    g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
                }
                // Soldier
                if (grid[r][c]=='S') {
                    g.setColor(Color.BLUE);
                    g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
                }

                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(c * cellSize, r * cellSize, cellSize, cellSize);
            }
        }
    }
}