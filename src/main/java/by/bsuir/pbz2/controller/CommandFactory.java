package by.bsuir.pbz2.controller;

import by.bsuir.pbz2.controller.impl.ExhibitionHallsCommand;
import by.bsuir.pbz2.data.connection.DataSource;
import by.bsuir.pbz2.data.connection.impl.DataSourceImpl;
import by.bsuir.pbz2.data.dao.ExhibitionHallDao;
import by.bsuir.pbz2.data.dao.impl.ExhibitionHallDaoImpl;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.impl.ExhibitionHallServiceImpl;
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

        controllers = new HashMap<>();
        controllers.put("exhibition_halls", new ExhibitionHallsCommand(exhibitionHallService));
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
