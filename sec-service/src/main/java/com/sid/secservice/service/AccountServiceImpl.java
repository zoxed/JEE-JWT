package com.sid.secservice.service;

import com.sid.secservice.entities.AppRole;
import com.sid.secservice.entities.AppUser;
import com.sid.secservice.repositories.AppRoleRepository;
import com.sid.secservice.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AppUserRepository userRepository;
    private AppRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AppUserRepository userRepository, AppRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public AppUser addNewUser(AppUser appUser)
    {
        String pw = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        return userRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole)
    {
        return roleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {

        AppUser user=userRepository.findByUsername(username);
        AppRole role=roleRepository.findByRolename(rolename);

        user.getRoles().add(role);
    }

    @Override
    public AppUser loadUserByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> listUsers()
    {
        return userRepository.findAll();
    }
}
