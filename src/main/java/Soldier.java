//Soldier.java


public class Soldier extends Human{
    
    public Soldier(int x, int y, int health) {
        super(x, y, health);
    }

    public void step(Entity[][] grid){
        super.step(grid);
        
        Zombie closestZombie=null;
        int minDistance=Integer.MAX_VALUE;
        
        //scanning hole grid to find closest zombie
        for(int x=0;x<grid.length;x++){
            for(int y=0;y<grid[0].length;y++){
                Entity e= grid[x][y];
                
                // is it zombie?
                if(e instanceof Zombie){
                    
                    // distance between this and zombie
                    int distance=Math.abs(this.getX()-x)+Math.abs(this.getY()-y);

                    //find min distance
                    if(minDistance>distance){
                        minDistance=distance;
                        closestZombie=(Zombie)e;
                    }
                }  
            }
        }
        // if no zombie, move random
        if(closestZombie==null){
            moveRandom(grid);
        }
        else if(minDistance<=1){
            attack(closestZombie);
        }
        //move to closest zombie
        else{
            moveToward(closestZombie, grid);
        }
    }
    public void attack(Zombie z){
        // can change the number of damage
        z.takeDamage(30);
    }

    // a auxiliary methods, move to zombies
    private void moveToward(Zombie z, Entity[][] grid){
        int dx=z.getX()-this.getX();
        int dy=z.getY()-this.getY();
    
        int nextX=this.getX();
        int nextY=this.getY();
        int rows=grid.length;
        int cols=grid[0].length;

        if(Math.abs(dx)>Math.abs(dy)){
            int moveAmount;
            if(dx>0){
                moveAmount=2;
            } 
            else{
                moveAmount=-2;
            }
            nextX=this.getX()+moveAmount;
        } 
        else{
            int moveAmount;
            if(dy>0){
                moveAmount=2;
            } 
            else{
                moveAmount=-2;
            }
            nextY=this.getY()+moveAmount;
        }

        if(nextX<0){
            nextX=0;
        } 
        if(nextX>=rows){
            nextX=rows-1;
        } 
        if(nextY<0){
            nextY=0;
        } 
        if(nextY>=cols){
            nextY=cols-1;
        } 

        if(grid[nextX][nextY]==null){
            grid[this.getX()][this.getY()]=null;
            this.setX(nextX);
            this.setY(nextY);
            grid[nextX][nextY]=this;
        }
    }
}
