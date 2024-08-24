package com.example.playermaker;
import com.example.playermaker.dto.PlayerRequestDTO;
import com.example.playermaker.exception.InvalidTopPlayersException;
import com.example.playermaker.service.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlayerServiceImplTest {

	private PlayerServiceImpl playerService;
	private Validator validator;

	@BeforeEach
	public void setUp() {
		playerService = new PlayerServiceImpl();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}


	@Test
	public void testValidRequestDTO() {
		PlayerRequestDTO request = new PlayerRequestDTO(2, Arrays.asList(
				Arrays.asList("Sharon", "Shalom", "Ronaldo"),
				Arrays.asList("Shalom", "Myke", "Ronaldo")
		));

		Set<ConstraintViolation<PlayerRequestDTO>> violations = validator.validate(request);

		assertTrue(violations.isEmpty(), "DTO should pass validation");
	}


	@Test
	public void testRequiredTopPlayersZero() {
		PlayerRequestDTO request = new PlayerRequestDTO(0, Arrays.asList(
				Arrays.asList("Sharon", "Shalom", "Ronaldo")
		));

		Set<ConstraintViolation<PlayerRequestDTO>> violations = validator.validate(request);

		assertFalse(violations.isEmpty(), "DTO should fail validation due to requiredTopPlayers being 0");

		ConstraintViolation<PlayerRequestDTO> violation = violations.stream()
				.filter(v -> v.getPropertyPath().toString().equals("requiredTopPlayers"))
				.findFirst()
				.orElse(null);

		assertNotNull(violation);
		assertEquals("requiredTopPlayers must be greater than 0.", violation.getMessage());
	}

	@Test
	public void testEmptyPlayersList() {
		PlayerRequestDTO request = new PlayerRequestDTO(2, Collections.emptyList());

		Set<ConstraintViolation<PlayerRequestDTO>> violations = validator.validate(request);

		assertFalse(violations.isEmpty(), "DTO should fail validation due to empty list of players");

		ConstraintViolation<PlayerRequestDTO> violation = violations.stream()
				.filter(v -> v.getPropertyPath().toString().equals("participatedPlayers"))
				.findFirst()
				.orElse(null);

		assertNotNull(violation);
		assertEquals("List of participated players cannot be empty.", violation.getMessage());
	}

	@Test
	public void testNullPlayersList() {
		PlayerRequestDTO request = new PlayerRequestDTO(2, null);

		Set<ConstraintViolation<PlayerRequestDTO>> violations = validator.validate(request);

		assertFalse(violations.isEmpty(), "DTO should fail validation due to null list of players");

		ConstraintViolation<PlayerRequestDTO> violation = violations.stream()
				.filter(v -> v.getPropertyPath().toString().equals("participatedPlayers"))
				.findFirst()
				.orElse(null);

		assertNotNull(violation);
		assertEquals("List of participated players cannot be null.", violation.getMessage());
	}
}
