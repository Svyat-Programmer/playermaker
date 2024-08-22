package com.example.playermaker;
import com.example.playermaker.dto.PlayerRequestDTO;
import com.example.playermaker.exception.EmptyPlayersListException;
import com.example.playermaker.exception.InvalidTopPlayersException;
import com.example.playermaker.exception.NullRequestException;
import com.example.playermaker.service.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlayerServiceImplTest {

	private PlayerServiceImpl playerService;

	@BeforeEach
	public void setUp() {
		playerService = new PlayerServiceImpl();
	}

	@Test
	public void testGetTopPlayersValidRequest() {
		PlayerRequestDTO request = new PlayerRequestDTO(2, Arrays.asList(
				Arrays.asList("Sharon", "Shalom", "Ronaldo"),
				Arrays.asList("Shalom", "Myke", "Ronaldo"),
				Arrays.asList("Sharon", "Ronaldo")
		));

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

		InvalidTopPlayersException exception = assertThrows(InvalidTopPlayersException.class, () -> {
			playerService.getTopNPlayers(request);
		});

		assertEquals("requiredTopPlayers must be greater than 0.", exception.getMessage());
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


	@Test
	public void testEmptyPlayersList() {
		PlayerRequestDTO request = new PlayerRequestDTO(2, Collections.emptyList());

		EmptyPlayersListException exception = assertThrows(EmptyPlayersListException.class, () -> {
			playerService.getTopNPlayers(request);
		});

		assertEquals("List of participated players is empty.", exception.getMessage());
	}


	@Test
	public void testNullPlayersList() {
		PlayerRequestDTO request = new PlayerRequestDTO(2, null);

		NullRequestException exception = assertThrows(NullRequestException.class, () -> {
			playerService.getTopNPlayers(request);
		});

		assertEquals("Request body or list of participated players is null.", exception.getMessage());
	}
}

