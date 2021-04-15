package com.cbsys.saleexplore.compos.search;

import com.cbsys.saleexplore.compos.search.searcher.DiscountLucSearcher;
import com.cbsys.saleexplore.compos.search.searcher.StoreLucSearcher;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LucSearcherTest {

    @Autowired
    private DiscountLucSearcher discountIndexSearcher;

    @Autowired
    private StoreLucSearcher storeLucSearcher;

    @Test
    @Ignore
    public void discountSearchTest() {
        try {
            List<Long> ids = discountIndexSearcher.searchWithSpecialChar("adidas", "0", 100);

            System.out.println(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void storeSearchTest() {
        try {
            List<Long> ids = storeLucSearcher.searchWithSpecialChar("Aldo", "279", 100);

            //System.out.println(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
