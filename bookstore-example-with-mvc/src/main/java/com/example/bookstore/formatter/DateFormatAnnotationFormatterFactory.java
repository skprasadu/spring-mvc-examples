package com.example.bookstore.formatter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

/**
 * 
 * 
 *
 */
public class DateFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> types = new HashSet<Class<?>>(1);
        types.add(Date.class);
        return types;
    }

    @Override
    public Printer<?> getPrinter(DateFormat annotation, Class<?> fieldType) {
        return createFormatter(annotation);
    }

    @Override
    public Parser<?> getParser(DateFormat annotation, Class<?> fieldType) {
        return createFormatter(annotation);
    }

    private DateFormatter createFormatter(DateFormat annotation) {
        DateFormatter formatter = new DateFormatter();
        formatter.setFormat(annotation.format());
        return formatter;
    }

}
