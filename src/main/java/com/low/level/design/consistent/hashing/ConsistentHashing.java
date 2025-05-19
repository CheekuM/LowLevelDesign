package com.low.level.design.consistent.hashing;

import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing {

	/*
	 * Operations 1. add Servers 2. remove servers 3. get Server - all keys on the
	 * specified server 4. add key
	 */

	private SortedMap<Integer, String> hashRing = new TreeMap();
	private int numberOfReplicas;

	public ConsistentHashing(int numberOfReplicas) {
		// TODO Auto-generated constructor stub
		this.numberOfReplicas = numberOfReplicas;
	}

	public void addServer(String server) {
		String key;
		for (int i = 0; i < numberOfReplicas; i++) {
			key = server + "_" + i;
			int hash = key.hashCode();
			hashRing.put(hash, key);
			System.out.println("Inserted server : " + server + " Virtual Node : " + key + " with hash value :" + hash);
		}
	}

	public void deleteServer(String server) {
		if (hashRing.isEmpty())
			return;
		String key;
		for (int i = 0; i < numberOfReplicas; i++) {
			key = server + "_" + i;
			int hash = key.hashCode();
			hashRing.remove(hash);
			System.out.println("Deleted server : " + server + " Virtual Node : " + key + " with hash value :" + hash);
		}
	}

	public void getKey(String key) {
		if (hashRing.isEmpty())
			return;
		int hash = key.hashCode();
		String server;
		if (!hashRing.containsKey(hash)) {
			SortedMap<Integer, String> tailRing = hashRing.tailMap(hash);
			int serverHash = tailRing.isEmpty() ? hashRing.firstKey() : tailRing.firstKey();
			server = hashRing.get(serverHash);
		} else {
			server = hashRing.get(hash);
		}
		System.out.println("Server : " + server + " is handling the given key : " + key);

	}
}
