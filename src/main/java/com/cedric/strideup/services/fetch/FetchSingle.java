package com.cedric.strideup.services.fetch;

import java.util.List;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Purpose : Fetch the Park identified as 'parkCode' and return them to as a JSONObject
 * @Rule : If the same parkCode is present in both the Remote API and the Internal Database ,
 *         Use the one of the Internal Database given that it is 
 *         the updated version from our point of view
 * @Author : C. Carteron
 */
public class FetchSingle {
    
    private GetAPI getAPI = new GetAPI();
    
    public FetchSingle() {}
    
    public FetchSingle( GetAPI getAPI ) {
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

        JSONObject tempObj = new JSONObject();
        JSONArray tempArr = new JSONArray();
        tempObj.put("total" , "1");
        tempObj.put("limit" , "50");
        tempObj.put("start" , "0");

        if( dataStringList.isEmpty() == false ) {
            JSONObject tempBody = new JSONObject(dataStringList.get(0).getBody());
            tempArr.put( tempBody );
            tempObj.put("data" , tempArr);

            return tempObj;
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
        JSONObject json = getAPI.getAPI( parkCode ); 
        JSONObject tempObj = new JSONObject();
        JSONArray tempArr = new JSONArray();
        tempObj.put("total" , "1");
        tempObj.put("limit" , "50");
        tempObj.put("start" , "0");
        if( json != null ) {
            try {
                JSONObject tempBody = new JSONObject( json.getJSONArray("data").getJSONObject(0).toString() );

                String s2 = tempBody.getString("parkCode");
                if( s2.equals(parkCode) ) {
                    tempArr.put( tempBody );
                    tempObj.put("data" , tempArr);
                    
                    return tempObj;
                }
            } catch( Exception e ) {
                return null;
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
    public JSONObject getUnit( String parkCode , DataStringRepo dataStringRepo ) {
        
        // Check the Internal DB.
        // If the Park exists, return its JSONObject.
        JSONObject resObj = getUnit_CheckDB( parkCode , dataStringRepo );
        if( resObj != null )
            return resObj;
        
        // Check the RemoteAPI.
        // If the Park exists, return its JSONObject.
        resObj = getUnit_CheckAPI( parkCode );
        if( resObj != null )
            return resObj;
        
        // If the Park does not exist in both the IntetnalDB and the RemoteAPI,
        return null;
    }
}
