package com.cbsys.saleexplore.entity;


import lombok.Data;

@Data
public class Country {

    private int id;

    private String iso2;

    private String countryName;

    private String currency;

    private String phoneCode;

}
