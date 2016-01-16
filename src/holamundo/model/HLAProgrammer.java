package holamundo.model;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.AttributeNotOwned;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectInstanceNotKnown;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public abstract class HLAProgrammer implements Runnable {

	public static final String CLASS_NAME = "Programmer";
	public static final String WORK_DONE_NAME = "WorkDone";
	private ObjectInstanceHandle objectInstanceHandle;
	private String objectInstanceName;
	private AttributeHandle workDoneAttributeHandle;
	private double workDone;
	private RTIambassador rtiAmbassador;

	public HLAProgrammer(
			AbstractFederate federate, 
			ObjectInstanceHandle objectInstanceHandle, 
			String objectInstanceName) 
					throws NameNotFound, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError {
		this.objectInstanceHandle = objectInstanceHandle;
		this.objectInstanceName = objectInstanceName;
		this.rtiAmbassador = federate.getRTIAmbassador();
		this.workDoneAttributeHandle = rtiAmbassador.getAttributeHandle(federate.getProgrammerObjectClassHandle(), WORK_DONE_NAME);
	}

	public abstract void run();

	public void incrementWorkDone(double workDone) throws FederateNotExecutionMember, NotConnected, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, RTIinternalError {
		this.workDone += workDone;
		AttributeHandleValueMap attributeValues = this.rtiAmbassador.getAttributeHandleValueMapFactory().create(1);
		attributeValues.put(workDoneAttributeHandle, encodeWorkDone(this.workDone));
		this.rtiAmbassador.updateAttributeValues(objectInstanceHandle, attributeValues, null);
	}

	public byte[] encodeWorkDone(double workDone) {
		return ByteBuffer.allocate(8).putDouble(workDone).array();
	}

	public double decodeWorkDone(byte[] buffer) {
		return ByteBuffer.wrap(buffer).getDouble();
	}

	protected byte[] encodeName(String objectInstanceName) {
		return Charset.forName("UTF-8").encode(objectInstanceName).array();
	}

	public ObjectInstanceHandle getObjectInstanceHandle() {
		return objectInstanceHandle;
	}

	public void setObjectInstanceHandle(ObjectInstanceHandle objectInstanceHandle) {
		this.objectInstanceHandle = objectInstanceHandle;
	}

	public String getObjectInstanceName() {
		return objectInstanceName;
	}

	public void setObjectInstanceName(String objectanstanceName) {
		this.objectInstanceName = objectanstanceName;
	}

	public double getWorkDone() {
		return workDone;
	}

	public void setWorkDone(double workDone) {
		this.workDone = workDone;
	}

	public AttributeHandle getWorkDoneAttributeHandle() {
		return workDoneAttributeHandle;
	}

	public void setWorkDoneAttributeHandle(AttributeHandle workDoneAttributeHandle) {
		this.workDoneAttributeHandle = workDoneAttributeHandle;
	}

	public void reflectAttributeValues(AttributeHandleValueMap attributeValues) {
		byte[] workDone = attributeValues.get(getWorkDoneAttributeHandle());
		this.setWorkDone(decodeWorkDone(workDone));
	}
}
