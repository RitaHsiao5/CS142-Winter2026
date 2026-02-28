// CS 142 xHuman
// ----------------------------------------------------------------------------
// xHuman objects
// Color  : GREEN
// Move   : random direction(N,E,S,W)
// ----------------------------------------------------------------------------

import java.awt.*;
import java.util.Random;

// For reusing all methods, states of xEntity, need to be extends xEntity
public class xHuman extends xEntity {
	// State / Field of xSoldier
    private Direction currDir;
    //private int hunger; // the maximum number of food
    private boolean isprotect;

    // -----------------------OVERRIDE METHODS---------------------------------
    // 0. Constructor not inheritance from xEntity
    public xHuman(int x, int y){
        super();
        isprotect = false;
        setX(x);
        setY(y);
    }

    // 1. Color: BLUE
    public Color getColor() {
        return Color.GREEN;
    }

    // 2. Move random direction(N,E,S,W),
    public Direction move() {
        // Update currDir for 6 steps
        Random rand = new Random();
        int randomInt = rand.nextInt(4);
        if(randomInt == 0){
            currDir = Direction.NORTH;
        } else if (randomInt == 1) {
            currDir = Direction.EAST;
        } else if (randomInt == 2) {
            currDir = Direction.SOUTH;
        } else {
            currDir = Direction.WEST;
        }
        return currDir;
    }
    // ----------------------- END OVERRIDE METHODS ---------------------------
}