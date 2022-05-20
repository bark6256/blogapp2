package com.cos.blogapp2.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

	@GetMapping("/board")
	public String list() {
		return "board/list";
	}
	
	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id) {
		return "board/detail";
	}
	
	@GetMapping("/board/saveForm")
	public String boardSaveForm() {
		return "board/saveForm";
	}
}
