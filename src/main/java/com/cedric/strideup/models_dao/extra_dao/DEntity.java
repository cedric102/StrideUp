package com.cedric.strideup.models_dao.extra_dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.Data;

@Entity
@Table(name="data_entity")
@Data
public class DEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dataId;

    private String idString;
    private String url;
    private String fullName;
    private String parkCode;

    @Column( columnDefinition="TEXT" )
    private String description;
    private String latitude;
    private String longitude;
    private String latLong;
    private String activitiesSize;
    private String topicsSize;
    private String states;
    private String contactsSize;
    private String phoneNumbersSize;
    private String emailAddressesSize;
    private String entranceFeesSize;
    private String entrancePassesSize;
    private String feesSize;

    @Column( columnDefinition="TEXT" )
    private String directionsInfo;
    private String directionsUrl;
    private String operatingHoursSize;
    private String addressesSize;
    private String imagesSize;

    @Column( columnDefinition="TEXT" )
    private String weatherInfo;
    private String name;
    private String designation;

    public DEntity() {}
    public DEntity( int dataId , JSONObject obj ) {
        this.dataId = dataId;
        this.idString = obj.getString("id");
        this.url = obj.getString("url");
        this.fullName = obj.getString("fullName");
        this.parkCode = obj.getString("parkCode");
        this.description = obj.getString("description");
        this.latitude = obj.getString("latitude");
        this.longitude = obj.getString("longitude");
        this.latLong = obj.getString("latLong");
        this.activitiesSize = String.valueOf( obj.getJSONArray("activities").length() );
        this.topicsSize = String.valueOf( obj.getJSONArray("topics").length() );
        this.states = obj.getString("states");
        this.phoneNumbersSize = String.valueOf( obj.getJSONObject("contacts").getJSONArray("phoneNumbers").length() );
        this.emailAddressesSize = String.valueOf( obj.getJSONObject("contacts").getJSONArray("emailAddresses").length() );
        this.entranceFeesSize = String.valueOf( obj.getJSONArray("entranceFees").length() );
        this.entrancePassesSize = String.valueOf( obj.getJSONArray("entrancePasses").length() );
        this.feesSize = String.valueOf( obj.getJSONArray("fees").length() );
        this.directionsInfo = obj.getString("directionsInfo");
        this.directionsUrl = obj.getString("directionsUrl");
        this.operatingHoursSize = String.valueOf( obj.getJSONArray("operatingHours").length() );
        this.addressesSize = String.valueOf( obj.getJSONArray("addresses").length() );
        this.imagesSize = String.valueOf( obj.getJSONArray("images").length() );
        this.weatherInfo = obj.getString("weatherInfo");
        this.name = obj.getString("name");
        this.designation = obj.getString("designation");

    }

}
