package com.connectify.Interfaces;


import com.connectify.dto.ChatMemberDTO;
import com.connectify.dto.SignUpRequest;
import com.connectify.model.entities.ChatMember;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerAPI extends Remote{

    boolean signUp(SignUpRequest signUpRequest) throws RemoteException;
    List<ChatMemberDTO> getAllUserChats(String userId) throws RemoteException;
}
