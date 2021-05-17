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
@Table( name="email_address")
@Data
public class EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dataId;
    private int emailAddressId;
    
    @Column( columnDefinition="TEXT" )
    private String description;
    private String emailAddressString;
    
    public EmailAddress( int dataId , int emailAddressId , JSONObject obj ) {

        this.dataId = dataId;
        this.emailAddressId = emailAddressId;
        this.description = obj.getString("description");
        this.emailAddressString = obj.getString("emailAddress");

    }
    
}
