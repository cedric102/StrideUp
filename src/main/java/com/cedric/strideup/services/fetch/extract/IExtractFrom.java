package com.cedric.strideup.services.fetch.extract;

import org.json.JSONObject;

public interface IExtractFrom {
    public void extractFromDB();
    public void extractFromAPI();
    public JSONObject buildJSON();
}
