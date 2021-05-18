package com.cedric.strideup.services.fetch;

import java.util.HashMap;

import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;
import com.cedric.strideup.services.fetch.extract.ExtractFrom;

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
public class FetchALL extends ExtractFrom {

    // private GetAPI getAPI = new GetAPI();
    // private Map<String ,String> mp;
    // private List<DataString> t;

    public FetchALL() {
        this.mp = new HashMap<String ,String>();
        this.getAPI = new GetAPI();
    }

    public FetchALL( GetAPI getAPI ) {
        this.getAPI = getAPI;
        this.mp = new HashMap<String ,String>();
    }

    /**
     * @Purpose : Inorder, perform the RemoteAPI Fetch, Internal DB Fetch, and JSON Build
     * @Note : For each of RemoteAPI Fetch, Internal DB Fetch , The Map needs to be updated
     * @param dataStringRepo
     * @return JSONObject
     */
    public JSONObject getAll( DataStringRepo dataStringRepo ) {

        this.extractFromAPI();

        this.t = dataStringRepo.findAll();
        this.extractFromDB();

        JSONObject res = this.buildJSON();
        
        this.mp.clear();

        return res;
    }
    
}
