package ie.myinfinity.demo.creditcard.entities.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Echinofora1973
 */
@Converter
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return localDate==null?null:Date.valueOf(localDate);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return dbData==null?null:dbData.toLocalDate();
    }
}
