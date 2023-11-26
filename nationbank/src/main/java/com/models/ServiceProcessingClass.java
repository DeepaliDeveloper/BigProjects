package com.models;

import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.*;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.*;

@Component
public class ServiceProcessingClass {

	Configuration cfg;
	SessionFactory sf;
	Session ses;
	
	public ServiceProcessingClass() {
		try {
			cfg = new Configuration().configure();
			sf = cfg.addAnnotatedClass(AccountsEntity.class).buildSessionFactory();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<AccountsEntity> getRecord(int acno) {

		ArrayList<AccountsEntity> list = new ArrayList<AccountsEntity>();
		AccountsEntity ac;
		try {
			ses = sf.getCurrentSession();
			ses.beginTransaction();
			CriteriaBuilder cb = ses.getCriteriaBuilder();
			CriteriaQuery<AccountsEntity> qry = cb.createQuery(AccountsEntity.class);

			Root<AccountsEntity> root = qry.from(AccountsEntity.class);
			qry.select(root).where(cb.equal(root.get("acno"), acno));

			List<AccountsEntity> resultList = ses.createQuery(qry).getResultList();
			if (resultList.size() > 0) {
				ac = new AccountsEntity();

				for (int i = 0; i < resultList.size(); i++) {
					AccountsEntity ae = (AccountsEntity) resultList.get(i);
					ac.setAcno(ae.getAcno());
					ac.setAccnm(ae.getAccnm());
					ac.setAcctype(ae.getAcctype());
					ac.setBal(ae.getBal());
				}
			} else {
				ac = new AccountsEntity();
				ac.setAcno(0);
				ac.setAccnm("not valid");
				ac.setAcctype("not valid");
				ac.setBal(0.0f);
			}

		} catch (Exception e) {
			ac = new AccountsEntity();
			ac.setAcno(0);
			ac.setAccnm("error occured");
			ac.setAcctype("error occured");
			ac.setBal(0.0f);
			e.printStackTrace();
		}

		ses.getTransaction().commit();
		ses.close();
		list.add(ac);

		return list;
	}

	public String deleteRecord(int acno) {

		String status = "";
		try {
			ses = sf.getCurrentSession();
			ses.beginTransaction();
			CriteriaBuilder cb = ses.getCriteriaBuilder();
			CriteriaDelete<AccountsEntity> cdelete = cb.createCriteriaDelete(AccountsEntity.class);

			Root<AccountsEntity> root = cdelete.from(AccountsEntity.class);
			cdelete.where(cb.equal(root.get("acno"), acno));

			int result = ses.createQuery(cdelete).executeUpdate();

			ses.getTransaction().commit();
			ses.close();

			if (result > 0)
				status = "Successfully deleted";
			else
				status = "Not deleted";

		} catch (Exception e) {
			status = "error occured.";
			System.out.println(e);
		}

		return status;
	}

	public String updateRecord(int acno, float bal) {

		String status = "not valid";

		try {
			ses = sf.getCurrentSession();
			ses.beginTransaction();
			CriteriaBuilder cb = ses.getCriteriaBuilder();
			CriteriaUpdate<AccountsEntity> cupdate = cb.createCriteriaUpdate(AccountsEntity.class);

			Root<AccountsEntity> root = cupdate.from(AccountsEntity.class);
			cupdate.set("bal", bal);
			cupdate.where(cb.equal(root.get("acno"), acno));

			int result = ses.createQuery(cupdate).executeUpdate();

			ses.getTransaction().commit();
			ses.close();

			if (result > 0)
				status = " Balance updated successfully";
			else
				status = "Updation failed";

		} catch (Exception e) {
			status = "error occured.";
			e.printStackTrace();
		}

		return status;
	}

	public String insertNewRecord(AccountsEntity ae) {

		String status = "not valid";
		try {
			ses = sf.getCurrentSession();
			ses.beginTransaction();
			ses.save(ae);
			ses.getTransaction().commit();
			ses.close();

			status = "Data saved..";

		} catch (Exception e) {
			status = "error occured.";
			e.printStackTrace();
		}

		return status;
	}
}
