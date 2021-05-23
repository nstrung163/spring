package com.learn.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.springbatch.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
