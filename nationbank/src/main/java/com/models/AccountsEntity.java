package com.models;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name="accounts")
public class AccountsEntity {
	
	@Id
	@Column(name="accno")
	private int acno;
	
	@Column(name="accnm")
	private String accnm;
	
	@Column(name="acctype")
	private String acctype;
	
	@Column(name="balance")
	private float bal;
}
