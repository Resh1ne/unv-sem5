package by.bsuir.pbz2.controller;

import by.bsuir.pbz2.controller.impl.ErrorCommand;
import by.bsuir.pbz2.controller.impl.artist.ArtistCommand;
import by.bsuir.pbz2.controller.impl.artist.ArtistsCommand;
import by.bsuir.pbz2.controller.impl.artist.CreateArtistCommand;
import by.bsuir.pbz2.controller.impl.artist.CreateArtistFormCommand;
import by.bsuir.pbz2.controller.impl.artist.DeleteArtistCommand;
import by.bsuir.pbz2.controller.impl.artist.EditArtistCommand;
import by.bsuir.pbz2.controller.impl.artist.EditArtistFormCommand;
import by.bsuir.pbz2.controller.impl.artwork.ArtworkCommand;
import by.bsuir.pbz2.controller.impl.artwork.ArtworksCommand;
import by.bsuir.pbz2.controller.impl.artwork.CreateArtworkCommand;
import by.bsuir.pbz2.controller.impl.artwork.CreateArtworkFormCommand;
import by.bsuir.pbz2.controller.impl.artwork.DeleteArtworkCommand;
import by.bsuir.pbz2.controller.impl.artwork.EditArtworkCommand;
import by.bsuir.pbz2.controller.impl.artwork.EditArtworkFormCommand;
import by.bsuir.pbz2.controller.impl.exhibition.CreateExhibitionCommand;
import by.bsuir.pbz2.controller.impl.exhibition.CreateExhibitionFormCommand;
import by.bsuir.pbz2.controller.impl.exhibition.CurExhibitionsCommand;
import by.bsuir.pbz2.controller.impl.exhibition.DeleteExhibitionCommand;
import by.bsuir.pbz2.controller.impl.exhibition.EditExhibitionCommand;
import by.bsuir.pbz2.controller.impl.exhibition.EditExhibitionFormCommand;
import by.bsuir.pbz2.controller.impl.exhibition.ExhibitionCommand;
import by.bsuir.pbz2.controller.impl.exhibition.ExhibitionParAndArtCommand;
import by.bsuir.pbz2.controller.impl.exhibition.ExhibitionsCommand;
import by.bsuir.pbz2.controller.impl.exhibition_hall.CreateExhibitionHallCommand;
import by.bsuir.pbz2.controller.impl.exhibition_hall.CreateExhibitionHallFormCommand;
import by.bsuir.pbz2.controller.impl.exhibition_hall.DeleteExhibitionHallCommand;
import by.bsuir.pbz2.controller.impl.exhibition_hall.EditExhibitionHallCommand;
import by.bsuir.pbz2.controller.impl.exhibition_hall.EditExhibitionHallFormCommand;
import by.bsuir.pbz2.controller.impl.exhibition_hall.ExhibitionHallCommand;
import by.bsuir.pbz2.controller.impl.exhibition_hall.ExhibitionHallsCommand;
import by.bsuir.pbz2.controller.impl.owner.CreateOwnerCommand;
import by.bsuir.pbz2.controller.impl.owner.CreateOwnerFormCommand;
import by.bsuir.pbz2.controller.impl.owner.DeleteOwnerCommand;
import by.bsuir.pbz2.controller.impl.owner.EditOwnerCommand;
import by.bsuir.pbz2.controller.impl.owner.EditOwnerFormCommand;
import by.bsuir.pbz2.controller.impl.owner.OwnerCommand;
import by.bsuir.pbz2.controller.impl.owner.OwnersCommand;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.connection.impl.DataSourceImpl;
import by.bsuir.pbz2.data.dao.ArtistDao;
import by.bsuir.pbz2.data.dao.ArtworkDao;
import by.bsuir.pbz2.data.dao.ExhibitionDao;
import by.bsuir.pbz2.data.dao.ExhibitionHallDao;
import by.bsuir.pbz2.data.dao.OwnerDao;
import by.bsuir.pbz2.data.dao.impl.ArtistDaoImpl;
import by.bsuir.pbz2.data.dao.impl.ArtworkDaoImpl;
import by.bsuir.pbz2.data.dao.impl.ExhibitionDaoImpl;
import by.bsuir.pbz2.data.dao.impl.ExhibitionHallDaoImpl;
import by.bsuir.pbz2.data.dao.impl.OwnerDaoImpl;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.ArtworkService;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.impl.ArtistServiceImpl;
import by.bsuir.pbz2.service.impl.ArtworkServiceImpl;
import by.bsuir.pbz2.service.impl.ExhibitionHallServiceImpl;
import by.bsuir.pbz2.service.impl.ExhibitionServiceImpl;
import by.bsuir.pbz2.service.impl.OwnerServiceImpl;
import by.bsuir.pbz2.util.PropertiesManager;
import by.bsuir.pbz2.util.impl.PropertiesManagerImpl;
import lombok.extern.log4j.Log4j2;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class CommandFactory implements Closeable {
    public static final CommandFactory INSTANCE = new CommandFactory();
    private final Map<String, Command> controllers;
    private final List<Closeable> closeables;

    private CommandFactory() {
        DataSource dataSource = getDataSource();
        closeables = new ArrayList<>();
        closeables.add(dataSource);

        ExhibitionHallDao exhibitionHallDao = new ExhibitionHallDaoImpl(dataSource);
        ExhibitionHallService exhibitionHallService = new ExhibitionHallServiceImpl(exhibitionHallDao);
        ExhibitionDao exhibitionDao = new ExhibitionDaoImpl(dataSource);
        ExhibitionService exhibitionService = new ExhibitionServiceImpl(exhibitionDao);
        OwnerDao ownerDao = new OwnerDaoImpl(dataSource);
        OwnerService ownerService = new OwnerServiceImpl(ownerDao);
        ArtistDao artistDao = new ArtistDaoImpl(dataSource);
        ArtistService artistService = new ArtistServiceImpl(artistDao);
        ArtworkDao artworkDao = new ArtworkDaoImpl(dataSource);
        ArtworkService artworkService = new ArtworkServiceImpl(artworkDao);

        controllers = new HashMap<>();
        controllers.put("error", new ErrorCommand());
        controllers.put("exhibition_halls", new ExhibitionHallsCommand(exhibitionHallService));
        controllers.put("exhibition_hall", new ExhibitionHallCommand(exhibitionHallService));
        controllers.put("exhibitions", new ExhibitionsCommand(exhibitionService));
        controllers.put("exhibition", new ExhibitionCommand(exhibitionService));
        controllers.put("exhibition_par_and_art", new ExhibitionParAndArtCommand(exhibitionService));
        controllers.put("cur_exhibitions", new CurExhibitionsCommand(exhibitionService));
        controllers.put("owners", new OwnersCommand(ownerService));
        controllers.put("owner", new OwnerCommand(ownerService));
        controllers.put("artists", new ArtistsCommand(artistService));
        controllers.put("artist", new ArtistCommand(artistService));
        controllers.put("artworks", new ArtworksCommand(artworkService));
        controllers.put("artwork", new ArtworkCommand(artworkService));
        controllers.put("create_exhibition_hall_form", new CreateExhibitionHallFormCommand(ownerService));
        controllers.put("create_exhibition_hall", new CreateExhibitionHallCommand(exhibitionHallService, ownerService));
        controllers.put("edit_exhibition_hall_form", new EditExhibitionHallFormCommand(exhibitionHallService, ownerService));
        controllers.put("edit_exhibition_hall", new EditExhibitionHallCommand(exhibitionHallService, ownerService));
        controllers.put("delete_exhibition_hall", new DeleteExhibitionHallCommand(exhibitionHallService));
        controllers.put("create_exhibition_form", new CreateExhibitionFormCommand(exhibitionHallService));
        controllers.put("create_exhibition", new CreateExhibitionCommand(exhibitionService, exhibitionHallService));
        controllers.put("delete_exhibition", new DeleteExhibitionCommand(exhibitionService));
        controllers.put("edit_exhibition_form", new EditExhibitionFormCommand(exhibitionService, exhibitionHallService));
        controllers.put("edit_exhibition", new EditExhibitionCommand(exhibitionService, exhibitionHallService));
        controllers.put("create_owner_form", new CreateOwnerFormCommand());
        controllers.put("create_owner", new CreateOwnerCommand(ownerService));
        controllers.put("edit_owner_form", new EditOwnerFormCommand(ownerService));
        controllers.put("edit_owner", new EditOwnerCommand(ownerService));
        controllers.put("delete_owner", new DeleteOwnerCommand(ownerService));
        controllers.put("create_artist_form", new CreateArtistFormCommand());
        controllers.put("create_artist", new CreateArtistCommand(artistService));
        controllers.put("edit_artist_form", new EditArtistFormCommand(artistService));
        controllers.put("edit_artist", new EditArtistCommand(artistService));
        controllers.put("delete_artist", new DeleteArtistCommand(artistService));
        controllers.put("create_artwork", new CreateArtworkCommand(artistService, artworkService));
        controllers.put("create_artwork_form", new CreateArtworkFormCommand(artistService));
        controllers.put("delete_artwork", new DeleteArtworkCommand(artworkService));
        controllers.put("edit_artwork", new EditArtworkCommand(artistService, artworkService));
        controllers.put("edit_artwork_form", new EditArtworkFormCommand(artistService, artworkService));
    }

    private static DataSource getDataSource() {
        PropertiesManager propertiesManager = new PropertiesManagerImpl("/app.properties");
        String profile = propertiesManager.getKey("my.app.profile");
        String url = propertiesManager.getKey("my.app.db." + profile + ".url");
        String user = propertiesManager.getKey("my.app.db." + profile + ".user");
        String password = propertiesManager.getKey("my.app.db." + profile + ".password");
        String driver = propertiesManager.getKey("my.app.db." + profile + ".driver");
        return new DataSourceImpl(password, user, url, driver);
    }

    public Command get(String command) {
        Command controller = controllers.get(command);
        if (controller == null) {
            return controllers.get("error");
        }
        return controller;
    }

    @Override
    public void close() {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
