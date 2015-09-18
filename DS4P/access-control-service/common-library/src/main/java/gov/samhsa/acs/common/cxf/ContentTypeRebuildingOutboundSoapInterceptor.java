package gov.samhsa.acs.common.cxf;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentTypeRebuildingOutboundSoapInterceptor extends
		AbstractSoapInterceptor {

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String SEMICOLON = ";";
	private static final String EQUAL = "=";
	private static final String SPACE = " ";
	private static final String EMPTY = "";
	private static final String BACKSLASH = "\\";
	private static final String DOUBLE_QUOTE = "\"";
	private static final int IDX_KEY = 0;
	private static final int IDX_VALUE = 1;
	private static final String SEMICOLON_WITH_SPACE = SEMICOLON + SPACE;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ContentTypeRebuildingOutboundSoapInterceptor() {
		super(Phase.WRITE);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		debug(() -> message.toString());
		debug(() -> new StringBuilder().append(CONTENT_TYPE)
				.append(" before modification: ")
				.append(message.get(CONTENT_TYPE).toString()).toString());

		final String contentType = (String) message.get(CONTENT_TYPE);

		final List<Pair> contentTypeList = new ArrayList<>();

		try (Scanner scanner = new Scanner(contentType)) {
			scanner.useDelimiter(SEMICOLON);
			while (scanner.hasNext()) {
				final String next = scanner.next();
				final String[] split = next.split(EQUAL);
				contentTypeList.add(new Pair(clean(split, IDX_KEY),
						split.length > IDX_VALUE ? Optional.of(clean(split,
								IDX_VALUE)) : Optional.empty()));
			}
		}

		final String correctedContentType = contentTypeList
				.stream()
				.map(ContentTypeRebuildingOutboundSoapInterceptor::toCorrectedContentType)
				.collect(joining(SEMICOLON_WITH_SPACE));

		debug(() -> new StringBuilder().append(CONTENT_TYPE)
				.append(" after modification: ").append(correctedContentType)
				.toString());
		message.put(CONTENT_TYPE, correctedContentType);
	}

	private final void debug(Supplier<String> msg) {
		if (this.logger.isDebugEnabled()) {
			logger.debug(msg.get());
		}
	}

	private static final String clean(String[] split, int idx) {
		return split[idx].replace(DOUBLE_QUOTE, EMPTY)
				.replace(BACKSLASH, EMPTY).replace(SPACE, EMPTY);
	}

	private static final String toCorrectedContentType(Pair entry) {
		return new StringBuilder().append(entry.getKey())
				.append(wrap(entry.getValue().orElse(EMPTY))).toString();
	}

	private static final String wrap(String value) {
		return EMPTY.equals(value) ? value : new StringBuilder().append(EQUAL)
				.append(DOUBLE_QUOTE).append(value).append(DOUBLE_QUOTE)
				.toString();
	}

	private class Pair {
		private final String key;
		private final Optional<String> value;

		public Pair(String key, Optional<String> value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return new StringBuilder().append(this.key).append("=")
					.append(this.value.orElse("")).toString();
		}

		private String getKey() {
			return key;
		}

		private Optional<String> getValue() {
			return value;
		}
	}

}
