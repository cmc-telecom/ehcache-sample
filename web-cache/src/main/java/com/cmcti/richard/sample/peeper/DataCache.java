package com.cmcti.richard.sample.peeper;

import org.ehcache.PersistentUserManagedCache;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.ehcache.config.builders.UserManagedCacheBuilder.*;

/**
 * @author Nguyen Trong Cuong (cuong.nt1@cmctelecom.vn)
 * @since 4/27/17
 */
public class DataCache {

    private static final Logger _LOG = LoggerFactory.getLogger(DataCache.class);

    private UserManagedCache<String, List<String>> cache;

    @SuppressWarnings("unchecked")
    public void setupCache() {
        cache = (UserManagedCache<String, List<String>>)  (UserManagedCache<?, ?>) newUserManagedCacheBuilder(String.class, List.class)
                .identifier("data-cache")
                .build(true);
        _LOG.info("Cache setup is done!");
    }

    public List<String> getFromCache() {
        return cache.get("all-peeps");
    }

    public void addToCache(List<String> peeps) {
        cache.put("all-peeps", peeps);
    }

    public void clearCache() {
        cache.clear();
    }

    public void close() {
        cache.close();
    }

}
