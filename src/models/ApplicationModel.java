package models;

import database.Handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public abstract class ApplicationModel {
    private Connection connection;

    ApplicationModel() {
        try {
            this.connection = Handler.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract String getTable();

    void insertQuery(List columns, List values) {
        String insertStatement =
                "INSERT INTO " + getTable() + " " +
                "(" + String.join(",", columns) + ")" +
                valuesString(values.size());

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
            for(int i = 0; i < values.size(); i++) {
                preparedStatement.setString(i + 1, values.get(i).toString());
            }
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    private String valuesString(int valuesLength) {
        List<String> valuesMarks = new ArrayList<>(Collections.nCopies(valuesLength, "?"));
        return "VALUES" + "(" +
                valuesMarks.stream().map(Object::toString).collect(Collectors.joining(", ")) +
                ")";
    }
}
