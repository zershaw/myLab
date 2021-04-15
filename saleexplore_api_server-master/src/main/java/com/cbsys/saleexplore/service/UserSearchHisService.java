package com.cbsys.saleexplore.service;

import com.cbsys.saleexplore.iservice.IUserSearchHistoryService;
import com.cbsys.saleexplore.entity.UserSearchHistory;
import com.cbsys.saleexplore.enums.UserSearchHisTypeEm;
import com.cbsys.saleexplore.idao.IUserSearchHistoryDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserSearchHisService implements IUserSearchHistoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSearchHisService.class);

    @Autowired
    private IUserSearchHistoryDAO userHisDao;

    @Override
    @Async
    public void insertDiscountSearchHis(long userId, String kwQuery) {

        kwQuery = kwQuery.trim();

        UserSearchHistory history = new UserSearchHistory();
        history.setUserId(userId);
        history.setKwQuery(kwQuery);
        history.setSearchType(UserSearchHisTypeEm.DISCOUNT);

        userHisDao.deleteByKw(userId, UserSearchHisTypeEm.DISCOUNT, kwQuery);
        userHisDao.insert(history);
    }

    @Override
    public List<String> getDiscountSearchHis(long userId, int topk) {
        return userHisDao.get(userId, UserSearchHisTypeEm.DISCOUNT, topk);
    }

    @Override
    public void cleanSearchHis(long userId, UserSearchHisTypeEm searchType) {
        userHisDao.delete(userId, searchType);
    }

    @Override
    @Async
    @Transactional
    public void insertStoreSearchHis(long userId, String kwQuery) {
        try {
            kwQuery = kwQuery.trim();

            UserSearchHistory history = new UserSearchHistory();
            history.setUserId(userId);
            history.setKwQuery(kwQuery);
            history.setSearchType(UserSearchHisTypeEm.STORE);

            userHisDao.deleteByKw(userId, UserSearchHisTypeEm.STORE, kwQuery);
            userHisDao.insert(history);
        } catch (Exception e) {
            LOGGER.error("Exception occurs during async insert history", e);
        }
    }

    @Override
    public List<String> getStoreSearchHis(long userId,  int topk) {
        return userHisDao.get(userId, UserSearchHisTypeEm.STORE, topk);
    }

}
