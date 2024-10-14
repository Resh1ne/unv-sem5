package by.bsuir.pbz2.data.connection.impl;

import by.bsuir.pbz2.data.connection.DataSource;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;

@Log4j2
public class DataSourceImpl implements DataSource {
    private CustomConnectionPool connectionPool;
    private final String password;
    private final String user;
    private final String url;
    private final String driver;
    @Setter
    private int poolSize = 4;

    public DataSourceImpl(String password, String user, String url, String driver) {
        this.password = password;
        this.user = user;
        this.url = url;
        this.driver = driver;
        connectionPool = new CustomConnectionPool(driver, url, user, password, poolSize);
        log.info("Connection pool initialized");
    }

    @Override
    public Connection getConnection() {
        if (connectionPool == null) {
            connectionPool = new CustomConnectionPool(driver, url, user, password, poolSize);
            log.info("Connection pool initialized");
        }
        return connectionPool.getConnection();
    }

    @Override
    public void close() {
        if (connectionPool != null) {
            connectionPool.destroyPool();
            connectionPool = null;
            log.info("Connection pool destroyed");
        }
    }
}
