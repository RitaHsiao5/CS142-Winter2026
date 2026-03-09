import java.util.Random;

public abstract class Human extends LivingEntity {
    protected boolean infected = false;
    protected static Random rand = new Random();
    private int infectionTimer = 0;
    public Human(int x, int y, int health) {
        super(x, y, health);
    }

    // Move randomly to a neighboring cell within grid bounds
    public void moveRandom(Entity[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int oldX = getX();
        int oldY = getY();
        int newX = getX();
        int newY = getY();

        // Try to move one step in random direction
        int dir = rand.nextInt(4); // 0: up, 1: down, 2: left, 3: right
        switch (dir) {
            case 0: if(newY > 0) newY--; break;
            case 1: if(newY < cols - 1) newY++; break;
            case 2: if(newX > 0) newX--; break;
            case 3: if(newX < rows - 1) newX++; break;
        }

        // Check if the new cell is empty
        if(grid[newX][newY] == null) {
            grid[oldX][oldY] = null;
            setX(newX);
            setY(newY);
            grid[newX][newY] = this;
        }
        // else: stay in place (collision)
    }

    // Check infection status
    public boolean isInfected() {
        return infected;
    }
    // Infect this human
    public void infect() {
        infected = true;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }
    // Step method: will be implemented in subclasses
    @Override
    public void step(Entity[][] grid) {
        if (infected == true) {
            infectionTimer++;
        }
        moveRandom(grid);
    }
    public int getInfectionSteps() {
        return infectionTimer;
    }

}
