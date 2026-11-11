package com.trackflow.user;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="users")
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String password;
 @Column(nullable=false) private String name;
 private Instant createdAt=Instant.now();
 protected AppUser() {}
 public AppUser(String name,String email,String password){this.name=name;this.email=email;this.password=password;}
 public Long getId(){return id;} public String getEmail(){return email;} public String getPassword(){return password;} public String getName(){return name;}
}
