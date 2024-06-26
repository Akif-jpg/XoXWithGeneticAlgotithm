package agents;

import java.util.Iterator;
import java.util.Random;

import models.Move;
import models.MoveTree;
import models.Table;
import models.Value;

public abstract class Agent implements Cloneable{
    protected Value agentValue;
    protected Random random;
    protected MoveTree moveTree;
    private MoveTree bufferedMoveTree;

    public Agent() {
        random = new Random();
        moveTree = new MoveTree();
        for (int i = 0; i < 5; i++) {
            moveTree.add(new MoveTree());
        }


        bufferedMoveTree = this.moveTree;
    }

    protected Agent(MoveTree moveTree) {
        random = new Random();
        this.moveTree = moveTree;
        bufferedMoveTree = this.moveTree;
    }

    public void reset() {
        this.bufferedMoveTree = moveTree;
    }

    public Table move(Table table) {
        Table play = new Table();
        // Eğer verilen table daha önceden karşılaşılmış hamle uzayı içerisinde mevcutsa
        Iterator<Table> itr = bufferedMoveTree.tableMoveTree.keySet().iterator();
        try {
            play = (Table) table.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (itr.hasNext()) {
            Table t1 = itr.next();
            if (t1.equals(table)) {
                play.move(bufferedMoveTree.tableMoveTree.get(t1));
                bufferedMoveTree = bufferedMoveTree.nextTree;
                return play;
            }
        }

        // Random hareketler ile oyun oynanacak
        int[] moveable = new int[9];
        int lengthMoveable = 0;
        for (int i = 0; i < 9; i++) {
            if (table.getValues()[i].equals(Value.E)) {
                moveable[lengthMoveable] = i;
                lengthMoveable++;
            }
        }
        int movePosition = moveable[random.nextInt(lengthMoveable)];
        models.Move move = new models.Move(movePosition, agentValue);
        try {
            play = (Table) table.clone();
            play.move(move);
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Mevcut table moveTree içerisine kaydedilir ve devam edilir
        bufferedMoveTree.tableMoveTree.put(table, move);
        bufferedMoveTree = bufferedMoveTree.nextTree;

        return play;
    }

    public void printMoveTree() {
        MoveTree clone;
        try {
            clone = (MoveTree) this.moveTree.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("can't clone move tree", e);
        }
        int n = 0;
        do {
            System.out.println("ağacın %d. dalı".formatted(n++));
            clone.tableMoveTree.entrySet().forEach(xox -> {
                xox.getKey().printTable();
                xox.getValue().printMove();
                System.out.println("--------------------------------");
            });
            clone = clone.nextTree;
        } while (clone.hasNext());
    }

        public Move findMove(Table table){
        MoveTree tree;  
        int moveNumber = 6;
        tree = this.moveTree;
        for(int i = 0; i < moveNumber; i++){            
            Iterator<Table> it =  tree.tableMoveTree.keySet().iterator();
            while(it.hasNext()){
               Table t = (Table) it.next();
               if(t.equals(table)){
                   return tree.tableMoveTree.get(t);
               }
            }
            tree = moveTree.nextTree;
        }
        

         return null;        
        
    }
    public Value getAgentValue(){
        return this.agentValue;
    }

    public MoveTree getMoveTree() {
        return this.moveTree;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        if(this.getAgentValue().equals(Value.X)){
            return new Xagent((MoveTree) this.moveTree.clone());
        }else{
            return new Oagent((MoveTree) this.moveTree.clone());
        }
    }

}
