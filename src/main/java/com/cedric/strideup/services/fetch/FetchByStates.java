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
public class FetchByStates extends ExtractFrom {

    public FetchByStates() {
        this.getAPI = new GetAPI();
        this.mp = new HashMap<String ,String>();
    }

    public FetchByStates( GetAPI getAPI ) {
        this.getAPI = getAPI;
    }

    /**
     * @Purpose : Method to get the Park identitied as 'parkCode'
     * @Approach : Check if the 'parkCode' is present in the InternalDB and then the RemoteAPI
     *             Return the result if the parkCode exists
     *             Note: if the Park exists in the InternalDB, the RempteAPI will not even be checked 
     *                   given that the InternalDB is the most up-to-date version from our point of view.
     * @param parkCode
     * @param dataStringRepo
     * @return
     */
    public JSONObject getUnit( String states , DataStringRepo dataStringRepo ) {

        this.states = states;
        this.getAPI.constructParam_States( this.states );
        this.extractFromAPI();

        // Fetch from the Internal Database
        this.t = dataStringRepo.findAllByStates( this.states );
        this.extractFromDB();

        JSONObject res = buildJSON();
        
        this.mp.clear();

        return res;
        
    }
    
}
