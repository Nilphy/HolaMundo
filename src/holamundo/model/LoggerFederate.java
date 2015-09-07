package holamundo.model;

import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.exceptions.FederateInternalError;
import holamundo.view.LoggerOverviewController;

import java.util.HashMap;
import java.util.Map;

public class LoggerFederate extends AbstractFederate {

	private static final String FEDERATE_NAME = "LoggerFederate";
	
	private LoggerOverviewController controller;
	private Map<ObjectInstanceHandle, HLAProgrammer> hlaProgrammers;
	
	public LoggerFederate(LoggerOverviewController controller) throws Exception {
		this.controller = controller;
		this.hlaProgrammers = new HashMap<>();
	}
	
	public void execFederation() throws Exception {
		federate(FEDERATE_NAME, new LoggerFederateAmbassador());
	}
	
	public void endFederationExecution() throws Exception {
		System.out.println("EndFederationExcecution");
		resignFromFederation();
	}
	
	
	public LoggerOverviewController getController() {
		return this.controller;
	}
	
	public Map<ObjectInstanceHandle, HLAProgrammer> getProgrammers() {
		return this.hlaProgrammers;
	}
	
	@Override
	protected AttributeHandleSet configureProgrammer() throws Exception {
		AttributeHandleSet programmerAttributeHandles = super.configureProgrammer();
		getRTIAmbassador().subscribeObjectClassAttributes(getProgrammerObjectClassHandle(), programmerAttributeHandles);
		return programmerAttributeHandles;
	}
	
	@Override
	protected void configureInformInteraction() throws Exception {
		super.configureInformInteraction();
		getRTIAmbassador().subscribeInteractionClass(getInformInteractionClassHandle());
	}
	
	public class LoggerFederateAmbassador extends NullFederateAmbassador implements FederateAmbassador {
		

		@Override
	    public void discoverObjectInstance(
	    		ObjectInstanceHandle objectInstanceHandle, 
	    		ObjectClassHandle objectClassHandle, 
	    		String objectInstanceName) throws FederateInternalError {
	    	discoverObjectInstance(objectInstanceHandle, objectClassHandle, objectInstanceName, null);
	    }
	
	    @Override
	    public void discoverObjectInstance(
	    		ObjectInstanceHandle objectInstanceHandle, 
	    		ObjectClassHandle objectClassHandle, 
	    		String objectInstanceName, 
	    		FederateHandle producingFederate) throws FederateInternalError {
	    	if (getProgrammerObjectClassHandle().equals(objectClassHandle)) {
		        HLAProgrammer hlaProgrammer;
		        try {
		        	hlaProgrammer = new HLAProgrammer(LoggerFederate.this, objectInstanceHandle, objectInstanceName) {
		        		
		        		@Override
		        		public void run() {
		        			// TODO Auto-generated method stub
		        		}
		        	};
		        } catch (Exception e) {
		        	throw new FederateInternalError(e.getMessage());
		        }
		        programmerDiscovered(hlaProgrammer);
	      	}
	    }
	
	    private void programmerDiscovered(HLAProgrammer hlaProgrammer) {
			getController().log("Discovered Programmer: " + hlaProgrammer.getObjectInstanceName());
			getProgrammers().put(hlaProgrammer.getObjectInstanceHandle(), hlaProgrammer);
		}

		@Override
	    public void reflectAttributeValues(
	    		ObjectInstanceHandle objectInstanceHandle, 
	    		AttributeHandleValueMap attributeValues, 
	    		byte[] tag, 
	    		OrderType sentOrdering, 
	    		TransportationTypeHandle transportationTypeHandle, 
	    		SupplementalReflectInfo reflectInfo) throws FederateInternalError {
			HLAProgrammer programmer = getProgrammers().get(objectInstanceHandle);
	    	programmer.reflectAttributeBalues(attributeValues);
	    	getController().log(programmer.getWorkDone());
	    }
	
	    @Override
	    public void receiveInteraction(
				InteractionClassHandle interactionClassHandle, 
				ParameterHandleValueMap parameterValues, 
				byte[] tag,
				OrderType sentOrdering, 
				TransportationTypeHandle transportationTypeHandle, 
				SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
	    	System.out.println("receive interaction");
	    }
	
	    @Override
	    public void removeObjectInstance(
				ObjectInstanceHandle objectInstanceHandle, 
				byte[] tag, 
				OrderType sentOrdering, 
				SupplementalRemoveInfo removeInfo) throws FederateInternalError {
	    	System.out.println("remove object instance");
	    }
	
	    @Override
	    public void provideAttributeValueUpdate(
				ObjectInstanceHandle objectInstanceHandle, 
				AttributeHandleSet attributeHandles, 
			    byte[] tag) throws FederateInternalError {
	    	System.out.println("provideAttributeValueUpdate");
	    }
	
	    @Override
	    public void requestAttributeOwnershipAssumption(
	    		ObjectInstanceHandle objectInstanceHandle, 
	    		AttributeHandleSet attributeHandles, 
	    		byte[] tag) throws FederateInternalError {
	        System.out.println("requestAttributeOwnershipAssumption");
	    }
	
	    @Override
	    public void attributeOwnershipAcquisitionNotification(
	    		ObjectInstanceHandle objectInstanceHandle, 
	    		AttributeHandleSet attributeHandles, 
	    		byte[] tag) throws FederateInternalError {
		    System.out.println("attributeOwnsershipAcquisitionNotification");
	    }
	
	    @Override
	    public void attributeOwnershipUnavailable(
	    		ObjectInstanceHandle objectInstanceHandle, 
	    		AttributeHandleSet attributeHandles) throws FederateInternalError {
	    	System.out.println("attributeOwnershipUnavailable");
	    }
	
	    @Override
	    public void requestAttributeOwnershipRelease(
	    		ObjectInstanceHandle objectInstanceHandle, 
	    		AttributeHandleSet attributeHandles, 
	    		byte[] tag) 
	    				throws FederateInternalError {
	    	System.out.println("request attriibute ownsership release");
	    }
	}
}
