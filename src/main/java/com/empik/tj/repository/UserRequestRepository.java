package com.empik.tj.repository;

import com.empik.tj.model.db.UserRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequestEntity, String> {

}
