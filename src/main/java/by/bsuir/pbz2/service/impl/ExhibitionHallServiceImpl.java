package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.dao.ExhibitionHallDao;
import by.bsuir.pbz2.data.entity.ExhibitionHall;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExhibitionHallServiceImpl implements ExhibitionHallService {
    private final ExhibitionHallDao exhibitionHallDao;


    @Override
    public ExhibitionHallDto create(ExhibitionHallDto dto) {
        ExhibitionHall exhibitionHall = toExhibitionHallEntity(dto);
        ExhibitionHall exhibitionHallCreated = exhibitionHallDao.create(exhibitionHall);
        return toExhibitionHallDto(exhibitionHallCreated);
    }

    private ExhibitionHall toExhibitionHallEntity(ExhibitionHallDto dto) {
        ExhibitionHall exhibitionHall = new ExhibitionHall();
        exhibitionHall.setName(dto.getName());
        exhibitionHall.setArea(dto.getArea());
        exhibitionHall.setAddress(dto.getAddress());
        exhibitionHall.setPhone(dto.getPhone());
        exhibitionHall.setOwnerId(dto.getOwnerId());
        return exhibitionHall;
    }

    private ExhibitionHallDto toExhibitionHallDto(ExhibitionHall entity) {
        ExhibitionHallDto exhibitionHallDto = new ExhibitionHallDto();
        exhibitionHallDto.setId(entity.getId());
        exhibitionHallDto.setName(entity.getName());
        exhibitionHallDto.setArea(entity.getArea());
        exhibitionHallDto.setAddress(entity.getAddress());
        exhibitionHallDto.setPhone(entity.getPhone());
        exhibitionHallDto.setOwnerId(entity.getOwnerId());
        return exhibitionHallDto;
    }

    @Override
    public ExhibitionHallDto getById(Long id) {
        ExhibitionHall exhibitionHall = exhibitionHallDao.findById(id);
        if (exhibitionHall == null) {
            throw new RuntimeException("No exhibition hall with id: " + id);
        }
        return toExhibitionHallDto(exhibitionHall);
    }

    @Override
    public List<ExhibitionHallDto> getAll() {
        return exhibitionHallDao.findAll()
                .stream()
                .map(this::toExhibitionHallDto)
                .toList();
    }

    @Override
    public ExhibitionHallDto update(ExhibitionHallDto dto) {
        ExhibitionHall exhibitionHall = toExhibitionHallEntity(dto);
        exhibitionHall.setId(dto.getId());
        ExhibitionHall exhibitionHallCreated = exhibitionHallDao.update(exhibitionHall);
        return toExhibitionHallDto(exhibitionHallCreated);
    }

    @Override
    public void delete(Long id) {
        ExhibitionHall exhibitionHall = exhibitionHallDao.findById(id);
        if (exhibitionHall == null) {
            throw new RuntimeException("Exhibition hall with id: " + id + " not found");
        }
        exhibitionHallDao.delete(id);
    }
}
