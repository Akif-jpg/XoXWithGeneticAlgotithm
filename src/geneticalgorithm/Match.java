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
    int maxMatchNumber;
    // ----------------------------------------------------------------

    public void setMaxMatchNumber(int maxMatchNumber) {
        this.maxMatchNumber = maxMatchNumber;
    }

    public Match() {
        super();
        habitat = new AgentsHabitat();
        maxMatchNumber = 1500;
    }

    public Match(AgentsHabitat habitat) {
        super();
        this.habitat = habitat;
        maxMatchNumber = 1500;
    }
    // ----------------------------------------------------------------

    public void play() {
        LinkedList<Agent> xagents = habitat.getXagents();
        LinkedList<Agent> oagents = habitat.getOagents();
        int matchNumber = Math.min(maxMatchNumber,Math.min(xagents.size(), oagents.size()));
        System.out
                .println("Eşleştirmeler başladı " + matchNumber + " kadar maç oynanacak");
        habitat.setRandomMatches();
        Table table;
        Xagent x;
        Oagent o;
        Agent agent;
        int i = 0;
        int winnerX = 0;
        int winnerO = 0;
        int draw = 0;
        for (; i < matchNumber; i++) {
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
                    // Kazanan ajan kazananlar ağacına kaydedilecek ve kaybeden ajan ise yok
                    // edilecek
                    if (j % 2 == 0) {
                        x.reset();
                        habitat.getWinnerXagents().add(x);
                        winnerX++;

                    } else {
                        o.reset();
                        habitat.getWinnerOagents().add(o);
                        winnerO++;
                    }
                    break;
                } else if (!moveable(table)) {
                    // Her iki ajanda birden berabere kalanlar uzayına eklenecek
                    x.reset();
                    o.reset();
                    habitat.getDrawOagents().add(o);
                    habitat.getDrawXagents().add(x);
                    draw++;

                }
            }

        }

        System.out.println("Beraberlik sayısı: "+ draw);
        System.out.println("Kazanan X Sayısı: "+ winnerX);
        System.out.println("Kazanan O sayısı: "+ winnerO);

        for (; i < Math.max(xagents.size(), oagents.size()); i++) {
            if (xagents.size() > oagents.size()) {
                if(xagents.size() < maxMatchNumber)
                    habitat.getDrawXagents().add(xagents.get(i));
            } else if(oagents.size() < maxMatchNumber) {
                habitat.getDrawOagents().add(oagents.get(i));
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