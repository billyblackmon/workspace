package com.in28minutes.springboot.basics.springbootin10steps;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordsController {
	
	@GetMapping("/records")
	public List<Record> getAllRecords() {
		return Arrays.asList(
				new Record(11, "You Really Got Me", "The Kinks"));
	}
	
	
}
