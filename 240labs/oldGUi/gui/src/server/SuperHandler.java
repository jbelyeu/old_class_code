/**
 * 
 */
package server;

import java.io.IOException;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;



/**Exists solely to relate handlers
 * @author jbelyeu
 *
 */
public abstract class SuperHandler implements HttpHandler
{
	protected static Logger logger = Logger.getLogger("recordIndexer"); 
	protected XStream xmlStream = new XStream(new DomDriver());
	
	protected static void initLog() throws IOException 
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
}
