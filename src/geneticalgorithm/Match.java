package geneticalgorithm;

import java.util.Arrays;
import java.util.LinkedList;

import agents.Agent;
import agents.Oagent;
import agents.Xagent;
import models.Table;
import models.Value;

public class Match {
    AgentsHabitat habitat;
    // ----------------------------------------------------------------

    public Match() {
        super();
        habitat = new AgentsHabitat();
    }

    public Match(AgentsHabitat habitat) {
        super();
        this.habitat = habitat;
    }
    // ----------------------------------------------------------------

    public void play() {
        LinkedList<Agent> xagents = habitat.getXagents();
        LinkedList<Agent> oagents = habitat.getOagents();
        System.out
                .println("Eşleştirmeler başladı " + Math.min(xagents.size(), oagents.size()) + " kadar maç oynanacak");
        habitat.setRandomMatches();
        Table table;
        Xagent x;
        Oagent o;
        Agent agent;
        int i = 0;
        for (; i < Math.min(xagents.size(), oagents.size()); i++) {
            table = new Table();
            x = (Xagent) xagents.get(i);
            o = (Oagent) oagents.get(i);
            for (int j = 0; j < 9; j++) {
                if (j % 2 == 0) {
                    agent = x;
                } else {
                    agent = o;
                }
                table = agent.move(table);
                if (isWinned(table)) {
                    // Maç kazanılmışsa
                    System.out.println("Maçı: %s kazandı".formatted(agent.getAgentValue()));
                    // Kazanan ajan kazananlar ağacına kaydedilecek ve kaybeden ajan ise yok
                    // edilecek
                    if (j % 2 == 0) {
                        x.reset();
                        habitat.getWinnerXagents().add(x);

                    } else {
                        o.reset();
                        habitat.getWinnerOagents().add(o);
                    }
                    break;
                } else if (!moveable(table)) {
                    System.out.println("Maç berabere bitti");
                    // Her iki ajanda birden berabere kalanlar uzayına eklenecek
                    x.reset();
                    o.reset();
                    habitat.getDwarfOagents().add(o);
                    habitat.getDwarfXagents().add(x);

                }
            }

        }
        for (; i < Math.max(xagents.size(), oagents.size()); i++) {
            if (xagents.size() > oagents.size()) {
                habitat.getDwarfXagents().add(xagents.get(i));
            } else {
                habitat.getDwarfOagents().add(oagents.get(i));
            }
        }
    }

    /**
     * @param models.Table
     * @return table moveable or not
     *         {@summary !Arrays.stream(table.getValues()).allMatch(v ->
     *         !v.equals(Value.E))}
     */
    public boolean moveable(Table table) {
        return !Arrays.stream(table.getValues()).allMatch(v -> !v.equals(Value.E));
    }

    /**
     * Bu fonksiyon verilen bir table içerisindeki oyunun bitip bitmediğini kontrol
     * eder
     * 
     * @param models.Table
     * @return this game is winned or not as true or false
     */
    public boolean isWinned(Table table) {
        Value[] values = table.getValues();
        // satırları kontrol et
        for (int i = 0; i < 7; i += 3) {
            if (values[i].equals(values[i + 1]) && values[i + 1].equals(values[i + 2]) && values[i] != Value.E) {
                return true;
            }
        }

        // sütunları kontrol et
        for (int i = 0; i <= 2; i++) {
            if (values[i].equals(values[i + 3]) && values[i + 3].equals(values[i + 6]) && values[i] != Value.E) {
                return true;
            }
        }
        // çapraz yerleri kontrol et

        if (values[4] != Value.E && values[0].equals(values[4]) && values[4].equals(values[8])) {
            return true;
        }

        if (values[4] != Value.E && values[2].equals(values[4]) && values[4].equals(values[6])) {
            return true;
        }

        return false;

    }
}