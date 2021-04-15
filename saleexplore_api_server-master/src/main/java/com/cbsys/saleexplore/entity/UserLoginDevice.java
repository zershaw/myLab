package com.cbsys.saleexplore.entity;


import lombok.Data;


@Data
public class UserLoginDevice {

	/*
	 * following property are saved in ext table
	 */
	private long id;

	private String deviceUUID;
	private String deviceModel; // model of the device
	private String deviceVersion; // which version of the os
	private String deviceManufacturer; // which company made this
	private boolean deviceIsVirtual; // whether visited by virtual machine or not

	public UserLoginDevice(long id){
		this.id=id;
	}

}
