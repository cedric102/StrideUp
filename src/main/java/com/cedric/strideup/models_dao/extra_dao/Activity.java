package com.cedric.strideup.models_dao.extra_dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.Data;

@Entity
@Table( name="activities")
@Data
public class Activity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dataId;
    private int activityId;

    private String activityIdString;
    private String name;
    
    public Activity( int dataId , int activityId , JSONObject obj ) {

        this.dataId = dataId;
        this.activityId = activityId;
        this.activityIdString = obj.getString("id");
        this.name = obj.getString("name");
    }
}
