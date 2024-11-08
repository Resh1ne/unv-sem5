package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.dao.ExhibitionDao;
import by.bsuir.pbz2.data.entity.Exhibition;
import by.bsuir.pbz2.data.entity.ExhibitionParticipantsAndArtworks;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionParticipantsAndArtworksDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExhibitionServiceImpl implements ExhibitionService {
    private final ExhibitionDao exhibitionDao;

    @Override
    public List<ExhibitionParticipantsAndArtworksDto> getParticipantsArtworksByExhibitionId(Long id) {
        return exhibitionDao.findParticipantsArtworksByExhibitionId(id)
                .stream()
                .map(this::toExhibitionParticipantsAndArtworksDto)
                .toList();
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
        exhibition.setHallId(dto.getHallId());
        exhibition.setType(dto.getType());
        exhibition.setStartDate(dto.getStartDate());
        exhibition.setEndDate(dto.getEndDate());
        return exhibition;
    }

    private ExhibitionDto toExhibitionDto(Exhibition entity) {
        ExhibitionDto exhibitionDto = new ExhibitionDto();
        exhibitionDto.setId(entity.getId());
        exhibitionDto.setName(entity.getName());
        exhibitionDto.setHallId(entity.getHallId());
        exhibitionDto.setType(entity.getType());
        exhibitionDto.setStartDate(entity.getStartDate());
        exhibitionDto.setEndDate(entity.getEndDate());
        return exhibitionDto;
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
