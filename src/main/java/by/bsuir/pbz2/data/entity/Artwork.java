package by.bsuir.pbz2.data.entity;

import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@Setter
@Getter
public class Artwork {
    private Long id;
    private String title;
    private ExecutionType executionType;
    private LocalDate creationDate;
    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal volume;
    private Artist artistId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artwork artwork = (Artwork) o;
        return Objects.equals(id, artwork.id) && Objects.equals(title, artwork.title) && executionType == artwork.executionType && Objects.equals(creationDate, artwork.creationDate) && Objects.equals(height, artwork.height) && Objects.equals(width, artwork.width) && Objects.equals(volume, artwork.volume) && Objects.equals(artistId, artwork.artistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, executionType, creationDate, height, width, volume, artistId);
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", executionType=" + executionType +
                ", creationDate=" + creationDate +
                ", height=" + height +
                ", width=" + width +
                ", volume=" + volume +
                ", artistId=" + artistId +
                '}';
    }
}
