package holamundo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LogEvent {
	
	private static final DateTimeFormatter DATE_TIME_FORMATER = DateTimeFormatter.ofPattern("d/m/Y H:M:S.N");
	private ObjectProperty<LocalDateTime> receivedMessageDate;
	private StringProperty event;

	public LogEvent() {
        this(0);
    }
	
	public LogEvent(double unitsOfWork) {
		this.event = new SimpleStringProperty(String.format("Programer has done %f units of work", unitsOfWork));
		this.receivedMessageDate =  new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
	}

	@Override
	public String toString() {
		return DATE_TIME_FORMATER.format(receivedMessageDate.getValue()) + ": " + event.getValue();
	}
}
