package com.mohammed.service;

import java.util.Collection;
import java.util.Optional;

import com.mohammed.entities.UserEntity;

public interface UserService {
   Collection<UserEntity> getAllUser();
   Optional<UserEntity> getUserById(Long id);
   Optional<UserEntity> findByUserId(String userId);
   Optional<UserEntity> findByEmail(String email);
   Optional<UserEntity> findByUserName(String userName);
   UserEntity saveOrUpdate(UserEntity user);
   void deleteUser(String userId);
   Optional<UserEntity>  findByUserNameAndPassword(String userName,String password);
}