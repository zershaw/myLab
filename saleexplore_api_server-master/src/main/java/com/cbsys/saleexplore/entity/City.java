package com.cbsys.saleexplore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class City {

	private int id;

	private String cityName;

	private int countryId;

	private String countryName;

	private String currency;

	private String timezone;

	@JsonIgnore
	private String cityFlightCode;

	private boolean isActive;

	private float latitude;

	private float longitude;

}
