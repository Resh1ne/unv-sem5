package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.dao.ExhibitionDao;
import by.bsuir.pbz2.data.entity.CurrentExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;
import by.bsuir.pbz2.data.entity.ExhibitionHall;
import by.bsuir.pbz2.data.entity.ExhibitionParticipantsAndArtworks;
import by.bsuir.pbz2.data.entity.Owner;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.CurrentExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import by.bsuir.pbz2.service.dto.ExhibitionParticipantsAndArtworksDto;
import by.bsuir.pbz2.service.dto.OwnerDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExhibitionServiceImpl implements ExhibitionService {
    private final ExhibitionDao exhibitionDao;

    @Override
    public List<CurrentExhibitionDto> getCurrentExhibition() {
        return exhibitionDao.findCurrentExhibition()
                .stream()
                .map(this::toCurrentExhibitionDto)
                .toList();
    }

    @Override
    public List<ExhibitionParticipantsAndArtworksDto> getParticipantsArtworksByExhibitionId(Long id) {
        return exhibitionDao.findParticipantsArtworksByExhibitionId(id)
                .stream()
                .map(this::toExhibitionParticipantsAndArtworksDto)
                .toList();
    }

    private CurrentExhibitionDto toCurrentExhibitionDto(CurrentExhibition entity) {
        CurrentExhibitionDto exhibitionDto = new CurrentExhibitionDto();
        exhibitionDto.setExhibitionName(entity.getExhibitionName());
        exhibitionDto.setHallAddress(entity.getHallAddress());
        return exhibitionDto;
    }

    private ExhibitionParticipantsAndArtworksDto toExhibitionParticipantsAndArtworksDto(ExhibitionParticipantsAndArtworks entity) {
        ExhibitionParticipantsAndArtworksDto exhibitionDto = new ExhibitionParticipantsAndArtworksDto();
        exhibitionDto.setExhibitionName(entity.getExhibitionName());
        exhibitionDto.setExhibitionStartDate(entity.getExhibitionStartDate());
        exhibitionDto.setExhibitionEndDate(entity.getExhibitionEndDate());
        exhibitionDto.setArtworkTitle(entity.getArtworkTitle());
        exhibitionDto.setExecutionType(entity.getExecutionType());
        exhibitionDto.setArtistName(entity.getArtistName());
        exhibitionDto.setArtistAge(entity.getArtistAge());
        exhibitionDto.setCreationDate(entity.getCreationDate());
        return exhibitionDto;
    }

    private Exhibition toExhibitionEntity(ExhibitionDto dto) {
        Exhibition exhibition = new Exhibition();
        exhibition.setName(dto.getName());
        exhibition.setHallId(toExhibitionHallEntity(dto.getHallId()));
        exhibition.setType(dto.getType());
        exhibition.setStartDate(dto.getStartDate());
        exhibition.setEndDate(dto.getEndDate());
        return exhibition;
    }

    private ExhibitionDto toExhibitionDto(Exhibition entity) {
        ExhibitionDto exhibitionDto = new ExhibitionDto();
        exhibitionDto.setId(entity.getId());
        exhibitionDto.setName(entity.getName());
        exhibitionDto.setHallId(toExhibitionHallDto(entity.getHallId()));
        exhibitionDto.setType(entity.getType());
        exhibitionDto.setStartDate(entity.getStartDate());
        exhibitionDto.setEndDate(entity.getEndDate());
        return exhibitionDto;
    }

    private ExhibitionHall toExhibitionHallEntity(ExhibitionHallDto dto) {
        ExhibitionHall exhibitionHall = new ExhibitionHall();
        exhibitionHall.setId(dto.getId());
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
    public ExhibitionDto create(ExhibitionDto dto) {
        Exhibition exhibition = toExhibitionEntity(dto);
        Exhibition exhibitionCreated = exhibitionDao.create(exhibition);
        return toExhibitionDto(exhibitionCreated);
    }

    @Override
    public ExhibitionDto getById(Long id) {
        Exhibition exhibition = exhibitionDao.findById(id);
        if (exhibition == null) {
            throw new RuntimeException("No exhibition with id: " + id);
        }
        return toExhibitionDto(exhibition);
    }

    @Override
    public List<ExhibitionDto> getAll() {
        return exhibitionDao.findAll()
                .stream()
                .map(this::toExhibitionDto)
                .toList();
    }

    @Override
    public ExhibitionDto update(ExhibitionDto dto) {
        Exhibition exhibition = toExhibitionEntity(dto);
        exhibition.setId(dto.getId());
        Exhibition exhibitionCreated = exhibitionDao.update(exhibition);
        return toExhibitionDto(exhibitionCreated);
    }

    @Override
    public void delete(Long id) {
        Exhibition exhibition = exhibitionDao.findById(id);
        if (exhibition == null) {
            throw new RuntimeException("Exhibition with id: " + id + " not found");
        }
        exhibitionDao.delete(id);
    }
}
