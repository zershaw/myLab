package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.IUserInteretsService;
import com.cbsys.saleexplore.entity.UserInterests;
import com.cbsys.saleexplore.idao.IUserInterestsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInterestsService implements IUserInteretsService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserInterestsService.class);

    @Autowired
    private IUserInterestsDAO userInterestsDAO;

    @Async
    @Override
    public void update(long userId, int categoryId) {
        LOGGER.info("begin to update user {} interests", userId);
        userInterestsDAO.insert(Arrays.asList(new UserInterests(userId, categoryId)));
    }

    @Async
    @Override
    public void update(long userId, List<Integer> categoryIds) {
        LOGGER.info("begin to batch update user {} interests", userId);
        List<UserInterests> userInterestsList = categoryIds.stream().map(categoryId -> new UserInterests(userId, categoryId)).collect(Collectors.toList());
        userInterestsDAO.insert(userInterestsList);
    }


}
