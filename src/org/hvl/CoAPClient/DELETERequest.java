package org.hvl.CoAPClient;

import org.hvl.CoAP.CoAPCodeRegistries;

public class DELETERequest extends Request{
    public DELETERequest(){
    	super(CoAPCodeRegistries.DELETE, true);
    }
    public void execute(HandleRequest handle) {
		handle.performDELETEMethod(this);
	}
}
