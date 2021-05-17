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

    private JSONObject saveToDB( String dst , DataStringRepo dataStringRepo ){

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

        return ds;
    }

    private JSONObject retreiveFromDB( JSONObject ds , DataStringRepo dataStringRepo ){

        // Extract the newly created Park from the Internal Database
        List<DataString> dataStringList = dataStringRepo.findAllByParkCode( ds.getString("parkCode") );
 
        // Create the JSONObject corresponding to the newly created object
        JSONObject resObj = new JSONObject();
        JSONArray resArr = new JSONArray();
        JSONObject resBody = new JSONObject(dataStringList.get(0).getBody());
        resArr.put( resBody );
        resObj.put("total" , ""+resArr.length());
        resObj.put("limit" , "50");
        resObj.put("start" , "0");
        resObj.put("data" , resArr);

        return resObj;
    }

    /**
     * @Purpose : Create a new Park if it noes not exist yet
     * @param dst
     * @param dataStringRepo
     * @return JSONObject or null
     */
    public JSONObject post( String dst , DataStringRepo dataStringRepo) {

        // Save the entry in the DB
        JSONObject ds = saveToDB( dst , dataStringRepo );
        if( ds == null )
            return null;

        // Extract the newly created Park from the Internal Database
        JSONObject resJSON = retreiveFromDB( ds , dataStringRepo );

        // Return the result
        return resJSON;
        
    }
    
}
