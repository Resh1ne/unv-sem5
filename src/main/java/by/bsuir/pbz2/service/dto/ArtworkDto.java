package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.Artist;
import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
public class ArtworkDto {
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
        ArtworkDto that = (ArtworkDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && executionType == that.executionType && Objects.equals(creationDate, that.creationDate) && Objects.equals(height, that.height) && Objects.equals(width, that.width) && Objects.equals(volume, that.volume) && Objects.equals(artistId, that.artistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, executionType, creationDate, height, width, volume, artistId);
    }

    @Override
    public String toString() {
        return "ArtworkDto{" +
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
