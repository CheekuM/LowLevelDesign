package com.low.level.design.key.value.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class KeyValueStoreManager {

	/*
	 * 
	 * get(String key) => Should return the value (object with attributes and their
	 * values). Return null if key not present search(String attributeKey, String
	 * attributeValue) => Returns a list of keys that have the given attribute key,
	 * value pair. put(String key, List<Pair<String, String>> listOfAttributePairs)
	 * => Adds the key and the attributes to the key-value store. If the key already
	 * exists then the value is replaced. delete(String key) => Deletes the key,
	 * value pair from the store. keys() => Return a list of all the keys
	 */

	private final Map<String, Value> keyValueMap;
	private final Map<String, Class<?>> attributeObjectMap;
	private final ReentrantReadWriteLock readWriteLock; // used it here as it is read heavy system 

	private final static KeyValueStoreManager KEYVALUESTOREMANAGER_INSTANCE = new KeyValueStoreManager();

	private KeyValueStoreManager() {
		readWriteLock = new ReentrantReadWriteLock();
		keyValueMap = new ConcurrentHashMap<String, Value>();
		attributeObjectMap = new ConcurrentHashMap<String, Class<?>>();
	}

	public static KeyValueStoreManager getInstance() {
		return KEYVALUESTOREMANAGER_INSTANCE;
	}

	public Map<String, Object> get(String key) {

		try {
			readWriteLock.readLock().lock();
			if (!keyValueMap.containsKey(key))
				return null;
			else
				return keyValueMap.get(key).getAttributes();

		} finally {
			readWriteLock.readLock().unlock();
		}
	}

	public List<String> search(String attributeKeys, String attributeValue) {

		List<String> keys = new ArrayList();

		for (Entry<String, Value> attributeList : keyValueMap.entrySet()) {
			Map<String, Object> attribute = attributeList.getValue().getAttributes();
			if (attribute.containsKey(attributeKeys) && attribute.get(attributeKeys).toString().equals(attributeValue))
				keys.add(attributeList.getKey());
		}
		return keys;
	}

	public void put(String key, List<Pair<String, String>> listOfAttributePairs) throws Exception {
		try {
			readWriteLock.writeLock().lock();
			Map<String, Object> attributesList = new HashMap();
			for (Pair attribute : listOfAttributePairs) {
				Class<?> attributeValueObjectType = getObjectInstanceClass(attribute.getValue().toString());
				if (attributeObjectMap.containsKey(attribute.getKey())
						&& attributeValueObjectType != attributeObjectMap.get(attribute.getKey())) {
					throw new Exception("Data Type Error");
				}
				attributeObjectMap.put(attribute.getKey().toString(), attributeValueObjectType);
				attributesList.put(attribute.getKey().toString(),
						convertToExpectedDataType(attribute.getValue().toString(), attributeValueObjectType));
			}
			keyValueMap.put(key, new Value(attributesList));
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}
	
	public void deleteKey(String key) {
		try {
			readWriteLock.writeLock().lock();
			if(keyValueMap.containsKey(key)) {
				keyValueMap.remove(key);
				System.out.println("Deleted Successfully");
			}
			else System.out.println("Key not present");
			
		}finally {
			readWriteLock.writeLock().unlock();
		}
	}

	private Class<?> getObjectInstanceClass(String object) {
		// TODO Auto-generated method stub
		if (object.matches("\\d+"))
			return Integer.class;
		else if (object.matches("\\d+\\.\\d+"))
			return Double.class;
		else if (object.equalsIgnoreCase("true") || object.equalsIgnoreCase("false"))
			return Boolean.class;
		else
			return String.class;
	}

	private Object convertToExpectedDataType(String key, Class<?> object) {
		if (object.equals(Integer.class)) {
			return Integer.parseInt(key);
		} else if (object.equals(Double.class)) {
			return Double.parseDouble(key);
		} else if (object.equals(Boolean.class)) {
			return Boolean.parseBoolean(key);
		} else
			return key;
	}

	public List<String> keys() {
		return keyValueMap.entrySet().stream().map(n -> n.getKey()).collect(Collectors.toList());
	}

}
