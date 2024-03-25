
import geneticalgorithm.Darwin;


public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        //İşin en önemli kısmına nihayet gelebildik.
        Darwin drw = new Darwin();
        drw.setMutationDensity(0.3f);
        drw.setAgentsNumber(50);
        drw.startSelection();
        
        drw.getHabitat().getXagents().getFirst().printMoveTree();

    }
}
