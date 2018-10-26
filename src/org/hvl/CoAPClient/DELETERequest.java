package org.hvl.CoAPClient;

import org.hvl.CoAP.CoAPCodeRegistries;

public class DELETERequest extends Request{
    public DELETERequest(){
    	super(CoAPCodeRegistries.DELETE, true);
    }
    public void dispatch(HandleRequest handle) {
		handle.performDELETE(this);
	}
}
