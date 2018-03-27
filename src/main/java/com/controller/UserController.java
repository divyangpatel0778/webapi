package com.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.models.ErrorObject;
import com.models.JwtUser;
import com.models.Persons;
import com.models.UserLogin;
import com.models.UserLoginDetails;
import com.models.UserRegist;
import com.models.UserRegistration;
import com.models.UserRegistrationDetails;
import com.models.Users;
import com.security.JwtGenerator;
import com.security.JwtValidator;
import com.service.UserService;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	private static final Logger LOGGER = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/auth/token")
	public UserLogin authenticateToken(@RequestBody Users user) {
		UserLogin userLogin = new UserLogin();
		Users userFetch = null;
		try {
			if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
				LOGGER.info("Username and Password Required Success=2");
				userLogin.setSuccess(2);
				userLogin.setMsg("Username and Password Required");
				userLogin.setData(new UserLoginDetails());

			} else {

				userFetch = userService.fetchUser(user.getUsername(), user.getPassword());

				if (userFetch != null) {
					userLogin.setMsg("Login Successfully");
					userLogin.setSuccess(1);
					JwtUser jwtUser = new JwtUser();
					jwtUser.setUserName(userFetch.getUsername());
					jwtUser.setId(userFetch.getId());
					UserLoginDetails userDetails = new UserLoginDetails();
					userDetails.setId(userFetch.getId());
					userDetails.setEmail(userFetch.getUserEmail());
					userDetails.setFull_name(userFetch.getPerson().getFullName());
					String token = JwtGenerator.generate(jwtUser);
					userDetails.setToken(token);
					userService.updateToken(userFetch.getId(), token);
					userLogin.setData(userDetails);
				} else {
					LOGGER.info("Invalid Username and Password Success=3");
					userLogin.setSuccess(3);
					userLogin.setMsg("Invalid Username and Password");
					userLogin.setData(new UserLoginDetails());
				}

			}
		} catch (Exception e) {
			LOGGER.error(e);
			LOGGER.info("Invalid Username and Password Success=3");
			userLogin.setSuccess(3);
			userLogin.setMsg("Invalid Username and Password");
			userLogin.setData(new UserLoginDetails());
		}
		return userLogin;
	}

	@RequestMapping(value = "/user/UpdateProfile")
	public Object UpdateProfile(@RequestBody Users user) {
		ModelMap model = new ModelMap();
		ErrorObject error = new ErrorObject("Not Found", 404);
		Boolean isValidate = true;
		if (StringUtils.isBlank(user.getPerson().getFirstName())) {
			LOGGER.info("FirstName is required Success=2");
			error.setMsg("FirstName is required");
			error.setErrorCode(406);
			isValidate = false;
		}

		if (StringUtils.isBlank(user.getPerson().getMiddleName())) {
			LOGGER.info("MiddleName is required Success=3");
			error.setMsg("MiddleName is required");
			error.setErrorCode(406);
			isValidate = false;
		}

		if (StringUtils.isBlank(user.getPerson().getLastName())) {
			LOGGER.info("LastName is required Success=4");
			error.setMsg("LastName is required");
			error.setErrorCode(406);
			isValidate = false;
		}

		if (StringUtils.isBlank(user.getPerson().getPhone()) || !StringUtils.isNumeric(user.getPerson().getPhone())
				|| (StringUtils.length(user.getPerson().getPhone()) != 10)) {
			LOGGER.info("Phone can not be empty and it must be 10 digit numeric value Success=5");
			error.setMsg("Phone can not be empty and it must be 10 digit numeric value");
			error.setErrorCode(406);
			isValidate = false;
		}

		if (isValidate == false) {

			return error;
		} else {
			try {
				return userService.UpdateProfile(user);
				// return user;
			} catch (Exception e) {
				LOGGER.error(e);

			}
			return model.put("msg", "updation Failed");
			// return user;
		}
	}

	@PostMapping(value = "/user/token/validate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ModelMap> validateToken(@RequestBody Users user) {
		ModelMap model = new ModelMap();
		JwtValidator validator = new JwtValidator();
		JwtUser jwtUser = validator.validate(user.getToken());
		if (jwtUser == null) {
			model.put("Is UnAuthenticated", "Token Expired");
		} else {
			model.put("Is Authenticated", jwtUser);
		}
		return ResponseEntity.ok().body(model);
	}

	/*---Get a user by id---*/
	@GetMapping("/user/{id}")
	public ResponseEntity<Users> get(@PathVariable("id") long id) {
		Users user = userService.get(id);
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/user/fetchUser")
	public ResponseEntity<Users> fetchUser(@RequestBody Users user) {
		Users userResult = userService.fetchUser(user.getUsername(), user.getPassword());
		return ResponseEntity.ok().body(userResult);
	}

	/*---get all books---
	@GetMapping("/users")
	public ResponseEntity<List<Users>> list() {
		List<Users> users = userService.list();
		return ResponseEntity.ok().body(users);
	}
	
	---Update a user by id---
	@PutMapping(value = "/user/update/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Users> updateUser(@PathVariable("id") Integer id, @RequestBody Users user) {
		userService.updateUser(id, user);
		return ResponseEntity.ok().body(user);
	}*/

	/*---Delete a user by id---*/
	@GetMapping("/user/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("UserId") Integer UserId) {
		userService.delete(UserId);
		return ResponseEntity.ok().body("User has been deleted successfully.");
	}

	@RequestMapping(value = "/user/register")
	public Object registerUser(@RequestBody UserRegistration userRegistration) {
		UserRegist userRegist = new UserRegist();
		ErrorObject error = new ErrorObject("Not Found", 404);
		Boolean isValidate = true;
		if (StringUtils.isBlank(userRegistration.getIspatient())) {
			error.setMsg("isPatient should be either 0 or 1");
			error.setErrorCode(406);
			isValidate = false;
		}
		if (StringUtils.isBlank(userRegistration.getIsdoctor())) {
			error.setMsg("isDoctor should be either 0 or 1");
			error.setErrorCode(406);
			isValidate = false;
		}
		if (StringUtils.isEmpty(userRegistration.getPassword())
				|| StringUtils.isBlank(userRegistration.getPassword())) {
			error.setMsg("password can not be empty");
			error.setErrorCode(406);
			isValidate = false;
		}
		if (StringUtils.isEmpty(userRegistration.getFirstname())
				|| StringUtils.isBlank(userRegistration.getFirstname())) {
			error.setMsg("Firstname can not be empty");
			error.setErrorCode(406);
			isValidate = false;
		}
		if (StringUtils.isEmpty(userRegistration.getLastname())
				|| StringUtils.isBlank(userRegistration.getLastname())) {
			error.setMsg("Lastname can not be empty");
			error.setErrorCode(406);
			isValidate = false;
		}
		if (StringUtils.isEmpty(userRegistration.getEmail()) || StringUtils.isBlank(userRegistration.getEmail())) {
			error.setMsg("Email can not be empty");
			error.setErrorCode(406);
			isValidate = false;
		}
		if (StringUtils.isEmpty(userRegistration.getPhone()) || StringUtils.isBlank(userRegistration.getPhone())
				|| !StringUtils.isNumeric(userRegistration.getPhone())
				|| (StringUtils.length(userRegistration.getPhone()) < 10)) {
			error.setMsg("Phone can not be empty and it must be at least 10 digit numeric value");
			error.setErrorCode(406);
			isValidate = false;
		}
		if (StringUtils.isBlank(userRegistration.getIsdoctor())) {
			error.setMsg("IsDoctor can not be empty");
			error.setErrorCode(406);
			isValidate = false;
		}
		if (StringUtils.isBlank(userRegistration.getIspatient())) {
			error.setMsg("IsPatient can not be empty");
			error.setErrorCode(406);
			isValidate = false;
		}
		if ("0".equals(userRegistration.getIspatient()) && "0".equals(userRegistration.getIsdoctor())) {
			error.setMsg("IsPatient and isDoctor can not be empty");
			error.setErrorCode(406);
			isValidate = false;
		}
		if (userService.fetchUserByEmail(userRegistration.getEmail()) != null) {
			error.setMsg("User already exists.");
			isValidate = false;
		}

		if (isValidate == false) {
			return error;
			/*
			 * userLogin.setSuccess(error.getErrorCode());
			 * userLogin.setMsg(error.getMsg());
			 */
			// userLogin.setData(new UserLoginDetails());
		} else {
			try {
				Users users = new Users();
				Persons persons = new Persons();
				persons.setFirstName(userRegistration.getFirstname());
				persons.setLastName(userRegistration.getLastname());
				persons.setPhone(userRegistration.getPhone());
				users.setPerson(persons);
				users.setPassword(userRegistration.getPassword());
				users.setUsername(userRegistration.getEmail());
				users.setUserEmail(userRegistration.getEmail());
				if (StringUtils.isNotBlank(userRegistration.getIsdoctor())
						&& "1".equals(userRegistration.getIsdoctor())) {
					users.setType("Doctor");
				} else if (StringUtils.isNotBlank(userRegistration.getIspatient())
						&& "1".equals(userRegistration.getIspatient())) {
					users.setType("Patient");
				}

				Integer userId = userService.userRegistration(users);
				userRegist.setMsg("Registration Successfully");
				userRegist.setSuccess(1);

				UserRegistrationDetails userRegistrationDetails = new UserRegistrationDetails();
				userRegistrationDetails.setId(userId);
				userRegistrationDetails.setEmail(users.getUserEmail());
				userRegistrationDetails
						.setFull_name(userRegistration.getFirstname() + " " + userRegistration.getLastname());

				userRegist.setData(userRegistrationDetails);

			} catch (Exception e) {
				userRegist.setSuccess(2);
				userRegist.setMsg("Invalid Data");
				userRegist.setData(new UserRegistrationDetails());
			}
		}
		return userRegist;
	}
}