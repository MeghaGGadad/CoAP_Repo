package org.hvl.CoAPClient;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAP.MediaTypeRegistery;
import org.hvl.CoAPServer.Response;
import org.hvl.Interfaces.ChannelManager;
import org.hvl.Interfaces.Client;
import org.hvl.Interfaces.ClientChannel;
import org.hvl.Interfaces.CoapChannelManager;


public class CoapClient implements Client{
	
	private static final int PORT = 5683;
    static int counter = 0;
    ChannelManager channelManager = null;
    ClientChannel clientChannel = null;
    
    public static void main(String[] args) throws UnknownHostException {
    
    CommandLineParser cmdParser = new DefaultParser();
    Options options = new Options();
    options.addOption("t", true, "Content type for given resource for PUT/POST");
    options.addOption("e", true, "Include text as payload");
    options.addOption("m", true, "Request method (get|put|post|delete), default is 'get'");
    options.addOption("N", false, "Send NON-confirmable message");
    options.addOption("P", true, "Use proxy (adds Proxy-Uri option)");
    options.addOption("T", true, "Include specified token");
    CommandLine cmd = null;
    try {
        cmd = cmdParser.parse(options, args);
        //System.out.println("arguments: " +cmd);
    } catch (ParseException e) {
        System.out.println("Unexpected exception: " + e.getMessage() );
        //printInfo();
        showHelp(options);
    }
    
 // Make sure we have at least the URI argument
    if (cmd == null || cmd.getArgs().length != 1) {
    	//printInfo();
    	showHelp(options);
    }
			
					
		
		CoapClient client = new CoapClient();
        client.channelManager = CoapChannelManager.getInstance();
        client.runTestClient(cmd);
}
    public static void showHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("JCoAP-Client URI", "", options,
                "Examples: \n"
                + "    JCoAP-Client -m get coap://[::1]/.well-known/core\n");
        System.exit(-1);
}
    public void runTestClient(CommandLine cmd) throws UnknownHostException{
    	while(true){ //new
            try {
                String urlOrig = cmd.getArgs()[0];
                
                urlOrig = urlOrig.replaceFirst("coap", "http");
                URL url = new URL(urlOrig);
                int port = url.getPort();
                if (port < 0) port = PORT;

                clientChannel = channelManager.connect(this, InetAddress.getByName(url.getHost()), port);
                Request request;

                boolean confirm = !cmd.hasOption("N");
                switch (cmd.getOptionValue("m", "get").toLowerCase()) {
                    case "put":
                    	request = ClientChannel.createRequest(confirm, CoAPCodeRegistries.Code.PUTRequest);
                        break;
                    case "post":
                    	request = ClientChannel.createRequest(confirm, CoAPCodeRegistries.Code.POSTRequest);
                        break;
                    case "delete":
                    	request = ClientChannel.createRequest(confirm, CoAPCodeRegistries.Code.DELETERequest);
                        break;
                    default:
                    	request = ClientChannel.createRequest(confirm, CoAPCodeRegistries.Code.GETRequest);
                }

                if (cmd.hasOption("e")) request.setPayload(cmd.getOptionValue("e"));
                if (cmd.hasOption("T")) request.setToken(cmd.getOptionValue("T").getBytes());
                //if (cmd.hasOption("P")) request.setProxyUri(cmd.getOptionValue("P"));
                if (cmd.hasOption("t")) {
                    Integer type = Integer.parseInt(cmd.getOptionValue("t"));
                    int mediaType = 0;
					request.setContentType(MediaTypeRegistery.toString(mediaType));
                }

                request.setUriHost(url.getHost());
                request.setUriPort(port);
                request.setURI(url.getPath());
                request.setUriQuery(url.getQuery());

                request.setContentType(MediaTypeRegistery.OCTET_STREAM);
                request.setToken("ABCD".getBytes());
                request.setUriHost("123.123.123.123");
                request.setUriPort(1234);
                request.setURI("/.well-known/core");
                request.setURI("/st");
                request.setUriQuery("a=1&b=2&c=3");			
                //request.setProxyUri("http://proxy.org:1234/proxytest");

    			clientChannel.sendMessage(request);
    			System.out.println(request);
    			System.out.println("Sent Request");
            
            } catch (UnknownHostException e) {
                Logger.getLogger(CoapClient.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            } catch (MalformedURLException ex) {
                Logger.getLogger(CoapClient.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(-2);
            }
            try { //new
    	        Thread.sleep(10000); //new
    	       } catch (InterruptedException e) { //new
    	         e.printStackTrace();//new
    	       }
        	}//new
        }
    

	
   
    
    /* If the user is not aware of what the arguments should pass
	 * 
	 * 
	 */
	public static void printInfo() {
		System.out.println("Java Coap Simple Client");
		System.out.println();
		System.out.println("Usage: SimpleClient [-l] METHOD URI [PAYLOAD]");
		System.out.println("  METHOD  : {GET, POST, PUT, DELETE}");
		System.out.println("  URI     : The URI to the remote endpoint or resource");
		System.out.println("  PAYLOAD : The data to send with the request");
		System.out.println("Options:");
		System.out.println("  -l      : Wait for multiple responses");
		System.out.println();
		System.out.println("Examples:");
		System.out.println("  SimpleClient GET coap://10.0.1.97:5683/temp");
		System.out.println("  SimpleClient POST coap://example.com:5683/~sensors/readings.xml");
	}
	
	@Override
	public void onResponse(ClientChannel channel, Response response) {
		System.out.println("Received response");
	}
    
	
	@Override
	public void onConnectionFailed(ClientChannel channel, boolean notReachable, boolean resetByServer) {
		System.out.println("Connection Failed");
	}
	
}