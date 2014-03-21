package com.proj;

import java.util.Arrays;
import java.util.List;

import com.proj.client.Client;
import com.proj.server.Server;

/**
 * This class runs the appropriate code depending on
 * command line argument!
 */
public class MainClass {

	public static void main(String[] args) {
		List<String> a = Arrays.asList(args);
		
		if (a.contains("--help") || a.contains("-h")) {
			System.out.println(
				"Supreme Calendar!\n" +
				"Welcome to Supreme Calendar - the ultimate calendar for all your\n" +
				"calendar needs. Here is a quick introduction:\n\n" +
				"-s --server              Starts the server software\n" +
				"-h --help                Displays this introduction\n" +
				"\nFor the client:\n" +
				"-a --address <addr>      Server ip or hostname\n" +
				"-p --port <port>         Server port\n" +
				//"\nFor the server software, you can also add additional parameters:\n" +
				//"-m --mysql-host <addr>   The server running mysql (localhost by default)\n" +
				//"-u --mysql-user <user>   Mysql username\n" +
				//"-p --mysql-pass <pass>   Mysql password\n" +
				"\nEnjoy!"
			);
			return;
		}
		
		if (a.contains("--server") || a.contains("-s")) {
			Server.main(args);
		} else {
			Client.main(args);
		}
	}

}
