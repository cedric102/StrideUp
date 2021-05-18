package com.cedric.strideup.services.fetch.extract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.services.GetAPI;
import com.cedric.strideup.services.features.JSONProcessing;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExtractFrom implements IExtractFrom {

    public GetAPI getAPI;
    public Map<String ,String> mp;
    public List<DataString> t;
    public String states;

    
    public ExtractFrom() {
        mp = new HashMap<String ,String>();
    }

    public ExtractFrom( GetAPI getAPI ) {
        this.getAPI = getAPI;
        mp = new HashMap<String ,String>();
    }
    
    /**
     * @Purpose : Check the Internal Database for the identified 'parkCode'
     * @Note : If the identified 'parkNode' does not exist in the InternalDB, return null
     * @param parkCode
     * @param dataStringRepo
     * @return JSONObject
     */
    @Override
    public void extractFromDB() {

        // Populate / Override the Map
        for( DataString d : t ) {
            mp.put( d.getParkCode() , d.getBody().toString() );
        }
        
    }

    @Override
    public void extractFromAPI() {
        
        // Fetch from the Remote API
        JSONObject obj = getAPI.getAPIFlex( );

        // Populate the Map
        JSONArray arr = obj.getJSONArray("data");
        for( int i=0 ; i<arr.length() ; i++ ) {
            JSONObject temp = arr.getJSONObject(i);
            mp.put( temp.getString("parkCode") , temp.toString() );
        }
        
    }

    @Override
    public JSONObject buildJSON() {

        JSONArray parkArray = new JSONArray();
        JSONObject mainBody = new JSONObject();
        JSONProcessing.makeTheJSONObjectOrdered( mainBody );

        mainBody.put("total" , ""+mp.size() );
        mainBody.put("limit" , "50");
        mainBody.put("start" , "0");
        for( Map.Entry<String, String> s : mp.entrySet() )
            parkArray.put( new JSONObject( s.getValue() ) );
        mainBody.put("data" , parkArray);

        return mainBody;
    }
    
}
