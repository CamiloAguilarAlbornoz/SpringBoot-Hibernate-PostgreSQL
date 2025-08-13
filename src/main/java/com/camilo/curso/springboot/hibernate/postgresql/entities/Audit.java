package com.camilo.curso.springboot.hibernate.postgresql.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Embeddable
public class Audit {
	
	@Column(name = "create_at")
	private LocalDateTime createAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updateAt;

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
	
	@PrePersist
	public void prePersist() {
		System.out.println("Evento del ciclo de vida del entity PrePersist");
		this.createAt = LocalDateTime.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		System.out.println("Evento del ciclo de vida del entity PreUpdate");
		this.updateAt = LocalDateTime.now();
	}
}
