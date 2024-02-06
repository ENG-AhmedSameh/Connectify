package com.connectify.services;

import com.connectify.Server;
import com.connectify.dto.ContactsDTO;
import com.connectify.model.dao.ContactsDAO;
import com.connectify.model.dao.GroupDAO;
import com.connectify.model.dao.impl.ContactsDAOImpl;
import com.connectify.model.dao.impl.GroupDAOImpl;
import com.connectify.model.entities.Group;

import java.rmi.RemoteException;
import java.util.List;

public class GroupService {

    public boolean createGroup(List<ContactsDTO> contactsDTOS, String groupName, String groupDescription, byte[] image) {
        GroupDAO groupDAO = new GroupDAOImpl();
        return groupDAO.createGroup(contactsDTOS, groupName, groupDescription, image);
    }
}
