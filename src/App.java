
import geneticalgorithm.Darwin;
import models.Move;
import models.Table;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Iterator;

import agents.Agent;

public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // İşin en önemli kısmına nihayet gelebildik.
        Darwin drw = new Darwin();
        drw.setMutationDensity(0.3f);
        drw.startSelection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Yazmaya başlayabilisin");
        while(true){
            int a = Integer.decode(reader.readLine());
            drw.getHabitat().getOagents().get(a).printMoveTree();
        }   

    }
}
