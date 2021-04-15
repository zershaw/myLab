package com.cbsys.saleexplore.controller;


import com.cbsys.saleexplore.iservice.store.IStoreBusiService;
import com.cbsys.saleexplore.config.ConstantConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreBusiController {

    @Autowired
    private IStoreBusiService storeBusiService;

    /**
     * Shop owner would like to claim his business
     */
    @GetMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC
            + ConstantConfig.API_VCODE_ONE + "/business/claim")
    public void claimBusiIntro(@RequestParam("email") String email) {

        // send the verification code to the user's email
        storeBusiService.sendBusinessClaimInfo(email);

    }

}
