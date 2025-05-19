package com.low.level.design.key.value.store;

import java.util.Map;

public class Value {
	private final Map<String, Object> attributes;

	public Value(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder valueToString = new StringBuilder();
		for(Map.Entry<String, Object> attribute: attributes.entrySet()) {
			valueToString.append(attribute.getKey()).append(" : ").append(attribute.getValue().toString()).append(", ");
		}
		return valueToString.substring(0, valueToString.length()-1);
	}
}
