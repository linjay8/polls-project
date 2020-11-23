package OfficeHours;

import java.util.*;

import models.DatabaseUtil;

public class OfficeHoursList {
	
	private static ArrayList<OfficeHours> ohList = new ArrayList<OfficeHours>();
	
	public static ArrayList<OfficeHours> getOHList()
	{
		return ohList;
	}
	
	public static OfficeHours getOH (String classCode)
	{
		for (OfficeHours oh : ohList)
		{
			if (classCode == oh.getClassCode())
			{
				return oh;
			}
		}
		return null;
	}
	
	public static void addOH (OfficeHours oh)
	{
		ohList.add(oh);
	}
	
	public static void removeOH (OfficeHours oh)
	{
		ohList.remove(oh);
	}

}
