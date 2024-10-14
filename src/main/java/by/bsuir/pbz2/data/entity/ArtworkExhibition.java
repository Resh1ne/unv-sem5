package by.bsuir.pbz2.data.entity;

import java.util.Objects;

public class ArtworkExhibition {
    private Exhibition exhibitionId;
    private Artwork artworkId;

    public Exhibition getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Exhibition exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public Artwork getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Artwork artworkId) {
        this.artworkId = artworkId;
    }

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
