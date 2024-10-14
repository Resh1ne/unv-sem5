package by.bsuir.pbz2.data.entity;

import by.bsuir.pbz2.data.entity.enums.OwnerType;

import java.util.Objects;

public class Owner {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private OwnerType ownerType;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(OwnerType ownerType) {
        this.ownerType = ownerType;
    }

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
