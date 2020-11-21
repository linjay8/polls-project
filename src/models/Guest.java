package models;
public class Guest extends User
{

	public Guest(String name_, String email_, int userId_)
	{
		super( name_,  email_,  0,  userId_);
	}

	@Override
	public String getFullName()
	{
		return "Anonymous";
	}


}