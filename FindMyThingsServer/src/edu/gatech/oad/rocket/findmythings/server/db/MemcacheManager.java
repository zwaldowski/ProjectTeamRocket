package edu.gatech.oad.rocket.findmythings.server.db;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class MemcacheManager implements CacheManager {
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new Memcache<>(name);
	}
}
