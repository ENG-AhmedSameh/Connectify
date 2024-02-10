package com.connectify.services;

import com.connectify.dto.ContactsDTO;
import com.connectify.model.dao.GroupDAO;
import com.connectify.model.dao.impl.GroupDAOImpl;

import java.util.List;

public class GroupService {

    public boolean createGroup(List<ContactsDTO> contactsDTOS, String groupName, String groupDescription, byte[] image) {
        GroupDAO groupDAO = new GroupDAOImpl();
        return groupDAO.createGroup(contactsDTOS, groupName, groupDescription, image);
    }
}
