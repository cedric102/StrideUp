package com.cedric.strideup.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/**
 * @Purpose : Used to Fetch the data from the remote API
 * @Coverage : It could search for all the parkCode at once , or a single one
 * @Structure : There are two methods:
 *      - getAPI() : Returns a JSONObject of the Result for the exercise
 *      - getAPIString() : Returns a String Representation of the result to keep the field order for the exercise
 */
public class GetAPI {

    // URL Constants to shape the structure of the request
    private final String M_AUTHORIZATION = "api_key=UQiC8xXIenM5EUe7z3i6fBfkQh56fynBxLAvQKfn";
    private final String M_HTTP = "https://developer.nps.gov/api/v1/parks?limit=500&";
    
    private String params = "";

    /**
     * @Purpose : Fetch the Data from the Remote API and return its JSONObject Representation
     * @param s
     * @return JSONObject
     */
    public void constructParam_ParkCode( String s ) {
        if( !s.equals( "" ) )
            this.params += "parkCode="+s+"&";
    }
    public void constructParam_States( String s ) {
        if( !s.equals( "" ) )
            this.params += "stateCode="+s+"&";
    }

    /**
     * @Purpose : Fetch the Data from the Remote API and 
     *            return its String Representation to preserve the response fields order
     * @param s
     * @return String
     */
    public JSONObject getAPIFlex() {

        try {
            URL url = new URL( M_HTTP + this.params + M_AUTHORIZATION );

            // Step 2
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            this.params = "";

            // Step 3
            // conn.setRequestProperty( "Authorization" , "Bearer " + M_AUTHORIZATION );
            // conn.setRequestProperty( "Content-Type" , "application/json" );
            conn.setRequestMethod( "GET" );

            /////////////////////////////////////////////
            // Extract the Response
            // Step 1: Extract the Buffer
            // Step 2: Populate the Response
            // Step 3: Store the Response in a JSON Format
            // Step 4: Extract the Budjet Parameter
            /////////////////////////////////////////////

            // Step 1
            BufferedReader in = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
         
            // Step 2
            String output; 
            StringBuilder response = new StringBuilder();
            while ( ( output = in.readLine() ) != null ) {
                response.append( output );  
            }
            in.close();

            return new JSONObject( response.toString() );
        } catch( Exception e ) {
            e.printStackTrace();
            this.params = "";
        }
        return null;
    }

}
