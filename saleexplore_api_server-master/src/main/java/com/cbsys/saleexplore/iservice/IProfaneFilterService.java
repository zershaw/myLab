package com.cbsys.saleexplore.iservice;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProfaneFilterService {

    /**
     * @param imageContent the content of the uploaded image
     * @return whether this text has any profane images
     */
    boolean containsProfaneImage(byte[] imageContent);

    /**
     * @param text user's input
     * @return whether this text has any profane phrases
     */
    boolean containsProfaneText(String text);


}
