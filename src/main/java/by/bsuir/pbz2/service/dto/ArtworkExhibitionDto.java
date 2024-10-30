package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.Exhibition;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class ArtworkExhibitionDto {
    private Exhibition exhibitionId;
    private Artwork artworkId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtworkExhibitionDto that = (ArtworkExhibitionDto) o;
        return Objects.equals(exhibitionId, that.exhibitionId) && Objects.equals(artworkId, that.artworkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exhibitionId, artworkId);
    }

    @Override
    public String toString() {
        return "ArtworkExhibitionDto{" +
                "exhibitionId=" + exhibitionId +
                ", artworkId=" + artworkId +
                '}';
    }
}
