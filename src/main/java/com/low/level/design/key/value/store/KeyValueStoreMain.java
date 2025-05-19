package com.low.level.design.key.value.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class KeyValueStoreMain {

	private KeyValueStoreManager keyValueStoreManager = KeyValueStoreManager.getInstance();

	@GetMapping("/keyValueStoreTest")
	public String test() {

		/*
		 * put sde_bootcamp title SDE-Bootcamp price 30000.00 enrolled false
		 * estimated_time 30 get sde_bootcamp keys put sde_kickstart title SDE-Kickstart
		 * price 4000 enrolled true estimated_time 8 get sde_kickstart keys put
		 * sde_kickstart title SDE-Kickstart price 4000.00 enrolled true estimated_time
		 * 8 get sde_kickstart keys delete sde_bootcamp get sde_bootcamp keys put
		 * sde_bootcamp title SDE-Bootcamp price 30000.00 enrolled true estimated_time
		 * 30 search price 30000.00 search enrolled true
		 */
		String str = "title SDE-Bootcamp price 30000.00 enrolled false estimated_time 30";
		String[] input = str.split(" ");
		Pair<String, String> pair;
		List<Pair<String, String>> list = new ArrayList<>();
		for (int i = 0; i < input.length; i += 2) {
			pair = new Pair(input[i], input[i + 1]);
			list.add(pair);
		}
		try {
			keyValueStoreManager.put("sde_bootcamp", list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Map<String, Object> map = keyValueStoreManager.get("sde_bootcamp");
		StringBuilder s = new StringBuilder();
		if (map == null)
			System.out.println("No entry found for sde_bootcamp");
		else {
			map.entrySet().stream()
					.forEach(entry -> s.append(entry.getKey()).append(":").append(entry.getValue()).append(","));
		}
		System.out.println(s.substring(0, s.length() - 1));
		System.out.println(keyValueStoreManager.keys().toString());

		str = "title SDE-Kickstart price 4000 enrolled true estimated_time 8";
		input = str.split(" ");

		list = new ArrayList<>();
		for (int i = 0; i < input.length; i += 2) {
			pair = new Pair(input[i], input[i + 1]);
			list.add(pair);
		}
		try {
			keyValueStoreManager.put("sde_kickstart", list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		map = keyValueStoreManager.get("sde_kickstart");
		StringBuilder s1 = new StringBuilder();
		if (map == null)
			System.out.println("No entry found for sde_kickstart");
		else {
			map.entrySet().stream()
					.forEach(entry -> s1.append(entry.getKey()).append(":").append(entry.getValue()).append(","));
		}
		// System.out.println(s1.substring(0, s1.length() - 1));
		System.out.println(keyValueStoreManager.keys().toString());

		str = "title SDE-Kickstart price 4000.00 enrolled true estimated_time 8";
		input = str.split(" ");

		list = new ArrayList<>();
		for (int i = 0; i < input.length; i += 2) {
			pair = new Pair(input[i], input[i + 1]);
			list.add(pair);
		}
		try {
			keyValueStoreManager.put("sde_kickstart", list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		map = keyValueStoreManager.get("sde_kickstart");
		StringBuilder s2 = new StringBuilder();
		if (map == null)
			System.out.println("No entry found for sde_kickstart");
		else {
			map.entrySet().stream()
					.forEach(entry -> s2.append(entry.getKey()).append(":").append(entry.getValue()).append(","));
		}
		System.out.println(s.substring(0, s2.length() - 1));
		System.out.println(keyValueStoreManager.keys().toString());

		keyValueStoreManager.deleteKey("sde_bootcamp");
		map = keyValueStoreManager.get("sde_bootcamp");
		StringBuilder s23 = new StringBuilder();
		if (map == null)
			System.out.println("No entry found for sde_bootcamp");
		else {
			map.entrySet().stream()
					.forEach(entry -> s23.append(entry.getKey()).append(":").append(entry.getValue()).append(","));
		}
		System.out.println(keyValueStoreManager.keys().toString());
		System.out.println(keyValueStoreManager.search("price", "30000.00").toString());
		System.out.println(keyValueStoreManager.search("enrolled", "true").toString());
		return "Successfully Test. For error, check logs";
	}

}
