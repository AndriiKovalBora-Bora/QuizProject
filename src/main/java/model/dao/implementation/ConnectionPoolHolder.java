/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

/**
 * Keeps dataSource
 */
public class ConnectionPoolHolder {

    /**
     * DataSource
     */
    private static volatile DataSource dataSource;

    /**
     * Gets DataSource
     *
     * @return DataSource
     */
    public static DataSource getDataSource() {
        Logger.getLogger(ConnectionPoolHolder.class).info("ConnectionPoolHolder class, getDataSource method");
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl("jdbc:mysql://localhost:3306/quiz?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
                            "&useLegacyDatetimeCode=false&serverTimezone=UTC");
                    ds.setUsername("root");
                    ds.setPassword("root");
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }
}
