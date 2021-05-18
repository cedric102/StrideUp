package com.cedric.strideup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.GetAPI;
import com.cedric.strideup.services.fetch.FetchByParkCode;
import com.cedric.strideup.services.fetch.FetchImpl;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @Purpose : Test the 'Get Unit' Part of the exercise
 * @Tests : Test for the result if the data that is requested is present or not
 * @Author : C. Carteron
 */
public class Test_FetchUnit {

    FetchImpl fetchSingle = null;
    GetAPI getAPI = mock(GetAPI.class);
    DataStringRepo dataStringRepo = mock(DataStringRepo.class);

    @Before
    public void setUp() {
        fetchSingle = new FetchByParkCode( getAPI );
    }

    /**
     * @Purpose : Test the case where the data looked for exists
     * @Approach : Emulate a dummy data generated for "abli" parkCode for findAllByParkCode
     * @Result: 'getUnit' would generate non-null data
     */
    @Test
    public void existantData() {

        GetAPI getAPI2 = new GetAPI();

        // Generate any data
        getAPI2.constructParam_ParkCode( "abli" );
        JSONObject json = getAPI2.getAPIFlex();
    
        // Genetate Empty Data.
        DataString ds = new DataString();
        ds.setBody("{\"Dummy\":\"Dummy\"}");
        ds.setParkCode("abli");
        List<DataString> l = new ArrayList<>();
        l.add(ds);

        // Provide the expected data from the GetAPI Mockito
        getAPI.constructParam_ParkCode( "abli" );
        Mockito.when(getAPI.getAPIFlex()).thenReturn(json);
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
        getAPI2.constructParam_ParkCode( "abli" );
        JSONObject json = getAPI2.getAPIFlex();
    
        // Empty Data.
        List<DataString> l = new ArrayList<>();

        // Provide the expected data from the GetAPI Mockito
        getAPI.constructParam_ParkCode( "abli" );
        Mockito.when(getAPI.getAPIFlex()).thenReturn(json);
        Mockito.when(dataStringRepo.findAllByParkCode("abll")).thenReturn(l);

        // Result of the Test
        JSONObject res = fetchSingle.getUnit( "abll" , dataStringRepo );
        assertEquals( null , res );

    }

}

