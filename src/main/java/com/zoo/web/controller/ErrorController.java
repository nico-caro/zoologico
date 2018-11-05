package com.zoo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	
	
	@GetMapping("/403")
	public String forbbiden() {
		
		return "403.html";
	}

}
