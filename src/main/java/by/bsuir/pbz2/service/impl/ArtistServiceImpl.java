package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.dao.ArtistDao;
import by.bsuir.pbz2.data.entity.Artist;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.dto.ArtistDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistDao artistDao;

    @Override
    public ArtistDto create(ArtistDto dto) {
        log.info("Creating new artist with name: {}", dto.getName());
        Artist artist = toArtistEntity(dto);
        Artist artistCreated = artistDao.create(artist);
        log.info("Created artist with ID: {}", artistCreated.getId());
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
        log.info("Fetching artist with ID: {}", id);
        Artist artistEntity = artistDao.findById(id);
        if (artistEntity == null) {
            log.error("No artist found with ID: {}", id);
            throw new RuntimeException("No artist with id: " + id);
        }
        log.info("Fetched artist with ID: {}", id);
        return toArtistDto(artistEntity);
    }

    @Override
    public List<ArtistDto> getAll() {
        log.info("Fetching all artists");
        List<ArtistDto> artists = artistDao.findAll()
                .stream()
                .map(this::toArtistDto)
                .toList();
        log.info("Fetched {} artists", artists.size());
        return artists;
    }

    @Override
    public ArtistDto update(ArtistDto dto) {
        log.info("Updating artist with ID: {}", dto.getId());
        Artist artist = toArtistEntity(dto);
        artist.setId(dto.getId());
        Artist artistUpdated = artistDao.update(artist);
        log.info("Updated artist with ID: {}", artistUpdated.getId());
        return toArtistDto(artistUpdated);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting artist with ID: {}", id);
        Artist artist = artistDao.findById(id);
        if (artist == null) {
            log.error("Artist with ID: {} not found", id);
            throw new RuntimeException("Artist with id: " + id + " not found");
        }
        artistDao.delete(id);
        log.info("Deleted artist with ID: {}", id);
    }
}
