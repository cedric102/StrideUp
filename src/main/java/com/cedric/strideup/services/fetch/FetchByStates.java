package com.cedric.strideup.services.fetch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;
import com.cedric.strideup.services.features.JSONProcessing;

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
public class FetchByStates extends IFetch {

    private GetAPI getAPI = new GetAPI();
    private Map<String ,String> mp;

    public FetchByStates() {}

    public FetchByStates( GetAPI getAPI ) {
        this.getAPI = getAPI;
    }

    /**
     * @Purpose : Check the Internal Database for the identified 'parkCode'
     * @Note : If the identified 'parkNode' does not exist in the InternalDB, return null
     * @param parkCode
     * @param dataStringRepo
     * @return JSONObject
     */

    // Fetch from the Repote API and populate the Map
    protected void extractFromAPI ( String states ) {

        // Fetch from the Remote API
        getAPI.constructParam_States( states );
        JSONObject obj = getAPI.getAPIFlex( );

        // Populate the Map
        JSONArray arr = obj.getJSONArray("data");
        for( int i=0 ; i<arr.length() ; i++ ) {
            JSONObject temp = arr.getJSONObject(i);
            this.mp.put( temp.getString("parkCode") , temp.toString() );
        }

    }

    // Fetch from the Internal Database and populate / override the Map
    protected void extractFromDB ( String states , DataStringRepo dataStringRepo ) {

        // Fetch from the Internal Database
        List<DataString> t = dataStringRepo.findAllByStates( states );

        // Populate / Override the Map
        for( DataString d : t ) {
            this.mp.put( d.getParkCode() , d.getBody().toString() );
        }

    }

    @Override
    // Build the JSONObject to be returned.
    protected JSONObject buildJSON() {

        JSONArray parkArray = new JSONArray();
        JSONObject mainBody = new JSONObject();
        JSONProcessing.makeTheJSONObjectOrdered( mainBody );

        mainBody.put("total" , ""+this.mp.size() );
        mainBody.put("limit" , "50");
        mainBody.put("start" , "0");
        for( Map.Entry<String, String> s : this.mp.entrySet() )
            parkArray.put( new JSONObject( s.getValue() ) );
        mainBody.put("data" , parkArray);

        return mainBody;
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
    @Override
    public JSONObject getUnit( String states , DataStringRepo dataStringRepo ) {
        
        this.mp = new HashMap<String ,String>();

        extractFromAPI( states );
        extractFromDB( states , dataStringRepo );

        JSONObject res = buildJSON();
        
        return res;
    }
    
}
