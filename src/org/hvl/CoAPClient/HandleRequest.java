package org.hvl.CoAPClient;


public interface HandleRequest {
	
	public void performGETMethod(GETRequest request);
	public void performPOSTMehod(POSTRequest request);
	public void performPUTMethod(PUTRequest request);
	public void performDELETEMethod(DELETERequest request);
	 
}
