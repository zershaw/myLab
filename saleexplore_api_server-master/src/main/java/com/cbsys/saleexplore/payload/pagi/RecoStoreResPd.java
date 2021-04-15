package com.cbsys.saleexplore.payload.pagi;


import com.cbsys.saleexplore.entity.Store;
import lombok.Getter;

import java.util.List;

/**
 * store dynamic pagination response
 */
public class RecoStoreResPd extends AnchorBaseRes {

    @Getter
    private List<Store> results;

    public RecoStoreResPd(QueryStorePd requestPd, List<Store> results){
        super();
        this.results = results;

        // previous request
        if(requestPd.getMaxId() == 0){
            this.refresh = null;
        }else{
            this.refresh = new QueryStorePd(requestPd);
            if(requestPd.getSinceId() != 0){
                this.refresh.setMaxId(requestPd.getSinceId() - 1);
                this.refresh.setSinceId(requestPd.getSinceId() - requestPd.getLimit());
            }else{
                this.refresh.setMaxId(0);
                this.refresh.setSinceId(0);
            }


        }

        // next request
        if(results == null || results.size() < requestPd.getLimit()){
            this.loadMore = null;

            this.sinceId = requestPd.getSinceId();
            this.maxId = requestPd.getMaxId();

        }else{
            this.loadMore = new QueryStorePd(requestPd);

            if(requestPd.getMaxId() == 0){
                this.loadMore.setSinceId(0);
                this.loadMore.setMaxId(results.size() - 1);

                this.sinceId = 0;
                this.maxId = results.size() - 1;

            }else{
                this.loadMore.setSinceId(requestPd.getMaxId() + 1);
                this.loadMore.setMaxId(requestPd.getMaxId() + results.size());

                this.sinceId = requestPd.getMaxId() + 1;
                this.maxId = requestPd.getMaxId() + results.size();
            }
        }



    }

}
