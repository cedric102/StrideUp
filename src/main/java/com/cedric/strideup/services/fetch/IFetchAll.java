package com.cedric.strideup.services.fetch;

import com.cedric.strideup.repositories.DataStringRepo;

import org.json.JSONObject;

public interface IFetchAll {
    
    public JSONObject getAll( DataStringRepo dataStringRepo ) ;
    
}
