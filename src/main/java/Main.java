import java.util.HashSet;
import java.util.Set;
import javax.swing.Timer;

public class Main {
    public static void main(String[] args) {
        Greetings.all().forEach(System.out::println);
        //test line to show commit

        SimulationModel model = new SimulationModel();

        model.initialize(15, 15, 10, 2, 2);

        int totalSteps = 20;
        for (int step = 1; step <= totalSteps; step++) {
            
            System.out.println("-Start New Turn-");
            
            model.update(); 
            model.spreadInfection(); 
            
            // 呼叫你在 SimulationModel 寫好的印出地圖 
            model.printGrid();
        }
        
}