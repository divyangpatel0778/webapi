package com.dao;

import com.models.Users;

public interface UserDao {

	long save(Users user);

	Users get(long id);

	Boolean authenticate(String username, String password);

	// List<Users> list();

	// void updateUser(Integer id, Users user);

	void updateToken(Integer id, String token);

	void delete(Integer UserId);

	Users fetchUser(String username, String password);

	Integer userRegistration(Users users);

	Users fetchUserByEmail(String email);

	Object UpdateProfile(Users user);

}
