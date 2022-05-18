package com.mcformation.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

    static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    public static String objectToJson(Object object) {
        String res = null;
        try {
            ObjectMapper ow = new ObjectMapper();
            res = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing object to json", e);
        }
        return res;
    }

}
