package by.bsuir.pbz2.service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ArtistDto {
    private Long id;
    private String name;
    private String birthPlace;
    private LocalDate birthDate;
    private String biography;
    private String education;
}
