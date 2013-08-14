package com.amj.proofOfConcept;

import java.io.IOException;
import java.net.MalformedURLException;

public interface ServiceInterface<N extends NodeContextObject> {

	public void message( N contextObject ) throws Exception;
	
	public void execute(ServiceLogicInterface contextObject, NodeContextObject target) throws MalformedURLException, IOException;
}
