package models;
import java.util.ArrayList;

public class Class {
    // instructor, student list
    private ArrayList<Poll> pollList;

    public Class(){
        pollList = new ArrayList<Poll>();
    }

    public ArrayList<Poll> getPollList() {
        return pollList;
    }
}
