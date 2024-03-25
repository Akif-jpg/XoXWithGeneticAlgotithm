package geneticalgorithm;

import java.util.LinkedList;
import java.util.stream.IntStream;

import agents.Agent;
import agents.Oagent;
import agents.Xagent;

/**
 * AgentsHabitat
 * storage for agents and its status
 */
public class AgentsHabitat {
    private LinkedList<Agent> xagents;
    private LinkedList<Agent> oagents;

    private LinkedList<Agent> winnerXagents;
    private LinkedList<Agent> winnerOagents;

    private LinkedList<Agent> dwarfXagents;
    private LinkedList<Agent> dwarfOagents;

    //----------------------------------------------------------------
    public AgentsHabitat(){
        this.xagents = new LinkedList<Agent>();
        this.oagents = new LinkedList<Agent>();

        this.winnerXagents = new LinkedList<Agent>();
        this.winnerOagents = new LinkedList<Agent>();

        this.dwarfXagents = new LinkedList<Agent>();
        this.dwarfOagents = new LinkedList<Agent>();

    
    }
    //----------------------------------------------------------------

        /**
     * @return Map key is x agent number and value is o agent number
     */
    public void setRandomMatches() {
        // Her iki liste içinde elemanları uzunluğunu kapsayan bir liste oluşturuldu
        int[] x = IntStream.range(0, this.getXagents().size()).toArray();
        int[] o = IntStream.range(0, this.getOagents().size()).toArray();

        // Her iki listenin elemanları da kendi içlerinde karıştırıldı
        IntStream.range(0, x.length)
                .forEach(i -> {
                    int temp = x[i];
                    int randomIndex = (int) (Math.random() * (x.length - i) + i);
                    x[i] = x[randomIndex];
                    x[randomIndex] = temp;
                });
        
        IntStream.range(0, o.length)
                .forEach(i -> {
                    int temp = o[i];
                    int randomIndex = (int) (Math.random() * (o.length - i) + i);
                    o[i] = o[randomIndex];
                    o[randomIndex] = temp;
                });
    

        for(int i = 0; i < x.length; i++){
            Agent agent = this.xagents.get(i);
            this.xagents.set(i, this.xagents.get(x[i]));
            this.xagents.set(x[i], agent);
        }

        for(int i = 0; i < o.length; i++){
            Agent agent = this.oagents.get(i);
            this.oagents.set(i, this.oagents.get(o[i]));
            this.oagents.set(o[i], agent);
        }

    }
    public void generateAgents(int generateX, int generateO) {
        IntStream.range(0, generateX).forEach(x -> {
            System.out.println("x ajanı üretildi number: " + x);
            this.xagents.add(new Xagent());
        });
        IntStream.range(0, generateO).forEach(x -> {
            System.out.println("o ajanı üretildi number: " + x);
            this.oagents.add(new Oagent());
        });
    }

    public LinkedList<Agent> getXagents() {
        return xagents;
    }

    public LinkedList<Agent> getOagents() {
        return oagents;
    }

    public LinkedList<Agent> getWinnerXagents() {
        return winnerXagents;
    }

    public LinkedList<Agent> getWinnerOagents() {
        return winnerOagents;
    }

    public LinkedList<Agent> getDwarfXagents() {
        return dwarfXagents;
    }

    public LinkedList<Agent> getDwarfOagents() {
        return dwarfOagents;
    }

    public void setXagents(LinkedList<Agent> xagents) {
        this.xagents = xagents;
    }

    public void setOagents(LinkedList<Agent> oagents) {
        this.oagents = oagents;
    }

    public void setWinnerXagents(LinkedList<Agent> winnerXagents) {
        this.winnerXagents = winnerXagents;
    }

    public void setWinnerOagents(LinkedList<Agent> winnerOagents) {
        this.winnerOagents = winnerOagents;
    }

    public void setDwarfXagents(LinkedList<Agent> dwarfXagents) {
        this.dwarfXagents = dwarfXagents;
    }

    public void setDwarfOagents(LinkedList<Agent> dwarfOagents) {
        this.dwarfOagents = dwarfOagents;
    }

}