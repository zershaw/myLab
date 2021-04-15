package com.cbsys.saleexplore.payload;

/**
 * the device information loaded from the frontend. the attribute names may not the same as they are in the DB
 */

import com.cbsys.saleexplore.entity.User;
import com.cbsys.saleexplore.entity.UserLoginDevice;
import lombok.Data;
import com.cbsys.saleexplore.enums.OsTypeEm;

@Data
public class LoginDevicePd {

    private int appVersionCode; // the version code of our own app
    private String deviceToken; // the device token used for push notification

    private String platform; // which operating system
    private String model; // model of the device
    private String uuid; // unique id
    private String version; // which version of the os
    private String manufacturer; // which company made this
    private String isVirtual; // whether visited by virtual machine or not

    public void setLoginDevice(UserLoginDevice loginDevice){

        loginDevice.setDeviceUUID(this.uuid);
        loginDevice.setDeviceManufacturer(this.manufacturer);
        loginDevice.setDeviceVersion(this.version);
        loginDevice.setDeviceModel(this.model);

        if(this.isVirtual != null && this.isVirtual.equals("true")){
            loginDevice.setDeviceIsVirtual(true);
        }
        else if(this.isVirtual  != null && this.isVirtual .equals("false")){
            loginDevice.setDeviceIsVirtual(false);
        }

    }

    public void setUserCodeToken(User user){

        if(platform.equals("Android")){
            user.setOsType(OsTypeEm.ANDROID);
        }else if(platform.equals("iOS")){
            user.setOsType(OsTypeEm.IOS);
        } else {
            user.setOsType(OsTypeEm.OTHER);
        }

        user.setDeviceToken(this.deviceToken);
    }
}

