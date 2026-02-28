public abstract class Human extends LivingEntity {
    protected boolean infected = false;

    // Constructor: gọi constructor của LivingEntity
    public Human(int x, int y, int health) {
        super(x, y, health);
    }

    // Attack a zombie
    //public void attack(Zombie z) {
        // TODO: add attack logic
    //}

    // Move randomly on the grid
    public void moveRandom(Entity[][] grid) {
        // TODO: add move logic
    }

    // Check infection status
    public boolean isInfected() {
        return infected;
    }

    // Infect this human
    public void infect() {
        infected = true;
    }

    // Step method will be implemented by subclasses (Citizen, Soldier, Doctor, etc.)
    @Override
    public abstract void step(Entity[][] grid);

}