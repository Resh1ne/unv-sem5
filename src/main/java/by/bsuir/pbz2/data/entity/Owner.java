package by.bsuir.pbz2.data.entity;

import by.bsuir.pbz2.data.entity.enums.OwnerType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class Owner {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private OwnerType ownerType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(id, owner.id) && Objects.equals(name, owner.name) && Objects.equals(address, owner.address) && Objects.equals(phone, owner.phone) && ownerType == owner.ownerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone, ownerType);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", ownerType=" + ownerType +
                '}';
    }
}
