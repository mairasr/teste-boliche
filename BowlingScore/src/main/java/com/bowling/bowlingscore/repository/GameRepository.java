package com.bowling.bowlingscore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bowling.bowlingscore.model.Game;

public interface GameRepository extends MongoRepository<Game, String> {

}
