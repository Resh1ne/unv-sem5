package by.bsuir.pbz2.data.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Artist {
    private Long id;
    private String name;
    private String birthPlace;
    private LocalDate birthDate;
    private String biography;
    private String education;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

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
