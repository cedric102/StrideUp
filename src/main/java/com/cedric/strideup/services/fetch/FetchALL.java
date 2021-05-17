package com.cedric.strideup.services.fetch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Purpose : Fetch all of the Parks and return them to as a JSONObject
 * @Rule : If the same parkCode is present in both the Remote API and the Internal Database ,
 *         Keep the one for the Internal Repository given that it is 
 *         the updated version from our point of view
 * @Approach : Store the Fetched data in a Map<String, String>. 
 *             The first key contains the parkCode and the second contains 
 *             the whole JSONObject represented as a string
 *             The DB Scan will override it next.
 * @Author : C. Carteron
 */
public class FetchALL {

    private GetAPI getAPI = new GetAPI();
    private Map<String ,String> mp;

    public FetchALL() {}

    public FetchALL( GetAPI getAPI ) {
        this.getAPI = getAPI;
    }

    // Fetch from the Repote API and populate the Map
    private void extractFromAPI () {

        // Fetch from the Remote API
        JSONObject obj = getAPI.getAPI( "" );

        // Populate the Map
        JSONArray arr = obj.getJSONArray("data");
        for( int i=0 ; i<arr.length() ; i++ ) {
            JSONObject temp = arr.getJSONObject(i);
            this.mp.put( temp.getString("parkCode") , temp.toString() );
        }

    }

    // Fetch from the Internal Database and populate / override the Map
    private void extractFromDB ( DataStringRepo dataStringRepo ) {

        // Fetch from the Internal Database
        List<DataString> t = dataStringRepo.findAll();

        // Populate / Override the Map
        for( DataString d : t ) {
            this.mp.put( d.getParkCode() , d.getBody().toString() );
        }

    }

    // Build the JSONObject to be returned.
    private JSONObject buildJSON() {

        JSONArray mainArray = new JSONArray();
        JSONObject mainBody = new JSONObject();

        mainBody.put("total" , ""+this.mp.size() );
        mainBody.put("limit" , "50");
        mainBody.put("start" , "0");
        for( Map.Entry<String, String> s : this.mp.entrySet() )
            mainArray.put( new JSONObject( s.getValue() ) );
        mainBody.put("data" , mainArray);

        return mainBody;
    }
    /**
     * @Purpose : Inorder, perform the RemoteAPI Fetch, Internal DB Fetch, and JSON Build
     * @Note : For each of RemoteAPI Fetch, Internal DB Fetch , The Map needs to be updated
     * @param dataStringRepo
     * @return JSONObject
     */
    public JSONObject getAll( DataStringRepo dataStringRepo ) {
        this.mp = new HashMap<String ,String>();

        extractFromAPI();
        extractFromDB( dataStringRepo );

        JSONObject res = buildJSON();
        
        return res;
    }
    
}
