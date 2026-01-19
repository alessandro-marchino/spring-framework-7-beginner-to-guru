package guru.springframework.spring7restmvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<List<Map<String, String>>> handleBindError(MethodArgumentNotValidException exception) {
		List<Map<String, String>> errorList = exception.getFieldErrors().stream()
			.map(fieldError -> Map.of(fieldError.getField(), fieldError.getDefaultMessage()))
			.toList();
		return ResponseEntity
			.badRequest()
			.body(errorList);
	}

}
