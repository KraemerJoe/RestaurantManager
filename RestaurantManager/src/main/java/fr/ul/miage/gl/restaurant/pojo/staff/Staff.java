package fr.ul.miage.gl.restaurant.pojo.staff;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import io.ebean.Model;
import io.ebean.annotation.NotNull;

@Entity
@Table(name = "\"STAFF\"")
public class Staff extends Model{

	@Id
	protected long staff_id;

	@NotNull
	private String name;

	@NotNull
	private String surname;

	@NotNull
	private String login;

	@NotNull
	private String password;

	@Enumerated(EnumType.STRING)
	private EnumRoles role;

	public Staff(String name, String surname, String login, String password, EnumRoles role) {
		super();
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.password = password;
		this.role = role;
	}

	public Staff(long staff_id) {
		super();
		this.staff_id = staff_id;
	}

	public long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EnumRoles getRole() {
		return role;
	}

	public void setRole(EnumRoles role) {
		this.role = role;
	}

}