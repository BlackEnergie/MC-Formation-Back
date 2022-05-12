package com.mcformation.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static String objectToJson(Object object) {
        String res = "parsing error";
        try {
            ObjectMapper ow = new ObjectMapper();
            res = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public static void printObject(Object object) {
        System.out.println(objectToJson(object));
    }

}
