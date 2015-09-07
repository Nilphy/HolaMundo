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
import holamundo.MainApp;
import javafx.application.Platform;

public class ProgrammersFederate extends AbstractFederate {

	private static final String FEDERATE_NAME = "ProgrammersFederate";
	
	public ProgrammersFederate() throws Exception {
		federate(FEDERATE_NAME, new ProgrammerFederateAmbassador());
		HLAProgrammer hlaProgrammer1 = registerFirstProgrammer();
		HLAProgrammer hlaProgrammer2 = registerSecondProgrammer();
		Platform.runLater(hlaProgrammer1);
		Platform.runLater(hlaProgrammer2);
	}

	private HLAProgrammer registerFirstProgrammer() throws Exception {
		ObjectInstanceHandle objectInstanceHandle = getRTIAmbassador().registerObjectInstance(getProgrammerObjectClassHandle());
		String objectInstanceName = getRTIAmbassador().getObjectInstanceName(objectInstanceHandle);
		HLAProgrammer hlaProgrammer = new HLAProgrammer(this, objectInstanceHandle, objectInstanceName) {

			@Override
			public void run() {
				try {
					this.incrementWorkDone(2);
					Thread.sleep(100);
					this.incrementWorkDone(1);
					Thread.sleep(200);
					this.incrementWorkDone(2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		return hlaProgrammer;
	}

	private HLAProgrammer registerSecondProgrammer() throws Exception {
		ObjectInstanceHandle objectInstanceHandle = getRTIAmbassador().registerObjectInstance(getProgrammerObjectClassHandle());
		String objectInstanceName = getRTIAmbassador().getObjectInstanceName(objectInstanceHandle);
		HLAProgrammer hlaProgrammer = new HLAProgrammer(this, objectInstanceHandle, objectInstanceName) {

			@Override
			public void run() {
				try {
					this.incrementWorkDone(3);
					Thread.sleep(500);
					this.incrementWorkDone(5);
					Thread.sleep(100);
					this.incrementWorkDone(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		return hlaProgrammer;
	}
	
	public void endFederationExecution() throws Exception {
		System.out.println("EndFederationExcecution");
		resignFromFederation();
		getRTIAmbassador().destroyFederationExecution(MainApp.FEDERATION_NAME);
	}
	
	public class ProgrammerFederateAmbassador extends NullFederateAmbassador implements FederateAmbassador {

		private ObjectClassHandle programmerObjectClassHandle;
		private Object programmerFederate;

		@Override
		public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle, ObjectClassHandle objectClassHandle, String objectInstanceName) throws FederateInternalError {
			discoverObjectInstance(objectInstanceHandle, objectClassHandle, objectInstanceName, null);
			System.out.println("discoverObjectInstance");
		}

		@Override
		public void discoverObjectInstance(ObjectInstanceHandle objectInstanceHandle, ObjectClassHandle objectClassHandle, String objectInstanceName, FederateHandle producingFederate)
				throws FederateInternalError {
			System.out.println("discoverObjectInstance");
		}

		@Override
		public void reflectAttributeValues(ObjectInstanceHandle objectInstanceHandle, AttributeHandleValueMap attributeValues, byte[] tag, OrderType sentOrdering,
				TransportationTypeHandle transportationTypeHandle, SupplementalReflectInfo reflectInfo) throws FederateInternalError {
			System.out.println("reflectAttributeValues");
		}

		@Override
		public void receiveInteraction(InteractionClassHandle interactionClassHandle, ParameterHandleValueMap parameterValues, byte[] tag, OrderType sentOrdering,
				TransportationTypeHandle transportationTypeHandle, SupplementalReceiveInfo receiveInfo) throws FederateInternalError {
			System.out.println("receiveInteraction");
		}

		@Override
		public void removeObjectInstance(ObjectInstanceHandle objectInstanceHandle, byte[] tag, OrderType sentOrdering, SupplementalRemoveInfo removeInfo) throws FederateInternalError {
			System.out.println("removeObjectInstance");
		}

		@Override
		public void provideAttributeValueUpdate(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			System.out.println("provideAttributeValueUpdate");
		}

		@Override
		public void requestAttributeOwnershipAssumption(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			System.out.println("requestAttributeOwnershipAssumption");
		}

		@Override
		public void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			System.out.println("attributeOwnershipAcquisitionNotification");
		}

		@Override
		public void attributeOwnershipUnavailable(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles) throws FederateInternalError {
			System.out.println("attributeOwnershipUnavailable");
		}

		@Override
		public void requestAttributeOwnershipRelease(ObjectInstanceHandle objectInstanceHandle, AttributeHandleSet attributeHandles, byte[] tag) throws FederateInternalError {
			System.out.println("requestAttributeOwnershipRelease");
		}

		public ObjectClassHandle getProgrammerObjectClassHandle() {
			return programmerObjectClassHandle;
		}

		public void setProgrammerObjectClassHandle(ObjectClassHandle programmerObjectClassHandle) {
			this.programmerObjectClassHandle = programmerObjectClassHandle;
		}

		public Object getProgrammerFederate() {
			return programmerFederate;
		}

		public void setProgrammerFederate(Object programmerFederate) {
			this.programmerFederate = programmerFederate;
		}
	}
}
