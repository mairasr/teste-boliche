package com.bowling.bowlingscore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bowling.bowlingscore.model.Frame;

public interface FrameRepository extends MongoRepository<Frame, String> {

}
