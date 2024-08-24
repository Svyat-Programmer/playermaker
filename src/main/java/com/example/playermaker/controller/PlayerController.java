package com.example.playermaker.controller;

import com.example.playermaker.dto.PlayerRequestDTO;
import com.example.playermaker.dto.PlayerResponseDTO;
import com.example.playermaker.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/top")
    public ResponseEntity<PlayerResponseDTO> getTopPlayers(@Valid @RequestBody PlayerRequestDTO playerRequestDTO) {

        List<String> topPlayers = playerService.getTopNPlayers(playerRequestDTO);

        return ResponseEntity.ok(new PlayerResponseDTO(topPlayers));
    }
}