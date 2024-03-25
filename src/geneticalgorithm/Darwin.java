package geneticalgorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.IntStream;

import agents.Agent;
import models.Move;
import models.MoveTree;
import models.Table;

/**
 * @author: <a href="darwin1809@gmail.com">Charles Darwin</a>
 *          {@summary
 *          Bu sınıfın içersinde geneticalgorithm.Match objesi kullanılarak
 *          elimizdeki x ve o ajanlar darwinen bir evrime tabi tutulacaktır.
 *          }
 *          Kazananlar 1 kez
 *          Berabere kalanlar 0 kez
 *          üreyebilecektir ve bu üremeler ajanların başarı sayısı göz önünde
 *          bulundurularak yapılacaktır.
 *          Kaybeden bireyler ise popülasyondan ayrılacaktır.
 * 
 * @version *1.0*
 */
public class Darwin {
    private int selectionsNumber;
    private int agentsNumber;
    @SuppressWarnings("unused")
    private float mutationDensity;
    private AgentsHabitat habitat;
    private Match match;

    public Darwin() {
        this.agentsNumber = 50;
        this.mutationDensity = 0.3f;
        this.selectionsNumber = 15;

        this.habitat = new AgentsHabitat();
        this.habitat.generateAgents(this.agentsNumber, this.agentsNumber);
        this.match = new Match(this.habitat);     
    }

    public void startSelection() {
        for (int i = 0; i < selectionsNumber; i++) {
            System.out.println("NESİL SAYISI: "+i);
            match.play();
            crossover(habitat.getWinnerXagents());
            crossover(habitat.getWinnerOagents());
            mutations();
            eliminate();
            if(habitat.getXagents().size() > habitat.getOagents().size())
                habitat.generateAgents(0, habitat.getXagents().size()-habitat.getOagents().size());
            else
                habitat.generateAgents(habitat.getOagents().size()-habitat.getXagents().size(), 0);
        }
    }

    public void crossover(LinkedList<agents.Agent> agents) {
        // Winned agents ordered randomly on array
        int[] o = IntStream.range(0, agents.size()).toArray();
        IntStream.range(0, o.length)
                .forEach(i -> {
                    int temp = o[i];
                    int randomIndex = (int) (Math.random() * (o.length - i) + i);
                    o[i] = o[randomIndex];
                    o[randomIndex] = temp;
                });

        // Every agent will become clone
        for (int i = 0; i < o.length; i++) {
            try {
                MoveTree clonedTree = (MoveTree) agents.get(o[i]).getMoveTree().clone();
                MoveTree bufferedMoveTree = clonedTree;
                do {
                    HashMap<Table, Move> clonedTM = (HashMap<Table, Move>) bufferedMoveTree.tableMoveTree;
                    int size = clonedTM.size();
                    int cursor = (int) Math.round(Math.random() * size);
                    Iterator<Table> Keyitr = clonedTM.keySet().iterator();
                    LinkedList<Table> removeList = new LinkedList<Table>();
                    for (int n = 0; n < size; n++) {
                        if (n > cursor) {
                            removeList.add(Keyitr.next());
                        }

                    }
                    removeList.stream().forEach(e -> clonedTM.remove(e));
                    bufferedMoveTree = bufferedMoveTree.nextTree;
                } while (bufferedMoveTree != null);

                Agent clonedAgent = (Agent) agents.get(i).clone();
                MoveTree clonedMoveTree2 = (MoveTree) clonedAgent.getMoveTree().clone();
                bufferedMoveTree = clonedMoveTree2;
                do {
                    HashMap<Table, Move> clonedTM = (HashMap<Table, Move>) bufferedMoveTree.tableMoveTree;
                    int size = clonedTM.size();
                    int cursor = (int) Math.round(Math.random() * size);
                    Iterator<Table> Keyitr =  clonedTM.keySet().iterator();
                    LinkedList<Table> removeList = new LinkedList<Table>();
                    for (int n = 0; n < cursor; n++) {
                        removeList.add(Keyitr.next());
                    }
                    removeList.stream().forEach(e -> clonedTM.remove(e));
                    bufferedMoveTree = bufferedMoveTree.nextTree;
                } while (bufferedMoveTree != null);
                clonedAgent.getMoveTree().merge(clonedTree);
                agents.add(clonedAgent);
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    /**
     * Random mutation on random agent
     */
    public void mutations() {

    }

    /**
     * This function will eliminate looser x and o agents
     */
    public void eliminate() {
        System.out.println("Kaybeden X ajanları elimine edildi");
        // X ajanları için eliminasyon ve kazanan veya kaybeden ajanların aktarılması
        habitat.getXagents().clear();
        System.out.println("Kazanan x ajanları oyuna tekrar dahil ediliyor. Sayıları: "+habitat.getWinnerXagents().size());
        habitat.getWinnerXagents().forEach(agent -> habitat.getXagents().add(agent));
        habitat.getWinnerXagents().clear();
        habitat.getDwarfXagents().forEach(agent -> habitat.getXagents().add(agent));
        habitat.getDwarfXagents().clear();
        System.out.println("x ajanlarının sayısı: "+habitat.getXagents().size());
        System.out.println("Kaybeden O ajanları elimine edildi");
        // O ajanları için eliminasyon ve kazanan veya kaybeden ajanların aktarılması
        habitat.getOagents().clear();
        System.out.println("Kazanan o ajanları oyuna tekrar dahil ediliyor. Sayıları: "+habitat.getWinnerOagents().size());
        habitat.getWinnerOagents().forEach(agent -> habitat.getOagents().add(agent));
        habitat.getWinnerOagents().clear();
        habitat.getDwarfOagents().forEach(agent -> habitat.getOagents().add(agent));
        habitat.getDwarfOagents().clear();
        System.out.println("o ajanlarının sayısı: "+habitat.getOagents().size());
        

    }

    public void setAgentsNumber(int agentsNumber) {
        this.agentsNumber = agentsNumber;
    }

    public void setSelectionsNumber(int selectionsNumber) {
        this.selectionsNumber = selectionsNumber;
    }

    /**
     * @param mutation Density between 0.0 - 1.0 should be
     *                 <p>
     *                 What will be the density of mutations?
     *                 </p>
     * 
     */
    public void setMutationDensity(float mutationDensity) {
        this.mutationDensity = mutationDensity;
    }

    public AgentsHabitat getHabitat() {
        return habitat;
    }
}
