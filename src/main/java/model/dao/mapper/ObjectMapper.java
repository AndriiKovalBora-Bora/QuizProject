/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Provides generalized methods for extracting entities from ResultSet
 */
public interface ObjectMapper<T> {

    /**
     * Extracts data from ResultSet and creates entity
     *
     * @param rs        ResultSet to be processed
     * @param tableName Name of the table
     * @return Generalized entity
     * @throws SQLException if error was occurred
     */
    T extractFromResultSet(ResultSet rs, String tableName) throws SQLException;

    /**
     * Checks if entity is unique or not
     *
     * @param cache  Map of entity, which were extracted earlier
     * @param entity Current entity
     * @return Unique entity
     */
    T makeUnique(Map<Integer, T> cache, T entity);
}
