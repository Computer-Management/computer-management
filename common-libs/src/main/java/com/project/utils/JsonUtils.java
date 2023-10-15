package com.project.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JsonUtils {

    private JsonUtils() {
    }

    private static ObjectMapper mapper;
    private static ObjectMapper mapperThin;
    private static ObjectMapper oracleMapper;
    private static Map<String, String> fieldMapper;

    private static Logger log = LogManager.getLogger(JsonUtils.class);

    static {
        fieldMapper = new HashMap<>();
        fieldMapper.put("user", "USERNAME");
        fieldMapper.put("min", "MINVALUE");
        fieldMapper.put("max", "MAXVALUE");
        fieldMapper.put("desc", "DESCRIPTION");
        fieldMapper.put("number", "NUMBERVALUE");
        fieldMapper.put("default", "DEFAULTVALUE");
        fieldMapper.put("time", "TIMEVALUE");
        fieldMapper.put("like", "LIKEVALUE");
        fieldMapper.put("comment", "COMMENTVALUE");
        fieldMapper.put("share", "SHAREVALUE");

        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);

        mapperThin = new ObjectMapper(mapper.getFactory());
        mapperThin.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapperThin.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        oracleMapper = new ObjectMapper();
        oracleMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        oracleMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        oracleMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        oracleMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        oracleMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        oracleMapper.setPropertyNamingStrategy(new UpperCaseStrategy());
    }

    static class UpperCaseStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {
        @Override
        public String translate(String propertyName) {
            if (fieldMapper.containsKey(propertyName.toLowerCase())) {
                return fieldMapper.get(propertyName.toLowerCase());
            }
            return propertyName.toUpperCase();
        }
    }

    public static <T> T jsonToObj(String object, Class<T> type) {
        try {
            return mapper.readValue(object, type);
        } catch (Exception e) {
            log.error("error jsonToObj:" + object, e);
            return null;
        }
    }

    public static <T> T jsonToObj(JsonObject object, Class<T> type) {
        try {
            return mapper.readValue(object.encode(), type);
        } catch (Exception e) {
            log.error("error jsonToObj:" + object, e);
            return null;
        }
    }

    public static <T> T jsonToObjWithEx(String object, Class<T> type) throws Exception {
        return mapper.readValue(object, type);
    }

    /**
     * Convert Json To Obj List
     *
     * @param objectList
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToObjList(String objectList, Class<T> type) {
        List<T> result = new ArrayList<>();

        if (objectList == null || objectList.isEmpty()) {
            return result;
        }

        try {
            JsonArray jsonArray = new JsonArray(objectList);
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.getJsonObject(i);
                result.add(mapper.readValue(jsonObject.encode(), type));
            }
        } catch (Exception e) {
            log.error("error jsonToObjList:" + objectList, e);
        }

        return result;
    }

    public static String objToString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            log.error("error objToString: " + e.getMessage(), e);
            return null;
        }
    }

    public static JsonObject objToJsonObj(Object o) {
        try {
            return new JsonObject(mapper.writeValueAsString(o));
        } catch (Exception e) {
            log.error("error objToJsonObj: " + e.getMessage(), e);
            return null;
        }
    }

    public static JsonObject objToThinJsonObj(Object o) {
        try {
            return new JsonObject(mapperThin.writeValueAsString(o));
        } catch (Exception e) {
            log.error("error objToThinJsonObj: " + e.getMessage(), e);
            return null;
        }
    }

    public static Map<String, Object> objToMap(Object obj) {
        JsonObject jo = objToJsonObj(obj);
        return jo != null ? jo.getMap() : null;
    }

    public static <T> T mapToObj(Map<String, String> map, Class<T> type) {
        return mapper.convertValue(map, type);
    }

    public static <T> T mapObjectToObj(Map<String, Object> map, Class<T> type) {
        return mapper.convertValue(map, type);
    }

    public static boolean isJSON(String source) {
        try {
            new JsonObject(source);
            return true;
        } catch (Exception e) {
            log.warn("not a valid json:" + source, e);
            return false;
        }
    }

    public static boolean isJSONArray(String source) {
        try {
            new JsonArray(source);
            return true;
        } catch (Exception e) {
            log.warn("not a valid json array:" + source, e);
            return false;
        }
    }

    public static String objToThinJsonString(Object data) {
        JsonObject jo = objToThinJsonObj(data);
        if (jo != null) {
            return jo.encode();
        }
        return null;
    }

    public static JsonObject objToJsonObjUpper(Object o) {
        try {
            return new JsonObject(oracleMapper.writeValueAsString(o));
        } catch (Exception e) {
            log.error("error objToJsonObj: " + e.getMessage(), e);
            return null;
        }
    }

    public static Map<String, Object> objToMapUpper(Object obj) {
        JsonObject jo = objToJsonObjUpper(obj);
        return jo != null ? jo.getMap() : null;
    }

    public static JsonObject JsonStringToJsonObject(String s) {
        try {
            return new JsonObject(s);
        } catch (Exception ex) {
            log.error("Cannot parse to JsonObject from String: " + s, ex);
            return null;
        }
    }
}
