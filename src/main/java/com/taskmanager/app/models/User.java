package com.taskmanager.app.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String nick;
    private String email;
    private String password;
    private String role;
    private String created;
    private String updated;
    private String status;
    private String verified;
	public User() {
		super();
	}
	public User(String id, String name, String nick, String email, String password, String role, String created,
			String updated, String status, String verified) {
		super();
		this.id = id;
		this.name = name;
		this.nick = nick;
		this.email = email;
		this.password = password;
		this.role = role;
		this.created = created;
		this.updated = updated;
		this.status = status;
		this.verified = verified;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", nick=" + nick + ", email=" + email + ", password=" + password
				+ ", role=" + role + ", created=" + created + ", updated=" + updated + ", status=" + status
				+ ", verified=" + verified + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVerified() {
		return verified;
	}
	public void setVerified(String verified) {
		this.verified = verified;
	}  
}
