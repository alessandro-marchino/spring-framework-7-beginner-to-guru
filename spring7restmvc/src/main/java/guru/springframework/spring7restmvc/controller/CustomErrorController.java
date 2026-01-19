package guru.springframework.spring7restmvc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<List<FieldError>> handleBindError(MethodArgumentNotValidException exception) {
		return ResponseEntity
			.badRequest()
			.body(exception.getBindingResult().getFieldErrors());
	}

}
