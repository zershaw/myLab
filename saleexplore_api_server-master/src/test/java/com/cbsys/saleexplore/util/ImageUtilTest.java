package com.cbsys.saleexplore.util;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class ImageUtilTest {

    @Test
    public void resizeTest(){

        File folder = new File("/Users/xuguangren/Dropbox/IIAI/Discount/Datasets/countryFlag");

        try{
            for (File f : folder.listFiles()) {

                if (f.isFile()) {
                    if (f.getName().endsWith(".png")) {
                        BufferedImage inputImage = ImageIO.read(f);
                        ImageUtil.resize(inputImage,
                                "/Users/xuguangren/Dropbox/IIAI/Discount/Datasets/countryFlagThumb/" + f.getName().replace(".png", ".jpeg"),
                                40, 40);
                    }
                }

            }
        }catch (Exception e){
            //e.printStackTrace();
        }
    }

}
