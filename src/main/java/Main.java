
public class Main {
    public static void main(String[] args) {
        //test line to show commit

        SimulationModel model = new SimulationModel();
        model.initializeGridWithCounts(30, 50, 100, 10);
        
        SimulationGUI gui = new SimulationGUI(model);
        gui.display(); 
    } 
}