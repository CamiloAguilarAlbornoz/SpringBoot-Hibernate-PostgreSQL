package com.camilo.curso.springboot.hibernate.postgresql.dto;

public class PersonDTO {

	private String name;
	private String lastName;
	
	public PersonDTO(String name, String lastName) {
		this.name = name;
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public String toString() {
		return "{\nname : " + name + 
				"\nlastName : " + lastName + "\n},\n";
	}
}
