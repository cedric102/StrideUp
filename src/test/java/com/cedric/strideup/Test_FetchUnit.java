package com.cedric.strideup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import com.cedric.strideup.controller.MainController;
import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;
import com.cedric.strideup.services.fetch.FetchALL;
import com.cedric.strideup.services.fetch.FetchSingle;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @Purpose : Test the 'Get Unit' Part of the exercise
 * @Tests : Test for the result if the data that is requested is present or not
 * @Author : C. Carteron
 */
public class Test_FetchUnit {
   
    @Autowired
    private MockMvc mvc;

    FetchSingle fetchSingle = null;
    GetAPI getAPI = mock(GetAPI.class);
    DataStringRepo dataStringRepo = mock(DataStringRepo.class);

    @Before
    public void setUp() {
        fetchSingle = new FetchSingle( getAPI );
    }

    /**
     * @Purpose : Test the case where the data looked for exists
     * @Approach : Emulate a dummy data generated for "abli" parkCode for findAllByParkCode
     * @Result: 'getUnit' would generate non-null data
     */
    @Test
    public void temp() {

        GetAPI getAPI2 = new GetAPI();

        // Generate any data
        JSONObject json = getAPI2.getAPI("abli");
    
        // Genetate Empty Data.
        DataString ds = new DataString();
        ds.setBody("{\"Dummy\":\"Dummy\"}");
        ds.setParkCode("abli");
        List<DataString> l = new ArrayList<>();
        l.add(ds);

        // Provide the expected data from the GetAPI Mockito
        Mockito.when(getAPI.getAPI("abli")).thenReturn(json);
        Mockito.when(dataStringRepo.findAllByParkCode("abli")).thenReturn(l);

        // Result of the Test
        JSONObject res = fetchSingle.getUnit( "abli" , dataStringRepo );
        assertNotEquals( null , res );
    }

    /**
     * @Purpose : Test the case where the data looked for does not exist
     * @Approach : Emulate the an empty data generated for "abli" parkCode for findAllByParkCode
     * @Result: 'getUnit' would generate null data
     */
    @Test
    public void inexistantData() {

        GetAPI getAPI2 = new GetAPI();

        // Generate any data
        JSONObject json = getAPI2.getAPI("abll");
    
        // Empty Data.
        List<DataString> l = new ArrayList<>();

        // Provide the expected data from the GetAPI Mockito
        Mockito.when(getAPI.getAPI("abll")).thenReturn(json);
        Mockito.when(dataStringRepo.findAllByParkCode("abll")).thenReturn(l);

        // Result of the Test
        JSONObject res = fetchSingle.getUnit( "abll" , dataStringRepo );
        assertEquals( null , res );

    }

}

