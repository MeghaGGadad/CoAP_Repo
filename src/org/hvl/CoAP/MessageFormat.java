package org.hvl.CoAP;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.hvl.CoAPClient.Request;




public class MessageFormat {
	//As per section 3 Message Format
	
	// number of bits used for the encoding of the Coap version field
	public static final int VERSION_BITS     = 2;
	
	/** The token, a 0-8 byte array.  9-15 are reserved */
	private byte[] token;
	
	// Bits used for the encoding of the option count field
	public static final int OPTIONCOUNT_BITS = 4;
	
	// Bits used for the encoding of the message type field
	public static final int TYPE_BIT        = 2;
	
	// Bits used for the encoding of the request method/response code field
	public static final int CODE_BITS        = 8;
	
	// Bits used for the encoding of the transaction ID/msg ID
	public static final int ID_BITS         = 16;
	
	// Bits used for the encoding of the option delta
	public static final int OPTIONDELTA_BITS = 4;
	
	/** Bits used for the encoding of the base option length field
	*if all bits in this field are set to one, the extended option length
	**field is additionally used to encode the option length */
		public static final int OPTIONLENGTH_BASE_BITS     = 4;
		
	/** number of bits used for the encoding of the extended option length field
	*this field is used when all bits in the base option length field 
	** are set to one */
		public static final int OPTIONLENGTH_EXTENDED_BITS = 8;
		
		/** The charset for Coap is UTF-8 */
	public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	
	/** The Coap URI scheme */
	public static final String COAP_URI_SCHEME = "coap";
	
	/** The Coaps URI scheme */
	public static final String COAP_SECURE_URI_SCHEME = "coaps";
	
	//The message's ID
	
	private int MID = 59723;
	
	/** options of this message*/
	protected CoAPOptionRegistry options;
	
	//The message's code
	private int code;
	
	/** The type of the message. One of {CON, NON, ACK or RST}. */
	private CoAPCodeRegistries.Type Type;
	
	int type=0;
	
	//The message's URI
	private URI uri;
	
	/*
	 * The message's version. Default message's version set to 1. **/
	private int version = 1;
	
	//The payload of message's 
	private byte[] payload;
	
	private boolean complete;
	
	//A time stamp of the messages
		private long timestamp;
		
	public static final int RESPONSE_TIMEOUT_MS = 2000;
	public static final double RESPONSE_RANDOM_FACTOR = 1.5;
	public static final int MAX_RETRANSMIT = 4;
	public static final int ACK_RST_RETRANS_TIMEOUT_MS = 120000;

	//The message's options
		private Map<Integer, List<Options>> optionMap = new TreeMap<Integer, List<Options>>();
	
		public int getVersion() {
			return this.version;
		}

	
	public MessageFormat newReply(boolean ack) {

		MessageFormat reply = new MessageFormat();
		
		/** set message type CON<NON<ACK<RST
		**return type for CON message can be either ACK or RST(page 8) 
		if no packets are lost, each CON message 
		*return message of type ACK or RST 
		*/
		if (Type == CoAPCodeRegistries.Type.CON) {
			reply.Type = ack ? 
					CoAPCodeRegistries.Type.ACK : CoAPCodeRegistries.Type.RST;
		} else {
			reply.Type = CoAPCodeRegistries.Type.NON;
		}
		
		//prints the message ID
		reply.MID = this.MID;
		
		 //prints token
		//Section 5.3
		reply.setOption(getFirstOption(CoAPOptionRegistry.TOKEN));;
		
		// set the receiver URI of the reply to the sender of this message
		reply.uri = this.uri;
		
		 //create an empty reply by default
		reply.code = CoAPCodeRegistries.EMPTY_MESSAGE;
		
		return reply;
		
	}
	/*
	 * This method sets the payload of this coap message
	 * 
	 * @param payload The payload to which the current message payload should be
	 *  
	 */
	public void setPayloadByte(byte[] payload) {
		this.payload = payload;
	}
	
	public void setPayload(String payload, int mediaType) {
		if (payload != null) {
			try {
				// set internal byte array
				setPayloadByte(payload.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return;
			}
			
			// set content type option
			setOption(new Options(mediaType, CoAPOptionRegistry.CONTENT_TYPE));
		}
	}
	
	public void setPayloadString(String payload) {
		setPayload(payload, MediaTypeRegistery.PLAIN);
	}
	
	/*
	 * Sets the option with the specified option number
	 * 
	 * @param opt The option to set
	 */
	public void setOption(Options opt) {

		if (opt != null) {
			List<Options> options = new ArrayList<Options>();
			options.add(opt);
			setOptions(opt.getOptionsNum(), options);
		}
	}
	
	public boolean hasOption(int optionNumber) {
		return getFirstOption(optionNumber) != null;
	}
	
	
	/**
	 * Get the size of the payload. 
	 * if payload is not specified return null otherwise return length of payload
	 * @return the payload size
	 */
	public int getpayloadSize() {
		
        if(payload == null)
        	return 0;
        else 
        	return payload.length;
	}
	
	/**
	 * Returns the payload in the form of a string. 
	 * Returns blank string if no
	 * payload is defined.
	 */
	public String getPayloadAsString() {
		if (payload==null)
			return "";
		return new String(payload, UTF8_CHARSET);
	}
	
	
	public void setOptions(int optNum, List<Options> opt) {
		// TODO Check if all options are consistent with optionNumber
		optionMap.put(optNum, opt);
	}

	/*
	 * Returns the first option with the specified option number
	 */
	public Options getFirstOption(int optNum) {
  		
  		List<Options> list = getOptions(optNum);
  		
  	    if(list != null && !list.isEmpty())
  	    	return list.get(0);
  	    else
  	    	return null;
	}
      
      public void addOption(Options opt) {
  		
  		List<Options> list = optionMap.get(opt.getOptionsNum());
  		if (list == null) {
  			list = new ArrayList<Options>();
  			optionMap.put(opt.getOptionsNum(), list);
  		}
  		list.add(opt);
  	}	
      
      
      /**
  	 * This method gets the set of options. If no set has defined,a new
  	 * one will be created. EmptyMessages will not have any options.
  	 * 
  	 * @return the options
  	 */
  	public CoAPOptionRegistry getOptions() {
  		if (options == null)
  			options = new CoAPOptionRegistry();
  		return options;
  	}
      
      public List<Options> getOptions(int optNumber) {
  		return optionMap.get(optNumber);
  	}
      
      public boolean hasFormat(int mediaType) {
  		Options opt = getFirstOption(CoAPOptionRegistry.CONTENT_TYPE);
  		
  	    if(opt != null)
  	    	return opt.getIntegerVal() == mediaType;
  	    else 
  	    	return false;
      }

	public static MessageFormat newAcknowledgement(MessageFormat msg) {
		
		MessageFormat ack = new MessageFormat();
		
		// set message type to Acknowledgement
		ack.setType(CoAPCodeRegistries.Type.ACK);
		
		// echo the Message ID(4.2)
		ack.setMID(msg.getMID());
		
		// set receiver URI to sender URI of the message
		// to acknowledge
		ack.setURI(msg.getURI());
		
		// create an empty Acknowledgement by default,can be piggy-backed with a response by the user
		ack.setMethodCode(CoAPCodeRegistries.EMPTY_MESSAGE);
		
		return ack;
	}
     
      public static MessageFormat newReset(MessageFormat msg) {
  		
  		MessageFormat rst = new MessageFormat();
  		
  		// set message type to Reset
  		rst.setType(CoAPCodeRegistries.Type.RST);
  		
  		// echo the Message ID
  		rst.setMID(msg.getMID());
  		
  		// set receiver URI to sender URI of the message to reset
  		rst.setURI(msg.getURI());
  		
  		// Reset must be empty(page 8)
  		//Empty Message
  		 //A message with a Code of 0.00; neither a request nor a response.
  		rst.setMethodCode(CoAPCodeRegistries.EMPTY_MESSAGE);
  		
  		return rst;
  	}
  	
      public Request setURI(String uri) {
  		try {
  			if (!uri.startsWith("coap://") && !uri.startsWith("coaps://"))
  				uri = "coap://" + uri;
  			
  			setURI(new URI(uri));
  		 return (Request) this;
  		} catch (URISyntaxException e) {
  			System.out.printf("[%s] Failed to set URI: %s\n",
  				getClass().getName(), e.getMessage());
  			return (Request) this;
  		}
  	}
      

      
      public void setURI(URI uri2) {
		// TODO Auto-generated method stub
		
	}
      

      /*
  	 * This function returns the ID of this Coap message
  	 * 
  	 * @return The current ID.
  	 */  
    
	public int getMID() {
    	  return this.MID;
	}
	
	public final static int MID_MIN = 0;
    public final static int MID_MAX = 65535;
	
	public synchronized int getNewID() {
        if (MID < MID_MAX) {
            ++MID;
        } else
        	MID = MID_MIN;
        return MID;
    }
    
	public synchronized void initRandom() {
        // generate random 16 bit messageId
        Random random = new Random();
        MID = random.nextInt(MID_MAX + 1);
    }
      
	/*
	 * This function returns the URI of this Coap message
	 * 
	 * @return The current URI
	 */  
	public URI getURI() {
  		return this.uri;
  	}
     
	/*
	 * Default constructor for a new Coap message
	 */
	public MessageFormat () {
  	}
      
	/*
	 * Constructor for a new Coap message
	 * 
	 * @param type The type of the Coap message ACK,NON,RST,CON
	 * @param code The code of the Coap message GET,PUT,POST,DELETE
	 */  
	public MessageFormat(CoAPCodeRegistries.Type type, int code) {
  		this.Type = type;
  		this.code = code;
  	}
      
	/*
	 * Constructor for a new Coap message
	 * 
	 * @param uri The URI of the Coap message
	 * @param payload The payload of the Coap message
	 */
      public MessageFormat(URI uri, CoAPCodeRegistries.Type type, int code, int id, byte[] payload) {
  		this.uri = uri;
  		this.Type = type;
  		this.code = code;
  		this.MID = id;
  		this.payload = payload;
  	}
      
      
  /*This procedure sets the ID of this Coap message
  	 * 
  	 * @param id The message ID to which the current message ID should
  	 *           be set to
  	 */
      public int setMID(int id) {
  		return(this.MID = id);
  	}
    
      /*
  	 * This methods sets the code of this Coap message
  	 * to GET,PUT,POST or DELETE depending on argument passed
  	 * @param code The message code to which the current message code should
  	 *             be set to
  	 */
      public void setMethodCode(int code) {
  		this.code = code;
  	}
      
      public int getMethodCode() {
  		return this.code;
  	}
      /*
  	 * This procedure sets the type of this Coap message
  	 * 
  	 * @param Type The message type to which the current message type should
  	 *                be set to
  	 */
      public void setType(CoAPCodeRegistries.Type ack) {
  		this.Type = ack;
  	}
      
      /*
  	 * This function returns the type of this Coap message
  	 * Confirmable (0), Non-confirmable (1), Acknowledgement (2), or
     * Reset (3)
  	 * @return The current type.
  	 */
  	public CoAPCodeRegistries.Type getType() {
  		return Type;
  	}
      
      public static CoAPCodeRegistries.Type getTypeByID(int id) {
  		switch (id) {
  			case 0:
  				return CoAPCodeRegistries.Type.CON;
  			case 1:
  				return CoAPCodeRegistries.Type.NON;
  			case 2:
  				return CoAPCodeRegistries.Type.ACK;
  			case 3:
  				return CoAPCodeRegistries.Type.RST;
  			default:
  				return CoAPCodeRegistries.Type.CON;
  		}
  	}
     
     /*
	 * To checks, if the message is a request message(0)
	 * 
	 * @return True if the message is a request
	 */
	public boolean isRequest() {
		return CoAPCodeRegistries.isRequestCode(code);
	}
	
	/*
	 * To checks if the message is a response message
	 * 
	 * @return True if the message is a response
	 */
	public boolean isResponse() {
		return CoAPCodeRegistries.isResponseCode(code);
	}
    
	public boolean isConfirmable() {
		return type == CoAPCodeRegistries.CON;
	}
	
	public boolean isNonConfirmable() {
		
		return type == CoAPCodeRegistries.NON;
	}
	
	public boolean isAcknowledgement() {
		return type == CoAPCodeRegistries.ACK;
	}
	
	public boolean isReset() {
		return type == CoAPCodeRegistries.RST;
	}
	
	public boolean isReply() {
		return isAcknowledgement() || isReset();
	}
	
	public void handle(HandleMessage handler) {
		// subclasses can implement
		
	}
	
public void DetailPrint(PrintStream out) {
		
		out.println("Coap MESSAGE---------------------");
		
		List<Options> options = getOptionList();
		
		out.printf("URI of the Message   : %s\n", uri != null ? uri.toString() : "NULL");
		out.printf("ID of the Message   : %d\n", MID);
		out.printf("Code of the Message  : %s\n", CoAPCodeRegistries.toString(code));
		out.printf("Options of the Message: %d\n", options.size());
		for (Options opt : options) {
			out.printf("  * %s:  (%d Bytes)\n", 
				opt.getName(), opt.getLength()
			);
		}
		out.printf("Payload: %d Bytes\n", getpayloadSize());
		out.println("------------------------------------------------------");
		if (getpayloadSize() > 0) out.println(getPayloadAsString());
		out.println("-----------------------------------------------");
		
	}
	
	private List<Options> getOptionList() {

      List<Options> list = new ArrayList<Options>();
		
		for (List<Options> option : optionMap.values()) {
			list.addAll(option);
		}
		
		return list;
	}	

	public void Printlog() {
		DetailPrint(System.out);
	}

	/*
	 * Notification method that is called when the message's complete flag
	 * changed to true.
	 * subclasses may override this method to add custom handling code
	 * */
	 
	protected void completed() {
		// do nothing
	}
	
	
	
	protected boolean iscompleted() {
		return complete;
		
	}
	/*check for the empty token, if no token specified returns null or token length is zero 
	 * 
	 */
	public boolean EmptyToken() {
		return token == null || token.length == 0;
	}
	
	/** This method returns Token of the message
	 * 
	 */
	public byte[] getToken() {
		return token;
	}
	
	
	
	public void setToken(byte[] token){
    	if (token == null){
    		return;
    	}
    	if (token.length < 1 || token.length > 8){
    		throw new IllegalArgumentException("Invalid Token Length");
    	}
    	
    }
	
	public void timedOut() {
		System.out.println("Time out");
	}
	
	/*
	 * check two messages if they have the same message ID
	 * 
	 * @param msg1 The first message
	 * @param msg2 the second message
	 * @return True if the messages are same
	 */
	public static boolean isDuplicateMessageID(MessageFormat msg1, MessageFormat msg2) 
	{
		
		if (
			msg1 != null && msg2 != null &&  // both messages must exist
		                  msg1.getMID() == msg2.getMID()     // checks for the same IDs
		) 
			
		return true;
			
	    else {
			return false;
		}
	}
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* This method 
	 * sets the timestamp for current message.
	 * 
	 * @param timestamp, in milliseconds
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		
		
	}
	
	
	
	
	/*
	 * get the timestamp associated with this message.
	 * 
	 * @return The timestamp , in milliseconds
	 */
	public long getTimestamp() {
		return timestamp;
	}
     
	public String endpointID() {
		InetAddress address = null;
		try {
			address = getInetAddress();
		} catch (UnknownHostException e) {
		}
		return String.format("%s:%d", 
			address != null ? address.getHostAddress() : "NULL",
			uri != null ? uri.getPort() : -1
		);
	}
	
	public InetAddress getInetAddress() throws UnknownHostException {
		return InetAddress.getByName(uri != null ? uri.getHost() : null);
	}
	
	/*identifier of the sender
	 * 
	 */
	public String key() {
		return String.format("%s#%d", endpointID(),MID);
	}


	
	
	public void setContentType(int plain) {}
}
