package com.cbsys.saleexplore.service;

import org.junit.Test;

public class ProfaneFilterServiceTest {


    @Test
    public void testProfaneText(){
        ProfaneFilterService filterService = new ProfaneFilterService();

        assert filterService.containsProfaneText("Half price") == false;
        assert filterService.containsProfaneText("fucked up it");
        assert filterService.containsProfaneText("毛片");
        assert filterService.containsProfaneText("سكس");

    }

}
