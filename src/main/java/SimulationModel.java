// SimulationModel.java

public class SimulationModel {
    
    private Entity[][] grid;
    private int rows;
    private int cols;


    public synchronized void initializeGridWithCounts(int r, int c, int h, int z){
        rows=r;
        cols=c;
        grid=new Entity[rows][cols];

        int numH=0;
        while(numH<h){
            int x=(int)(Math.random()*rows);
            int y=(int)(Math.random()*cols);
            if (grid[x][y]==null){
                int a=(int)(Math.random()*100);
                if(a<5){
                    grid[x][y]=new MiracleDoctor(x, y, 150);
                }
                else if(a<20){
                    grid[x][y]=new Doctor(x, y, 100);
                }
                else if(a<40){
                    grid[x][y]=new Soldier(x, y, 150);
                }
                else{
                    grid[x][y]=new Citizen(x, y, 100);
                }
                numH++;
            }
        }

        int numZ=0;
        while(numZ<z){
            int x=(int)(Math.random()*rows);
            int y=(int)(Math.random()*cols);
            if (grid[x][y]==null){
                int a=(int)(Math.random()*100);
                if(a<5){
                    grid[x][y]=new LordOfZombie(x, y, 150);
                }
                else if(a<25){
                    grid[x][y]=new SeniorZombie(x, y, 100);
                }
                else{
                    grid[x][y]=new NormalZombie(x, y, 100);
                }
                numZ++;
            }
        }
    }

    //update every time
    public synchronized void update(){

        // clear grid
        Entity[][] newGrid=new Entity[rows][cols];

        //Temporary list the entity which already be change
        java.util.Set<Entity> processed=new java.util.HashSet<>();

        for(int x=0;x<rows;x++){
            for(int y=0;y<cols;y++){
                Entity e=grid[x][y];

                if(e==null || processed.contains(e)){
                    continue;
                }
                // remove dead body
                if(e instanceof LivingEntity && !((LivingEntity) e).isAlive()) {
                    continue; 
                }
                if(e instanceof Human){
                    Human h=(Human)e;
                    // if this human be infect for too long.
                    // this value can be change(10)
                    if(h.isInfected() && h.getInfectionSteps()>=10){
                        e=new NormalZombie(x, y, 100);
                    }
                }
                e.step(grid); 
                processed.add(e);

                // put into grid
                int nx=e.getX();
                int ny=e.getY();

                if(nx<0){
                    nx=0;
                } 
                else if(nx>=rows){
                    nx=rows-1;
                } 
                if(ny<0){
                    ny=0;
                } 
                else if(ny>=cols){
                    ny=cols-1;
                } 
                
                e.setX(nx);
                e.setY(ny);

                //check is this block nothing?
                if (newGrid[nx][ny]==null){
                    newGrid[nx][ny]=e;
                }
                // if there are any entity in this block
                else{
                    newGrid[x][y]=e;
                    e.setX(x); 
                    e.setY(y);
                }
            }
        }
        grid=newGrid;
    }

    public Entity[][] getGrid(){
        return grid;
    }

    //End if there is no human or no zombie
    public String checkGameOver(){
        int[] stats=getStats();
        int numH=stats[0];
        int numZ=stats[1];
        
        if(numH==0){
            return "DOOMSDAY";
        }
        if(numZ==0){
            return "HUMAN WIN";
        }
        return null;
    }

    //count the number of human and zombie
    public synchronized int[] getStats(){
        int h=0;
        int z=0;
        for(int x=0;x<rows;x++){
            for(int y=0;y<cols;y++){
                if(grid[x][y] instanceof Human){
                    h++;
                }
                else if(grid[x][y] instanceof Zombie){
                    z++;
                }
            }
        }
        return new int[]{h, z};
    }
}