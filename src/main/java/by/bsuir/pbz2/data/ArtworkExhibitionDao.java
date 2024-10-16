package by.bsuir.pbz2.data;

import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.ArtworkExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;

import java.util.List;

public interface ArtworkExhibitionDao {
    boolean create(ArtworkExhibition artworkExhibition);

    ArtworkExhibition findByExhibitionArtworkId(Artwork artworkId, Exhibition exhibitionId);

    List<ArtworkExhibition> findAll();

    boolean delete(ArtworkExhibition artworkExhibition);
}
