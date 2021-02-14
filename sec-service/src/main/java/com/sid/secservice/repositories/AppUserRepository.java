package com.sid.secservice.repositories;

import com.sid.secservice.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
        AppUser findByUsername(String username);

}
