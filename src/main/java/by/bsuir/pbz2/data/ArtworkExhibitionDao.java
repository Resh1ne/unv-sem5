package by.bsuir.pbz2.data;

import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.ArtworkExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;

import java.util.List;

public interface ArtworkExhibitionDao {
    ArtworkExhibition create(ArtworkExhibition artworkExhibition);

    ArtworkExhibition findByArtworkId(Artwork artworkId);

    ArtworkExhibition findByExhibitionId(Exhibition exhibitionId);

    ArtworkExhibition update(ArtworkExhibition artworkExhibition);

    List<ArtworkExhibition> findAll();

    boolean delete(ArtworkExhibition artworkExhibition);
}
