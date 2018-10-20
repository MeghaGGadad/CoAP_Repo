package org.hvl.TestBed;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	
	
	
	public static void getMethod(String host, int port, String path)
			throws IOException {

		// Opening Connection based on the port number 80(HTTP) and 443(HTTPS)
		Socket clientSocket = null;
		clientSocket = new Socket(host, port);

		System.out.println("======================================");
		System.out.println("Connected");
		System.out.println("======================================");

		// Declare a writer to this url
		PrintWriter request = new PrintWriter(clientSocket.getOutputStream(),
				true);

		// Declare a listener to this url
		BufferedReader response = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));

		// Sending request to the server
		// Building HTTP request header
		request.print("GET /" + path + "/ HTTP/1.1\r\n"); // "+path+"
		request.print("Host: " + host + "\r\n");
		request.print("Connection: close\r\n");
		request.print("Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n");
		request.print("\r\n");
		request.flush();
		System.out.println("Request Sent!");
		System.out.println("======================================");

		// Receiving response from server
		String responseLine;
		while ((responseLine = response.readLine()) != null) {
			System.out.println(responseLine);
		}
		System.out.println("======================================");
		System.out.println("Response Recieved!!");
		System.out.println("======================================");

		response.close();
		request.close();
		clientSocket.close();
	}

	


   }




