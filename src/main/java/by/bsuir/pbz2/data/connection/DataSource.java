package by.bsuir.pbz2.data.connection;

import java.io.Closeable;
import java.sql.Connection;

public interface DataSource extends Closeable {
    Connection getConnection();
}
