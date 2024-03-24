package models;

import java.util.HashMap;

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
    public void add(MoveTree moveTree) {
        if (this.nextTree == null) {
            this.nextTree = moveTree;
        } else {
            this.nextTree.add(moveTree);
        }
    }

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

    public int size() {
        if (this.nextTree == null) {
            return 1;
        }

        return this.nextTree.size() + 1;
    }

    public MoveTree get(int index) {
        if (index == 0) {
            return this;
        } else if (this.nextTree != null) {
            return this.nextTree.get(index--);
        }

        throw new IllegalStateException("Invalid index  out of range: " + index);
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
