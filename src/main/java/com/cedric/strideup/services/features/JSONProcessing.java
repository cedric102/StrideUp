package com.cedric.strideup.services.features;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.json.JSONObject;

public class JSONProcessing {
    
    // Method to keep the field in order of insertion.
    public static void makeTheJSONObjectOrdered( JSONObject obj ) {
        try{
            Field changeMap = obj.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set( obj , new LinkedHashMap<>() );
            changeMap.setAccessible(false);
            
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }
    
}
