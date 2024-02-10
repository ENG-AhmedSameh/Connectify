package com.connectify.model.dao;


import com.connectify.dto.ContactsDTO;
import com.connectify.model.entities.Group;

import java.util.List;

public interface GroupDAO extends DAO<Group,Integer> {
    boolean createGroup(List<ContactsDTO> contactsDTOS, String groupName, String groupDescription, byte[] image);
}
