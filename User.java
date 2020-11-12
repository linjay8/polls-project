import java.util.ArrayList;

public class User{
    private String name;
    private String email;
    private  int accountLevel;    // guest = 0,  student = 1,  instructor = 2
    private  int userId;
    private ArrayList<Poll> publicPolls;

    public User(String name_, String email_, int accountLevel_, int userId_){
        name = name_;
        email = email_;
        accountLevel = accountLevel_;
        userId = userId_;
    }

    public User(){
        name = "";
        email = "";
        accountLevel = -1;
        userId = -1;
    }

    public ArrayList<Poll> getPublicPolls(){
        // Database query
        // Save info to publicPolls

        return publicPolls;
    }

    public String getFullName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public int getAccountLevel() {
        return accountLevel;
    }

    public int getUserId() {
        return userId;
    }

    public void addPublicPoll(Poll p){
        publicPolls.add(p);
    }
}