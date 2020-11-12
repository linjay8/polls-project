import java.util.ArrayList;

public class Student extends User {
    private ArrayList<Class> classes;
    private ArrayList<Poll> privatePolls;

    public Student(String name_, String email_, int userId_){
        super( name_,  email_,  1,  userId_);
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public void setPrivatePolls() {
        privatePolls.clear();
        for(Class c : classes){
            for (Poll p : c.getPollList()){
                privatePolls.add(p);
            }
        }
    }

    public ArrayList<Poll> getPrivatePolls() {
        return privatePolls;
    }
}
