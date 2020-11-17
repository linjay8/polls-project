import java.util.ArrayList;

public class Instructor extends User
{
	private ArrayList<Class> classes;
	private OfficeHours officeHours;
	private ArrayList<Poll> privatePolls;


	public Instructor(String name_, String email_, int userId_)
	{
		super( name_,  email_,  2,  userId_);
	}

	public ArrayList<Class> getClasses() 
	{
		return classes;
	}

	public ArrayList<Poll> getPrivatePolls() {
		return privatePolls;
	}

	/** Called when "create poll" button is pressed **/
	public void createPublicPoll()
	{
		// Collect information from website
		// Prompt instructor for how many questions they want to create
		// loop to create response objects
		// need 2 to 4 of strings for options
		Poll p = new Poll();
		addPublicPoll(p);
	}

	public void createPrivatePoll()
	{
		// Collect information from website
		Poll p = new Poll();
		privatePolls.add(p);
	}


	public void setPrivatePolls()
	{
		privatePolls.clear();
		for(Class c : classes){
			for (Poll p : c.getPollList()){
				privatePolls.add(p);
			}
		}
	}
	public ArrayList<Poll> getPrivatePolls()
	{
		return privatePolls;
	}

	public OfficeHours getOfficeHours() 
	{
		return officeHours;
	}

	public void createOfficeHours()
	{

	}

}