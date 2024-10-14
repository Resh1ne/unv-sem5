package by.bsuir.pbz2.data.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
public class Artist {
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
        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id) && Objects.equals(name, artist.name) && Objects.equals(birthPlace, artist.birthPlace) && Objects.equals(birthDate, artist.birthDate) && Objects.equals(biography, artist.biography) && Objects.equals(education, artist.education);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthPlace, birthDate, biography, education);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", birthDate=" + birthDate +
                ", biography='" + biography + '\'' +
                ", education='" + education + '\'' +
                '}';
    }
}
