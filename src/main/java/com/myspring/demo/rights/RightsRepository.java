package com.myspring.demo.rights;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RightsRepository extends JpaRepository<Rights, Long> {
    @Query("SELECT r FROM Rights r")
    List<Rights> getAllRights();

    @Query("SELECT r FROM Rights r WHERE r.id = :id")
    Optional<Rights> getOneToken(@Param("id") Long id);

    @Query("SELECT r FROM Rights r WHERE r.id = :id")
    Optional<Rights> findWithUserId(@Param("id") Long id);

    // @Query("SELECT EXISTS (SELECT 1 FROM Rights WHERE User_Id = :User_Id)")
    // Boolean alreadyCreated(@Param("User_Id") int User_Id);
}