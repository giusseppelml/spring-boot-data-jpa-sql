package com.curso.java.spring.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity //inidicar que esta clase tambien es modelo de base de datos
@Table(name="clientes") //tabla de base de datos
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id //indicar que es el id de la base de datos
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty //validaciones
	private String nombre;
	
	@NotEmpty //validaciones
	private String apellido;
	@NotEmpty //validaciones
	@Email //validacion para correos
	private String email;
	
	@NotNull //validaciones
	@Column(name="create_at") //definiendo el nombre del campo de la base de datos
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyy-MM-dd") //formato para guardar fechas
	private Date createAt;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
		
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getCreateAt() {
		return createAt;
	}
	
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	/*@PrePersist
	public void prePersist() {
		createAt = new Date();
	}*/
}