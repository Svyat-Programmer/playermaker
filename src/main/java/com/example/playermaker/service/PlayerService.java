package com.example.playermaker.service;

import com.example.playermaker.dto.PlayerRequestDTO;

import java.util.List;

public interface PlayerService {
    List<String> getTopNPlayers(PlayerRequestDTO playerRequestDTO);

}
