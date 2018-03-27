package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.models.Users;

@Repository
public class UserDaoImp implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long save(Users user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(user);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			throw ex;
		} finally {
			session.close();
		}

		return user.getId();
	}

	public void updateToken(Integer id, String token) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Users user = session.byId(Users.class).load(id);
			user.setToken(token);
			transaction.commit();

		} catch (Exception ex) {
			transaction.rollback();
			throw ex;
		} finally {
			session.close();
		}
	}

	/*
	 * @Override public void updateUser(Integer id, Users user) { Session
	 * session = sessionFactory.getCurrentSession(); Users user2 =
	 * session.byId(Users.class).load(id); user2.setToken(user.getToken()); //
	 * user2.setPassword(user.getPassword()); //
	 * user2.setUsername(user.getUsername()); //
	 * sessionFactory.getCurrentSession().saveOrUpdate(user); session.flush(); }
	 */

	@Override
	public Users get(long id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Users user = null;
		try {
			transaction = session.beginTransaction();
			user = session.get(Users.class, id);
			transaction.commit();
			return user;

		} catch (Exception e) {

		}
		return user;

		// return sessionFactory.getCurrentSession().get(Users.class, id);
	}

	/*
	 * @Override public List<Users> list() { Session session =
	 * sessionFactory.getCurrentSession(); CriteriaBuilder cb =
	 * session.getCriteriaBuilder(); CriteriaQuery<Users> cq =
	 * cb.createQuery(Users.class); Root<Users> root = cq.from(Users.class);
	 * cq.select(root); Query<Users> query = session.createQuery(cq); return
	 * query.getResultList(); }
	 */

	@Override
	public void delete(Integer UserId) {
		Session session = sessionFactory.openSession();
		// Session session = null;
		// sessionFactory.openSession();
		Transaction transaction = null;
		try {
			if (UserId > 0 && sessionFactory != null && !sessionFactory.isClosed()) {
				session = sessionFactory.openSession();
				session.beginTransaction();
				Users user = (Users) session.get(Users.class, UserId);
				session.delete(user.getPerson());
				session.getTransaction().commit();
				transaction = session.beginTransaction();
				// Session session = sessionFactory.getCurrentSession();
			}
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}

	}

	@Override
	public Boolean authenticate(String username, String password) {
		Session session = sessionFactory.openSession();
		boolean userFound = false;
		// Query using Hibernate Query Language\
		/*
		 * Criteria cr = session.createCriteria(User.class);
		 * cr.add(Restrictions.eq("username", username));
		 * cr.add(Restrictions.eq("password", password));
		 */

		String SQL_QUERY = "from Users where username = ? and  password = ?";
		Query query = session.createQuery(SQL_QUERY);
		query.setParameter(0, username);
		query.setParameter(1, password);
		List list = query.list();
		if ((list != null) && (list.size() > 0)) {
			userFound = true;
		}

		session.close();
		return userFound;

	}

	@Override
	public Users fetchUser(String username, String password) {
		Session session = sessionFactory.openSession();
		Users userFound = null;
		// Query using Hibernate Query Language
		String SQL_QUERY = " from Users as o where o.username=? and o.password=?";
		Query query = session.createQuery(SQL_QUERY);
		query.setParameter(0, username);
		query.setParameter(1, password);
		List list = query.list();

		if ((list != null) && (list.size() > 0)) {
			userFound = (Users) list.get(0);
		}

		session.close();
		return userFound;
	}

	@Override
	public Integer userRegistration(Users user) {

		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			session.close();
		}

		return user.getId();

	}

	@Override
	public Users fetchUserByEmail(String email) {
		Session session = sessionFactory.openSession();
		Users userFound = null;
		// Query using Hibernate Query Language
		String SQL_QUERY = " from Users as o where o.userEmail=?";
		Query query = session.createQuery(SQL_QUERY);
		query.setParameter(0, email);
		List list = query.list();

		if ((list != null) && (list.size() > 0)) {
			userFound = (Users) list.get(0);
		}

		session.close();
		return userFound;
	}

	@Override
	public Object UpdateProfile(Users user) {
		ModelMap model = new ModelMap();
		Session session = sessionFactory.openSession();

		Transaction transaction = null;

		try {

			Users userExist = session.byId(Users.class).load(user.getId());

			if (userExist == null) {
				model.put("msg", "user does not exist");
				return model;
			}

			session.clear();

			transaction = session.beginTransaction();

			session.update(user);

			transaction.commit();

		} catch (Exception ex) {

			transaction.rollback();
			ex.printStackTrace();

		} finally {

			session.close();

		}

		model.put("msg", "Update successfully");
		return model;

	}
}