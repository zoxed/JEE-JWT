package com.sid.secservice.service;

import com.sid.secservice.entities.AppRole;
import com.sid.secservice.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole (AppRole appRole);
    
    void addRoleToUser (String username, String rolename);
    AppUser loadUserByUsername (String username);
    List<AppUser> listUsers();

}
