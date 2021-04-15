package com.cbsys.saleexplore.scheduler;


import com.cbsys.saleexplore.config.ConstantConfig;
import com.cbsys.saleexplore.iservice.store.IStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StoreInfoScheduler {

    @Autowired
    private IStoreService storeService;

    private static final Logger logger = LoggerFactory.getLogger(StoreInfoScheduler.class);

    @Scheduled(initialDelay = ConstantConfig.SCHEDULER_UPDATE_STORE_NUMBER_DISCOUNTS,
            fixedRate = ConstantConfig.SCHEDULER_UPDATE_STORE_NUMBER_DISCOUNTS)
    public void updateStoreNumberOfDiscounts() {
        /**
         * update the number of valid discounts for the stores
         */
        logger.info("SCHEDULER: Starting updating the number discounts for stores");

        storeService.updateNumberOfDiscount(0);

        logger.info("SCHEDULER: Finished updating the number discounts for stores");

    }


}
