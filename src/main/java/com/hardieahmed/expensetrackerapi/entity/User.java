package com.hardieahmed.expensetrackerapi.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_users")
public class User {
	@Id
	@SequenceGenerator(
			name = "user_sequence",
			sequenceName = "user_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = SEQUENCE,
			generator = "user_sequence"
	)
	@Column(
			name = "id",
			updatable = false
	)
	private Long id;
	
	@Column(
			name = "name",
			nullable = false,
			columnDefinition = "TEXT"
	)
	@NotBlank(message = "User name cannot be blank")
	@Size(min = 2, message = "User name cannot be less than 2 characters")
	private String name;
	
	@Column(
			name = "email",
			nullable = false,
			unique = true
	)
	private String email;
	
	@Column(
			name = "password",
			nullable = false
	)
	@JsonIgnore
	private String password;
	
	@Column(
			name = "age"
	)
	private Long age;
	
	@Column(
			name = "created_at",
			nullable = false,
			updatable = false
	)
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column(
			name = "updated_at",
			nullable = false
	)
	@UpdateTimestamp
	private Timestamp updatedAt;
}
