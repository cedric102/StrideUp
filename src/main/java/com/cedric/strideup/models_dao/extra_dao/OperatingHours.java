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
@Table( name="operating_hours")
@Data
public class OperatingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dataId;
    private int operatingHoursId;
    
    private String exceptionsSize;

    @Column( columnDefinition="TEXT" )
    private String description;
    private String name;

    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    
    public OperatingHours( int dataId , int operatingHoursId , JSONObject obj ) {

        this.dataId = dataId;
        this.operatingHoursId = operatingHoursId;
        this.exceptionsSize = String.valueOf( obj.getJSONArray("exceptions").length() );
        this.description = obj.getString("description");
        this.name = obj.getString("name");

        JSONObject tempObj = obj.getJSONObject("standardHours");
        this.monday = tempObj.getString("monday");
        this.tuesday = tempObj.getString("tuesday");
        this.wednesday = tempObj.getString("wednesday");
        this.thursday = tempObj.getString("thursday");
        this.friday = tempObj.getString("friday");
        this.saturday = tempObj.getString("saturday");
        this.sunday = tempObj.getString("sunday");
        
    }
    
}
