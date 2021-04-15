package com.cbsys.saleexplore.util;

import com.cbsys.saleexplore.config.RedisConstant;
import org.springframework.data.redis.connection.RedisConnection;

import java.nio.charset.StandardCharsets;
import java.util.Set;

public class SearchCacheUtil {
    public static void insertStoreSearch(RedisConnection conn, long userId, Set<String> storeIds) {
        storeIds.stream().forEach(storeId -> {
            String key = RedisConstant.STORE_SEARCH_HISTORY + storeId;
            insertToRedis(conn, userId, key);
        });
    }

    private static void insertToRedis(RedisConnection conn, long userId, String key) {
        conn.sAdd(key.getBytes(StandardCharsets.UTF_8), String.valueOf(userId).getBytes(StandardCharsets.UTF_8));
        conn.expire(key.getBytes(StandardCharsets.UTF_8), RedisConstant.SEARCH_EXPIRE_SECS);
    }

    public static void insertCategorySearch(RedisConnection conn, long userId, Set<String> categoryIds) {
        categoryIds.stream().forEach(categoryId -> {
            String key  = RedisConstant.CATEGORY_SEARCH_HISTORY + categoryId;
            insertToRedis(conn, userId, key);
        });
    }
}
