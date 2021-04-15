package com.sawyer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class UserFile {
    private Integer id;
    private String oldFileName; // 老文件名
    private String newFileName; // 新文件名
    private String ext; // 文件后缀
    private String path; // 存储路径
    private String size; // 文件大小
    private String type; // 文件类型
    private String isImg; // 是否是图片
    private Integer downcounts; // 下载次数
    private Date uploadTime; // 上传时间
    private Integer userId; // 用户外键


}
