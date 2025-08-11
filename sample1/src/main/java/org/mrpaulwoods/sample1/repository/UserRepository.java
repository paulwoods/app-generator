package org.mrpaulwoods.sample1.repository;

import org.mrpaulwoods.sample1.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
}
