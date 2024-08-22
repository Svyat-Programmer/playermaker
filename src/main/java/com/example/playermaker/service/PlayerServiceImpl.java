package com.example.playermaker.service;

import com.example.playermaker.dto.PlayerRequestDTO;
import com.example.playermaker.exception.EmptyPlayersListException;
import com.example.playermaker.exception.InvalidTopPlayersException;
import com.example.playermaker.exception.NullRequestException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Override
    public List<String> getTopNPlayers(PlayerRequestDTO playerRequestDTO) {
        if (playerRequestDTO == null || playerRequestDTO.getParticipatedPlayers() == null) {
            throw new NullRequestException("Request body or list of participated players is null.");
        }

        if (playerRequestDTO.getParticipatedPlayers().isEmpty()) {
            throw new EmptyPlayersListException("List of participated players is empty.");
        }
        Map<String, Integer> playerParticipation = new HashMap<>();

        for (List<String> game : playerRequestDTO.getParticipatedPlayers()) {
            Set<String> uniquePlayers = new HashSet<>(game);

            for (String player : uniquePlayers) {
                playerParticipation.put(player, playerParticipation.getOrDefault(player, 0) + 1);
            }
        }
        int totalUniquePlayers = playerParticipation.size();

        if (playerRequestDTO.getRequiredTopPlayers() <= 0) {
            throw new InvalidTopPlayersException("requiredTopPlayers must be greater than 0.");
        }

        if (playerRequestDTO.getRequiredTopPlayers() > totalUniquePlayers) {
            throw new InvalidTopPlayersException("requested top players (" + playerRequestDTO.getRequiredTopPlayers() +
                    ") is greater than the number of unique players (" + totalUniquePlayers + ").");
        }

        List<Map.Entry<String, Integer>> sortedPlayers = new ArrayList<>(playerParticipation.entrySet());
        sortedPlayers.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        List<String> topPlayers = new ArrayList<>();
        for (int i = 0; i < playerRequestDTO.getRequiredTopPlayers() && i < sortedPlayers.size(); i++) {
            topPlayers.add(sortedPlayers.get(i).getKey());
        }

        return topPlayers;
    }


}

