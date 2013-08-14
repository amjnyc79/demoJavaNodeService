package com.amj.proofOfConcept;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Driver {

	public static void main(String[] args) throws ScriptException, IOException, InterruptedException {
		String fileName = "demo.js";
		
			
		//Create the implemented Test Service
		TestService testService = new  TestService( fileName , "http://localhost:9080" );
		testService.startNodeService();
		
		
		//Create the Node ContextObject
		NodeContextObject nodeContextObject = new NodeContextObject("DOW_JONES_TEST_CONTENT");
		ServiceLogicInterface logic = new ServiceLogicInterface(){
			
			
			// MUST use the logic /validation 
			@Override
			public boolean performCustomBuisnessLogic(
					NodeContextObject nodeContextObject) {
				
				// This is a place holder for custom validation and logic
				return true; // we just want to test a success case..
			}
			
		};
		
		
		// We have the service, object and logic.. perform the request
		testService.execute(logic,nodeContextObject);
		System.out.println("RESPONSE "+nodeContextObject.getContextResponse());
		Thread.sleep(1000);
		
		
	
		
	   
	}

}

class TestService extends AbstractNodeService{
	
	
	public TestService(  String pathToScript, String url ) throws IOException{
		super(pathToScript,url);
	}

	@Override
	public void message(NodeContextObject contextObject) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
