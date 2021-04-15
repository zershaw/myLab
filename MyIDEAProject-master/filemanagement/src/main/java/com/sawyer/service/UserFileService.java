package com.sawyer.service;

import com.sawyer.entity.UserFile;

import java.util.List;

public interface UserFileService {
    List<UserFile> findByUserId(Integer id);

    //在数据库中保存文件【信息】
    void save(UserFile userFile);

    UserFile findById(Integer id);

    // 根据id更新文件信息（下载次数）
    void update(UserFile userFile);


    void delete(Integer id);
}
