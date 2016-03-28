/**
 * 
 */
package server.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**Class to restart DB. May add functionality to reload individual tables, for now can only do who DB
 * @author jbelyeu
 *
 */
public class DatabaseCleaner
{

	public static void clean (Database db, boolean commit)
	{
		db.startTransaction();
		cleanProjects(db);
		cleanUsers(db);
		cleanFields(db);
		cleanBatches(db);
		cleanFieldValues(db);
		cleanRecords(db);		
		db.endTransaction(commit);
	}
	
	
	private static void cleanProjects(Database db)
	{
		try
		{
			PreparedStatement dropStat = null;
			String sql = "DROP TABLE IF EXISTS Projects;";
			dropStat = db.getConnection().prepareStatement(sql);
			dropStat.executeUpdate();
			
			PreparedStatement createStat = null;
			sql = "CREATE TABLE Projects (ID INTEGER PRIMARY KEY autoincrement NOT NULL UNIQUE, "
					+ "Title VARCHAR NOT NULL UNIQUE, RecordsPerImage INTEGER NOT NULL, "
					+ "FirstYCoordinate INTEGER NOT NULL, RecordHeight INTEGER NOT NULL);";
			
			createStat = db.getConnection().prepareStatement(sql);
			createStat.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("Failed to clean and reload database table Projects");
			e.printStackTrace();
		}
	}
	
	private static void cleanUsers(Database db)
	{
		try
		{
			PreparedStatement dropStat = null;
			String sql = "DROP TABLE IF EXISTS Users;";
			dropStat = db.getConnection().prepareStatement(sql);
			dropStat.executeUpdate();
			
			PreparedStatement createStat = null;
			sql = "CREATE TABLE Users (ID INTEGER PRIMARY KEY autoincrement NOT NULL UNIQUE, "
					+ "UserName VARCHAR NOT NULL UNIQUE, Password VARCHAR NOT NULL, "
					+ "FirstName VARCHAR NOT NULL, LastName VARCHAR NOT NULL, "
					+ "Email VARCHAR NOT NULL, RecordsIndexed INTEGER, CurrentBatch VARCHAR);";
			
			createStat = db.getConnection().prepareStatement(sql);
			createStat.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("Failed to clean and reload database table Users");
			e.printStackTrace();
		}
	}
	
	private static void cleanFields(Database db)
	{
		try
		{
			PreparedStatement dropStat = null;
			String sql = "DROP TABLE IF EXISTS Fields;";
			dropStat = db.getConnection().prepareStatement(sql);
			dropStat.executeUpdate();
			
			PreparedStatement createStat = null;
			sql = "CREATE TABLE Fields (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
					+ "ProjectID INTEGER NOT NULL, FieldTitle VARCHAR NOT NULL, "
					+ "XCoordinate INTEGER NOT NULL, Width INTEGER NOT NULL, "
					+ "FieldHelpFileName VARCHAR NOT NULL, KnownDataFileName VARCHAR, "
					+ "FieldNumber INTEGER NOT NULL);";
			
			createStat = db.getConnection().prepareStatement(sql);
			createStat.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("Failed to clean and reload database table Fields");
			e.printStackTrace();
		}
	}
	
	private static void cleanBatches(Database db)
	{
		try
		{
			PreparedStatement dropStat = null;
			String sql = "DROP TABLE IF EXISTS Batches;";
			dropStat = db.getConnection().prepareStatement(sql);
			dropStat.executeUpdate();
			
			PreparedStatement createStat = null;
			sql = "CREATE TABLE Batches (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
					+ "ProjectID INTEGER NOT NULL, ImagePath VARCHAR NOT NULL, Assigned INTEGER);";
			
			createStat = db.getConnection().prepareStatement(sql);
			createStat.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("Failed to clean and reload database table Batches");
			e.printStackTrace();
		}		
	}
	
	private static void cleanFieldValues(Database db)
	{
		try
		{
			PreparedStatement dropStat = null;
			String sql = "DROP TABLE IF EXISTS FieldValues;";
			dropStat = db.getConnection().prepareStatement(sql);
			dropStat.executeUpdate();
			
			PreparedStatement createStat = null;
			sql = "CREATE TABLE FieldValues (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
					+ "RecordID INTEGER NOT NULL, RecordNumber INTEGER NOT NULL, "
					+ "RecordText VARCHAR NOT NULL, FieldID INTEGER NOT NULL );";
			
			createStat = db.getConnection().prepareStatement(sql);
			createStat.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("Failed to clean and reload database table FieldValues");
			e.printStackTrace();
		}
	}
	
	private static void cleanRecords(Database db)
	{
		try
		{
			PreparedStatement dropStat = null;
			String sql = "DROP TABLE IF EXISTS Records;";
			dropStat = db.getConnection().prepareStatement(sql);
			dropStat.executeUpdate();
			
			PreparedStatement createStat = null;
			sql = "CREATE TABLE Records (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
					+ "BatchID INTEGER NOT NULL, RowNumber INTEGER NOT NULL);";
			
			createStat = db.getConnection().prepareStatement(sql);
			createStat.executeUpdate();
		}
		catch(SQLException e)
		{
			System.out.println("Failed to clean and reload database table Records");
			e.printStackTrace();
		}		
	}
}
