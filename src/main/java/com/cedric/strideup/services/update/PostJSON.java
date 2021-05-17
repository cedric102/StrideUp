package com.cedric.strideup.services.update;

import java.util.List;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Purpose : Incorporate the POST Request to create a new parkCode
 * @Instruction : Create a park and save it in the database
 * @Rule : - If the data already exists, raise an error.
 *           The instruction indicated that this command should "CREATE" a new park , not updating it if it already exists
 *           Therefore, under this condition, an error should be raised with Status Code 'HttpStatus.NOT_ACCEPTABLE'
 *         - If the data does not exist, just create the new park and return the response with 'HttpStatus.OK'
 * @uthor : C. Carteron
 */
public class PostJSON {

    private GetAPI getAPI = new GetAPI();
    /**
     * @Purpose : Create a new Park if it noes not exist yet
     * @param dst
     * @param dataStringRepo
     * @return JSONObject or null
     */
    public JSONObject post( String dst , DataStringRepo dataStringRepo) {

        JSONObject ds = new JSONObject(dst);

        // Check if the parkCode exist in the Internal Database
        DataString dsExists = dataStringRepo.findByParkCode( ds.getString("parkCode") );
        if( dsExists != null )
            return null;

        // Check if the parkCode exist in the Remote API
        JSONObject json = getAPI.getAPI( ds.getString("parkCode") );
        int jsonSize = json.getJSONArray("data").length();
        if( jsonSize == 1 )
            return null;

        // Save the new Park in the Internal Database
        DataString d = new DataString( ds.getString("parkCode") , dst );
        dataStringRepo.save( d );
 
        // Extract the newly created Park from the Internal Database
        List<DataString> dataStringList = dataStringRepo.findAllByParkCode( ds.getString("parkCode") );
 
        // Create the JSONObject corresponding to the newly created object
        JSONObject tempObj = new JSONObject();
        JSONArray tempArr = new JSONArray();
        JSONObject tempBody = new JSONObject(dataStringList.get(0).getBody());
        tempArr.put( tempBody );
        tempObj.put("total" , ""+tempArr.length());
        tempObj.put("limit" , "50");
        tempObj.put("start" , "0");
        tempObj.put("data" , tempArr);
        
        // Return the result
        return tempObj;
        
    }
    
}
