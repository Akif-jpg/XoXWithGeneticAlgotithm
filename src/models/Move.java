package models;

public class Move {
    public int position;
    public Value value;

    public Move(int position, Value value) {
        super();
        this.position = position;
        this.value = value;
    }

    public Move() {
        super();
    }

    public void printMove(){
        System.out.println("position %d value %s".formatted(position, value));
    }

}
