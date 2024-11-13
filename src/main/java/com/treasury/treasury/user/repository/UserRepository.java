package com.treasury.treasury.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.treasury.treasury.user.schema.User;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByDocument(String document);
  Optional<User> findByNameAndPassword(String username, String password);
}
