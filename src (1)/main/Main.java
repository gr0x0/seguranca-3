package main;

import java.sql.SQLException;

import backend.ControladorBackend;
import backend.Query;

public class Main 
{
	public static void main(String[] args) throws ClassNotFoundException
	{
		ControladorBackend.getCtrlBE();
		if(args[0].contentEquals("logView"))
		{
			try {
				Query.getReg();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}