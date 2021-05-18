package com.cedric.strideup.services.fetch.extract;

import com.cedric.strideup.repositories.DataStringRepo;

import org.json.JSONObject;

public interface IExtractUnitFrom {
    public JSONObject getUnit_CheckAPI();
    public JSONObject getUnit_CheckDB( DataStringRepo dataStringRepo );
}
