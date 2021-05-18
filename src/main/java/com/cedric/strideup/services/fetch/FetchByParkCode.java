package com.cedric.strideup.services.fetch;

import java.util.List;

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
public class FetchByParkCode extends FetchImpl {

    private GetAPI getAPI = new GetAPI();

    public FetchByParkCode() {}

    public FetchByParkCode( GetAPI getAPI ) {
        this.getAPI = getAPI;
    }

    /**
     * @Purpose : Check the Internal Database for the identified 'parkCode'
     * @Note : If the identified 'parkNode' does not exist in the InternalDB, return null
     * @param parkCode
     * @param dataStringRepo
     * @return JSONObject
     */
    private JSONObject getUnit_CheckDB( String parkCode , DataStringRepo dataStringRepo ) {

        List<DataString> dataStringList = dataStringRepo.findAllByParkCode( parkCode );

        JSONObject mainBody = new JSONObject();
        JSONProcessing.makeTheJSONObjectOrdered( mainBody );

        JSONArray parkArray = new JSONArray();
        mainBody.put("total" , "1");
        mainBody.put("limit" , "50");
        mainBody.put("start" , "0");

        if( dataStringList.isEmpty() == false ) {
            JSONObject tempBody = new JSONObject(dataStringList.get(0).getBody());
            parkArray.put( tempBody );
            mainBody.put("data" , parkArray);

            return mainBody;
        }
        return null;
    }
    
    /**
     * @Purpose : Check the RemoreAPI for the identified 'parkCode'
     * @Note : If the identified 'parkNode' does not exist in the RemoteAPI, return null
     * @param parkCode
     * @param dataStringRepo
     * @return JSONObject
     */
    private JSONObject getUnit_CheckAPI( String parkCode ) {
        JSONObject json = getAPI.getAPIFlex( ); 
        JSONObject mainBody = new JSONObject();
        JSONProcessing.makeTheJSONObjectOrdered( mainBody );

        JSONArray parkArray = new JSONArray();
        mainBody.put("total" , "1");
        mainBody.put("limit" , "50");
        mainBody.put("start" , "0");
        if( json != null ) {
            try {
                JSONObject tempBody = new JSONObject( json.getJSONArray("data").getJSONObject(0).toString() );

                String s2 = tempBody.getString("parkCode");
                if( s2.equals(parkCode) ) {
                    parkArray.put( tempBody );
                    mainBody.put("data" , parkArray);
                    
                    return mainBody;
                }
            } catch( Exception e ) {
                e.printStackTrace();
            }
    
        }
        return null;
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
    public JSONObject getUnit( String parkCode , DataStringRepo dataStringRepo ) {
        
        getAPI.constructParam_ParkCode( parkCode );
        
        // Check the Internal DB.
        // If the Park exists, return its JSONObject.
        JSONObject resJsonObject = getUnit_CheckDB( parkCode , dataStringRepo );
        if( resJsonObject != null )
            return resJsonObject;
        
        // Check the RemoteAPI.
        // If the Park exists, return its JSONObject.
        resJsonObject = getUnit_CheckAPI( parkCode );
        if( resJsonObject != null )
            return resJsonObject;
        
        // If the Park does not exist in both the IntetnalDB and the RemoteAPI,
        return null;
    }
    
}
