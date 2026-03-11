package guru.springframework.spring7structuredlogging.logging;

import org.springframework.boot.json.JsonWriter;
import org.springframework.boot.logging.structured.StructuredLogFormatter;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class JsonLogger implements StructuredLogFormatter<ILoggingEvent> {

	private final JsonWriter<ILoggingEvent> writer = JsonWriter.<ILoggingEvent>of(members -> {
		members.add("Level", ILoggingEvent::getLevel);
		members.add("Message", ILoggingEvent::getFormattedMessage);
		members.add("Timestamp", ILoggingEvent::getTimeStamp);
	}).withNewLineAtEnd();

	@Override
	public String format(ILoggingEvent event) {
		return writer.writeToString(event);
	}
}
