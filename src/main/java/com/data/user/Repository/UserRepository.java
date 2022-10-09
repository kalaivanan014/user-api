package com.data.user.Repository;

import com.data.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,String> {
   Optional<UserEntity> findByemailId(String emailId);

}
