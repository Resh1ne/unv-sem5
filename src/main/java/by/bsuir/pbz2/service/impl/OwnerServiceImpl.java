package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.dao.OwnerDao;
import by.bsuir.pbz2.data.entity.Owner;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.OwnerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerDao ownerDao;

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
        log.info("Creating owner with name: {}", dto.getName());
        Owner owner = toOwnerEntity(dto);
        Owner ownerCreated = ownerDao.create(owner);
        log.info("Created owner with name: {}", ownerCreated.getName());
        return toOwnerDto(ownerCreated);
    }

    @Override
    public OwnerDto getById(Long id) {
        log.info("Fetching owner with id: {}", id);
        Owner owner = ownerDao.findById(id);
        if (owner == null) {
            log.error("No owner found with id: {}", id);
            throw new RuntimeException("No owner with id: " + id);
        }
        log.info("Fetched owner with id: {}", id);
        return toOwnerDto(owner);
    }

    @Override
    public List<OwnerDto> getAll() {
        log.info("Fetching all owners");
        List<OwnerDto> owners = ownerDao.findAll()
                .stream()
                .map(this::toOwnerDto)
                .toList();
        log.info("Fetched {} owners", owners.size());
        return owners;
    }

    @Override
    public OwnerDto update(OwnerDto dto) {
        log.info("Updating owner with id: {}", dto.getId());
        Owner owner = toOwnerEntity(dto);
        owner.setId(dto.getId());
        Owner ownerUpdated = ownerDao.update(owner);
        log.info("Updated owner with id: {}", ownerUpdated.getId());
        return toOwnerDto(ownerUpdated);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting owner with id: {}", id);
        Owner owner = ownerDao.findById(id);
        if (owner == null) {
            log.error("Owner with id: {} not found", id);
            throw new RuntimeException("Owner with id: " + id + " not found");
        }
        ownerDao.delete(id);
        log.info("Deleted owner with id: {}", id);
    }
}
