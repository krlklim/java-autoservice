package models;

import config.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public abstract class ApplicationModel {
    private Connection connection;

    ApplicationModel() {
        try {
            this.connection = Database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract String getTable();

    void insertQuery(List<String> columns, List values) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement(columns, values));
            setStatementValues(preparedStatement, values).executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    ResultSet selectQuery(List<String> columns, List values) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement(columns));
            return setStatementValues(preparedStatement, values).executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    private String selectStatement(List<String> columns) {
        List<String> queryColumns = columns.stream().map(column -> column + " = ?").collect(Collectors.toList());
        return "SELECT * FROM " + getTable() + " WHERE " + columnsString(queryColumns, " AND ");
    }

    private PreparedStatement setStatementValues(PreparedStatement statement, List values) {
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

    private String insertStatement(List<String> columns, List values) {
        return "INSERT INTO " + getTable() + " " + columnsString(columns, ",") + valuesString(values.size());
    }

    private String columnsString(List<String> columns, String delimiter) {
        return "(" + String.join(delimiter, columns) + ")";
    }

    private String valuesString(int valuesLength) {
        List<String> valuesMarks = new ArrayList<>(Collections.nCopies(valuesLength, "?"));
        return "VALUES" + "(" +
                valuesMarks.stream().map(Object::toString).collect(Collectors.joining(", ")) +
                ")";
    }
}
