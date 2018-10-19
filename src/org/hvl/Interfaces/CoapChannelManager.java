package org.hvl.Interfaces;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Random;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.hvl.CoAP.MessageFormat;
import org.hvl.CoAPClient.CoapClient;
import org.hvl.CoAPServer.SimpleServer;


public class CoapChannelManager implements ChannelManager{
	
	// global message id
		private final static Logger logger = Logger.getLogger(CoapChannelManager.class); 
	    private int globalMessageId;
	    private static CoapChannelManager instance;
	    private HashMap<Integer, SocketInformation> socketMap = new HashMap<Integer, SocketInformation>();
	    Server serverListener = null;
	
	
	    private CoapChannelManager() {
	        logger.addAppender(new ConsoleAppender(new SimpleLayout()));
	        // ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF:
	        logger.setLevel(Level.WARN);
	    	initRandom();
	    }

	    public synchronized static ChannelManager getInstance() {
	        if (instance == null) {
	            instance = new CoapChannelManager();
	        }
	        return instance;
	    }
	    
	    

	    public final static int MESSAGE_ID_MIN = 0;
	    public final static int MESSAGE_ID_MAX = 65535;
	    
	    /**
	     * Creates a new, global message id for a new COAP message
	     */
	    @Override
	    public synchronized int getNewMessageID() {
	        if (globalMessageId < MESSAGE_ID_MAX) {
	            ++globalMessageId;
	        } else
	            globalMessageId = MESSAGE_ID_MIN;
	        return globalMessageId;
	    }

	    @Override
	    public synchronized void initRandom() {
	        // generate random 16 bit messageId
	        Random random = new Random();
	        globalMessageId = random.nextInt(MESSAGE_ID_MAX + 1);
	    }

	   
	    //@Override
	    public void createServerListener(Server serverListener, int localPort) {
	        if (!socketMap.containsKey(localPort)) {
	            //SocketInformation socketInfo = new SocketInformation(new CoapBasicSocketHandler(this, localPort), serverListener);
				SocketInformation socketInfo = null;
				socketMap.put(localPort, socketInfo);
	        } else {
	        	/*TODO: raise exception: address already in use */
	        	//throw new IllegalStateException();
	        }
	    }

	    public ClientChannel connect(Client client, InetAddress addr, int port) {
	    	CoapSocketHandler socketHandler = null;
			try {
				socketHandler = new CoapBasicSocketHandler(this);
				SocketInformation sockInfo = new SocketInformation(socketHandler, null); 
				//socketMap.put(socketHandler.getLocalPort());
				return socketHandler.connect(client, addr, port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		private class SocketInformation {
			public CoapSocketHandler socketHandler = null;
			public SimpleServer serverListener = null;
			public SocketInformation(CoapSocketHandler socketHandler,
					SimpleServer serverListener) {
				super();
				this.socketHandler = socketHandler;
				this.serverListener = serverListener;
			}
		}

		@Override
		public void setMessageId(int globalMessageId) {
			this.globalMessageId = globalMessageId;
		}

		

		public ServerChannel createServerChannel(CoapSocketHandler socketHandler, MessageFormat message, InetAddress addr,
				int port) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ClientChannel connect(CoapClient coapClient, InetAddress byName, int port) {
			// TODO Auto-generated method stub
			return null;
		}

	    
}  
	    
	


