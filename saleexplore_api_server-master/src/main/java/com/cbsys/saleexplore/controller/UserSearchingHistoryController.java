package com.cbsys.saleexplore.controller;


import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.enums.UserSearchHisTypeEm;
import com.cbsys.saleexplore.iservice.IUserSearchHistoryService;
import com.cbsys.saleexplore.payload.ApiResponsePd;
import com.cbsys.saleexplore.security.CurrentUser;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = ConstantConfig.URL_PREFIX_API + ConstantConfig.API_VCODE_ONE + "/userSearchHis")
public class UserSearchingHistoryController {

    @Autowired
    private IUserSearchHistoryService searchHistoryService;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succeed"),
            @ApiResponse(code = 1, message = "No such user exist")
    })
    @GetMapping(value = "/get")
    public ResponseEntity<?> getUserSearchHis(@AuthenticationPrincipal CurrentUser currentUser,
                                              @RequestParam("searchType") final int searchType,
                                              @RequestParam(value = "topk", required = false) Integer topk) {
        if (currentUser == null) {
            ApiResponsePd apiResponse = new ApiResponsePd(false, 1);
            return ResponseEntity.ok(apiResponse);
        }
        if(topk == null){
            topk = 10;
        }


        List<String> his = null;
        if (searchType == UserSearchHisTypeEm.DISCOUNT.getCode()) {
            his = searchHistoryService.getDiscountSearchHis(currentUser.getId(), topk);
        } else if (searchType == UserSearchHisTypeEm.STORE.getCode()) {
            his = searchHistoryService.getStoreSearchHis(currentUser.getId(), topk);
        }

        ApiResponsePd apiResponse = new ApiResponsePd(true, his);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping(value = "/delete")
    public void deleteUserSearchHis(@AuthenticationPrincipal CurrentUser currentUser,
                                    @RequestParam("searchType") final int searchType) {

        searchHistoryService.cleanSearchHis(currentUser.getId(), UserSearchHisTypeEm.valueOf(searchType));

    }
}
