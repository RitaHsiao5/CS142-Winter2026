// Creates an interactive GUI and displays an animation of a world of ants.
// I don't really know much about JFrame, so feel free to change anything.
// By Kyle Hamasaki

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class AntSimGUI extends JFrame {
    // The size of the square cells containing the Terrain, Ant, and WorldObject
    private final int cellSize = 10;
    // The WorldGrid
    private WorldGrid grid;
    // The entire interface of the program
    private JFrame frame;
    // A label that contains an image of the world
    private JLabel imageContainer;
    // Used to draw in imageContainer
    private Graphics g;

    public AntSimGUI() {
        this.grid = grid;
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("Ant Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creates an area on the frame for the animation.
        BufferedImage worldImage = new BufferedImage(grid.getWidth() * cellSize, grid.getHeight() * cellSize,
                BufferedImage.TYPE_INT_RGB);

        imageContainer = new JLabel(new ImageIcon(worldImage));
        g = worldImage.getGraphics();

        // Creates an area on the frame for the buttons.
        // Note that the buttons do not do anything yet, and that more buttons can be added.
        JPanel buttonContainer = new JPanel(new FlowLayout());

        // Button that speeds up the animation.
        JButton speedUpButton = new JButton("Speed Up");
        buttonContainer.add(speedUpButton);

        // Button that slows down the animation.
        JButton slowDownButton = new JButton("Slow down");
        buttonContainer.add(slowDownButton);

        frame.add(imageContainer, BorderLayout.CENTER);
        frame.add(buttonContainer, BorderLayout.SOUTH);
        // It might be better to preset the frame to a certain size than calling the pack() method.
        frame.pack();

        drawWorld();
        frame.setVisible(true);
    }

    // Draws the WorldGrid as a picture.
    // By Kyle Hamasaki
    public void drawWorld() {
        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                Point currentLocation = new Point(i, j);

                // Draws the Terrain
                Terrain terrain = grid.getTerrainAt(currentLocation);
                String terrainName = terrain.getSymbol();
                if (terrainName.equals("#")) {
                    // The color if the Terrain is Dirt
                    g.setColor(new Color(0x785B4C));
                    // The color if the Terrain is Rock
                } else if (terrainName.equals("R")) {
                    g.setColor(new Color(0xEA6C6E6E, true));
                } else if (terrainName.equals(".")) {
                    // The color if the Terrain is Tunnel
                    g.setColor(new Color(0x514031));
                } else {
                    // The color if the Terrain is Air/Terrain
                    g.setColor(new Color(0xFFFFFF));
                }
                g.fillRect(i, j, cellSize, cellSize);

                // Draws the WorldObject
                WorldObject worldObject = grid.getObjectAt(currentLocation);
                String worldObjectName = worldObject.getSymbol();
                // Draws the character of the WorldObject in the middle of the cell.
                g.drawString(worldObjectName, 5 + (i * cellSize), 5 + (j * cellSize));

                // I am unsure how to draw the Ants.
            }
        }
    }

    // Updates the WorldGrid and draws a new picture of the WorldGrid.
    public void update() {
        // Maybe WorldGrid should have an update() method, which this method could call.
        // grid.update();
        drawWorld();
    }
}
