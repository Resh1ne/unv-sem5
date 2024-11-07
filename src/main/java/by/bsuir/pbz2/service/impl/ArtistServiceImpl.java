package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.ArtistDao;
import by.bsuir.pbz2.data.entity.Artist;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.dto.ArtistDto;

import java.util.List;

public class ArtistServiceImpl implements ArtistService {
    private final ArtistDao artistDao;

    public ArtistServiceImpl(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    @Override
    public ArtistDto create(ArtistDto dto) {
        Artist artist = toArtistEntity(dto);
        Artist artistCreated = artistDao.create(artist);
        return toArtistDto(artistCreated);
    }

    private Artist toArtistEntity(ArtistDto dto) {
        Artist artistEntity = new Artist();
        artistEntity.setName(dto.getName());
        artistEntity.setBirthPlace(dto.getBirthPlace());
        artistEntity.setBirthDate(dto.getBirthDate());
        artistEntity.setBiography(dto.getBiography());
        artistEntity.setEducation(dto.getEducation());
        return artistEntity;
    }

    private ArtistDto toArtistDto(Artist entity) {
        ArtistDto artistDto = new ArtistDto();
        artistDto.setId(entity.getId());
        artistDto.setName(entity.getName());
        artistDto.setBirthPlace(entity.getBirthPlace());
        artistDto.setBirthDate(entity.getBirthDate());
        artistDto.setBiography(entity.getBiography());
        artistDto.setEducation(entity.getEducation());
        return artistDto;
    }

    @Override
    public ArtistDto getById(Long id) {
        Artist artistEntity = artistDao.findById(id);
        if (artistEntity == null) {
            throw new RuntimeException("No artist with id: " + id);
        }
        return toArtistDto(artistEntity);
    }

    @Override
    public List<ArtistDto> getAll() {
        return artistDao.findAll()
                .stream()
                .map(this::toArtistDto)
                .toList();
    }

    @Override
    public ArtistDto update(ArtistDto dto) {
        Artist artist = toArtistEntity(dto);
        artist.setId(dto.getId());
        Artist artistCreated = artistDao.update(artist);
        return toArtistDto(artistCreated);
    }

    @Override
    public void delete(Long id) {
        Artist artist = artistDao.findById(id);
        if (artist == null) {
            throw new RuntimeException("Artist with id: " + id + " not found");
        }
        artistDao.delete(id);
    }
}
