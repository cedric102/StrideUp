package com.cedric.strideup.controller;

import com.cedric.strideup.models_dao.DataString;
import com.cedric.strideup.repositories.DataStringRepo;
import com.cedric.strideup.services.fetch.FetchALL;
import com.cedric.strideup.services.fetch.FetchByParkCode;
import com.cedric.strideup.services.fetch.FetchByStates;
import com.cedric.strideup.services.fetch.IFetch;
import com.cedric.strideup.services.update.PostJSON;
import com.cedric.strideup.services.update.PutJSON;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Purpose : Provides control for the following endpoints :
 *   - GET /parks - list all parks ; optionally accept query parameters e.g. stateCode( US state codes )
 *   - GET /parks/{park code} - retrieve details of the park identified by 'park code'
 *   - POST /parks - create a park and save it in the database
 *   - PUT /parks/{park code} - update the existing park identified by 'park code'
 */
@RestController
@RequestMapping( path="/" )
public class MainController {

    JSONParser parser;

    PostJSON postJSON = new PostJSON();
    PutJSON putJSON = new PutJSON();
    
    IFetch fetchALL = new FetchALL();
    IFetch fetchSingleParkCode = new FetchByParkCode();
    IFetch fetchStates = new FetchByStates();

    @Autowired
    private DataStringRepo dataStringRepo;


    DataString ds = new DataString();

    @RequestMapping( path="/parks" , method = RequestMethod.GET , produces = "application/json; charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getAll() {
        
        JSONObject res = fetchALL.getAll( dataStringRepo );
        
        if( res == null )
            return new ResponseEntity<String>( HttpStatus.NOT_FOUND );
        return new ResponseEntity<String>( res.toString() , HttpStatus.OK);
    }
    
    @RequestMapping( path="/parks/states/{states}" , method = RequestMethod.GET , produces = "application/json; charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getUnitStates(@PathVariable("states") String states ) {
        
        JSONObject res = fetchStates.getUnit(states, dataStringRepo);
        
        if( res == null )
            return new ResponseEntity<String>( HttpStatus.NOT_FOUND );
        return new ResponseEntity<String>( res.toString() , HttpStatus.OK);
    }
    
    @RequestMapping( path="/parks/{parkCode}" , method = RequestMethod.GET , produces = "application/json; charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getUnit(@PathVariable("parkCode") String parkCode ) {
        
        JSONObject res = fetchSingleParkCode.getUnit(parkCode, dataStringRepo);
        
        if( res == null )
            return new ResponseEntity<String>( HttpStatus.NOT_FOUND );
        return new ResponseEntity<String>( res.toString() , HttpStatus.OK);
    }
    
    @RequestMapping( path="/parksTest/{parkCode}" , method = RequestMethod.GET , produces = "application/json; charset=UTF-8")
    public @ResponseBody ResponseEntity<String> getUnitTest(@PathVariable("parkCode") String parkCode , DataStringRepo dataStringRepo ) {
        
        this.dataStringRepo = dataStringRepo;
        JSONObject res = fetchSingleParkCode.getUnit(parkCode, this.dataStringRepo);
        
        if( res == null )
            return new ResponseEntity<String>( HttpStatus.NOT_FOUND );
        return new ResponseEntity<String>( res.toString() , HttpStatus.OK);
    }

    @RequestMapping( path="/parks" , method = RequestMethod.POST , produces = "application/json; charset=UTF-8")
    public @ResponseBody ResponseEntity<String> post( @RequestBody String dst ) {

        JSONObject res = postJSON.post(dst, dataStringRepo);
        if( res == null )
            return new ResponseEntity<String>( HttpStatus.NOT_ACCEPTABLE );
        return new ResponseEntity<String>( res.toString() , HttpStatus.OK);
        
    }

    @PutMapping( path="/parks" , produces = "application/json; charset=UTF-8" )
    public @ResponseBody ResponseEntity<String> put( @RequestBody String dst ) {
    
        JSONObject res = putJSON.put(dst, dataStringRepo);
        
        if( res == null )
            return new ResponseEntity<String>( HttpStatus.NOT_FOUND );
        return new ResponseEntity<String>( res.toString() , HttpStatus.OK);
        
    }

}
