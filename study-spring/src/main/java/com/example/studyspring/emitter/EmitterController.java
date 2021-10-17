package com.example.studyspring.emitter;

import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmitterController {

	@GetMapping("/emitter")
	public ResponseBodyEmitter emitter() {
		ResponseBodyEmitter emitter = new ResponseBodyEmitter();

		Executors.newSingleThreadExecutor().submit(() -> {
			try {
				for (int i = 0; i < 50; i++) {
					emitter.send("<p>Stream " + i + "</p>");
					Thread.sleep(100);
				}
			} catch (Exception e) {}
		});

		return emitter;
	}
}
