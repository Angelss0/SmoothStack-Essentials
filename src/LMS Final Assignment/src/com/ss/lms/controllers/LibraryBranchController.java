package com.ss.lms.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.lms.models.LibraryBranch;

public class LibraryBranchController extends Controller<LibraryBranch> {

    public LibraryBranchController() {
        super();
        tableName = "tbl_library_branch";
    }

    @Override
    public void add(LibraryBranch libraryBranch) throws SQLException {
        add(new Object[] {
            libraryBranch.getID(),
            libraryBranch.getName(),
            libraryBranch.getAddress()
        });
    }

    @Override
    public void delete(LibraryBranch libraryBranch) throws SQLException {
        delete(new String[] {
            "branchId"
        }, new Object[] {
            libraryBranch.getID()
        });
    }

    @Override
    protected LibraryBranch extractModel(ResultSet rs) throws SQLException {
        LibraryBranch libraryBranch = new LibraryBranch();
        libraryBranch.setID(rs.getInt("branchId"));
        libraryBranch.setName(rs.getString("branchName"));
        libraryBranch.setAddress(rs.getString("branchAddress"));

        return libraryBranch;
    }

    public LibraryBranch find(int branchId) throws SQLException {
        return read(new String[] {
            "branchId"
        }, new Object[] {
            branchId
        });
    }

    public void updateId(LibraryBranch branch, int id) throws SQLException {
        update(new String[] {
            "branchId"
        }, new String[] {
            "branchId"
        }, new Object[] {
            id,
            branch.getID()
        });
    }

    public void updateName(LibraryBranch branch, String name) throws SQLException {
        update(new String[] {
            "branchName"
        }, new String[] {
            "branchId"
        }, new Object[] {
            name,
            branch.getID()
        });
    }

    public void updateAddress(LibraryBranch branch, String address) throws SQLException {
        update(new String[] {
            "branchAddress"
        }, new String[] {
            "branchId"
        }, new Object[] {
            address,
            branch.getID()
        });
    }
}
