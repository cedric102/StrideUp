package com.cedric.strideup.services.fetch.extract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;
import com.cedric.strideup.services.features.JSONProcessing;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExtractUnitFrom {
    
    public GetAPI getAPI;
    public Map<String ,String> mp;
    public String parkCode;
    
    public ExtractUnitFrom() {
        mp = new HashMap<String ,String>();
    }

    public ExtractUnitFrom( GetAPI getAPI ) {
        this.getAPI = getAPI;
        mp = new HashMap<String ,String>();
    }
    
    /**
     * @Purpose : Check the Internal Database for the identified 'parkCode'
     * @Note : If the identified 'parkNode' does not exist in the InternalDB, return null
     * @param parkCode
     * @param dataStringRepo
     * @return JSONObject
     */
    public JSONObject getUnit_CheckDB( DataStringRepo dataStringRepo ) {

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
    public JSONObject getUnit_CheckAPI() {

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
}
