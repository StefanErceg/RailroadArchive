package org.unibl.etf.mdp.railroad;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.unibl.etf.mdp.railroad.archive.Archive;
import org.unibl.etf.mdp.railroad.archive.ArchiveInterface;

public class ArchiveServer {
	
	public static final String DIRECTORY =  System.getProperty("user.home") + File.separator + "Railroad" + File.separator + "Archive";
	
	public static void main(String[] args) {
		System.setProperty("java.security.policy", DIRECTORY + File.separator + "policy" + File.separator +  "server_policyfile.txt");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Archive archive = new Archive();
			ArchiveInterface stub = (ArchiveInterface) UnicastRemoteObject.exportObject(archive, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("Archive", stub);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
