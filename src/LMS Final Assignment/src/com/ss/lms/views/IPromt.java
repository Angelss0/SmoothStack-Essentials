package com.ss.lms.views;

import java.sql.SQLException;

@FunctionalInterface
public interface IPromt<T> { public boolean invoke(T arg) throws SQLException; }