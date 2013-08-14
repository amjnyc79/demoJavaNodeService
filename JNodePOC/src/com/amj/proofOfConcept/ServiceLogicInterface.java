package com.amj.proofOfConcept;

public interface ServiceLogicInterface<N extends NodeContextObject<?>> {

	public boolean performCustomBuisnessLogic(N nodeContextObject);
}
