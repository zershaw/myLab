package com.cbsys.saleexplore.controller;

import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.enums.PopularSearchTypeEm;
import com.cbsys.saleexplore.iservice.IPopularSearchService;
import com.cbsys.saleexplore.payload.ApiResponsePd;
import com.cbsys.saleexplore.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = ConstantConfig.URL_PREFIX_API_PUBLIC + ConstantConfig.API_VCODE_ONE + "/popularSearch")
public class PopularSearchController {

    @Autowired
    private IPopularSearchService popularSearchService;


    @GetMapping(value = "/get")
    public ResponseEntity<?> getPopularSearch(@AuthenticationPrincipal CurrentUser currentUser,
                                              @RequestParam(value = "topk", required = false) Integer topk) {
        if(topk == null){
            topk = 10;
        }

        List<String> brandPopSearch = popularSearchService.getPopularSearch(PopularSearchTypeEm.BRAND, topk);
        List<String> mallPopSearch = popularSearchService.getPopularSearch(PopularSearchTypeEm.MALL, topk);
        List<String> productPopSearch = popularSearchService.getPopularSearch(PopularSearchTypeEm.PRODUCT, topk);
        Map<String, List<String>> results = new HashMap<>();
        results.put("brandPopSearch", brandPopSearch);
        results.put("mallPopSearch", mallPopSearch);
        results.put("productPopSearch", productPopSearch);

        ApiResponsePd apiResponse = new ApiResponsePd(true, results);
        return ResponseEntity.ok(apiResponse);
    }


}
