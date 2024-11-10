package by.bsuir.pbz2.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;

@WebListener
@Log4j2
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context init event");
        CommandFactory commandFactory = CommandFactory.INSTANCE;
        log.info("Created {}", commandFactory.getClass());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Context destroy event");
        CommandFactory.INSTANCE.close();
    }
}
