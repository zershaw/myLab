package com.cbsys.saleexplore.dao;

import com.cbsys.saleexplore.entity.Discount;
import com.cbsys.saleexplore.idao.IDiscountDAO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DiscountDaoTest {

    @Autowired
    private IDiscountDAO discountDAO;

    @Test
    @Ignore
    public void testGetDiscounts(){

        List<Long> ids = new ArrayList<>();
        ids.add(15l);
        List<Discount> results = discountDAO.get(ids, null, null);
        assert results.size() == 1;

    }


}
