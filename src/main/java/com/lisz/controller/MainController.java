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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class MainController {
	@Autowired
	private PersonDao personDao;

//	@Autowired
//	private MongoTemplate template;

	@GetMapping("/person/{id}")
	public Mono<Person> getPerson(@PathVariable String id){
		System.out.println("id: " + id);
		return personDao.findById(id);
	}

	@GetMapping("/person")
	public Mono<Person> getPerson2(@RequestParam String id){
		System.out.println("id: " + id);
		return personDao.findById(id);
	}

	@PostMapping("/save")
	public Mono<Person> save(@RequestBody Person person) {
		//int i = 1/0;
		System.out.println("saving..." + person);
		return personDao.save(person);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id) {
		System.out.println("Deleting..." + id);
		personDao.deleteById(id);
	}

	@GetMapping("/findAll")
	public Flux<Person> findAll(){
		return personDao.findAll();
	}

	// 响应式的，分页就不好使了
//	@GetMapping("/findAllPaged")
//	public Page<Person> findAllPaged(@RequestParam int pageNum, @RequestParam int pageSize){
//		PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
//		return personDao.findAll(pageRequest);
//	}


	// 模糊查询和同步的Template都不好使了

	// 模糊查询
	@GetMapping("/findLike/{name}")
	public Flux<Person> findLike(@PathVariable String name){
		return personDao.findByNameLike(name).log(); // SpringJPA写法
		/*
		2021-03-20 17:17:34.118  INFO 60406 --- [nio-8080-exec-3] reactor.Flux.UsingWhen.2                 : onSubscribe(FluxUsingWhen.UsingWhenSubscriber)
		2021-03-20 17:17:34.118  INFO 60406 --- [nio-8080-exec-3] reactor.Flux.UsingWhen.2                 : request(unbounded)
		2021-03-20 17:17:34.120  INFO 60406 --- [       Thread-8] reactor.Flux.UsingWhen.2                 : onNext(Person{id='3', name='zhangsan', age=28, price=100.0})
		2021-03-20 17:17:34.120  INFO 60406 --- [       Thread-8] reactor.Flux.UsingWhen.2                 : onNext(Person{id='5', name='zhangsan', age=30, price=120.0})
		2021-03-20 17:17:34.120  INFO 60406 --- [       Thread-8] reactor.Flux.UsingWhen.2                 : onComplete()

		数据在onNext的时候一个个的返回，给Web容器，当onComplete的时候，打包成返回前端的数据，一下产生出来

		 */
	}
//
//	// Query查询
//	@GetMapping("/findWithTemplate")
//	public List<Person> findWithTemplate(int age){
//		Query query = new Query();
//		query.addCriteria(Criteria.where("age").lt(age));
//		return template.find(query, Person.class);
//	}
}
