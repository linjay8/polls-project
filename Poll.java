import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Poll {
    private int pollID;
    private int voterCount = 0 ;
    private Instructor creator;
    private Class myClass;
  //  private Boolean resultVisibility;
    private Boolean isPublic;
    private ArrayList<Response> options;   // List of possible answers (a, b, c, d)
    private ArrayList<Student> studentList;
    private Map<Integer, Integer> voteMap = new HashMap<Integer, Integer>();
    private Map<Integer, ArrayList<Student>> studentResponders = new HashMap<Integer, ArrayList<Student>>();



    public Poll(int _pollID, Instructor _creator, Class _myClass, Boolean _isPublic,
                ArrayList<Response> _options, ArrayList<Student> _studentList){
       pollID = _pollID;
       creator = _creator;
       myClass = _myClass;
       isPublic = _isPublic;
       options = _options;
       studentList = _studentList;

        for (Response r: _options){
            voteMap.put(r.getResponseID(), 0);
            studentResponders.put(r.getResponseID(), new ArrayList<Student>());
        }

    }

    public Poll(){

    }

    public int getPollID() {
        return pollID;
    }

    public Instructor getCreator() {
        return creator;
    }

    public void addVote(Response r, Student s){
        // Update total for this response
        int count = voteMap.get(r.getResponseID());
        count++;
        voteMap.put(r.getResponseID(), count);

        // Add to student list
        studentResponders.get(r.getResponseID()).add(s);

        // Update total voter count
        voterCount++;

        // Update count in database?
    }

    public int getVoterCount(){
        return voterCount;
    }

    // Determines if poll is visible based on User type
//    public Boolean getResultVisibility(User user) {
//        // use myclass
//
//        if (isPublic){
//            return true;
//        }
//
//        if (user.getAccountLevel() == 0){
//            return false;
//        }
//        else if (user.getAccountLevel() == 1){
//            return true;
//        }
//        else {
//            return true;
//        }
//
//    }

    public Boolean isPublic(){
        return isPublic;
    }

    public int getResponseCount(int responseID){
        return voteMap.get(responseID);
    }


    public Map<Integer, Integer> getVoteMap() {
        return voteMap;
    }
}
