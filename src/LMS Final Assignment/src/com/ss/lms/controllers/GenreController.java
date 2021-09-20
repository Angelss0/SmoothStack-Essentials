package com.ss.lms.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.lms.models.Genre;

public class GenreController extends Controller<Genre> {

    public GenreController() {
        super();
        tableName = "tbl_genre";
    }

    @Override
    public void add(Genre genre) throws SQLException {
        add(new Object[] {
            genre.getID(),
            genre.getName()
        });
    }

    @Override
    public void delete(Genre genre) throws SQLException {
        delete(new String[] {
            "genre_id"
        }, new Object[] {
            genre.getID()
        });
    }

    @Override
    protected Genre extractModel(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setID(rs.getInt("genre_id"));
        genre.setName(rs.getString("genre_name"));
        return genre;
    }

    public Genre find(int genre_id) throws SQLException {
        return read(new String[] {
            "genre_id"
        }, new Object[] {
            genre_id
        });
    }

    public void updateId(Genre genre, int id) throws SQLException {
        update(new String[] {
            "genre_id"
        }, new String[] {
            "genre_id"
        }, new Object[] {
            id,
            genre.getID()
        });
    }

    public void updateName(Genre genre, String name) throws SQLException {
        update(new String[] {
            "genre_name"
        }, new String[] {
            "genre_id"
        }, new Object[] {
            name,
            genre.getID()
        });
    }
}
