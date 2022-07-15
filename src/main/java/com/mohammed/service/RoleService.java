package com.mohammed.service;

import java.util.Collection;
import java.util.stream.Stream;

import com.mohammed.entities.RoleEntity;

public interface RoleService {
	 RoleEntity findByRoleName(String roleName);
	 Collection<RoleEntity> getAllRoles();
	 Stream<RoleEntity> getAllRolesStream();
	}
