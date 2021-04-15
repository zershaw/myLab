package com.cbsys.saleexplore.idao;

import com.cbsys.saleexplore.entity.User;
import com.cbsys.saleexplore.entity.UserLoginDevice;
import com.cbsys.saleexplore.enums.OsTypeEm;
import com.cbsys.saleexplore.payload.UserUpdatePd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Mapper
public interface IUserDAO {

    int getTotalNumber();

    List<User> get();

    /**
     * Get a single user
     */
    User getUser(@Param("id") Long id, @Param("email") String email,
                 @Param("providerId") String providerId, @Param("authProvider") Integer authProvider);

    /**
     * insert a single user to the db
     *
     * @return the number of rows affected
     */
    int insert(@Param("user") User user);

    /**
     * insert extended user device information to the database
     *
     * @return the number of rows affected
     */
    int insertLoginDevice(@Param("userLoginDevice") UserLoginDevice userLoginDevice);

    /**
     * Get the corresponding login device infor for a given usr Id
     *
     * @param id the user's id
     * @return the corresponding login device of the user
     */
    UserLoginDevice getUserLoginDevice(@Param("id") Long id);


    /**
     * update the device information of user login
     *
     * @return the number of rows affected
     */
    int updateLoginDeviceInfo(@Param("userLoginDevice") UserLoginDevice userLoginDevice);

    /**
     * mark a user as deleted by given given ID
     *
     * @return the number of rows affected
     */
    int markAsDelete(@Param("id") Long id);


    /**
     * update app related informations
     *
     * @return the number of rows affected
     */
    int updateUserAppInfo(
            @Param("id") Long id,
            @Param("deviceToken") String deviceToken,
            @Param("appVersionCode") int appVersionCode,
            @Param("osType") OsTypeEm osType,
            @Param("lastTimeLogin") Timestamp lastTimeLogin);

    /**
     * update geo-location of the user
     */
    int updateLastActive(@Param("id") long id,
                         @Param("latitude") float latitude,
                         @Param("longitude") float longitude);


    /**
     * update user password by email
     *
     * @return the number of rows affected
     */
    int updateUserEmailPwd(@Param("email") String email, @Param("password") String password);

    /**
     * update user password by email
     *
     * @return the number of rows affected
     */
    int updateUserEmailVeried(@Param("email") String email);


    /**
     * update user com.cbsys.saleexplore.profile image
     *
     * @return the number of rows affected
     */
    int updateProfileImage(@Param("imageName") String imageName, @Param("id") long id);


    int updateUser(
            @Param("id") Long id,
            @Param("userUPd") UserUpdatePd userUPd);


}
