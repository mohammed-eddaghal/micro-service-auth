package com.mohammed.doa.Test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.test.context.junit4.SpringRunner;

import com.mohammed.dao.UserRepository;
import com.mohammed.entities.UserEntity;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)//to make a connection between JUnit and Spring
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    UserEntity  user = new UserEntity(null, "med", "edd", "jfn3k4jt5bg3bjgkb3kjb35gkb56"
            , "mededd", "mededd"
            , "mededd@gmail.com", false,
            null );
    @Before
    public  void setup(){
        entityManager.persist(user);//save the User object at the beginning of every test
        entityManager.flush();
    }

    @Test
    public  void testFindAllUsers(){
      List<UserEntity> users= userRepository.findAll() ;
      org.hamcrest.MatcherAssert.assertThat(3,is(users.size()));//we have 2 users at the DB + the user we add at the setup of the test

    }
    @Test
    public void testSaveUser(){
        UserEntity  userSaved = userRepository.save(user);
        assertNotNull(userSaved.getId());
        org.hamcrest.MatcherAssert.assertThat("mededd",is(userSaved.getUserName()));
    }
    @Test
    public void testFindByUserName(){
       UserEntity userFound= userRepository.findByUserName("mededd").get();
       org.hamcrest.MatcherAssert.assertThat("mededd",is(userFound.getUserName()));
    }
    @Test
    public void testFindByUserId(){
        UserEntity userEntity=userRepository.findByUserId(user.getUserId()).get();
        org.hamcrest.MatcherAssert.assertThat(user.getUserName(),is(userEntity.getUserName()));

    }
    @Test
    public void testFindBy_Unknow_Id(){
        Optional<UserEntity> userFromDb=userRepository.findById(50L);
        assertEquals(Optional.empty(),Optional.ofNullable(userFromDb).get());
    }
    @Test
    public void testDeleteUser(){
        userRepository.deleteById(user.getId());
        Optional<UserEntity> userFromDb=userRepository.findByUserName(user.getUserName());
        assertEquals(Optional.empty(),Optional.ofNullable(userFromDb).get());
    }
    @Test
    public void testUpdateUser(){//test if the user account is activated
        Optional<UserEntity> userFromDb=userRepository.findByUserName(user.getUserName());
        userFromDb.get().setActivated(true);
        userRepository.save(userFromDb.get());
        Optional<UserEntity> userUpdateFromDb=userRepository.findByUserName(userFromDb.get().getUserName());
        assertNotNull(userUpdateFromDb);
        org.hamcrest.MatcherAssert.assertThat(true,is(userUpdateFromDb.get().isActivated()));

    }
}

