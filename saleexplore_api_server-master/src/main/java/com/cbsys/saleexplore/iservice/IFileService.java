package com.cbsys.saleexplore.iservice;

public interface IFileService {
    /**
     * @param fileName
     */
    void deleteImage(String fileName);

    /**
     * param inputStream
     */
    String uploadImage(byte[] imageContent);

}
