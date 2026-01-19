package guru.springframework.spring7restmvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
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

	@ExceptionHandler(ConstraintViolationException.class)
	ResponseEntity<List<Map<String, String>>> handleJPAViolation(ConstraintViolationException exception) {
		log.warn("Constraint violation exception");
		List<Map<String, String>> errorList = exception.getConstraintViolations().stream()
			.map(fieldError -> Map.of(fieldError.getPropertyPath().toString(), fieldError.getMessage()))
			.toList();
		return ResponseEntity.badRequest()
			.body(errorList);
	}

	@ExceptionHandler(TransactionSystemException.class)
	ResponseEntity<Void> handleJPAViolation(TransactionSystemException exception) {
		log.warn("Transaction system exception");
		return ResponseEntity.badRequest()
			.build();
	}

}
