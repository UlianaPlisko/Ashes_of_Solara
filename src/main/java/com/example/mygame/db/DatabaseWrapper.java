package com.example.mygame.db;

import com.example.mygame.exceptions.DataAccessException;
import lombok.Getter;
import lombok.Value;

import java.sql.*;


/**
 * A singleton wrapper class for managing database connections and executing SQL queries.
 * Provides methods for executing updates, queries, and managing transactions.
 */
@Getter
public class DatabaseWrapper {
    private static final String URL = "jdbc:postgresql://localhost:5432/MyGame";
    private static final String USER = "postgres";
    private static final String PASSWORD = "26160317";

    private static DatabaseWrapper instance;
    private Connection connection;

    /**
     * Constructs a new DatabaseWrapper with the specified database connection details.
     *
     * @throws DataAccessException if the database connection fails
     */
    private DatabaseWrapper() {
        try {
            Class.forName("org.postgresql.Driver"); // Ensure driver is loaded
            System.out.println("PostgreSQL JDBC Driver loaded");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new DataAccessException("PostgreSQL JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to connect to database: " + e.getMessage(), e);
        }
    }

    /**
     * Returns the singleton instance of DatabaseWrapper. Creates a new instance if it doesn't exist.
     *
     * @return the singleton DatabaseWrapper instance
     * @throws DataAccessException if instance creation fails
     */
    public static synchronized DatabaseWrapper getInstance() {
        if (instance == null) {
            try {
                instance = new DatabaseWrapper();
            } catch (Exception e) {
                throw new DataAccessException("Failed to create DatabaseWrapper instance", e);
            }
        }
        return instance;
    }

    /**
     * Executes an SQL update statement with the given parameters.
     *
     * @param sql    the SQL update statement
     * @param params the parameters to bind to the prepared statement
     * @return the number of rows affected
     * @throws DataAccessException if the query execution fails
     */
    public int executeUpdate(String sql, Object... params) {
        try (PreparedStatement stmt = prepareStatement(sql, params)) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Query execution failed: " + sql, e);
        }
    }

    /**
     * Executes an SQL query with the given parameters and returns the result set.
     * The caller is responsible for closing the returned ResultSet.
     *
     * @param sql    the SQL query
     * @param params the parameters to bind to the prepared statement
     * @return the ResultSet containing the query results
     * @throws DataAccessException if the query execution fails
     */
    public ResultSet executeQuery(String sql, Object... params) {
        try {
            PreparedStatement stmt = prepareStatement(sql, params);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new DataAccessException("Query execution failed: " + sql, e);
        }
    }

    /**
     * Prepares a SQL statement with the given parameters.
     *
     * @param sql    the SQL statement
     * @param params the parameters to bind
     * @return the prepared statement
     * @throws SQLException if statement preparation fails
     */
    private PreparedStatement prepareStatement(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt;
    }

    /**
     * method for starting transaction in manual transaction management invoked from
     * AOP class before any database update is done
     */
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * method for commit transaction in manual transaction management invoked from
     * AOP class after all database update is done
     */
    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * method for rollback transaction in manual transaction management invoked from
     * AOP class if there is any exception while database update is done
     */
    public void rollBackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    /**
     * Closes the database connection and resets the singleton instance.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                instance = null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to close database connection", e);
        }
    }

    /**
     * Provide reconnection when we lose internet connection.
     */
    public void reconnect() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to connect to database", e);
        }
    }
}
