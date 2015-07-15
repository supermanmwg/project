package com.weather.mwg.provider.cache;

/**
 * This is common interface for all caches.
 */
public interface Cache<K, V> {
    /**
     * Gets the @a value from the cache at the designated @a key.
     * 
     * @param key
     * @return value
     */
    V get(K key);

    /**
     * Put the @a value into the cache at the designated @a key.
     * 
     * @param key
     * @param value
     */
    void put(V value);

    /**
     * Removes the value associated with a key
     * 
     * @param key
     */
    void remove(K key);

    /**
     * Get the size of the cache.
     * 
     * @return size
     */
    int size();
}
