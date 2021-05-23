package com.learn.springbatch.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private Integer id;
	
	private String name;
	
	private String dept;
	
	private Integer salary;
	
	private Date time;
}
