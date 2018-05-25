package backend;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BlockedList 
{
	private static BlockedList blockedList = null;

	private ArrayList<String> blockedUsers = new ArrayList<String>();
	private ArrayList<Integer> dayOfBlock = new ArrayList<Integer>();
	private ArrayList<Integer> hourOfBlock = new ArrayList<Integer>();
	private ArrayList<Integer> minutesOfBlock = new ArrayList<Integer>();
	private ArrayList<Integer> secondsOfBlock = new ArrayList<Integer>();
	LocalDateTime date = LocalDateTime.now();

	private void BlockedList()
	{}

	public static BlockedList getBlockedList()
	{
		if(blockedList == null)
			blockedList = new BlockedList();
		return blockedList;
	}

	public boolean isUserBlocked(String user)
	{
		if(blockedUsers.size()==0)
			return false;
		else{
			for(int i = 0; i < blockedUsers.size(); i++)
			{
				if(user.contentEquals(blockedUsers.get(i)))
				{
					date = LocalDateTime.now();
					System.out.printf("Data de tentativa de login: dia "+date.getDayOfYear()+"; hora: "+date.getHour()+"; minutos: "+date.getMinute()+"; segundos: "+date.getSecond()+"\n");
					if((date.getSecond()+date.getMinute()*60+date.getHour()*3600) - (secondsOfBlock.get(i)+minutesOfBlock.get(i)*60+date.getDayOfYear()*3600) < 2*60)
					{
						return true;
					}
					blockedUsers.remove(i);
					secondsOfBlock.remove(i);
					minutesOfBlock.remove(i);
					hourOfBlock.remove(i);
					dayOfBlock.remove(i);
				}
			}
			return false;
		}
	}

	public void addBlockedUser(String user)
	{
		if(isUserBlocked(user) == false)
		{
			date = LocalDateTime.now();
			blockedUsers.add(user);
			dayOfBlock.add(date.getDayOfYear());
			hourOfBlock.add(date.getHour());
			minutesOfBlock.add(date.getMinute());
			secondsOfBlock.add(date.getSecond());
			System.out.printf("Data de bloqueio: dia "+date.getDayOfYear()+"; hora: "+date.getHour()+"; minutos: "+date.getMinute()+"; segundos: "+date.getSecond()+"\n");
		}
	}

}
