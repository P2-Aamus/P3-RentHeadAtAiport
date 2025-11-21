package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public final class DatabaseConfig {

    private static final HikariDataSource ds;

    static {
        try {
            Properties props = new Properties();
            try (InputStream is = DatabaseConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (is == null) throw new RuntimeException("config.properties not found");
                props.load(is);
            }

            HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(props.getProperty("db.url"));
            cfg.setUsername(props.getProperty("db.user"));
            cfg.setPassword(props.getProperty("db.password"));

            String maxPool = props.getProperty("db.maxPoolSize", "10");
            cfg.setMaximumPoolSize(Integer.parseInt(maxPool));

            ds = new HikariDataSource(cfg);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to initialize DataSource: " + e.getMessage());
        }
    }

    private DatabaseConfig() {}

    public static DataSource getDataSource() {
        return ds;
    }
}
