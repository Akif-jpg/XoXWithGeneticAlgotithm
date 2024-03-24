package geneticalgorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.IntStream;

import agents.Oagent;
import agents.Xagent;

/**
 * AgentsHabitat
 * storage for agents and its status
 */
public class AgentsHabitat {
    private LinkedList<Xagent> xagents;
    private LinkedList<Oagent> oagents;

    private LinkedList<Xagent> winnerXagents;
    private LinkedList<Oagent> winnerOagents;

    private LinkedList<Xagent> dwarfXagents;
    private LinkedList<Oagent> dwarfOagents;

    //----------------------------------------------------------------
    public AgentsHabitat(){
        this.xagents = new LinkedList<Xagent>();
        this.oagents = new LinkedList<Oagent>();

        this.winnerXagents = new LinkedList<Xagent>();
        this.winnerOagents = new LinkedList<Oagent>();

        this.dwarfXagents = new LinkedList<Xagent>();
        this.dwarfOagents = new LinkedList<Oagent>();
    }
    //----------------------------------------------------------------

        /**
     * @return Map key is x agent number and value is o agent number
     */
    public Map<Integer, Integer> setRandomMatches() {
        // Her iki liste içinde elemanları uzunluğunu kapsayan bir liste oluşturuldu
        int[] x = IntStream.range(0, this.getXagents().size()).toArray();
        int[] o = IntStream.range(0, this.getOagents().size()).toArray();

        // Her iki listenin elemanları da kendi içlerinde karıştırıldı
        
        IntStream.range(0, o.length)
                .forEach(i -> {
                    int temp = o[i];
                    int randomIndex = (int) (Math.random() * (o.length - i) + i);
                    o[i] = o[randomIndex];
                    o[randomIndex] = temp;
                });

        // İki arrayde birbirileri ile ilişkilendirilecek bir şekilde map edildi
        Map<Integer, Integer> resultMap = IntStream.range(0, Math.min(x.length, o.length))
                .boxed()
                .collect(HashMap::new,
                        (map, i) -> map.put(x[i], o[i]),
                        Map::putAll);

        return resultMap;
    }
    public void generateAgents(int generateX, int generateO) {
        IntStream.range(0, generateX).forEach(x -> xagents.add(new Xagent()));
        IntStream.range(0, generateO).forEach(o -> oagents.add(new Oagent()));
    }

    public LinkedList<Xagent> getXagents() {
        return xagents;
    }

    public LinkedList<Oagent> getOagents() {
        return oagents;
    }

    public LinkedList<Xagent> getWinnerXagents() {
        return winnerXagents;
    }

    public LinkedList<Oagent> getWinnerOagents() {
        return winnerOagents;
    }

    public LinkedList<Xagent> getDwarfXagents() {
        return dwarfXagents;
    }

    public LinkedList<Oagent> getDwarfOagents() {
        return dwarfOagents;
    }
}