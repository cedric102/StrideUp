package com.cedric.strideup.models_dao.extra_dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.Data;

@Entity
@Table( name="addresses")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dataId;
    private int addressId;
    
    private String postalCode;
    private String city;
    private String stateCode;
    private String line1;
    private String type;
    private String line3;
    private String line2;
    
    public Address( int dataId , int addressId , JSONObject obj ) {

        this.dataId = dataId;
        this.addressId = addressId;
        
        this.postalCode = obj.getString("postalCode");
        this.city = obj.getString("city");
        this.stateCode = obj.getString("stateCode");
        this.line1 = obj.getString("line1");
        this.type = obj.getString("type");
        this.line3 = obj.getString("line3");
        this.line2 = obj.getString("line2");
        
    }
    
}
