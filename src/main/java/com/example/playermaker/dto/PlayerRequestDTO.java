package com.example.playermaker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRequestDTO {
    @Min(value = 1, message = "requiredTopPlayers must be greater than 0.")
    private int requiredTopPlayers;

    @NotNull(message = "List of participated players cannot be null.")
    @NotEmpty(message = "List of participated players cannot be empty.")
    private List<@NotEmpty(message = "Each game must have at least one player.")List<String>> participatedPlayers;

}
