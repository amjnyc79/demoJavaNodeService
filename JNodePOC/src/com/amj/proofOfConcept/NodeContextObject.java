package com.amj.proofOfConcept;

public class NodeContextObject<T extends Comparable>{

	public static final String MESSAGE = "MESSAGE";
	private final T message;
	
	private String contextResponse;
	


	public NodeContextObject( T message ){
		this.message = message;
	}
	
	public T getContextMessage(){
		return this.message;
	}
	public String getContextResponse() {
		return contextResponse;
	}

	public void setContextResponse(String contextResponse) {
		this.contextResponse = contextResponse;
	}
	
	
}
