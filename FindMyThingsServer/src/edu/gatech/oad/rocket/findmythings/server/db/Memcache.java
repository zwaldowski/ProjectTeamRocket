package edu.gatech.oad.rocket.findmythings.server.db;

import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Shiro cache for App Engine memcached.  Some simplifications are made as Memcached
 * is global and these caches are meant to be local, with a name and all.  I've sorted
 * this by wrapping keys in a class to avoid string clashes.  We lose efficiency of course
 * as it will get serialized.
 * <p> I'm using the asynchronous service as it will be a little faster on the puts.
 * <p> Memcached on GAE is pretty slow, so I don't think this will speed stuff up, but it
 * will save on Datastore accesses, which are expensive.
 * @param <K>  The raw key class
 * @param <V>  The value class
 */
class Memcache<K, V> implements Cache<K, V> {

	private static final int EXPIRES = 300; // default to 5 minutes

	private final AsyncMemcacheService memcacheService;

	Memcache(String name) {
		this.memcacheService = MemcacheServiceFactory.getAsyncMemcacheService(name);
	}

	@SuppressWarnings("unchecked")
	public V get(K k) throws CacheException {
		try {
			return (V) memcacheService.get(k).get();
		} catch (InterruptedException e) {
			return null;
		} catch (ExecutionException e) {
			return null;
		}
	}

	public V put(K k, V v) throws CacheException {
		memcacheService.put(k, v, Expiration.byDeltaSeconds(EXPIRES));
		return null;
	}

	public V remove(K k) throws CacheException {
		memcacheService.delete(k);
		return null;
	}

	// broken+unimplementable

	public void clear() throws CacheException {
		// can't do this as it would clear the whole cache for this app, which could be very expensive
	}

	public int size() {
		return 0; // we can't get a count of just our keys
	}

	public Set<K> keys() {
		return new HashSet<>(); // you just can't list a distributed cache
	}

	public Collection<V> values() {
		return new HashSet<>(); // you can't list a distributed cache
	}
}
