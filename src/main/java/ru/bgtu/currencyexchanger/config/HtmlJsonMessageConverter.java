package ru.bgtu.currencyexchanger.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class HtmlJsonMessageConverter extends AbstractHttpMessageConverter<Object> {

    private final ObjectMapper objectMapper;

    public HtmlJsonMessageConverter(ObjectMapper objectMapper) {
        super(new org.springframework.http.MediaType("text", "html", StandardCharsets.UTF_8));
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException {
        String json = new StringHttpMessageConverter(StandardCharsets.UTF_8).read(String.class, inputMessage);
        return objectMapper.readValue(json, clazz);
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws HttpMessageNotWritableException {
        //skip
    }
}