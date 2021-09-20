package com.ss.lms.views;

public interface IAdminModel {
    public IOption add();
    public IOption update();
    public IOption delete();
    public IOption read();

    public IOption returnToAdmin();
}
