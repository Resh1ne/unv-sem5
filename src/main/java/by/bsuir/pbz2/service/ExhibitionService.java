package by.bsuir.pbz2.service;

import by.bsuir.pbz2.service.dto.CurrentExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionParticipantsAndArtworksDto;

import java.util.List;

public interface ExhibitionService extends CrudService<Long, ExhibitionDto> {
    List<ExhibitionParticipantsAndArtworksDto> getParticipantsArtworksByExhibitionId(Long id);
    List<CurrentExhibitionDto> getCurrentExhibition();
}
