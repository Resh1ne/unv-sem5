package by.bsuir.pbz2.data.entity;

import by.bsuir.pbz2.data.entity.enums.ExhibitionType;

import java.time.LocalDate;
import java.util.Objects;


public class Exhibition {
    private Long id;
    private String name;
    private ExhibitionHall exhibitionHallId;
    private ExhibitionType exhibitionType;
    private LocalDate startDate;
    private LocalDate endDate;

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

    public ExhibitionHall getExhibitionHallId() {
        return exhibitionHallId;
    }

    public void setExhibitionHallId(ExhibitionHall exhibitionHallId) {
        this.exhibitionHallId = exhibitionHallId;
    }

    public ExhibitionType getExhibitionType() {
        return exhibitionType;
    }

    public void setExhibitionType(ExhibitionType exhibitionType) {
        this.exhibitionType = exhibitionType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exhibition that = (Exhibition) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(exhibitionHallId, that.exhibitionHallId) && exhibitionType == that.exhibitionType && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, exhibitionHallId, exhibitionType, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Exhibition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exhibitionHallId=" + exhibitionHallId +
                ", exhibitionType=" + exhibitionType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
