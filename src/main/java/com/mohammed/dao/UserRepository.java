package com.mohammed.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohammed.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{
	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByUserName(String userName);
	Optional<UserEntity> findByUserId(String userId);
	void deleteByUserId(String userId);


}
