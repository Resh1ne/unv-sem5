package by.bsuir.pbz2.data.dao;

import by.bsuir.pbz2.data.entity.CurrentExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;
import by.bsuir.pbz2.data.entity.ExhibitionParticipantsAndArtworks;

import java.util.List;

public interface ExhibitionDao extends CrudDao<Long, Exhibition> {
    List<ExhibitionParticipantsAndArtworks> findParticipantsArtworksByExhibitionId(Long id);
    List<CurrentExhibition> findCurrentExhibition();
}
