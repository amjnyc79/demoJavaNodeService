package com.amj.proofOfConcept;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ScriptFile {
	
	public static final String[] basicServer	= { "var http = require('http');",	"http.createServer(function (request, response) {",
													"    response.writeHead(200, {","        'Content-Type': 'text/plain',",
													"        'Access-Control-Allow-Origin' : '*'",	"    });","    response.end('Hello World\n');",
													"}).listen(<port_param>);"};
	private final File script;
	private final Integer portADDR;
	
	public String getScriptPath( ){
		return this.script.getAbsolutePath();
	}
	public ScriptFile( String name, int listenerPort ) throws IOException{
		
		this.script = new File(name);
		//this.script.deleteOnExit();
		this.portADDR = listenerPort;
		
		if(!this.script.exists()){
			this.script.createNewFile();
		}
		
		this.generate_file( );
		
	}
	
	
	private void generate_file() throws FileNotFoundException{
		PrintStream streamOut = new PrintStream(new FileOutputStream(this.script,true),true);
		for(String line:basicServer){
			if(line.indexOf("<port_param>") > -1 ){
				line = line.replaceAll("<port_param>",this.portADDR.toString());
			}
			streamOut.print(line);
		}
		
	}
	
	

}
