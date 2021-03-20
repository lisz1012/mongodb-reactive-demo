package com.lisz.controller;

import com.lisz.dao.PersonDao;
import com.lisz.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
	@Autowired
	private PersonDao personDao;

	@Autowired
	private MongoTemplate template;

	@GetMapping("/person/{id}")
	public Person getPerson(@PathVariable String id){
		System.out.println("id: " + id);
		return personDao.findById(id).get();
	}

	@PostMapping("/save")
	public Person save(@RequestBody Person person) {
		System.out.println("saving..." + person);
		return personDao.save(person);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id) {
		System.out.println("Deleting..." + id);
		personDao.deleteById(id);
	}

	@GetMapping("/findAll")
	public List<Person> findAll(){
		return personDao.findAll();
	}

	@GetMapping("/findAllPaged")
	public Page<Person> findAllPaged(@RequestParam int pageNum, @RequestParam int pageSize){
		PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
		return personDao.findAll(pageRequest);
	}

	// 模糊查询
	@GetMapping("/findLike/{name}")
	public List<Person> findLike(@PathVariable String name){
		return personDao.findByNameLike(name); // SpringJPA写法
	}

	// Query查询
	@GetMapping("/findWithTemplate")
	public List<Person> findWithTemplate(int age){
		Query query = new Query();
		query.addCriteria(Criteria.where("age").lt(age));
		return template.find(query, Person.class);
	}
}
