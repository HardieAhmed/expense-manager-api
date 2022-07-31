package com.hardieahmed.expensetrackerapi.entity;

import static javax.persistence.GenerationType.SEQUENCE;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_expenses")
public class Expense {
	
	@Id
	@SequenceGenerator(
			name = "expense_sequence",
			sequenceName = "expense_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = SEQUENCE,
			generator = "expense_sequence"
	)
	@Column(
			name = "id",
			updatable = false
	)
	private Long id;
	
	@Column(
			name = "expense_name",
			nullable = false,
			columnDefinition = "TEXT"
	)
	@NotBlank(message = "Name cannot be blank")
	@Size(min = 2, message = "Name cannot be less than 2 characters")
	private String name;
	
	@Column(
			name = "description",
			columnDefinition = "TEXT"
	)
	private String description;
	
	
	@Column(
			name = "expense_amount",
			nullable = false
	)
	@NotNull(message = "Amount cannot be null")
	private BigDecimal amount;
	
	@Column(
			name = "category",
			nullable = false,
			columnDefinition = "TEXT"
	)
	@NotBlank(message = "Category cannot be null")
	private String category;
	
	@Column(
			name = "date",
			nullable = false
	)
	@NotNull(message = "Date cannot be null")
	private Date date;
	
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
	
	
	@ManyToOne(
			fetch = FetchType.LAZY,
			optional = false
	)
	@JoinColumn(
			name = "user_id",
			nullable = false
	)
	@OnDelete(action = CASCADE)
	@JsonIgnore
	private User user;
}
