package com.ss.lms.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.lms.models.Author;

public class AuthorController extends Controller<Author> {

    public AuthorController() {
        super();
        tableName = "tbl_author";
    }

    @Override
    public void add(Author author) throws SQLException {
        add(new Object[] {
            author.getID(),
            author.getName()
        });
    }

    @Override
    public void delete(Author author) throws SQLException {
        delete(new String[] {
            "authorId"
        }, new Object[] {
            author.getID()
        });
    }

    @Override
    protected Author extractModel(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setID(rs.getInt("authorId"));
        author.setName(rs.getString("authorName"));
        return author;
    }
    
    public Author find(int id) throws SQLException {
        return read(new String[] {
            "authorId"
        }, new Object[] {
            id
        });
    }
    
    public void updateId(Author author, int id) throws SQLException {
        update(new String[] {
            "authorId"
        }, new String[] {
            "authorName"
        }, new Object[] {
            id,
            author.getID()
        });
    }

    public void updateName(Author author, String name) throws SQLException {
        update(new String[] {
            "authorName"
        }, new String[] {
            "authorId"
        }, new Object[] {
            name,
            author.getID(),
        });
    }
}
