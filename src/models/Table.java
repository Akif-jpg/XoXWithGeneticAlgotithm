package models;

import java.util.Arrays;

public class Table implements Cloneable{
    private Value[] values;
    private final int length = 9;

    protected Table(Value[] values) {
        this.values = values;
    }

    public Table(){
        values = new Value[this.length];
        Arrays.setAll(values, value -> Value.E);
    }

    public static Table fromString(String str){
        String[] charset = str.split(" ");
        Table table = new Table();
        for(int i = 0; i < 9; i++){
            switch(charset[i].toUpperCase()){
                case "X":
                    table.getValues()[i] = Value.X;  
                    break;          
                case "O":
                    table.getValues()[i] = Value.O;
                    break;
                default:
                    table.getValues()[i] = Value.E;
                break;
            }
        }
        return table;
    }

    //Tabloyu yazdır
    public void printTable(){
       
        for(int i=0; i<length; i++){
            if(i%3==0){
                System.out.println("");
            }
            System.out.print(" "+values[i]);
        }
        System.out.println("");
    }

    //Tablo üzerinde hamle yapacak bir sınıf
    public void move(Move move) {
        values[move.position] = move.value;
    }

    public Value[] getValues() {
        return values;
    }

    public void setValues(Value[] values) {
        this.values = values;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass()){
            return false;
        }
       Table table = (Table) obj;
       return Arrays.equals(table.getValues(), this.getValues());
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return new Table(this.values.clone());
    }
    
}
