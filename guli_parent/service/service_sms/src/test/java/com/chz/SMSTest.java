package com.chz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/23 0:17
 * @Description: TODO
 */
@SpringBootTest
public class SMSTest {
    @Autowired
    private ObjectMapper mapper;

    @Test
    void test() throws JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",1123);
        String s = mapper.writeValueAsString(map);
        System.out.println(s);
    }
}
