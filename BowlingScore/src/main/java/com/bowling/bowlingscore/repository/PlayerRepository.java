package com.bowling.bowlingscore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bowling.bowlingscore.model.Player;

public interface PlayerRepository extends MongoRepository<Player, String> {

}
