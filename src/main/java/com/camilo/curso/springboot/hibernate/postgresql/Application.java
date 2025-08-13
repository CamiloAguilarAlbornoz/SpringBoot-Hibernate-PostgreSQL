package com.camilo.curso.springboot.hibernate.postgresql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.camilo.curso.springboot.hibernate.postgresql.dto.PersonDTO;
import com.camilo.curso.springboot.hibernate.postgresql.entities.Person;
import com.camilo.curso.springboot.hibernate.postgresql.repositories.PersonRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		/*list();
		System.out.println("\n=======================================================\n");
		findOne();
		System.out.println("\n=======================================================\n");
		findOneCustom();
		create();
		update();
		delete();
		deleteTwo();
		personalizeQuery();
		otherPersonalizeQuery();
		personalizeQueryDistinc();
		personalizeQueryUpper();
		personalizeQueryLower();
		personalizeQueryBetween();
		personalizeQueryCountMinMax();
		showAllAgregationFunction();
		subquery();*/
		whereIn();
	}
	
	@Transactional(readOnly = true)
	public void list() {
		System.err.println("============= TODAS LAS PERSONAS ===========\n");
		List<Person> personsList = (List<Person>) personRepository.findAll();
		personsList.stream().forEach(person -> System.out.println(person));
		System.err.println("\n============= TODAS LAS PERSONAS QUE DESARROLLAN JAVA Y KOTLIN ===========\n");
		List<Person> personsListPrograming = (List<Person>) personRepository.findByProgrammingLanguageByAnotherWay("Java", "Kotlin");
		personsListPrograming.stream().forEach(person -> System.out.println(person));
		System.err.println("\n============= NOMBRES DE LAS PERSONAS Y EL LENGUAJE EN QUE DESARROLLAN ===========\n");
		List<Object[]> namesListAndLanguage = (List<Object[]>) personRepository.findPersonValues();
		namesListAndLanguage.stream().forEach(person -> System.out.println(person[0] + " es experto(a) en " + person[1]));
		System.err.println("\n============= NOMBRES DE LAS PERSONA ===========\n");
		List<Object[]> namesPersonsList = (List<Object[]>) personRepository.findPersonNames("Camilo");
		namesPersonsList.stream().forEach(person -> System.out.println(person[0] + " es experto(a) en " + person[1]));
	}
	
	@Transactional(readOnly = true)
	public void findOne() {
		personRepository.findById(5L).ifPresent(person -> {
			System.err.println("\n============= Persona por el ID ===========\n");
			System.out.println(person);
		});
	}
	
	@Transactional(readOnly = true)
	public void findOneCustom() {
		personRepository.findOne(1L).ifPresent(person -> {
			System.err.println("\n============= Persona por el ID Personalizado ===========\n");
			System.out.println(person);
		});
		personRepository.findAllByName("Camilo").ifPresent(personList -> {
			System.err.println("\n============= Persona por el Nombre Personalizado ===========\n");
			personList.forEach(person -> {
				System.out.println(person);
			});
		});
		personRepository.findLikeName("l").ifPresent(personList -> {
			System.err.println("\n============= Persona por coincidencia ===========\n");
			personList.forEach(person -> {
				System.out.println(person);
			});
		});
		personRepository.findLikeName("l").ifPresent(personList -> {
			System.err.println("\n============= Persona por coincidencia ===========\n");
			personList.forEach(person -> {
				System.out.println(person);
			});
		});
		
		System.err.println("\n============= Persona por coincidencia V2 ===========\n");
		personRepository.findByNameContaining("ly").stream().forEach(person -> {
			System.out.println(person);
		});
	}
	
	@Transactional
	public void create() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el nombre");
		String name = sc.nextLine();
		System.out.println("\nIngrese el apellido");
		String lastName = sc.nextLine();
		System.out.println("\nIngrese el lenguaje de programación");
		String programmingLanguage = sc.nextLine();
		Person person = new Person(null, name, lastName, programmingLanguage);
		personRepository.save(person);
		sc.close();
	}
	
	@Transactional
	public void update() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a editar: ");
		long id = sc.nextLong();
		Optional<Person> optionalPerson = personRepository.findById(id);
		if (optionalPerson.isPresent()) {
			Person person = optionalPerson.get();
			System.out.println("Ingrese el lenguaje de programación: ");
			String programmingLanguage = sc.next();
			person.setProgrammingLanguage(programmingLanguage);
			personRepository.save(person);	
		} else {
			System.err.println("El usuario no se encuentra presente en el sistema.");
		}
		sc.close();
	}
	
	@Transactional
	public void delete() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar");
		long id = sc.nextLong();
		personRepository.deleteById(id);
		personRepository.findAll().forEach(System.out::println);
		sc.close();
	}
	
	@Transactional
	public void deleteTwo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar");
		long id = sc.nextLong();
		Optional<Person> optionlPerson = personRepository.findById(id);
		optionlPerson.ifPresentOrElse(
				personRepository::delete, 
				() -> System.err.println("\nNo existe una persona con el id ingresado\n")
			);
		personRepository.findAll().forEach(System.out::println);
		sc.close();
	}
	
	@Transactional(readOnly = true)
	public void personalizeQuery() {
		System.out.println("\nIngrese el id de la persona");
		Scanner sc = new Scanner(System.in);
		long id = sc.nextLong();
		String fullName = personRepository.getFullNameById(id);
		System.out.println("\n" + fullName + "\n");
		sc.close();
	}
	
	@Transactional(readOnly = true)
	public void otherPersonalizeQuery() {
		System.out.println("\nTodos los nombres y apellidos de personas\n");
		List<PersonDTO> allClassPerson = personRepository.findAllClassPersonDTO();
		allClassPerson.stream().forEach(System.out::println);
	}
	
	@Transactional(readOnly = true)
	public void personalizeQueryDistinc() {
		System.out.println("\nConsultas con nombres de personas\n");
		List<String> allNames = personRepository.findAllNamesDisting();
		allNames.stream().forEach(System.out::println);
	}
	
	@Transactional(readOnly = true)
	public void personalizeQueryUpper() {
		System.out.println("\nConsultas con nombres de personas en mayuscula\n");
		List<String> allNames = personRepository.getFullNameConcatUpper();
		allNames.stream().forEach(System.out::println);
	}
	
	@Transactional(readOnly = true)
	public void personalizeQueryLower() {
		System.out.println("\nConsultas con nombres de personas en minuscula\n");
		List<String> allNames = personRepository.getFullNameConcatLower();
		allNames.stream().forEach(System.out::println);
	}
	
	@Transactional(readOnly = true)
	public void personalizeQueryBetween() {
		System.out.println("\nConsultas personas con ids entre 2 y 5\n");
		List<Person> allNames = personRepository.getAllBetween(2L, 5L);
		allNames.stream().forEach(System.out::println);
	}
	
	@Transactional(readOnly = true)
	public void personalizeQueryCountMinMax() {
		System.out.println("\nTotal Personas\n");
		long totalPerson = personRepository.totalPerson();
		System.out.println(totalPerson);
		System.out.println("\nPersona con el id más pequeño\n");
		long minId = personRepository.minId();
		System.out.println(minId);
		System.out.println("\nPersona con el id más grande\n");
		long maxId = personRepository.maxId();
		System.out.println(maxId);
		System.out.println("\nPersona con el nombre más largo\n");
		List<Object[]> namesList = personRepository.getPersonNameLenght();
		namesList.stream().forEach(actualNameSize -> {
			String name = (String) actualNameSize[0];
			int quantity = (int) actualNameSize[1];
			System.out.println(name + " " + quantity);
		});
		System.out.println("\nPersona con el nombre más corto\n");
		int minName = personRepository.getMinName();
		System.out.println(minName);
		System.out.println("\nPersona con el nombre más largo\n");
		int maxName = personRepository.getMaxName();
		System.out.println(maxName);
	}
	
	@Transactional(readOnly = true)
	public void showAllAgregationFunction() {
		System.out.println("\nConsulta funciones de agregación min, max, sum, avg, count\n");
		Object[] resumeAgregationFunctions = (Object[]) personRepository.getResumeAgregationFunction();
		for (Object actualAgregationFunction : resumeAgregationFunctions) {
			System.out.println(actualAgregationFunction.toString());
		}
	}
	
	@Transactional(readOnly = true)
	private void subquery() {
		List<Object[]> shortestName = personRepository.getShortestName();
		System.out.println("============ Nombre con el tamaño más corto ================\n");
		shortestName.stream().forEach(nameAndSize -> 
			System.out.println("Nombre : " + nameAndSize[0] + " Tamaño : " + nameAndSize[1]));
		System.out.println("\n============ Ultimo registro ================\n");
		Optional<Person> lastRegister = personRepository.getLastRegistration();
		lastRegister.ifPresentOrElse(
				System.out::println,
				new Runnable() {
					public void run() {
						System.err.println("No existe el registro");
					}
				});
	}
	
	@Transactional(readOnly = true)
	private void whereIn() {
		List<Person> personsSelect = personRepository.getPersonByIds(Arrays.asList(1, 5));
		System.out.println("============ Personas seleccionadas ================\n");
		personsSelect.stream().forEach(System.out::println);
	}
}
