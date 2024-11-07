package by.bsuir.pbz2.data.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Artist {
    private Long id;
    private String name;
    private String birthPlace;
    private LocalDate birthDate;
    private String biography;
    private String education;
}
