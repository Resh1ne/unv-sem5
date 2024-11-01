package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.Owner;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
public class ExhibitionHallDto {
    private Long id;
    private String name;
    private BigDecimal area;
    private String address;
    private String phone;
    private Owner ownerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExhibitionHallDto that = (ExhibitionHallDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(area, that.area) && Objects.equals(address, that.address) && Objects.equals(phone, that.phone) && Objects.equals(ownerId, that.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, area, address, phone, ownerId);
    }

    @Override
    public String toString() {
        return "ExhibitionHallDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", area=" + area +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
