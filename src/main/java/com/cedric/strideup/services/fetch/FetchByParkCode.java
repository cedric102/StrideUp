package com.cedric.strideup.services.fetch;

import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;
import com.cedric.strideup.services.fetch.extract.ExtractUnitFrom;

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
public class FetchByParkCode extends ExtractUnitFrom {

    // private GetAPI getAPI;
    // private String parkCode;

    public FetchByParkCode() {
        this.getAPI = new GetAPI();
    }

    public FetchByParkCode( GetAPI getAPI ) {
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
    public JSONObject getUnit( String parkCode , DataStringRepo dataStringRepo ) {
        
        this.parkCode = parkCode;
        this.getAPI.constructParam_ParkCode( parkCode );

        // Check the Internal DB.
        // If the Park exists, return its JSONObject.
        JSONObject resJsonObject = this.getUnit_CheckDB( dataStringRepo );
        if( resJsonObject != null )
            return resJsonObject;
        
        // Check the RemoteAPI.
        // If the Park exists, return its JSONObject.
        resJsonObject = this.getUnit_CheckAPI();
        if( resJsonObject != null )
            return resJsonObject;

        // If the Park does not exist in both the IntetnalDB and the RemoteAPI,
        return null;
    }
    
}
