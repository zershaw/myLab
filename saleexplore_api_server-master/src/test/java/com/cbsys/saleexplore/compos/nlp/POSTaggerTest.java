package com.cbsys.saleexplore.compos.nlp;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

/**
 * @author Xiangnan Ren
 * @project discountserver
 */
public class POSTaggerTest {

    private POSTagger pt = new POSTagger();

    @Test
    public void getTaggedWordsTest() {
        List<String> extractedKeywords = pt.
                getTaggedWords("I want to buy a mouse and keyboard");
        List<String> expectedRes = Arrays.asList("mouse", "keyboard");
        Assert.assertArrayEquals(extractedKeywords.toArray(), expectedRes.toArray());
    }

    @Test
    public void getLemmatizeTest() {
        String result = pt.lemmatizeByStandfordNLPCore("sunglasses");
        assert result.equals("sunglass");
    }


}
