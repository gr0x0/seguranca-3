package logview;

import java.awt.BorderLayout;

import javax.swing.JRootPane;
import backend.Connect;
import backend.Query;

public class LogView
{
	static LogView logView = null;
	
	private LogView()
	{	}

	public static LogView getLogView() throws ClassNotFoundException
	{
		if(logView == null)
		{
//			new Connect("C:/Sqlite/BDSeg");
			logView = new LogView();
		}
		return logView;
	}
}