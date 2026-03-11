package guru.springframework.spring7structuredlogging.logging;

import org.springframework.boot.logging.structured.StructuredLogFormatter;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class KeyValueLogger implements StructuredLogFormatter<ILoggingEvent> {

	@Override
	public String format(ILoggingEvent event) {
		return "legel=" + event.getLevel() + ", message=" + event.getFormattedMessage();
	}
}
