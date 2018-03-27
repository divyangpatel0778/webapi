package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.UserDao;
import com.models.Users;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
	@Override
	public long save(Users user) {
		return userDao.save(user);
	}

	@Override
	public Users get(long id) {
		return userDao.get(id);
	}

	/*
	 * @Override public List<Users> list() { return userDao.list(); }
	 * 
	 * @Transactional
	 * 
	 * @Override public void updateUser(Integer id, Users user) {
	 * userDao.updateUser(id, user); }
	 */

	public void updateToken(Integer id, String token) {
		userDao.updateToken(id, token);
	}

	@Transactional
	@Override
	public void delete(Integer UserId) {
		userDao.delete(UserId);
	}

	@Override
	public Boolean authenticate(String username, String password) {
		return userDao.authenticate(username, password);
	}

	@Override
	public Users fetchUser(String username, String password) {
		return userDao.fetchUser(username, password);
	}

	@Override
	public Integer userRegistration(Users users) {
		return userDao.userRegistration(users);
	}

	@Override
	public Users fetchUserByEmail(String email) {
		return userDao.fetchUserByEmail(email);
	}

	@Override
	public Object UpdateProfile(Users user) {
		return userDao.UpdateProfile(user);
	}

}