package com.lisz.dao;

import com.lisz.entity.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonDao extends MongoRepository<Person, String> {

	List<Person> findByNameLike(String name);
}
