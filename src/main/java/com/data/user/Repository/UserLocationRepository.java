package com.data.user.Repository;

import com.data.user.entity.UserLocationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserLocationRepository extends CrudRepository<UserLocationEntity, Integer> {
    Optional<UserLocationEntity> findTop1ByUserIdOrderByCreatedOnDesc(String userId);
    Optional<List<UserLocationEntity>> findTop20ByUserIdOrderByCreatedOnDesc(String userId);

}
