package org.hvl.CoAPServer;



import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAP.HandleMessage;
import org.hvl.CoAP.MessageFormat;
import org.hvl.CoAPClient.Request;

public class Response extends MessageFormat {
	
	private Request request;
	
	
	
	public Response() {
		this(CoAPCodeRegistries.RESP_VALID);
	}
	
	public Response(int code) {
		setMethodCode(code);
	}
	
	/**
	 * Instantiates a new coap response.
	 *
	 * @param response the response
	 */
	public Response(Response response) {
		this.response = response;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	
	public Request getRequest() {
		return request;
	}
	
	public void respond() {
		if (request != null) {
			request.respondback(this);
		}
	}
	
	public int getTime() {
		if (request != null) {
			return (int)(getTimestamp() - request.getTimestamp());
		} else {
			return -1; 
		}
	}
	
	public void handle() {
		if (request != null) {
			request.responseHandel(this);
		}
	}
	
	
	@Override
	protected void completed() {
		if (request != null) {
			request.responseCompleted(this);
		}
	}
	
	@Override
	public void handle(HandleMessage handler) {
		handler.HandelResponse(this);
	}
	
	public boolean isPiggyBacked() {
		return isAcknowledgement() && getMethodCode() != CoAPCodeRegistries.EMPTY_MESSAGE;
	}

	public boolean isEmptyACK() {
		return isAcknowledgement() && getMethodCode() == CoAPCodeRegistries.EMPTY_MESSAGE;
	}

	
	
	private Response response;
	public String getResponseText() {
		return response.getPayloadString();
	}

}
	
	
	


 


