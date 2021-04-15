package com.sawyer.service;

import com.sawyer.dao.UserFileDAO;
import com.sawyer.entity.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional
public class UserFileServiceImpl implements UserFileService {
    @Autowired
    private UserFileDAO userFileDAO;

    @Override
    public List<UserFile> findByUserId(Integer id) {
        return userFileDAO.findByUserId(id);

    }

    @Override
    public void save(UserFile userFile) {
        String isImg = userFile.getType().startsWith("image") ? "是" : "否"; //判断方式：Type是否是以image开头的
        userFile.setIsImg(isImg); //是否是图 <span th:if="${file.isImg}!='是'" th:text="${file.isImg}"/>
        userFile.setDowncounts(0); // 初始化下载次数
        userFile.setUploadTime(new Date()); // 设置上传时间
        userFileDAO.save(userFile);
    }

    @Override
    public UserFile findById(Integer id) {
        return userFileDAO.findById(id);
    }

    @Override
    public void update(UserFile userFile) {
        userFileDAO.update(userFile);
    }

    @Override
    public void delete(Integer id) {
        userFileDAO.delete(id);
    }
}
