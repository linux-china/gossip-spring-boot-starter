package org.mvnsearch.boot.gossip.scaleccube.codec.jackson;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ZonedDateTimeSerializer extends StdSerializer<ZonedDateTime> {

	private static final long serialVersionUID = 6245182835980474796L;

	public ZonedDateTimeSerializer() {
		this(null, false);
	}

	protected ZonedDateTimeSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
	}

	@Override
	public void serialize(ZonedDateTime time, JsonGenerator generator,
			SerializerProvider provider) throws IOException {

		generator.writeString(time.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

	}

}