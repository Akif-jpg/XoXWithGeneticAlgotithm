import java.util.Arrays;

import agents.Agent;
import agents.Oagent;
import agents.Xagent;
import geneticalgorithm.AgentsHabitat;
import geneticalgorithm.Darwin;
import geneticalgorithm.Match;
import models.*;


public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // oyunun durumunu tutacak olan table objesinin düzgün çalışıp çalışmadığının kontrol edilmesi.
        Table table = new Table();
        Arrays.stream(table.getValues()).forEach(value -> System.out.print(" " + value));
        System.out.println("");
        Table table2 = (Table) table.clone();
        table2.move(new Move(2, Value.X));
        Arrays.stream(table.getValues()).forEach(value -> System.out.print(" " + value));

        System.out.println("");
        // Oyunun hamle ağacını tutacak olan yapının test edilmesi
        MoveTree moveTree = new MoveTree();
        moveTree.add(new MoveTree());
        moveTree.add(new MoveTree());
        moveTree.add(new MoveTree());
        moveTree.add(new MoveTree());
        moveTree.add(new MoveTree());
        moveTree.add(new MoveTree());
        moveTree.deleteLastItem();
        System.out.println("eleman sayisi: "+moveTree.size());

        // x ve o ajanları oluşturup küçük bir oyun oynat
        Agent x = new Xagent();
        Agent o = new Oagent();
        Table t = new Table();
        t.printTable();
        System.out.println("x hamle yaptı: ");
        t = x.move(t);
        t.printTable();
        System.out.println("o hamle yaptı: ");
        t = o.move(t);
        t.printTable();
        System.out.println("x hamle yaptı: ");
        t = x.move(t);
        t.printTable();
        System.out.println("o hamle yaptı: ");
        t = o.move(t);
        t.printTable();

        System.out.println("Rastgele hamleler üzerinden yapilan değerlendirme tamamlandi");

        //Yapılan hamlelerin hamle ağacına doğru bir şekilde kayedidili edilmediğinin değerlendirilmesinin yapılması

        System.out.println("hamlelr yazdırılıyor.");
        System.out.println("x movetree".toUpperCase());
        x.printMoveTree();
        System.out.println("o movetree".toUpperCase());
        o.printMoveTree();
        
        //Maçı bitirip her iki ajanı da yeni bir maça başlatalım
        x.reset();
        o.reset();
        t= new Table();

        //yeni maç başlasın :D
        t.printTable();
        System.out.println("x hamle yaptı: ");
        t = x.move(t);
        t.printTable();
        System.out.println("o hamle yaptı: ");
        t = o.move(t);
        t.printTable();
        System.out.println("x hamle yaptı: ");
        t = x.move(t);
        t.printTable();
        System.out.println("o hamle yaptı: ");
        t = o.move(t);
        t.printTable();

        // Üçüncü maç başlasın
        System.out.println("ÜÇÜNCÜ MAÇ BAŞLADI");
        o = new Oagent();
        x.reset();
        t = new Table();

        t.printTable();
        System.out.println("x hamle yaptı: ");
        t = x.move(t);
        t.printTable();
        System.out.println("o hamle yaptı: ");
        t = o.move(t);
        t.printTable();
        System.out.println("x hamle yaptı: ");
        t = x.move(t);
        t.printTable();
        System.out.println("o hamle yaptı: ");
        t = o.move(t);
        t.printTable();

        //Yapılan hamlelerin hamle ağacına doğru bir şekilde kayedidili edilmediğinin değerlendirilmesinin yapılması

        System.out.println("hamleler yazdırılıyor.");
        System.out.println("x movetree".toUpperCase());
        x.printMoveTree();
        System.out.println("o movetree".toUpperCase());
        o.printMoveTree();

        // Match objesinin doğru çalışıp çalışmadığını kontrol edilmesi.
        Match match = new Match();
        // Oyunun devam ettirilip ettirilemeyeceğini söyleyen kod
        System.out.println("Bu oyun oynanabilir: "+match.moveable(t));
        System.out.println("Bu oyun oynanabilir: "+match.moveable(new Table()));
        Arrays.setAll(table2.getValues(), value -> Value.X);
        System.out.println("Bu oyun oynanabilir:"+match.moveable(table2));
        //Oyunun bitip bitmediğini söyleyen kod 
        System.out.println("Bu oyun kazanılmadı: "+match.isWinned(t));
        System.out.println("Bu oyun kazanılmadı: "+match.isWinned(new Table()));
        System.out.println("Bu oyun kazanıldı: "+match.isWinned(table2));

        //Rastgele bir gurup x ve o oluşturp maçı başlatalım
        AgentsHabitat habitat = new AgentsHabitat();
        habitat.generateAgents(15, 15);
        match = new Match(habitat);
        match.play();

        //İşin en önemli kısmına nihayet gelebildik.
        @SuppressWarnings("unused")
        Darwin drw = new Darwin();
    }
}
