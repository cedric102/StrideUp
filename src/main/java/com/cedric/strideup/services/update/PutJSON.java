package com.cedric.strideup.services.update;

import java.util.List;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;
import com.cedric.strideup.services.features.JSONProcessing;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Purpose : Incorporate the PUT Request to update a existing parkCode
 * @Instruction : Update the existing park identified by 'park code'
 * @Rule : - If the data does not exist yet, raise an error.
 *           The instruction indicated that this command should not "CREATE" a new park , but just to 'UPDATE' it if it already exists in the database.
 *           Therefore, under this condition, an error should be raised with Status Code 'HttpStatus.NOT_FOUND'
 *         - If the data does exist, just update the already existing park and return the response with 'HttpStatus.OK'
 * @uthor : C. Carteron
 */
public class PutJSON {

    // Used to put the fields ordering in place for
    // when it is required to build the JSONObject again.
    String[] orderList = { "id" , 
                           "url" , 
                           "fullName" , 
                           "parkCode" , 
                           "description" , 
                           "latitude", 
                           "longitude" , 
                           "latLong" ,
                           "activities" ,
                           "topics" , 
                           "states" ,
                           "contacts" ,
                           "entranceFees" ,
                           "entrancePasses" ,
                           "fees" ,
                           "directionsInfo" , 
                           "directionsUrl" ,
                           "operatingHours",
                           "addresses" , 
                           "images" ,
                           "weatherInfo" ,
                           "name" , 
                           "designation" ,
                        };

    private GetAPI getAPI = new GetAPI();
    
    private JSONObject saveToDB( String dst , DataStringRepo dataStringRepo ){

        JSONObject ds = new JSONObject(dst);
        DataString dsExists = dataStringRepo.findByParkCode( ds.getString("parkCode") );
        JSONObject json;

        // Leach if the entity already exists
        if( dsExists == null ) {

            getAPI.constructParam_ParkCode( ds.getString("parkCode") );
            json = getAPI.getAPIFlex();

            int jsonSize = json.getJSONArray("data").length();

            if( jsonSize != 1 )
                return null;
        }

        // Update the new entity
        DataString d = new DataString( ds.getString("parkCode") , ds.getString("states") , dst );
        dataStringRepo.save( d );

        return ds;
    }

    private JSONObject retreiveFromDB( JSONObject ds , DataStringRepo dataStringRepo ){

        // Fetch the same fata from the Database
        List<DataString> dataStringList = dataStringRepo.findAllByParkCode( ds.getString("parkCode") );

        // Build the JSONObject from the extracted data from the database
        JSONObject tempObj = new JSONObject();
        JSONProcessing.makeTheJSONObjectOrdered(tempObj);
        // makeTheJSONObjectOrdered(tempObj);

        JSONArray parkArray = new JSONArray();
        JSONObject mainBody = new JSONObject();
        JSONObject mainBody2 = new JSONObject(dataStringList.get(0).getBody());

        JSONProcessing.makeTheJSONObjectOrdered( mainBody );
        
        // Keep the fields in order of insartion
        for( String s : orderList ) {
            if( s.equals("activities") || 
                s.equals("topics") || 
                s.equals("entranceFees") || 
                s.equals("entrancePasses") || 
                s.equals("fees") || 
                s.equals("operatingHours") || 
                s.equals("addresses") || 
                s.equals("images") )
                mainBody.put( s , mainBody2.getJSONArray( s ) );
            else if( s.equals("contacts") )
                mainBody.put( s , mainBody2.getJSONObject( s ) );
            else
                mainBody.put( s , mainBody2.getString( s ) );
        }

        parkArray.put( mainBody );
        tempObj.put("total" , ""+parkArray.length());
        tempObj.put("limit" , "50");
        tempObj.put("start" , "0");
        tempObj.put("data" , parkArray);
        
        // Return the JSONObject
        return tempObj;

    }

    /**
     * @Purpose : Update the already existing park identified as 'parkCode'
     *            unless the park does not exist yet
     * @param dst
     * @param dataStringRepo
     * @return JSONObject or null
     */
    public JSONObject put( String dst , DataStringRepo dataStringRepo ) {
    
        // Update the entry in the DB
        JSONObject ds = saveToDB( dst , dataStringRepo );
        if( ds == null )
            return null;

        // Extract the newly Updated Park from the Internal Database
        JSONObject resJSON = retreiveFromDB( ds , dataStringRepo );

        // Return the result
        return resJSON;
        
    }
}
