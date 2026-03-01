//SimulationGui.java

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class SimulationGUI extends JPanel{
    
    private SimulationModel model;
    //one block's size is 20
    private int cellSize=20;

    public SimulationGUI(SimulationModel model){
        this.model=model;

        Entity[][] grid=model.getGrid();

        //size of hole gui
        int r=grid.length;
        int c=grid[0].length;
        setPreferredSize(new Dimension(c*cellSize, r*cellSize));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Entity[][] grid=model.getGrid();
        
        for(int x=0;x<grid.length;x++){
            for(int y=0;y<grid[0].length;y++){
                
                //human is a blue on gui
                //This color can be change
                if(grid[x][y] instanceof Human){
                    g.setColor(Color.BLUE);
                }

                //all zombie is red
                else if(grid[x][y] instanceof Zombie){
                    g.setColor(Color.GREEN);
                }

                //not thing this block show white
                else{
                    g.setColor(Color.WHITE);
                }

                g.fillRect(y*cellSize, x*cellSize, cellSize, cellSize);
                
                // show the name under entity
                if(grid[x][y] instanceof LivingEntity){
                    LivingEntity le=(LivingEntity)grid[x][y];
                    g.setColor(Color.BLACK);
                    g.drawString(""+le.getHealth(), y*cellSize+5, x*cellSize+15);
                }

                //draw edge
                g.setColor(Color.GRAY);
                g.drawRect(y*cellSize, x*cellSize, cellSize, cellSize);
            }
        }
    }
}
