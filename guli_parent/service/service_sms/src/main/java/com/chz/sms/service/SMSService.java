package com.chz.sms.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;

/**
 * @Author: CHZ
 * @DateTime: 2020/7/22 20:52
 * @Description: TODO
 */
public interface SMSService {
    boolean sendSMS(HashMap<String, Object> param, String phoneNumber) throws JsonProcessingException;
}
