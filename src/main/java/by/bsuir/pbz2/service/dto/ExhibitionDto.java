package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.ExhibitionHall;
import by.bsuir.pbz2.data.entity.enums.ExhibitionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
public class ExhibitionDto {
    private Long id;
    private String name;
    private ExhibitionHall hallId;
    private ExhibitionType type;
    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExhibitionDto that = (ExhibitionDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(hallId, that.hallId) && type == that.type && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hallId, type, startDate, endDate);
    }

    @Override
    public String toString() {
        return "ExhibitionDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hallId=" + hallId +
                ", type=" + type +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
