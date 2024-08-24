package com.example.playermaker;

import com.example.playermaker.dto.PlayerRequestDTO;
import com.example.playermaker.exception.InvalidTopPlayersException;
import com.example.playermaker.service.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;


@SpringBootTest
public class PlayerServiceImplTest {

    private Validator validator;
    private PlayerServiceImpl playerService;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        playerService = new PlayerServiceImpl();
    }

    @AfterEach
    public void tearDown() {
        validator = null;
        playerService = null;
    }

    @Test
    public void testValidRequestDTO() {
        PlayerRequestDTO request = new PlayerRequestDTO(2, Arrays.asList(
                Arrays.asList("Sharon", "Shalom", "Ronaldo"),
                Arrays.asList("Shalom", "Myke", "Ronaldo")
        ));

        Set<ConstraintViolation<PlayerRequestDTO>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "DTO should pass validation");

        List<String> result = playerService.getTopNPlayers(request);
        assertEquals(2, result.size());
        assertEquals("Ronaldo", result.get(0));
        assertEquals("Shalom", result.get(1));
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
    public void testRequiredTopPlayersExceedsPlayerCount() {
        PlayerRequestDTO request = new PlayerRequestDTO(5, Arrays.asList(
                Arrays.asList("Sharon", "Shalom", "Ronaldo"),
                Arrays.asList("Shalom", "Ronaldo")
        ));

        InvalidTopPlayersException exception = assertThrows(InvalidTopPlayersException.class, () -> {
            playerService.getTopNPlayers(request);
        });

        assertEquals("requested top players (5) is greater than the number of unique players (3).", exception.getMessage());
    }
}
