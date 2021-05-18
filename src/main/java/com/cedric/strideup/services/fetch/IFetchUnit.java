package com.cedric.strideup.services.fetch;

import com.cedric.strideup.repositories.DataStringRepo;

import org.json.JSONObject;

public interface IFetchUnit {
    
    public JSONObject getUnit( String states , DataStringRepo dataStringRepo );
    
}
