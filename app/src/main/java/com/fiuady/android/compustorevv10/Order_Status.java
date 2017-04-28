package com.fiuady.android.compustorevv10;

/**
 * Created by Manuel on 19/04/2017.
 */
public class Order_Status {
    private int Id;
    private String Description;
    private int Editable;
    private int Previous;
    private int Next;

    public Order_Status(int id, String description, int editable, int previous, int next) {
        Id = id;
        Description = description;
        Editable = editable;
        Previous = previous;
        Next = next;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getEditable() {
        return Editable;
    }

    public void setEditable(int editable) {
        Editable = editable;
    }

    public int getPrevious() {
        return Previous;
    }

    public void setPrevious(int previous) {
        Previous = previous;
    }

    public int getNext() {
        return Next;
    }

    public void setNext(int next) {
        Next = next;
    }
}
