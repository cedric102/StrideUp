package com.cedric.strideup.services.update;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;

import org.json.JSONObject;

public class UpdateMgmt {

    private GetAPI getAPI;
    
    public UpdateMgmt(){
        getAPI = new GetAPI();
    }

    public JSONObject saveToDB_IfAbsent( String dst , DataStringRepo dataStringRepo ){

        JSONObject ds = new JSONObject(dst);

        // Check if the parkCode exist in the Internal Database
        DataString dsExists = dataStringRepo.findByParkCode( ds.getString("parkCode") );
        if( dsExists != null )
            return null;

        // Check if the parkCode exist in the Remote API
        getAPI.constructParam_ParkCode( ds.getString("parkCode") );
        JSONObject json = getAPI.getAPIFlex();

        int jsonSize = json.getJSONArray("data").length();
        if( jsonSize == 1 )
            return null;

        // Save the new Park in the Internal Database
        DataString d = new DataString( ds.getString("parkCode") , ds.getString("states") , dst );
        dataStringRepo.save( d );

        return ds; 
    }

    public JSONObject saveToDB_IfPresent( String dst , DataStringRepo dataStringRepo ){

        JSONObject ds = new JSONObject(dst);
        DataString dsExists = dataStringRepo.findByParkCode( ds.getString("parkCode") );
        JSONObject json;

        // Seach if the entity already exists
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
    
}
