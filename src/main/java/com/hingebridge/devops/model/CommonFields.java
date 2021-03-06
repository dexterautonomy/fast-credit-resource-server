package com.hingebridge.devops.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@MappedSuperclass
public class CommonFields {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn = new Date();
    private Boolean delFlag = false;
}