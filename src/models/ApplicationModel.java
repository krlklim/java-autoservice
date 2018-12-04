package models;

import config.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import helpers.ApplicationContext;

abstract class ApplicationModel {
    private static Connection fetchConnection() {
        return ApplicationContext.getInstance().getConnection();
    }

    static void insertQuery(String table, List<String> columns, List values) {
        try {
            PreparedStatement preparedStatement = fetchConnection().prepareStatement(insertStatement(table, columns, values));
            setStatementValues(preparedStatement, values).executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    static ResultSet selectQuery(String table, List<String> columns, List values) {
        try {
            PreparedStatement preparedStatement = fetchConnection().prepareStatement(selectStatement(table, columns));
            return setStatementValues(preparedStatement, values).executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    static ResultSet selectAllQuery(String table) {
        try {
            return fetchConnection().prepareStatement("SELECT * FROM " + table).executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    static void deleteQuery(String table, List<String> columns, List values) {
        try {
            PreparedStatement preparedStatement = fetchConnection().prepareStatement(deleteStatement(table, columns));
            setStatementValues(preparedStatement, values).executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    static void deleteById(String table, int id) {
        deleteQuery(table, Arrays.asList("id"), Arrays.asList(id));
    }

    private static String deleteStatement(String table, List<String> columns) {
        return "DELETE FROM " + table + " WHERE " + columnsString(queryColumns(columns), " AND ");
    }

    private static String selectStatement(String table, List<String> columns) {
        return "SELECT * FROM " + table + " WHERE " + columnsString(queryColumns(columns), " AND ");
    }

    private static List<String> queryColumns(List<String> columns) {
        return columns.stream().map(column -> column + " = ?").collect(Collectors.toList());
    }

    private static PreparedStatement setStatementValues(PreparedStatement statement, List values) {
        try {
            for(int i = 0; i < values.size(); i++) {
                statement.setString(i + 1, values.get(i).toString());
            }
            return statement;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return statement;
        }
    }

    private static String insertStatement(String table, List<String> columns, List values) {
        return "INSERT INTO " + table + " " + columnsString(columns, ",") + valuesString(values.size());
    }

    private static String columnsString(List<String> columns, String delimiter) {
        return "(" + String.join(delimiter, columns) + ")";
    }

    private static String valuesString(int valuesLength) {
        List<String> valuesMarks = new ArrayList<>(Collections.nCopies(valuesLength, "?"));
        return "VALUES" + "(" +
                valuesMarks.stream().map(Object::toString).collect(Collectors.joining(", ")) +
                ")";
    }
}
