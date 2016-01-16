package holamundo.view;

import holamundo.model.LogEvent;
import holamundo.model.LoggerFederate;
import holamundo.model.ProgrammersFederate;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class LoggerOverviewController {

	@FXML
	private TextArea text;
	private LoggerFederate loggerFederate;
	private ProgrammersFederate programmersFederate;

	public LoggerOverviewController() {
	}
	
	@FXML
	private void initialize() {
		this.text.setEditable(false);
		this.text.setText("Log: \n");
	}
	
    public void log(String message) {
    	this.text.appendText(message + "\n");
    }
    
    public void log(double workDone) {
    	this.text.appendText(new LogEvent(workDone) + "\n");
    }
    
    @FXML
    private void handleDestroyFederation() {
    	try {
	    	this.loggerFederate.endFederationExecution();
	    	this.programmersFederate.endFederationExecution();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    private void handleCreateFederation() {
		try {
			this.loggerFederate = new LoggerFederate(this);
			this.loggerFederate.execFederation();
			this.programmersFederate = new ProgrammersFederate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
