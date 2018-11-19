package org.hvl.Interfaces;

import java.io.IOException;
import java.net.InetAddress;

import org.hvl.CoAP.MessageFormat;




public class BasicSocketHandler implements CoapSocketHandler{
	
	
	private int localPort;
	
	
	
	public BasicSocketHandler(ChannelManager chManager) throws IOException {
        this(chManager, 9876);
    }
   
	public BasicSocketHandler(ChannelManager chManager, int port) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public int getLocalPort() {
		return localPort;
	}
	
	
	

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(MessageFormat msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Channel connect(Client cli, InetAddress remoteAdd, int remotePort) {
		if (cli == null){
			return null;
		}
    		

		return null;
	}
	
}
