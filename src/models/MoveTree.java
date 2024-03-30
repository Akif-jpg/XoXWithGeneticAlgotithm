package models;

import java.util.HashMap;
import java.util.Iterator;

public class MoveTree implements Cloneable {
    public MoveTree nextTree;
    public HashMap<Table, Move> tableMoveTree;

    public MoveTree() {
        tableMoveTree = new HashMap<Table, Move>();

    }

    protected MoveTree(MoveTree nexTree, HashMap<Table, Move> tableMoveList) {
        this.nextTree = nexTree;
        this.tableMoveTree = tableMoveList;
    }

    // ----------------------------------------------------------------
    /**
     * add new node to movetree
     * @param moveTree
     */
    public void add(MoveTree moveTree) {
        if (this.nextTree == null) {
            this.nextTree = moveTree;
        } else {
            this.nextTree.add(moveTree);
        }
    }
    /**
     * this function delete last item from movetree
     * @return false if this node is not the last node, else returns true
     */
    public boolean deleteLastItem() {
        if (this.nextTree != null && this.nextTree.deleteLastItem()) {
            this.nextTree = null;
            return false;
        }
        if (this.nextTree == null) {
            return true;
        }
        return false;
    }
    /**
     * 
     * @return length of this MoveTree
     */
    public int size() {
        if (this.nextTree == null) {
            return 1;
        }

        return this.nextTree.size() + 1;
    }
    /**
     * 
     * @param moveTree
     * @return models.MoveTree object equals moveTree
     */
    public MoveTree get(MoveTree moveTree) {
        if (this.equals(moveTree)) {
            return this;
        } else if (this.nextTree != null) {
            return this.nextTree.get(moveTree);
        }

        throw new IllegalStateException("Invalid index  out of range ");

    }

    /**
     * 
     * @param index
     * @return movetree by index
     */
    public MoveTree get(int index) {
        if (index <= 0) {
            return this;
        } else if (this.nextTree != null) {
            return this.nextTree.get(index--);
        }else{
            throw new IllegalStateException("Invalid index  out of range: " + index);
        }
    }

    /**
     * @param nodeNumber
     * @param tableList
     */
    @SuppressWarnings("unchecked")
    public void change(int nodeNumber, HashMap<Table, Move> tableMoveList) {
        if (nodeNumber != 0) {
            if (nextTree != null)
                this.nextTree.change(nodeNumber--, tableMoveList);
            else
                throw new IllegalStateException("nodeNumber is out of range");
        } else {
            this.tableMoveTree = (HashMap<Table, Move>) tableMoveList.clone();
        }

    }

    public void merge(MoveTree tree){
        Iterator<Table> itr = tree.tableMoveTree.keySet().iterator();
        while(itr.hasNext()) {
            Table tbl = itr.next();
            //Eğer her iki tablodaki her bir eleman birbirine eşit ise eleman silinsin
            this.tableMoveTree.keySet().removeIf(table -> tbl.equals(table));
            //merge işlemi yapılsın
            this.tableMoveTree.put(tbl, tree.tableMoveTree.get(tbl));
        }
        //Sıradaki ağaç için de merge işlemi yapılsın
        if(tree.hasNext()&&this.hasNext()){
            this.nextTree.merge(tree.nextTree);
        }
    }


    public boolean hasNext() {
        return this.nextTree != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() throws CloneNotSupportedException {
        if (this.nextTree == null) {
            return new MoveTree(null, (HashMap<Table, Move>) tableMoveTree.clone());
        }
        return new MoveTree((MoveTree) this.nextTree.clone(), (HashMap<Table, Move>) tableMoveTree.clone());
    }

}
