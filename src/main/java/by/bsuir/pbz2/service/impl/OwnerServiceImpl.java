package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.OwnerDao;
import by.bsuir.pbz2.data.entity.Owner;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.OwnerDto;

import java.util.List;

public class OwnerServiceImpl implements OwnerService {
    private final OwnerDao ownerDao;

    public OwnerServiceImpl(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    private Owner toOwnerEntity(OwnerDto dto) {
        Owner owner = new Owner();
        owner.setName(dto.getName());
        owner.setAddress(dto.getAddress());
        owner.setPhone(dto.getPhone());
        owner.setOwnerType(dto.getOwnerType());
        return owner;
    }

    private OwnerDto toOwnerDto(Owner entity) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(entity.getId());
        ownerDto.setName(entity.getName());
        ownerDto.setAddress(entity.getAddress());
        ownerDto.setPhone(entity.getPhone());
        ownerDto.setOwnerType(entity.getOwnerType());
        return ownerDto;
    }

    @Override
    public OwnerDto create(OwnerDto dto) {
        Owner owner = toOwnerEntity(dto);
        Owner ownerCreated = ownerDao.create(owner);
        return toOwnerDto(ownerCreated);
    }

    @Override
    public OwnerDto getById(Long id) {
        Owner owner = ownerDao.findById(id);
        if (owner == null) {
            throw new RuntimeException("No owner with id: " + id);
        }
        return toOwnerDto(owner);
    }

    @Override
    public List<OwnerDto> getAll() {
        return ownerDao.findAll()
                .stream()
                .map(this::toOwnerDto)
                .toList();
    }

    @Override
    public OwnerDto update(OwnerDto dto) {
        Owner owner = toOwnerEntity(dto);
        owner.setId(dto.getId());
        Owner ownerCreated = ownerDao.update(owner);
        return toOwnerDto(ownerCreated);
    }

    @Override
    public void delete(Long id) {
        Owner owner = ownerDao.findById(id);
        if (owner == null) {
            throw new RuntimeException("Owner with id: " + id + " not found");
        }
        ownerDao.delete(id);
    }
}
