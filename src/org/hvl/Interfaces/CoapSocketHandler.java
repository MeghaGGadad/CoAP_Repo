package org.hvl.Interfaces;

import java.net.InetAddress;

import org.hvl.CoAP.MessageFormat;



public interface CoapSocketHandler {
	
	public Channel connect(Client client, InetAddress remoteAddress, int remotePort);

    public void close();

    public void sendMessage(MessageFormat msg);

    int getLocalPort();

	

	

}
