package by.bsuir.pbz2.data.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class ArtworkExhibition {
    private Exhibition exhibitionId;
    private Artwork artworkId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtworkExhibition that = (ArtworkExhibition) o;
        return Objects.equals(exhibitionId, that.exhibitionId) && Objects.equals(artworkId, that.artworkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exhibitionId, artworkId);
    }

    @Override
    public String toString() {
        return "ArtworkExhibition{" +
                "exhibitionId=" + exhibitionId +
                ", artworkId=" + artworkId +
                '}';
    }
}
