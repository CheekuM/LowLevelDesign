package com.low.level.design.consistent.hashing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsistentashingController {

	@GetMapping("/consistenthashing")
	public String testCode() {
		ConsistentHashing consistentHashing = new ConsistentHashing(3);
		consistentHashing.addServer("server1");
		consistentHashing.addServer("server12");
		consistentHashing.addServer("server13");
		consistentHashing.getKey("key");
		consistentHashing.getKey("Shikha");
		consistentHashing.deleteServer("server1");
		consistentHashing.getKey("key");
		consistentHashing.getKey("Shikha");
		return "Code runs succesfully, Verify result in logs";
	}
}
