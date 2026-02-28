// CS 142 xSoldier
// ----------------------------------------------------------------------------
// xSoldier objects
// Color  : BLUE
// Move   : random direction(N,E,S,W)
// ----------------------------------------------------------------------------

import java.awt.*;
import java.util.Random;

// For reusing all methods, states of Critter, need to be extends Critter
public class xSoldier extends xEntity {
	// State / Field of xSoldier
    private Direction currDir;
    //private int hunger; // the maximum number of food
    //private int steps;

    // -----------------------OVERRIDE METHODS---------------------------------
    // 0. Constructor not inheritance from xEntity
    public xSoldier(int x, int y){
        super();
        currDir = Direction.EAST;
        setX(x);
        setY(y);
    }

    // 1. Color: BLUE
    public Color getColor() {
        return Color.BLUE;
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