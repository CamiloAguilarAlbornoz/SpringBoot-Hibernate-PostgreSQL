package com.camilo.curso.springboot.hibernate.postgresql.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.camilo.curso.springboot.hibernate.postgresql.dto.PersonDTO;
import com.camilo.curso.springboot.hibernate.postgresql.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

	public List<Person> findByProgrammingLanguage(String programmingLanguage);
	
	@Query("select p from Person p where p.id=?1")
	public Optional<Person> findOne(long id);
	
	@Query("select p from Person p where p.name=?1")
	public Optional<List<Person>> findAllByName(String name);
	
	@Query("select p from Person p where p.name like %?1%")
	public Optional<List<Person>> findLikeName(String name);
	
	public List<Person> findByNameContaining(String name);
	
	@Query("select p from Person p where p.programmingLanguage=?1 or p.programmingLanguage=?2")
	public List<Person> findByProgrammingLanguageByAnotherWay(String firstProgrammingLanguage, String secondProgrammingLanguage);
	
	@Query("select p.name, p.programmingLanguage from Person p")
	public List<Object[]> findPersonValues();
	
	@Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
	public List<Object[]> findPersonNames(String name);
	
	@Query("select concat(p.name, ' ', p.lastName) as fullName from Person p where p.id=?1")
	public String getFullNameById(long id);
	
	@Query("select new Person(p.name, p.lastName) from Person p")
	public List<Person> findAllClassPerson();
	
	@Query("select new com.camilo.curso.springboot.hibernate.postgresql.dto.PersonDTO(p.name, p.lastName) from Person p")
	public List<PersonDTO> findAllClassPersonDTO();
	
	@Query("select distinct(p.name) from Person p")
	public List<String> findAllNamesDisting();
	
	@Query("select UPPER(p.name || ' ' || p.lastName) from Person p")
	public List<String> getFullNameConcatUpper();
	
	@Query("select LOWER(p.name || ' ' || p.lastName) from Person p")
	public List<String> getFullNameConcatLower();
	
	@Query("select p from Person p where p.id between ?1 and ?2 order by p.name asc")
	public List<Person> getAllBetween(long idOne, long idTwo);
	
	@Query("select count(p) from Person p")
	public long totalPerson();
	
	@Query("select min(p.id) from Person p")
	public long minId();
	
	@Query("select max(p.id) from Person p")
	public long maxId();
	
	@Query("select p.name, length(p.name) from Person p")
	public List<Object[]> getPersonNameLenght();
	
	@Query("select min(length(p.name)) from Person p")
	public int getMinName();
	
	@Query("select max(length(p.name)) from Person p")
	public int getMaxName();
	
	@Query("select "
			+ "concat('Min = ', min(p.id)), "
			+ "concat('Max = ', max(p.id)),"
			+ "concat('Sum = ', sum(p.id)),"
			+ "concat('Avg = ', avg(length(p.name))),"
			+ "concat('count = ', count(p.id)) from Person p")
	public Object getResumeAgregationFunction();
	
	@Query("select p.name, length(p.name) "
			+ "from Person p "
			+ "where length(p.name) = (select min(length(p.name)) from Person p)")
	public List<Object[]> getShortestName();
	
	@Query("select p from Person p where p.id = (select max(p.id) from Person p)")
	public Optional<Person> getLastRegistration();
	
	@Query("select p from Person p where p.id in ?1")
	public List<Person> getPersonByIds(List<Integer> idsList);
}
