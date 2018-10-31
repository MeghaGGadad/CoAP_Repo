package org.hvl.Interfaces;

import java.net.InetAddress;

import org.hvl.CoAP.MessageFormat;




public interface ChannelManager {
	
	public int getNewMID();

    /* This method is called by the socket Listener to create a new Server Channel
     * the Channel Manager checks the Server Listener if it ready to accept a new connection */
	public ServerChannel createServerChannel(CoapSocketHandler socketHandler, MessageFormat message, InetAddress addr, int port);

	/* creates a server socket listener for incoming connections */
    public void createServerListener(Server serverListener, int localPort);

    /* called by a client to create a connection*/
     
    public Channel connect(Client client, InetAddress addr, int port);
    
    
    public void setMessageId(int globalMId);
    
    public void initRandom();

	

}
