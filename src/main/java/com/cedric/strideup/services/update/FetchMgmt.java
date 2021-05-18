package com.cedric.strideup.services.update;

import java.util.List;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.features.JSONProcessing;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchMgmt {

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

    public FetchMgmt(){
        System.out.println();
    }

    public JSONObject fetchSingle( JSONObject ds , DataStringRepo dataStringRepo ) {
    
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
}
