package com.lumpundform.utilerias;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> {
	public interface CacheEntryRemovedListener<K, V> {
		void notifyEntryRemoved(K key, V value);
	}

	private Map<K, V> cache;
	private CacheEntryRemovedListener<K, V> entryRemovedListener;

	public LRUCache(final int maxEntries) {
		cache = new LinkedHashMap<K, V>(maxEntries + 1, .75f, true) {
			public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				if (size() > maxEntries) {
					if (entryRemovedListener != null) {
						entryRemovedListener.notifyEntryRemoved(
								eldest.getKey(), eldest.getValue());
					}
					return true;
				}
				return false;
			}
		};
	}

	public void add(K key, V value) {
		cache.put(key, value);
	}

	public V get(K key) {
		return cache.get(key);
	}

	public Collection<V> retrieaveAll() {
		return cache.values();
	}

	public void setEntryRemovedListener(
			CacheEntryRemovedListener<K, V> entryRemovedListener) {
		this.entryRemovedListener = entryRemovedListener;
	}
}
