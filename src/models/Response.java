package models;
import java.util.Random;
public class Response {
    // private Poll poll;
    private int responseID;
    private String text;

    public Response(String _text){
        text = _text;
    	Random rand = new Random();
    	responseID = rand.nextInt(1000);

        
    }


//    public double getPercent(){
//        double total = poll.getVoterCount();
//        double count = getResponseCount();
//
//        return count / total;
//    }

    public int getResponseID() {
        return responseID;
    }

    public String getText() {
        return text;
    }

//    public int getResponseCount(){
//        return poll.getResponseCount(responseID);
//    }
}
