package com.example.playermaker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponseDTO {
    private List<String> mostParticipatedPlayers;
}
