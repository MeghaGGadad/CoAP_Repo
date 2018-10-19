package org.hvl.CoAPClient;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAP.CoAPCodeRegistries.Code;
import org.hvl.CoAP.CoAPCodeRegistries.Type;
import org.hvl.CoAP.CoAPOptionRegistry;
import org.hvl.CoAP.MessageFormat;
import org.hvl.CoAPServer.HandelResponse;
import org.hvl.CoAPServer.Response;

import org.hvl.CoAP.Communicator;
import org.hvl.CoAP.MediaTypeRegistery;





public class Request extends MessageFormat {
	
	private BlockingQueue<Response> responseQueue;
	private static final Response TIMEOUT_RESPONSE = new Response();
	 
	private int responseCount;
	//public void setUriHost(String host);
	
	/** The payload for a message. */
	private byte[] payload;
	
	Request request;
	// list of response handlers that are notified about incoming responses
	private List<HandelResponse> responseHandlers;
	
	//private static final long startTime = System.currentTimeMillis();
	
	/** To indicate current response for the request. */
	private Response response;
	
	/** This object used to wait for a response. */
	private Object lock;
	
	/** The request code. */
	private CoAPCodeRegistries.Code code;
	
		
	// Constructors 
		
	public Request(Code code) {
		super();
		this.code = code;
	}
		
	/* Constructor for a upcoming new coap messages
	 * @param code The method code of the message
	 * @param confirmable True if the request is to be sent as a CON
	 */ 
		public Request(int code, boolean confirmable) {
			
			super(confirmable ? 
				Type.CON : Type.NON, code);
		}
		
		/* This method places a new response to this request, 
		 * 
		 * @param response A response to the request
		 */
		public void respondback(Response response) {
			
			// assign response to the request
			response.setRequest(this);
			
			response.setURI(getURI());
			response.setOption(getFirstOption(CoAPOptionRegistry.TOKEN));

			
			if (responseCount == 0 && isConfirmable()) {
				response.setID(getID());
			}
			
			// set message type
			if (response.getType() == null) {
				if (responseCount == 0 && isConfirmable()) {
					// uses the piggy-backed response
					response.setType(Type.ACK);
				} else {
					/** use separate response depending on the type:
					CON response to CON request, 
					NON response to NON request**/
					response.setType(getType());
				}
			}
			
			// check if response is of remote origin, i.e.
			// was received by a communicator
			if (communicator != null) try {
				communicator.sendMessage(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} else {
				
				// handle locally
				response.handle();
			}
			++responseCount;
		}
			
		/**
		 * Wait for the response. This function blocks until there is a response or
		 * the request has been canceled.
		 * 
		 * @return the response
		 * @throws InterruptedException
		 *             the interrupted exception
		 */
		/*public Response waitForResponse() throws InterruptedException {
			return waitForResponse(0);
		}*/	
		
       public void respondback(int code, String message) {
			Response response = new Response(code);
			if (message != null) {
				response.setPayload(message);
			}
			respondback(response);
		}

		public void respondback(int code) {
			respondback(code, null);
		}
		
		public void acceptRequest() {
			if (isConfirmable()) {
				Response ack = new Response(CoAPCodeRegistries.EMPTY_MESSAGE);
				ack.setType(CoAPCodeRegistries.Type.ACK);
				respondback(ack);
			}
		}

		public void rejectRequest() {
			if (isConfirmable()) {
				Response rst = new Response(CoAPCodeRegistries.EMPTY_MESSAGE);
				rst.setType(Type.RST);
				respondback(rst);
			}
		}
		
		public void responseCompleted(Response response) {
			System.out.println("Completed");
		}
		
		
		/*
		 * This method returns a response placed using respond() and
		 * blocks until a response is available.
		 */
		public Response responseReceive() throws InterruptedException {
			
			// queue needed to perform this operation
			if (!responseQueueEnabled()) {
				System.out.println("WARNING: Responses may be lost, because of  Missing useResponseQueue(true) call, ");
				ResponseQueueEnable(true);
			}
			
			// receive response from a response queue
			Response response = responseQueue.take();
			
			// return back null if request timed out
			return response != TIMEOUT_RESPONSE ? response : null; 
		}
		
		@Override
		public void timedOut() {
			if (responseQueueEnabled()) {
				responseQueue.offer(TIMEOUT_RESPONSE);
			}
		}

		/*
		 * This method Registers a handler for responses to this request
		 * 
		 * 
		 */
		public void ResponseHandlerRegister(HandelResponse handler) {

			if (handler != null) {
				
				// creation of response handler list
				if (responseHandlers == null) {
					responseHandlers = new ArrayList<HandelResponse>();
				}
				
				responseHandlers.add(handler);
			}
		}

		/*
		 * Method to Unregisters a handler for responses to this request
		 * 
		 * @param handler The observer to remove from the handler list
		 */	
		public void ResponseHandlerUnregister(HandelResponse handler) {

			if (handler != null && responseHandlers != null) {
				
				responseHandlers.remove(handler);
			}
		}

		/*
		 * Method to Enable or disable the response queue
		 * 
		 *  NOTE: The response queue must be ON/enabled before making  
		 *       calls to responseReceive()
		 * 
		 * @param enable True to make enable and false to disable the response queue,
		 * respectively
		 */
		public void ResponseQueueEnable(boolean on) {
			if (on != responseQueueEnabled()) {
				responseQueue = on ? new LinkedBlockingQueue<Response>() : null;
			}
		}
		
		private boolean responseQueueEnabled() {
			return responseQueue != null;
		}

		/*
		 * To check if the response queue is enabled
		 * 
		 * NOTE: The response queue must be enabled BEFORE any possible
		 *       calls to responseReceive()
		 * 
		 * @return True iff the response queue is enabled, otherwise false
		 */	
		/**public boolean responseQueueOn() {
		//if(responseQueue != null)
		  //  return true;
		//else
			//return false;
			if (on != responseQueueEnabled()) {
				responseQueue = on ? new LinkedBlockingQueue<Response>() : null;
			}
			return on;
		}**/	
		
		/*
		 * This method is called whenever a response was placed to this request.
		 * other classes can override this method in order to handle responses.
		 * 
		 * @param response The response to handle
		 */
		public void responseHandel(Response response) {

			// add the response
			if (responseQueueEnabled()) {
				if (!responseQueue.offer(response)) {
					System.out.println("ERROR: Failed to add response to request");
				}
			}
		
			// notify response handlers
			if (responseHandlers != null) {
				for ( HandelResponse handler : responseHandlers) {
					handler.handleResponse(response);
				}
			}

		}
		
		/*@Override
		public void handleBy(HandelMessage handler) {
			handler.handleRequest(this);
		}*/
		
		
		
		/*public void setToken(byte[] bs) {
			// TODO Auto-generated method stub
			
		}*/


		/*public Object send() {
			// TODO Auto-generated method stub
			return null;
		}*/
		
		/**
		 * This method to set the request's options for host, port
		 * and path with a string in the following format
		 * <code>[scheme]://[host]:[port]{/resource}*?{&amp;query}*</code>
		 * 
		 * @param uri the URI defining the target resource
		 * @return this request
		 */
		/*public Request setURI(String uri) {
			try {
				if (!uri.startsWith("coap://") && !uri.startsWith("coaps://"))
					uri = "coap://" + uri;
				   setURI(new URI(uri));
				   return this; 
			} catch (URISyntaxException e) {
				throw new IllegalArgumentException("Failed to set uri "+uri + ": " + e.getMessage());
			}
		}*/
		
		

		private boolean rejected;
		/* To check whether message is rejected
		 * 
		 */
		public boolean isRejected() {
			return rejected;
		}
		
		public Response waitForResponse() throws InterruptedException {
			return waitForResponse(0);
		}
		
		public Response waitForResponse(long timeout) {
			long begin = System.currentTimeMillis();
			long expired = timeout>0 ? (begin + timeout) : 0;
			while (this.response == null && !isCanceled() && !isTimedOut() && !isRejected()) {
					try {
						lock.wait(timeout);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					long currentTime = System.currentTimeMillis();				
					// timeout expired?
					if (timeout > 0 && expired <= currentTime) {
						// end loop since response is empty
						break;
					}
				}
				Response r = this.response;
				this.response = null;
				return r;
			}
		

		private boolean timedOut;
		/*To check whether message is timeout
		 * 
		 */
		private boolean isTimedOut() {
			return timedOut;
		}

		private boolean canceled;
		/*To check if message is cancelled
		 * 
		 */
		private boolean isCanceled() {
			return canceled;
		}

		private Communicator communicator;
		
		public void send() throws IOException{
			Communicator comm = communicator != null ? communicator : defaultCommunicator();
			if (comm != null) {
				comm.sendMessage(this);
			}
			
		}

		private static Communicator DEFAULT_COMM;
		/*
		 * Returns the default communicator used for outgoing requests
		 * 
		 * @return The default communicator
		 */
		public static Communicator defaultCommunicator() {
			
			// lazy initialization
			if (DEFAULT_COMM == null) {
				try {
					DEFAULT_COMM = new Communicator();
				} catch (SocketException e) {
					System.out.printf("[%s] Failed to create default communicator: %s\n", 
						"CoAP", e.getMessage());
				}
			}
			return DEFAULT_COMM;
		}

		public void dispatch(HandelRequest handler) {
			System.out.printf("Unable to dispatch request with code '%s'", 
					CoAPCodeRegistries.toString(getCode()));
		}

		public void log() {
			// TODO Auto-generated method stub
			
		}

		public byte[] getPayload() {
			return payload;
		}

		


      /**
       * Convenience factory method to construct a GET request 
       * @return a new GET request
      */
       public static Request newGet() 
       { 
    	   return new Request(Code.GETRequest); 
       
       }

      private String scheme;

      public String getScheme() {
	  
    	  return scheme == null ? MessageFormat.COAP_URI_SCHEME : scheme;
      }

	public void setCommunicator(Communicator communicator) {
		this.communicator = communicator;
		
	}

	
	/**public void setUriHost(String host) {
		if (host == null) return;
		
		if (host.length() < 1 || host.length() > 0){
			throw new IllegalArgumentException("Invalid Uri-Host option length");
		}
		
	}**/
	
	public void setUriHost(String host) {
		if (host == null) return;
		
		if (host.length() < 1 || host.length() > CoAPOptionRegistry.MAX_LENGTH){
			throw new IllegalArgumentException("Invalid Uri-Host option length");
		}
		
	}

	public void setUriPort(int port) {
		
	     if (port < 0) return;
	}

	public void setUriQuery(String query) {
		if (query == null) return;
		
	}

	public void setContentType(String string) {
		
		
	}

	 

	/**private int numMessagesSent;
	public void sendMessage(MessageFormat msg) throws IOException {

		if (msg != null) {
			doSendMessage(msg);
			++numMessagesSent;
		}
	}
	
	protected void doSendMessage(MessageFormat msg)
			throws IOException {
	} **/
		
		
		

		
		
	

}

