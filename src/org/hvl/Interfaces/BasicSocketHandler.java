package org.hvl.Interfaces;

import java.io.IOException;
import java.net.InetAddress;

import org.hvl.CoAP.MessageFormat;




public class BasicSocketHandler implements CoapSocketHandler{
	
	
	private int localPort;
	
	
	
	public BasicSocketHandler(ChannelManager channelManager) throws IOException {
        this(channelManager, 9876);
    }
   
	public BasicSocketHandler(ChannelManager channelManager, int port) {
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
	public Channel connect(Client client, InetAddress remoteAddress, int remotePort) {
		if (client == null){
			return null;
		}
    		

		return null;
	}
	
}
