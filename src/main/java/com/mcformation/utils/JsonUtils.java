package com.mcformation.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtils {

    public static String objectToJson(Object object) {
        String res = "parsing error";
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
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
