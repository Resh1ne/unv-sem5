package by.bsuir.pbz2.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
public class ArtistDto {
    private Long id;
    private String name;
    private String birthPlace;
    private LocalDate birthDate;
    private String biography;
    private String education;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistDto artistDto = (ArtistDto) o;
        return Objects.equals(id, artistDto.id) && Objects.equals(name, artistDto.name) && Objects.equals(birthPlace, artistDto.birthPlace) && Objects.equals(birthDate, artistDto.birthDate) && Objects.equals(biography, artistDto.biography) && Objects.equals(education, artistDto.education);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthPlace, birthDate, biography, education);
    }

    @Override
    public String toString() {
        return "ArtistDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", birthDate=" + birthDate +
                ", biography='" + biography + '\'' +
                ", education='" + education + '\'' +
                '}';
    }
}
