package org.hvl.TestBed;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
	
	private int portno;
	private ServerSocket serverSocket;
	
	public HTTPServer(int portNumber) {
		this.portno = portNumber;
	}

	/**
	 * Creates a SocketServer object and starts the server.
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	
	public static void main(String[] args) throws InterruptedException, IOException {
		int portNumber = Integer.parseInt(args[0]);

		if (args.length < 1) {
		    System.err.println("Not enough arguments received.");
		    return;
		}
		// initializing the Socket Server
		HTTPServer socketServer = new HTTPServer(
				portNumber);
		socketServer.start();
	}

	private void start() throws IOException, InterruptedException {
		serverSocket = new ServerSocket(portno);
		System.out.println("Starting the socket server at port:" + portno);

		Socket client = null;

		while (true) {
			System.out.println("Waiting for clients...");
			client = serverSocket.accept();
			System.out.println("The following client has connected:"
					+ client.getInetAddress().getCanonicalHostName());
			// A client has connected to this server. Send welcome message
			Thread thread = new Thread(new ClientSocketHandler(client));
			thread.start();
				

       }
		}
	}
		
	




