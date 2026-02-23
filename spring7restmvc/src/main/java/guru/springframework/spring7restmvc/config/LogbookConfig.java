package guru.springframework.spring7restmvc.config;

import java.io.IOException;
import java.net.URI;

import org.slf4j.MDC;
import org.slf4j.event.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.RequestURI;
import org.zalando.logbook.logstash.LogstashLogbackSink;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@RequiredArgsConstructor
public class LogbookConfig {

	@Bean
	LogstashLogbackSink sink() {
		HttpLogFormatter formatter = new CustomHttpLogFormatter(new JsonMapper());
		LogstashLogbackSink sink = new LogstashLogbackSink(formatter, Level.INFO);
		return sink;
	}

	@RequiredArgsConstructor
	public static class CustomHttpLogFormatter implements HttpLogFormatter {
		private final JsonMapper jsonMapper;

		@Override
		public String format(Precorrelation precorrelation, HttpRequest request) throws IOException {
			// putMdc(MdcEcsField.USER_ID, getUserId());

			putMdc(MdcEcsField.SOURCE_ADDRESS, request.getRemote());

			putMdc(MdcEcsField.HTTP_REQUEST_ID, precorrelation.getId());
			putMdc(MdcEcsField.HTTP_REQUEST_METHOD, request.getMethod());
			putMdc(MdcEcsField.HTTP_VERSION, request.getProtocolVersion());

			// Added as JSON string - requires logstash config to convert to real object
			request.getHeaders().forEach((headerName, headerValues) -> {
				putMdc(MdcEcsField.HTTP_REQUEST_HEADERS + "." + headerName, jsonMapper.writeValueAsString(headerValues));
			});

			final URI uri = URI.create(RequestURI.reconstruct(request));
			putMdc(MdcEcsField.URL_FULL, uri.toString());
			putMdc(MdcEcsField.URL_DOMAIN, uri.getHost());
			putMdc(MdcEcsField.URL_PATH, uri.getPath());
			putMdc(MdcEcsField.URL_PORT, Integer.toString(uri.getPort()));
			putMdc(MdcEcsField.URL_QUERY, uri.getQuery());
			putMdc(MdcEcsField.URL_SCHEME, uri.getScheme());

			// "GET /some/url?some=param"
			final String path = uri.getQuery() == null
				? uri.getPath()
				: uri.getPath() + "?" + uri.getQuery();

			return request.getMethod() + " " + path;
		}

		@Override
		public String format(Correlation correlation, HttpResponse response) throws IOException {
			putMdc(MdcEcsField.HTTP_RESPONSE_STATUS_CODE, Integer.toString(response.getStatus()));
			putMdc(MdcEcsField.HTTP_VERSION, response.getProtocolVersion());

			// Added as JSON string - requires logstash config to convert to real object
			putMdc(MdcEcsField.HTTP_RESPONSE_HEADERS, jsonMapper.writeValueAsString(response.getHeaders()));

			putMdc(MdcEcsField.EVENT_DURATION, Long.toString(correlation.getDuration().toNanos()));
			putMdc(MdcEcsField.CUSTOM_DURATION, Long.toString(correlation.getDuration().toMillis()));

			final String path = getMdc(MdcEcsField.URL_QUERY) == null
				? getMdc(MdcEcsField.URL_PATH)
				: getMdc(MdcEcsField.URL_PATH) + "?" + getMdc(MdcEcsField.URL_QUERY);

			// "HTTP/1.1 200 - GET /some/url?some=param"
			return "%s %d - %s %s".formatted(response.getProtocolVersion(), response.getStatus(), getMdc(MdcEcsField.HTTP_REQUEST_METHOD), path);
		}

		private void putMdc(MdcEcsField field, String value) {
			MDC.put(field.toString(), value);
		}
		private void putMdc(String field, String value) {
			MDC.put(field, value);
		}

		private String getMdc(MdcEcsField field) {
			return MDC.get(field.toString());
		}
	}

	/**
	 * ECS fields that can be added to MDC.
	 *
	 * They will be picked up by the `logback-ecs-encoder` and added to the JSON log
	 * output.
	 *
	 * @see https://www.elastic.co/docs/reference/ecs/ecs-field-reference
	 */
	@Getter
	@RequiredArgsConstructor
	public enum MdcEcsField {
		ECS_VERSION("ecs.version"),
		USER_ID("user.id"),
		SOURCE_ADDRESS("source.address"),
		HTTP_VERSION("http.version"),
		HTTP_REQUEST_ID("http.request.id"),
		HTTP_REQUEST_METHOD("http.request.method"),
		HTTP_RESPONSE_STATUS_CODE("http.response.status_code"),
		// Semi-official, see https://github.com/elastic/ecs/issues/232
		HTTP_REQUEST_HEADERS("http.request.headers"),
		// Semi-official, see https://github.com/elastic/ecs/issues/232
		HTTP_RESPONSE_HEADERS("http.response.headers"),
		URL_FULL("url.full"),
		URL_DOMAIN("url.domain"),
		URL_PATH("url.path"),
		URL_PORT("url.port"),
		URL_QUERY("url.query"),
		URL_SCHEME("url.scheme"),
		EVENT_DURATION("event.duration"),
		/** Duration in ms */
		CUSTOM_DURATION("custom.duration"),
		;

		private final String name;

		public String toString() {
			return name;
		}
	}
}
