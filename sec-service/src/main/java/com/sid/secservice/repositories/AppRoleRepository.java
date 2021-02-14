package com.sid.secservice.repositories;

import com.sid.secservice.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
        AppRole findByRolename(String rolename);
}
