package com.hingebridge.devops.repositories;

import java.util.List;
import java.util.Optional;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hingebridge.devops.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>
{
	Optional<User> findByUsernameAndDelFlagFalse(String username);
	Boolean existsByUsername(String username);
	List<User> findAllByDelFlagFalse();
}