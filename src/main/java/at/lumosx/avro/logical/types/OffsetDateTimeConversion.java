package at.lumosx.avro.logical.types;

import org.apache.avro.Conversion;
import org.apache.avro.LogicalType;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeConversion extends Conversion<OffsetDateTime> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    public static final LogicalType OFFSET_DATE_TIME_TYPE = new LogicalType("offset-date-time");

    @Override
    public Class<OffsetDateTime> getConvertedType() {
        return OffsetDateTime.class;
    }

    @Override
    public String getLogicalTypeName() {
        return "offset-date-time";
    }

    @Override
    public OffsetDateTime fromCharSequence(CharSequence value, Schema schema, LogicalType type) {
        OffsetDateTime timestamp = null;
        try {
            timestamp = OffsetDateTime.parse(value, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            try {
                timestamp = OffsetDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
            } catch (Exception e1) {
                try {
                    timestamp = OffsetDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
                } catch (Exception e2) {
                    timestamp = OffsetDateTime.parse(value);
                }
            }
        }

        return timestamp;
    }

    @Override
    public CharSequence toCharSequence(OffsetDateTime value, Schema schema, LogicalType type) {
        return value.format(DATE_TIME_FORMATTER);
    }

    public static class TypeFactory implements LogicalTypes.LogicalTypeFactory {
        @Override
        public String getTypeName() {
            return OFFSET_DATE_TIME_TYPE.getName();
        }

        @Override
        public LogicalType fromSchema(Schema schema) {
            return OFFSET_DATE_TIME_TYPE;
        }
    }
}