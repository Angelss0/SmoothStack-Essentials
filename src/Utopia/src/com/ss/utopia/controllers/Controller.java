package com.ss.utopia.controllers;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Predicate;

import com.ss.utopia.util.ConnectionsManager;

public abstract class Controller<T> {

    public Controller() {}

    public Connection getConnection() throws SQLException {
        return ConnectionsManager.getConnection();
    }

    private void setPreparedStatementArgs(PreparedStatement preparedStatement, Object[] args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                preparedStatement.setNull(i+1, java.sql.Types.NULL);
            } else if (args[i].getClass() == Integer.class) {
                preparedStatement.setInt(i+1, (int)args[i]);
            } else if (args[i].getClass() == Float.class) {
                preparedStatement.setFloat(i+1, (Float)args[i]);
            } else if (args[i].getClass() == Boolean.class) {
                preparedStatement.setBoolean(i+1, (boolean)args[i]);
            } else if (args[i].getClass() == String.class) {
                preparedStatement.setString(i+1, (String)args[i]);
            } else if (args[i].getClass() == Date.class) {
                preparedStatement.setDate(i+1, (Date)args[i]);
            } else if (args[i].getClass() == Timestamp.class) {
                preparedStatement.setTimestamp(i+1, (Timestamp)args[i]);
            }
        }

        // System.out.println(preparedStatement.toString());
    }

    protected void add(Object[] args) throws SQLException {
        var stringJoiner = new StringJoiner(", ");

        for (int i = 0; i < args.length; i++)
            stringJoiner.add("?");

        var preparedStatement = getConnection().prepareStatement("INSERT INTO " + getTableName() +" VALUES (" + stringJoiner.toString() + ")");
        setPreparedStatementArgs(preparedStatement, args);

        preparedStatement.executeUpdate();
    }

    protected void update(String[] setArgs, String[] whereArgs, Object[] args) throws SQLException {
        if (setArgs.length == 0) { throw new SQLException(); }
        if (whereArgs.length == 0) { throw new SQLException(); }
        if (setArgs.length + whereArgs.length != args.length) { throw new SQLException(); }

        var setValues = new StringJoiner(", ");
        for (String arg : setArgs) { setValues.add(arg + "=?"); }

        var whereValues = new StringJoiner(" AND ");
        for (String arg : whereArgs) { whereValues.add(arg + "=?"); }

        var preparedStatement = getConnection().prepareStatement("UPDATE " + getTableName() + " SET " + setValues.toString() + " WHERE " + whereValues.toString());
        setPreparedStatementArgs(preparedStatement, args);
        preparedStatement.executeUpdate();
    }

    protected void delete(String[] whereArgs, Object[] args) throws SQLException {
        StringJoiner whereValues = new StringJoiner(" AND ");
        if (whereArgs.length == 0) { throw new SQLException(); }
        for (String arg : whereArgs) { whereValues.add(arg + "=?"); }

        PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM " + getTableName() + " WHERE " + whereValues.toString());
        setPreparedStatementArgs(preparedStatement, args);
        preparedStatement.executeUpdate();
    }

    protected T read(String[] whereArgs, Object[] args) throws SQLException {
        StringJoiner whereValues = new StringJoiner(" AND ");
        if (whereArgs.length == 0) { throw new SQLException(); }
        for (String arg : whereArgs) { whereValues.add(arg + "=?"); }

        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + whereValues.toString());
        setPreparedStatementArgs(preparedStatement, args);

        ResultSet resultSet = preparedStatement.executeQuery();
        T object = null;
        if (resultSet.next()) {
            object = extractModel(resultSet);
        }

        return object;
    }

    public List<T> readAll() throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM " + getTableName());

        ResultSet resultSet = preparedStatement.executeQuery();
        List<T> list = new ArrayList<>();
        while(resultSet.next()) {
            T object = extractModel(resultSet);
            list.add(object);
        }

        return list;
    }

    public void refreshFromList(List<T> tList, Predicate<T> filterType) throws SQLException {
        readAll().stream()
            .filter(filterType)
            .forEach((tObject) -> {
                try {
                    delete(tObject);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        tList.forEach((tObject) -> {
            try {
                add(tObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public int getMinFreeId(Function<T, Integer> getter) throws SQLException {
        var tList = readAll();
        // list.sort((b0, b1) -> b1.getBookID() - b0.getBookID());

        int i = 0;

        if (hasAutoincrement()) {
            resetAutoIncrement();
            i = 1;
        }

        for (T tObject : tList) {
            if (getter.apply(tObject) != i) { break; }
            i++;
        }
        return i;
    }

    public void resetAutoIncrement() throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement(
            "ALTER TABLE " + getTableName() + " AUTO_INCREMENT = ?"
        );
        preparedStatement.setInt(1, 0);

        System.out.println(preparedStatement.toString());

        preparedStatement.execute();
    }

    public abstract String getTableName();
    public abstract void add(T t) throws SQLException;
    public abstract void delete(T t) throws SQLException;

    protected abstract T extractModel(ResultSet rs) throws SQLException;

    protected abstract boolean hasAutoincrement();
}
