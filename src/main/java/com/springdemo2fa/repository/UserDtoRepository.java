package com.springdemo2fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springdemo2fa.model.UserDto;

@Repository
public interface UserDtoRepository extends JpaRepository<UserDto, Integer> {
    public UserDto findByUsername(String username);
}
