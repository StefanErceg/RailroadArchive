package org.unibl.etf.mdp.railroad;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.logging.Level;

import org.unibl.etf.mdp.railroad.archive.Archive;
import org.unibl.etf.mdp.railroad.archive.ArchiveInterface;

public class ArchiveServer {
	
	private static String SERVER_POLICY;
	private static Integer FILE_PORT;
	
	public static ErrorLog errorLog = new ErrorLog();
	
	public static void main(String[] args) {
		try {
			if (!Configuration.checkIfFileExists()) {
				Configuration.writeConfiguration();
			}
			Properties properties = Configuration.readParameters();
			SERVER_POLICY = properties.getProperty("SERVER_POLICY");
			FILE_PORT = Integer.valueOf(properties.getProperty("FILE_PORT"));
			System.setProperty("java.security.policy",  SERVER_POLICY);
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			Archive archive = new Archive();
			ArchiveInterface stub = (ArchiveInterface) UnicastRemoteObject.exportObject(archive, 0);
			Registry registry = LocateRegistry.createRegistry(FILE_PORT);
			registry.rebind("Archive", stub);
		} catch (Exception e) {
			errorLog.getLogger().log(Level.SEVERE, e.fillInStackTrace().toString());
		}
	}
}
