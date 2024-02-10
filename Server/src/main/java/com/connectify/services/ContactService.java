package com.connectify.services;

import com.connectify.dto.ContactsDTO;
import com.connectify.mapper.ContactMapper;
import com.connectify.model.dao.impl.ContactsDAOImpl;
import com.connectify.model.entities.User;

import java.util.List;

public class ContactService {

    public List<ContactsDTO> getContactsDTOList(String phoneNumber)
    {
        ContactsDAOImpl contactsData=new ContactsDAOImpl();
        List<User> userList= contactsData.getContactsList(phoneNumber);
        ContactMapper mapper =ContactMapper.INSTANCE;
        List<ContactsDTO>contactsDTOS =mapper.userListToContactDTOList(userList);
        return contactsDTOS;
    }
}
