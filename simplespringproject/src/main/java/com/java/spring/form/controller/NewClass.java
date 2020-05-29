package com.java.spring.form.controller;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class NewClass {

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:9091/employees";
//		int sum = restTemplate.getForObject(url, Integer.class);
//		System.out.println(sum);
		Object[] objects = restTemplate.getForObject(url, Object[].class);
//		System.out.println(Arrays.asList(objects));
		Map map;
		for (int i = 0; i < objects.length; i++) {
			map=(Map)objects[i];
			System.out.println(map.get("empId")+" "+map.get("empName")+" "+map.get("deptName"));
		}
		
	}

}
