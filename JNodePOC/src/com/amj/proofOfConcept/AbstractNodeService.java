package com.amj.proofOfConcept;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractNodeService<N extends NodeContextObject> implements ServiceInterface<N>{

	private final List<ServiceInterface> clients = new ArrayList<ServiceInterface>( );
	
	
	private 	  Logger  serviceLogger = Logger.getAnonymousLogger( );
	private 	  Process nodeProcess;
	private final String  pathToScript;
	private final Long 	  _serviceID;
	private       Thread  currentProcess;
	private final String targetURL;
	
	/*
	 * 
	 * @param pathToScript : the path to the js file you wish to implement
	 * @param targetURL    : the node.js host url
	 */
	protected AbstractNodeService( String pathToScript , String targetURL){
		this._serviceID = System.nanoTime();
		serviceLogger.log(Level.INFO,"CREATING NodeService:["+this.getClass().toString()+"] NodeService:ID["+_serviceID+"]");
		this.pathToScript = pathToScript;
		this.targetURL = targetURL;
	}
	

	/*
	 * before the you can execute calls to node, start this service
	 */
	public void startNodeService(   ) throws IOException{
		final ProcessBuilder builder = new ProcessBuilder("node",pathToScript);
		Runnable newService = new Runnable( ){

			@Override
			public void run() {
				try {
					Process nodeProcess = builder.start();
					serviceLogger.log(Level.INFO,"STARTING Service for NodeService:ID["+_serviceID+"]");
					serviceLogger.log(Level.INFO,"using scriptFile["+pathToScript+"] call for NodeService:ID["+_serviceID+"]");
				} catch (IOException e) {
					serviceLogger.log(Level.SEVERE,"ERROR STARTING [NODE] service call for NodeService:ID["+_serviceID+"]");
					e.printStackTrace();
					
				}
				
			}
			
		};
		
		this.serviceLogger.log(Level.CONFIG, "pre Thread(NodeService) INIT sleep delay( 1ms )");
		
		this.currentProcess = new Thread( newService );
		this.currentProcess.start();
		
		this.serviceLogger.log(Level.CONFIG, "post Thread(NodeService) INIT");
 		
	}

	


	
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see com.amj.proofOfConcept.ServiceInterface#execute(com.amj.proofOfConcept.ServiceLogicInterface, com.amj.proofOfConcept.NodeContextObject)
	 *
	 * @param ServiceLogicInterface ( validation and Logic, and NodeContextObject )
	 */

	
	@Override
	public void execute(ServiceLogicInterface contextObjectLogic, NodeContextObject target) throws MalformedURLException, IOException{
		serviceLogger.log(Level.INFO, "executing call on subject :["+ contextObjectLogic.toString() +"]");
		if( contextObjectLogic.performCustomBuisnessLogic(target)){
			this.connectToService( target , new URL( this.targetURL ));
			serviceLogger.log(Level.INFO, "executing call on subject :["+ contextObjectLogic.toString() +"] RESULT= PASSED criteria for["+contextObjectLogic);
		}else{
			
			serviceLogger.log(Level.INFO, "executing call on subject :["+ contextObjectLogic.toString() +"] RESULT= FAILED criteria for["+contextObjectLogic);
		}
		
		
	}
	
	
	
	/*
	 * the actual connecting to the existing Node Service
	 * 
	 */
	
	private void connectToService( NodeContextObject contextObject, URL url ) throws IOException{
		serviceLogger.log(Level.INFO, "Start the service call...");
		URLConnection connection = url.openConnection();
		connection.setRequestProperty(NodeContextObject.MESSAGE, contextObject.getContextMessage().toString());
		InputStream response = connection.getInputStream();
		ByteBuffer buffer = ByteBuffer.allocate(response.available());
		serviceLogger.log(Level.INFO, "CALL COMPLETE, GETTING THE RESPONSE...");
		response.read(buffer.array());
		contextObject.setContextResponse( new String( buffer.array() ));
		
	}
	
	
}
