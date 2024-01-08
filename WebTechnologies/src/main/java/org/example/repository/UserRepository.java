package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findCustomerByPseudonym(final String pseudonym);
//    @Query(value = "select * from USER_ENTITY u where upper(u.name) like upper(concat('%',:name,'%'))"
//            , nativeQuery = true)
//    List<UserEntity> findAllByUserName(String name);


}
