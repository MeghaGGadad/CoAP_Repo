package org.hvl.Interfaces;

import java.net.InetAddress;

import org.hvl.CoAP.MessageFormat;
import org.hvl.Interfaces.ClientChannel;
import org.hvl.CoAPClient.CoapClient;



public interface ChannelManager {
	
	public int getNewMessageID();

    /* called by the socket Listener to create a new Server Channel
     * the Channel Manager then asked the Server Listener if he wants to accept a new connection */
	public ServerChannel createServerChannel(CoapSocketHandler socketHandler, MessageFormat message, InetAddress addr, int port);

	/* creates a server socket listener for incoming connections */
    public void createServerListener(Server serverListener, int localPort);

    /* called by a client to create a connection
     * TODO: allow client to bind to a special port */
    //public ClientChannel connect(Client client, InetAddress addr, int port);
    
    /* This function is for testing purposes only, to have a determined message id*/
    public void setMessageId(int globalMessageId);
    
    public void initRandom();

	public ClientChannel connect(CoapClient coapClient, InetAddress byName, int port);

}
