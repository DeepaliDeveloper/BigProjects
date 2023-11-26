package com.service.nationbank;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import com.models.AccountsEntity;
import com.models.ServiceProcessingClass;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RestController
@ComponentScan("com.models")
@RequestMapping("/api")
public class RestControllerClass {
	
	@Autowired
	ServiceProcessingClass obj;
	
	String status = "";

	@GetMapping("/msg")
	@Produces(MediaType.TEXT_PLAIN)
	public String msg() {
		return "welcome to our bank's services";
	}

	@GetMapping("/getrecord/{acno}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<AccountsEntity> serviceForGetRecord(@PathVariable int acno) {

		ArrayList<AccountsEntity> lis = new ArrayList<AccountsEntity>();
		lis = obj.getRecord(acno);

		return lis;
	}

	@DeleteMapping("/deleterecord/{acno}")
	@Produces(MediaType.TEXT_PLAIN)
	public String serviceForDeleteRecord(@PathVariable int acno) {

		status = obj.deleteRecord(acno);

		return status;
	}

	@PutMapping("/updatarecord")
	@Produces(MediaType.TEXT_PLAIN)
	public String serviceForUpdateRecord(@FormParam("acno") int acno, @FormParam("bal") float bal) {

		status = obj.updateRecord(acno, bal);

		return status;
	}

	@PostMapping("/newrecord")
	@Produces(MediaType.TEXT_PLAIN)
	public String serviceForInsertNewRecord(@FormParam("acno") int acno, @FormParam("acnm") String acnm,
			@FormParam("acty") String acty, @FormParam("bal") float bal) {
		
		AccountsEntity ae = new AccountsEntity();
		ae.setAcno(acno);
		ae.setAccnm(acnm);
		ae.setAcctype(acty);
		ae.setBal(bal);

		status = obj.insertNewRecord(ae);

		return status;
	}
}
