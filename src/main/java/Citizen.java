public class Citizen extends Human {

    public Citizen(int x, int y, int health) {
        super(x, y, health);
    }

    @Override
    public void step(Entity[][] grid) {
        moveRandom(grid);
       // To do: add more logic
    }
}