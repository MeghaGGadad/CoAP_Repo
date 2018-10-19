package org.hvl.TestBed;

import java.io.IOException;
import java.net.UnknownHostException;


public class HTTPClient {
	
	
public static void main(String[] args) throws IOException {
		
		
	 while (true) {

		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String command = args[2];
		String path = args[3];
		//String payload = args[4];
		
		// Method Check GET or PUT
		if ("GET".equals(command)) {
			try {
				Client.getMethod(host, port, path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} /**else if ("PUT".equals(command)) {
			try {
				Client.putMethod(host, port, path);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}**/
		//}
	 else{
			System.out.println("Check the HTTP command! It should be GET");
			return;
		}
	
			    try {
			        Thread.sleep(10000);
			       } catch (InterruptedException e) {
			         e.printStackTrace();
			       }	
	
	}
			  }
		

		

	}

	
		
		
	




