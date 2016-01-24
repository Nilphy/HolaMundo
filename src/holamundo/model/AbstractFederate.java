package holamundo.model;

import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.CallbackModel;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ParameterHandle;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.ResignAction;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.exceptions.FederationExecutionAlreadyExists;
import holamundo.MainApp;

public class AbstractFederate {

	private RTIambassador rtiAmbassador;
	private ObjectClassHandle programmerObjectClassHandle;
	private static final String INFORM_INTERACTION_NAME = "InformInteraction";
	private static final String INFORM_INTERACTION_MESSAGE_PARAM_NAME = "InformInteractionMessageParamName";
	
	private InteractionClassHandle informInteractionClassHandle;
	private ParameterHandle messageParameterHandle;

	public AbstractFederate() throws Exception {
		rtiAmbassador = RtiFactoryFactory.getRtiFactory().getRtiAmbassador();
	}
	
	protected AttributeHandleSet configureProgrammer() throws Exception {
		setProgrammerObjectClassHandle(getRTIAmbassador().getObjectClassHandle(HLAProgrammer.CLASS_NAME));
		AttributeHandleSet programmerAttributeHandles = getRTIAmbassador().getAttributeHandleSetFactory().create();
		programmerAttributeHandles.add(getRTIAmbassador().getAttributeHandle(getProgrammerObjectClassHandle(), HLAProgrammer.WORK_DONE_NAME));
		getRTIAmbassador().publishObjectClassAttributes(getProgrammerObjectClassHandle(), programmerAttributeHandles);
		return programmerAttributeHandles;
	}
		
	protected void configureInformInteraction() throws Exception {
		setInformInteractionClassHandle(getRTIAmbassador().getInteractionClassHandle(INFORM_INTERACTION_NAME));
		setMessageParameterHandle(getRTIAmbassador().getParameterHandle(getInformInteractionClassHandle(), INFORM_INTERACTION_MESSAGE_PARAM_NAME));
		getRTIAmbassador().publishInteractionClass(getInformInteractionClassHandle());
	}
	
	protected void federate(String federateName, NullFederateAmbassador federateAmbassador) throws Exception {
		getRTIAmbassador().connect(federateAmbassador, CallbackModel.HLA_EVOKED);
		try {
			getRTIAmbassador().createFederationExecution(MainApp.FEDERATION_NAME, MainApp.class.getResource(MainApp.FDD));
		} catch (FederationExecutionAlreadyExists feae) {
			// The federation has already been created by another federate
		}
		getRTIAmbassador().joinFederationExecution(federateName, MainApp.FEDERATION_NAME);
		configureProgrammer();
		configureInformInteraction();
		new Thread(() -> {
			try {
				while (true) {
					getRTIAmbassador().evokeMultipleCallbacks(1, 1);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}).start();
	}
	
	protected void resignFromFederation() throws Exception {
		getRTIAmbassador().resignFederationExecution(ResignAction.DELETE_OBJECTS);
	}
	
	public ObjectClassHandle getProgrammerObjectClassHandle() {
		return programmerObjectClassHandle;
	}

	private void setProgrammerObjectClassHandle(ObjectClassHandle programmerObjectClassHandle) {
		this.programmerObjectClassHandle = programmerObjectClassHandle;
	}
	
	public InteractionClassHandle getInformInteractionClassHandle() {
		return informInteractionClassHandle;
	}

	public void setInformInteractionClassHandle(InteractionClassHandle informInteractionClassHandle) {
		this.informInteractionClassHandle = informInteractionClassHandle;
	}
	
	public ParameterHandle getMessageParameterHandle() {
		return messageParameterHandle;
	}

	public void setMessageParameterHandle(ParameterHandle messageParameterHandle) {
		this.messageParameterHandle = messageParameterHandle;
	}

	public RTIambassador getRTIAmbassador() {
		return rtiAmbassador;
	}
}
