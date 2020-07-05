package com.chz.eduservice.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 同时对Date和LocalDateTime都生效
 */
@Configuration
public class JacksonConfig {
    @Value("${spring.jackson.date-format}")
    private String pattern;
    @Value("${spring.jackson.time-zone}")
    private String timeZone;

//    @Bean
    public LocalDateTimeSerializer serializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    public LocalDateTimeDeserializer deserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer builderCustomizer() {
        return mapperBuilder -> {
            //同理LocalDate, LocalTime
            //添加序列化LocalDateTime
            mapperBuilder.serializerByType(LocalDateTime.class, serializer());
            //添加反序列化LocalDateTime
            mapperBuilder.deserializerByType(LocalDateTime.class, deserializer());
            mapperBuilder.timeZone(timeZone);
        };
    }
}
