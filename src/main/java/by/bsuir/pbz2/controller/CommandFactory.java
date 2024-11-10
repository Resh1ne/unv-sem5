package by.bsuir.pbz2.controller;

import by.bsuir.pbz2.controller.impl.CurExhibitionsCommand;
import by.bsuir.pbz2.controller.impl.ErrorCommand;
import by.bsuir.pbz2.controller.impl.ExhibitionCommand;
import by.bsuir.pbz2.controller.impl.ExhibitionHallCommand;
import by.bsuir.pbz2.controller.impl.ExhibitionHallsCommand;
import by.bsuir.pbz2.controller.impl.ExhibitionParAndArtCommand;
import by.bsuir.pbz2.controller.impl.ExhibitionsCommand;
import by.bsuir.pbz2.controller.impl.OwnerCommand;
import by.bsuir.pbz2.controller.impl.OwnersCommand;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.connection.impl.DataSourceImpl;
import by.bsuir.pbz2.data.dao.ExhibitionDao;
import by.bsuir.pbz2.data.dao.ExhibitionHallDao;
import by.bsuir.pbz2.data.dao.OwnerDao;
import by.bsuir.pbz2.data.dao.impl.ExhibitionDaoImpl;
import by.bsuir.pbz2.data.dao.impl.ExhibitionHallDaoImpl;
import by.bsuir.pbz2.data.dao.impl.OwnerDaoImpl;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.OwnerService;
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
