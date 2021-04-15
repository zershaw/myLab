package com.cbsys.saleexplore.compos.search;

import com.cbsys.saleexplore.iservice.discount.IDiscountSearchService;
import com.cbsys.saleexplore.iservice.store.IStoreSearchService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LucIndexBuilderTest {

    @Autowired
    private IStoreSearchService storeSearchService;

    @Autowired
    private IDiscountSearchService discountSearchService;

    // RUN THIS FUNCTION WHEN YOU NEED TO RESET OR REGENERATE THE INDEX
    @Test
    @Ignore
    public void initiIndexData() {

        System.out.println("Start reseting store index");
        storeSearchService.resetLuceneIndex();

        System.out.println("Start reseting discount index");
        discountSearchService.resetLuceneIndex();

    }

}
