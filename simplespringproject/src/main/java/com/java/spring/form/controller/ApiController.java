package com.java.spring.form.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

//@Controller
//@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON,consumes = MediaType.APPLICATION_JSON)
public class ApiController {
	public static List<Object> main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:9091/employees";
		Object[] objects = restTemplate.getForObject(url, Object[].class);
		System.out.println(Arrays.asList(objects));
		return Arrays.asList(objects);
	}
}

/*The RestTemplate class is the central tool for performing client-side HTTP operations in Spring. 
 * It provides several utility methods for building HTTP requests and handling responses.
 * And, since RestTemplate integrates well with Jackson, it can serialize/deserialize most objects to and from JSON without much effort.
 *  However, working with collections of objects is not so straightforward.
 */

/*Request processing failed; nested exception is org.springframework.web.client.RestClientException:
 *  Could not extract response: no suitable HttpMessageConverter found for response type [class [Ljava.lang.Object;] and content type [application/json]] with root cause
 */
