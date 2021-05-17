package com.cedric.strideup.services.extra;

import com.cedric.strideup.models_dao.extra_dao.Topic;
import com.cedric.strideup.models_dao.extra_dao.Activity;
import com.cedric.strideup.models_dao.extra_dao.Address;
import com.cedric.strideup.models_dao.extra_dao.DEntity;
import com.cedric.strideup.models_dao.extra_dao.EmailAddress;
import com.cedric.strideup.models_dao.extra_dao.EntranceFee;
import com.cedric.strideup.models_dao.extra_dao.Except;
import com.cedric.strideup.models_dao.extra_dao.Image;
import com.cedric.strideup.models_dao.extra_dao.OperatingHours;
import com.cedric.strideup.models_dao.extra_dao.PhoneNumber;
import com.cedric.strideup.repositories.extra_repo.ActivityRepo;
import com.cedric.strideup.repositories.extra_repo.AddressRepo;
import com.cedric.strideup.repositories.extra_repo.DataRepo;
import com.cedric.strideup.repositories.extra_repo.EmailAddressRepo;
import com.cedric.strideup.repositories.extra_repo.EntranceFeeRepo;
import com.cedric.strideup.repositories.extra_repo.ExceptRepo;
import com.cedric.strideup.repositories.extra_repo.ImageRepo;
import com.cedric.strideup.repositories.extra_repo.OperatingHoursRepo;
import com.cedric.strideup.repositories.extra_repo.PhoneNumberRepo;
import com.cedric.strideup.repositories.extra_repo.TopicsRepo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class WholeDatabase {
    
    protected final String M_AUTHORIZATION = "api_key=UQiC8xXIenM5EUe7z3i6fBfkQh56fynBxLAvQKfn" ;
    protected final String M_HTTP = "https://developer.nps.gov/api/v1/parks?" ;

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private DataRepo dataRepo;

    @Autowired
    private EmailAddressRepo emailAddressRepo;

    @Autowired
    private EntranceFeeRepo entranceFeesRepo;

    @Autowired
    private ExceptRepo exceptRepo;

    @Autowired
    private ImageRepo imagesRepo;

    @Autowired
    private OperatingHoursRepo operatingHoursRepo;

    @Autowired
    private PhoneNumberRepo phoneNumberRepo;

    @Autowired
    private TopicsRepo topicsRepo;
    
    private void saveInDB( JSONObject obj ) {

        JSONArray arr = obj.getJSONArray("data");
        for( int j=0 ; j<arr.length() ; j++ ) {
            JSONObject o = arr.getJSONObject(j);
            DEntity ent = new DEntity( j , o );
            dataRepo.save(ent); 

            JSONArray activityArr = o.getJSONArray("activities");
            for( int k=0 ; k<activityArr.length() ; k++ ) {
                JSONObject actO = activityArr.getJSONObject(k);
                Activity act = new Activity( j , k , actO );
                activityRepo.save(act);
            }

            JSONArray topicsArr = o.getJSONArray("topics");
            for( int k=0 ; k<topicsArr.length() ; k++ ) {
                JSONObject topO = topicsArr.getJSONObject(k);
                Topic top = new Topic( j , k , topO );
                topicsRepo.save(top);
            }

            JSONArray pnArr = o.getJSONObject("contacts").getJSONArray("phoneNumbers");
            for( int k=0 ; k<pnArr.length() ; k++ ) {
                JSONObject pnO = pnArr.getJSONObject(k);
                PhoneNumber pn = new PhoneNumber( j , k , pnO );
                phoneNumberRepo.save(pn);
            }
            JSONArray emailAddrArr = o.getJSONObject("contacts").getJSONArray("emailAddresses");
            for( int k=0 ; k<emailAddrArr.length() ; k++ ) {
                JSONObject emailAddrO = emailAddrArr.getJSONObject(k);
                EmailAddress emailAddr = new EmailAddress( j , k , emailAddrO );
                emailAddressRepo.save(emailAddr);
            }
            JSONArray entranceFeesArr = o.getJSONArray("entranceFees");
            for( int k=0 ; k<entranceFeesArr.length() ; k++ ) {
                JSONObject entranceFeesO = entranceFeesArr.getJSONObject(k);
                EntranceFee entranceFees = new EntranceFee( j , k , entranceFeesO );
                entranceFeesRepo.save(entranceFees);
            }

            JSONArray operatingHoursArr = o.getJSONArray("operatingHours");
            for( int k=0 ; k<operatingHoursArr.length() ; k++ ) {
                JSONObject operatingHoursO = operatingHoursArr.getJSONObject(k);
                OperatingHours operatingHours = new OperatingHours( j , k , operatingHoursO );
                operatingHoursRepo.save(operatingHours);
            }
            
            for( int k=0 ; k<operatingHoursArr.length() ; k++ ) {
                JSONObject operatingHoursO = operatingHoursArr.getJSONObject(k);
                JSONArray exceptArr = operatingHoursO.getJSONArray("exceptions");
                for( int l=0 ; l<exceptArr.length() ; l++ ) {
                    JSONObject exceptO = exceptArr.getJSONObject(l);
                    Except except = new Except( j , k , l , exceptO );
                    exceptRepo.save(except);
                }
                OperatingHours operatingHours = new OperatingHours( j , k , operatingHoursO );
                operatingHoursRepo.save(operatingHours);
            }
            JSONArray addressesArr = o.getJSONArray("addresses");
            for( int k=0 ; k<addressesArr.length() ; k++ ) {
                JSONObject addressO = addressesArr.getJSONObject(k);
                Address address = new Address( j , k , addressO );
                addressRepo.save(address);
            }
            JSONArray imagesArr = o.getJSONArray("images");
            for( int k=0 ; k<imagesArr.length() ; k++ ) {
                JSONObject imageO = imagesArr.getJSONObject(k);
                Image image = new Image( j , k , imageO );
                imagesRepo.save(image);
            }

            
        }

    }

    private JSONObject extractFromDB( String s ) {
        DEntity ent = dataRepo.findAllByParkCode( s );
        JSONObject tempObj = new JSONObject();

        int id = ent.getDataId();
        tempObj.put("id" , ent.getIdString() );
        tempObj.put("url" , ent.getUrl() );
        tempObj.put("fullName" , ent.getFullName() );
        tempObj.put("parkCode" , ent.getParkCode() );
        tempObj.put("description" , ent.getDescription() );
        tempObj.put("latitude" , ent.getLatitude() );
        tempObj.put("longitude" , ent.getLongitude() );
        tempObj.put("latlong" , ent.getLatLong() );
        JSONArray actArr = new JSONArray( );
        for( Activity a : activityRepo.findAllByDataId( id ) ) {
            actArr.put(a);
            JSONObject t = new JSONObject();
            t.put("id" , a.getId() );
            t.put("name" , a.getName() );
            actArr.put(t);
        }
        tempObj.put("activities" , actArr );

        JSONArray topArr = new JSONArray( );
        for( Topic a : topicsRepo.findAllByDataId( id ) ) {
            topArr.put(a);
            JSONObject t = new JSONObject();
            t.put("id" , a.getId() );
            t.put("name" , a.getName() );
            topArr.put(t);
        }
        tempObj.put("topics" , topArr );
        tempObj.put("states" , ent.getStates() );
        tempObj.put("contacts" , "" );

        JSONArray pnArr = new JSONArray( );
        for( PhoneNumber a : phoneNumberRepo.findAllByDataId( id ) ) {
            JSONObject t = new JSONObject();
            t.put("phoneNumber" , a.getPhoneNumberString());
            t.put("description" , a.getDescription() );
            t.put("extension" , a.getExtension() );
            t.put("type" , a.getType() );
            pnArr.put(t);
        }

        JSONArray emailAddrArr = new JSONArray( );
        for( EmailAddress a : emailAddressRepo.findAllByDataId( id ) ) {
            JSONObject t = new JSONObject();
            t.put("description" , a.getDescription());
            t.put("emailAddress" , a.getEmailAddressString() );
            emailAddrArr.put(t);
        }
        JSONObject cont = new JSONObject();
        cont.put("phoneNumbers" , pnArr );
        cont.put("emailAddresses" , emailAddrArr );
        tempObj.put("contacts" , cont);

        JSONArray entranceFeesArr = new JSONArray( );
        for( EntranceFee a : entranceFeesRepo.findAllByDataId( id ) ) {
            JSONObject t = new JSONObject();
            t.put("cost" , a.getCost());
            t.put("description" , a.getDescription() );
            t.put("title" , a.getTitle() );
            entranceFeesArr.put(t);
        }
        tempObj.put("entranceFees" , entranceFeesArr );
        tempObj.put("entrancePasses" , "" );
        tempObj.put("fees" , "" );

        tempObj.put("directionsInfo" , ent.getDirectionsInfo() );
        tempObj.put("directionsUrl" , ent.getDirectionsUrl() );
        JSONArray operationHoursArr = new JSONArray( );
        for( OperatingHours a : operatingHoursRepo.findAllByDataId( id ) ) {
            int operId = a.getOperatingHoursId();

            JSONArray exceptArr = new JSONArray();
            for( Except b : exceptRepo.findByDataIdAndOperationHourId( id , operId ) ) {
                JSONObject tb = new JSONObject();
                tb.put("startDate" , b.getStartDate());
                tb.put("name" , b.getName());
                tb.put("endDate" , b.getEndDate());
                exceptArr.put(tb);
            }
            JSONObject t = new JSONObject();
            t.put("exceptions" , exceptArr );
            t.put("description" , a.getDescription() );
            JSONObject sh = new JSONObject();
            sh.put("monday" , a.getMonday());
            sh.put("tuesday" , a.getTuesday());
            sh.put("wednesday" , a.getWednesday());
            sh.put("thursday" , a.getThursday());
            sh.put("frigay" , a.getFriday());
            sh.put("satursday" , a.getSaturday());
            sh.put("sunday" , a.getSunday());

            t.put("standardHours" , sh );
            t.put("name" , a.getName() );
            operationHoursArr.put(t);
        }
        tempObj.put("operatingHours" , operationHoursArr );

        JSONArray addressArr = new JSONArray( );
        for( Address a : addressRepo.findAllByDataId( id ) ) {
            JSONObject t = new JSONObject();
            t.put("postalCode" , a.getPostalCode());
            t.put("city" , a.getCity() );
            t.put("stateCode" , a.getStateCode() );
            t.put("line1" , a.getLine1() );
            t.put("type" , a.getType() );
            t.put("line3" , a.getLine3() );
            t.put("line2" , a.getLine2() );
            addressArr.put(t);
        }
        tempObj.put("addresses" , addressArr );

        JSONArray imagesArr = new JSONArray( );
        for( Image a : imagesRepo.findAllByDataId( id ) ) {
            JSONObject t = new JSONObject();
            t.put("credit" , a.getCredit());
            t.put("title" , a.getTitle() );
            t.put("altText" , a.getAltText() );
            t.put("caption" , a.getCaption() );
            t.put("url" , a.getUrl() );
            imagesArr.put(t);
        }
        tempObj.put("images" , imagesArr );

        tempObj.put("weatherInfo" , ent.getWeatherInfo() );
        tempObj.put("name" , ent.getName() );
        tempObj.put("designation" , ent.getDesignation() );

        return null;
    }
}
