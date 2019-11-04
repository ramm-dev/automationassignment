package com.util;

public class ResourcesList {
    public static String CategoryResourcesID(int Id)
    {
        String resource="/v1/Categories/"+Id+"/Details.json";
        return resource;
    }
}
