public class Response {
    private Poll poll;
    private int responseID;
    private String text;

    public Response(Poll _poll, int _responseID, String _text){
        poll = _poll;
        responseID = _responseID;
        text = _text;
    }


    public double getPercent(){
        double total = poll.getVoterCount();
        double count = getResponseCount();

        return count / total;
    }

    public int getResponseID() {
        return responseID;
    }

    public String getText() {
        return text;
    }

    public int getResponseCount(){
        return poll.getResponseCount(responseID);
    }
}
