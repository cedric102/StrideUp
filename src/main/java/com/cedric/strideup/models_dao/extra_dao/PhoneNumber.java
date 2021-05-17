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
@Table( name="phone_numbers")
@Data
public class PhoneNumber {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dataId;
    private int phoneNumberId;
    
    private String phoneNumberString;

    @Column( columnDefinition="TEXT" )
    private String description;
    private String extension;
    private String type;
    
    public PhoneNumber( int dataId , int phoneNumberId , JSONObject obj ) {

        this.dataId = dataId;
        this.phoneNumberId = phoneNumberId;
        this.phoneNumberString = obj.getString("phoneNumber");
        this.description = obj.getString("description");
        this.extension = obj.getString("extension");
        this.type = obj.getString("type");
    }
    
}