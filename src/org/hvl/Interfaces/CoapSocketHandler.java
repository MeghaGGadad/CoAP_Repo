package org.hvl.Interfaces;

import java.net.InetAddress;

import org.hvl.CoAP.MessageFormat;



public interface CoapSocketHandler {
	
	public ClientChannel connect(Client client, InetAddress remoteAddress, int remotePort);

    public void close();

    public void sendMessage(MessageFormat msg);

    //public CoapChannelManager getChannelManager();

	int getLocalPort();

	//void removeClientChannel(ClientChannel channel);

	//void removeServerChannel(ServerChannel channel);

}
