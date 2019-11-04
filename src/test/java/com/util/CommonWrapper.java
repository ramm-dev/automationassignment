package com.util;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommonWrapper
{
    public static JsonPath rawToJSON(Response fullResponse)
    {
        String res = fullResponse.asString();
        JsonPath jsp = new JsonPath(res);
        return jsp;
    }

    public static JSONArray jsnObjectToArray(String res, String nodeName) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(res);
        JSONArray resultArray = (JSONArray) jsonObject.get(nodeName);
        return resultArray;
    }
}
