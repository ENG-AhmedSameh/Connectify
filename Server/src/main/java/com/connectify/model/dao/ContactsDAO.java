package com.connectify.model.dao;


import com.connectify.model.entities.Contacts;

public interface ContactsDAO extends DAO<Contacts, String> {
    boolean areAlreadyFriends(String userPhone, String phone2);
}

