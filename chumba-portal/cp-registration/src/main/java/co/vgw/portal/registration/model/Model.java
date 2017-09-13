package co.vgw.portal.registration.model;

import java.util.UUID;

public interface Model {
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param userName
	 * @param password
	 * @param phone
	 * @return UUID of the created user record.
	 */
	UUID registerUser(String firstName, String lastName, String userName, String password, String phone);
}
