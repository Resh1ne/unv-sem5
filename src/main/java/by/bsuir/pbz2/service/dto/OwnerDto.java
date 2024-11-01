package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.enums.OwnerType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class OwnerDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private OwnerType ownerType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDto ownerDto = (OwnerDto) o;
        return Objects.equals(id, ownerDto.id) && Objects.equals(name, ownerDto.name) && Objects.equals(address, ownerDto.address) && Objects.equals(phone, ownerDto.phone) && ownerType == ownerDto.ownerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone, ownerType);
    }

    @Override
    public String toString() {
        return "OwnerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", ownerType=" + ownerType +
                '}';
    }
}
