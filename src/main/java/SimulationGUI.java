//SimulationGui.java

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationGUI extends JPanel{
    
    private SimulationModel model;
    //one block's size is 20
    private int cellSize=20;

    private Timer timer;
    private int turn=300;

    // label to control
    private JLabel humanLabel;
    private JLabel zombieLabel;
    private JSlider humanSlider;
    private JSlider zombieSlider;
    private JLabel hValueLabel;
    private JLabel zValueLabel;


    public SimulationGUI(SimulationModel model){
        this.model=model;
        Entity[][] grid=model.getGrid();

        //size of hole gui
        int r=grid.length;
        int c=grid[0].length;
        setPreferredSize(new Dimension(c*cellSize, r*cellSize));

        ActionListener listener=new ActionListener(){
            public void actionPerformed(ActionEvent e){
                model.update();
                repaint();
                refreshStats();

                // if human or zombie is gone
                String result= model.checkGameOver();
                if(result!=null){
                    timer.stop();
                    javax.swing.JOptionPane.showMessageDialog(null, result);
                }
            }
        };
        timer=new Timer(turn, listener);

        humanLabel=new JLabel("Human number: 0");
        zombieLabel=new JLabel("Zombie number: 0");
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Entity[][] grid=model.getGrid();
        
        for(int x=0;x<grid.length;x++){
            for(int y=0;y<grid[0].length;y++){
                Entity e=grid[x][y];
                
                //Soldier is a blue on gui
                //This color can be change
                if (e instanceof Human) {
                    Human h = (Human) e;
                
                    if (h.isInfected()){
                        g.setColor(new Color(250, 180, 255)); 
                    }
                    // color it depends on it career
                    else if (h instanceof MiracleDoctor){
                        g.setColor(new Color(150, 255, 200)); 
                    }
                    else if (h instanceof Doctor){
                        g.setColor(new Color(200, 255, 140)); 
                    }
                    else if (h instanceof Soldier){
                        g.setColor(new Color(150, 200, 255));
                    } 
                    //citizen
                    else {
                        g.setColor(new Color(210, 220, 255));
                    }
                }
                //Lord of zombie is orange
                else if(grid[x][y] instanceof LordOfZombie){
                    g.setColor(new Color(255, 160, 160));
                }
                //all zombie is red
                else if(grid[x][y] instanceof Zombie){
                    g.setColor(new Color(255, 200, 60));
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
                    g.drawString(""+le.getHealth(), y*cellSize+1, x*cellSize+10);
                }

                //draw edge
                g.setColor(new Color(240, 240, 240));
                g.drawRect(y*cellSize, x*cellSize, cellSize, cellSize);
            }
        }
    }

    public void display() {
        JFrame frame=new JFrame("Zombie Survival Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Show grid left
        frame.add(this, BorderLayout.CENTER);

        // show controlPanel rightside
        JPanel controlPanel=new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //start button
        JButton startB=new JButton("START");
        startB.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                timer.start();
            }
        });

        //pause button
        JButton pauseB=new JButton("PAUSE");
        pauseB.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                timer.stop();
            }
        });

        // human slider
        hValueLabel=new JLabel("Set Initial Humans: 100");
        hValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanSlider=new JSlider(JSlider.HORIZONTAL, 10, 500, 100);
        humanSlider.setMajorTickSpacing(100);
        humanSlider.setPaintTicks(true);
        humanSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e){
                hValueLabel.setText("Set Humans: "+humanSlider.getValue());
            }
        });

        // zombie slider
        zValueLabel=new JLabel("Set Initial Zombies: 20");
        zValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        zombieSlider=new JSlider(JSlider.HORIZONTAL, 0, 100, 20);
        zombieSlider.setMajorTickSpacing(20);
        zombieSlider.setPaintTicks(true);
        zombieSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e){
                zValueLabel.setText("Set Zombies: "+zombieSlider.getValue());
            }
        });

        // RESTART button
        JButton restartB=new JButton("RESTART");
        restartB.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                timer.stop();
                int h=humanSlider.getValue();
                int z=zombieSlider.getValue();
        
                model.initializeGridWithCounts(30, 50, h, z);
        
                repaint();
                refreshStats();
            }
        });

        controlPanel.add(startB);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        controlPanel.add(pauseB);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(hValueLabel);
        controlPanel.add(humanSlider);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(zValueLabel);
        controlPanel.add(zombieSlider);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(restartB);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        controlPanel.add(new JSeparator());
        controlPanel.add(humanLabel);
        controlPanel.add(zombieLabel);

        frame.add(controlPanel, BorderLayout.EAST);
        frame.pack();
        refreshStats();
        frame.setVisible(true);
    }

    //show the population 
    public void refreshStats() {
        int[] stats=model.getStats();
        humanLabel.setText("Human number: "+stats[0]);
        zombieLabel.setText("Zombie number: "+stats[1]);
    }
}
