package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.ArtworkDao;
import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.service.ArtworkService;
import by.bsuir.pbz2.service.dto.ArtworkDto;

import java.util.List;

public class ArtworkServiceImpl implements ArtworkService {
    private final ArtworkDao artworkDao;

    public ArtworkServiceImpl(ArtworkDao artworkDao) {
        this.artworkDao = artworkDao;
    }

    @Override
    public ArtworkDto create(ArtworkDto dto) {
        Artwork artwork = toArtworkEntity(dto);
        Artwork artworkCreated = artworkDao.create(artwork);
        return toArtworkDto(artworkCreated);
    }

    private Artwork toArtworkEntity(ArtworkDto dto) {
        Artwork artwork = new Artwork();
        artwork.setTitle(dto.getTitle());
        artwork.setExecutionType(dto.getExecutionType());
        artwork.setCreationDate(dto.getCreationDate());
        artwork.setHeight(dto.getHeight());
        artwork.setWidth(dto.getWidth());
        artwork.setVolume(dto.getVolume());
        artwork.setArtistId(dto.getArtistId());
        return artwork;
    }

    private ArtworkDto toArtworkDto(Artwork entity) {
        ArtworkDto artworkDto = new ArtworkDto();
        artworkDto.setId(entity.getId());
        artworkDto.setTitle(entity.getTitle());
        artworkDto.setExecutionType(entity.getExecutionType());
        artworkDto.setCreationDate(entity.getCreationDate());
        artworkDto.setHeight(entity.getHeight());
        artworkDto.setWidth(entity.getWidth());
        artworkDto.setVolume(entity.getVolume());
        artworkDto.setArtistId(entity.getArtistId());
        return artworkDto;
    }

    @Override
    public ArtworkDto getById(Long id) {
        Artwork artwork = artworkDao.findById(id);
        if (artwork == null) {
            throw new RuntimeException("No artwork with id: " + id);
        }
        return toArtworkDto(artwork);
    }

    @Override
    public List<ArtworkDto> getAll() {
        return artworkDao.findAll()
                .stream()
                .map(this::toArtworkDto)
                .toList();
    }

    @Override
    public ArtworkDto update(ArtworkDto dto) {
        Artwork artwork = toArtworkEntity(dto);
        artwork.setId(dto.getId());
        Artwork artworkCreated = artworkDao.update(artwork);
        return toArtworkDto(artworkCreated);
    }

    @Override
    public void delete(Long id) {
        Artwork artwork = artworkDao.findById(id);
        if (artwork == null) {
            throw new RuntimeException("Artwork with id: " + id + " not found");
        }
        artworkDao.delete(id);
    }
}
