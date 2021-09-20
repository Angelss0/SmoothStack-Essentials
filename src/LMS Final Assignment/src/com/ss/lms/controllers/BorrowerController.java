package com.ss.lms.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.lms.models.Borrower;

public class BorrowerController extends Controller<Borrower> {

    public BorrowerController() {
        super();
        tableName = "tbl_borrower";
    }

    @Override
    public void add(Borrower borrower) throws SQLException {
        add(new Object[] {
            borrower.getCardNo(),
            borrower.getName(),
            borrower.getAddress(),
            borrower.getPhone()
        });
    }

    @Override
    public void delete(Borrower borrower) throws SQLException {
        delete(new String[] {
            "cardNo"
        }, new Object[] {
            borrower.getCardNo()
        });
    }

    @Override
    protected Borrower extractModel(ResultSet rs) throws SQLException {
        Borrower borrower = new Borrower();
        borrower.setCardNo(rs.getInt("cardNo"));
        borrower.setName(rs.getString("name"));
        borrower.setAddress(rs.getString("address"));
        borrower.setPhone(rs.getString("phone"));
        return borrower;
    }

    public Borrower find(int cardNo) throws SQLException {
        return read(new String[] {
            "cardNo"
        }, new Object[] {
            cardNo
        });
    }
    
    public void updateCardNo(Borrower borrower, int cardNo) throws SQLException {
        update(new String[] {
            "cardNo"
        }, new String[] {
            "name",
            "address",
            "phone"
        }, new Object[] {
            cardNo,
            borrower.getName(),
            borrower.getAddress(),
            borrower.getPhone()
        });
    }

    public void updateName(Borrower borrower, String name) throws SQLException {
        update(new String[] {
            "name"
        }, new String[] {
            "cardNo",
        }, new Object[] {
            name,
            borrower.getCardNo(),
        });
    }

    public void updateAddress(Borrower borrower, String address) throws SQLException {
        update(new String[] {
            "address"
        }, new String[] {
            "cardNo",
        }, new Object[] {
            address,
            borrower.getCardNo(),
        });
    }

    public void updatePhone(Borrower borrower, String phone) throws SQLException {
        update(new String[] {
            "phone"
        }, new String[] {
            "cardNo",
        }, new Object[] {
            phone,
            borrower.getCardNo(),
        });
    }
}
