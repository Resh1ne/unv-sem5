package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.dao.ExhibitionHallDao;
import by.bsuir.pbz2.data.entity.ExhibitionHall;
import by.bsuir.pbz2.data.entity.Owner;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import by.bsuir.pbz2.service.dto.OwnerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ExhibitionHallServiceImpl implements ExhibitionHallService {
    private final ExhibitionHallDao exhibitionHallDao;

    @Override
    public ExhibitionHallDto create(ExhibitionHallDto dto) {
        log.info("Creating exhibition hall with name: {}", dto.getName());
        ExhibitionHall exhibitionHall = toExhibitionHallEntity(dto);
        ExhibitionHall exhibitionHallCreated = exhibitionHallDao.create(exhibitionHall);
        log.info("Created exhibition hall with name: {}", exhibitionHallCreated.getName());
        return toExhibitionHallDto(exhibitionHallCreated);
    }

    private ExhibitionHall toExhibitionHallEntity(ExhibitionHallDto dto) {
        ExhibitionHall exhibitionHall = new ExhibitionHall();
        exhibitionHall.setName(dto.getName());
        exhibitionHall.setArea(dto.getArea());
        exhibitionHall.setAddress(dto.getAddress());
        exhibitionHall.setPhone(dto.getPhone());
        exhibitionHall.setOwnerId(toOwnerEntity(dto.getOwnerId()));
        return exhibitionHall;
    }

    private Owner toOwnerEntity(OwnerDto dto) {
        Owner owner = new Owner();
        owner.setId(dto.getId());
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

    private ExhibitionHallDto toExhibitionHallDto(ExhibitionHall entity) {
        ExhibitionHallDto exhibitionHallDto = new ExhibitionHallDto();
        exhibitionHallDto.setId(entity.getId());
        exhibitionHallDto.setName(entity.getName());
        exhibitionHallDto.setArea(entity.getArea());
        exhibitionHallDto.setAddress(entity.getAddress());
        exhibitionHallDto.setPhone(entity.getPhone());
        exhibitionHallDto.setOwnerId(toOwnerDto(entity.getOwnerId()));
        return exhibitionHallDto;
    }

    @Override
    public ExhibitionHallDto getById(Long id) {
        log.info("Fetching exhibition hall with id: {}", id);
        ExhibitionHall exhibitionHall = exhibitionHallDao.findById(id);
        if (exhibitionHall == null) {
            log.error("No exhibition hall found with id: {}", id);
            throw new RuntimeException("No exhibition hall with id: " + id);
        }
        log.info("Fetched exhibition hall with id: {}", id);
        return toExhibitionHallDto(exhibitionHall);
    }

    @Override
    public List<ExhibitionHallDto> getAll() {
        log.info("Fetching all exhibition halls");
        List<ExhibitionHallDto> exhibitionHalls = exhibitionHallDao.findAll()
                .stream()
                .map(this::toExhibitionHallDto)
                .toList();
        log.info("Fetched {} exhibition halls", exhibitionHalls.size());
        return exhibitionHalls;
    }

    @Override
    public ExhibitionHallDto update(ExhibitionHallDto dto) {
        log.info("Updating exhibition hall with id: {}", dto.getId());
        ExhibitionHall exhibitionHall = toExhibitionHallEntity(dto);
        exhibitionHall.setId(dto.getId());
        ExhibitionHall exhibitionHallUpdated = exhibitionHallDao.update(exhibitionHall);
        log.info("Updated exhibition hall with id: {}", exhibitionHallUpdated.getId());
        return toExhibitionHallDto(exhibitionHallUpdated);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting exhibition hall with id: {}", id);
        ExhibitionHall exhibitionHall = exhibitionHallDao.findById(id);
        if (exhibitionHall == null) {
            log.error("Exhibition hall with id: {} not found", id);
            throw new RuntimeException("Exhibition hall with id: " + id + " not found");
        }
        exhibitionHallDao.delete(id);
        log.info("Deleted exhibition hall with id: {}", id);
    }
}
