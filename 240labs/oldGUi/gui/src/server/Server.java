/**
 * 
 */
package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import com.sun.net.httpserver.*;
import server.facade.*;

/**
 * @author jbelyeu
 *
 */
public class Server
{
	private static Logger logger;
	private static int portNumber;
	private static final int maxWaitingConnections = 10;
	
	static 
	{
		try 
		{
			initLog();
		}
		catch (IOException e) 
		{
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException 
	{
		Level logLevel = Level.FINE;
		logger = Logger.getLogger("Indexer");
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);	
	}
	
	private HttpServer server;
		
	private Server()
	{
		return;
	}
	
	private void run(String[] args)
	{
		logger.info("Initializing Model");

		try
		{
			ServerFacade.initialize();
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try 
		{
			server = HttpServer.create(new InetSocketAddress(portNumber), maxWaitingConnections);
		} 
		catch (IOException e) 
		{
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/GetProjects", getProjectsHandler);
		server.createContext("/GetFields", getFieldsHandler);
		server.createContext("/GetFile", getFileHandler);
		server.createContext("/DownLoadBatch", downLoadBatchHandler);
		server.createContext("/GetSampleImage", getSampleImageHandler);
		server.createContext("/SubmitBatch", submitBatchHandler);
		server.createContext("/Search", searchHandler);
		
		logger.info("Starting HTTP Server");

		server.start();
	}
	
	private HttpHandler validateUserHandler = new ValidateUserHandler();
	private HttpHandler getProjectsHandler = new GetProjectsHandler();
	private HttpHandler getFieldsHandler = new GetFieldsHandler();
	private HttpHandler getFileHandler = new GetFileHandler();
	private HttpHandler downLoadBatchHandler = new DownLoadBatchHandler();
	private HttpHandler getSampleImageHandler = new GetSampleImageHandler();
	private HttpHandler submitBatchHandler = new SubmitBatchHandler();
	private HttpHandler searchHandler = new SearchHandler();
	
	public static void main(String[] args) 
	{
		if (args.length > 0)
		{
			portNumber = Integer.parseInt(args[0]);
		}
		else
		{
			portNumber = 8080;
		}
		new Server().run(args);
	}
}
