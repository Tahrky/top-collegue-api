/**
 *
 */
package dev.entities;

/**
 *
 * @author BIRABEN-BIANCHI Hugo
 */
public class Email {
	private String email;

	Email () {

	}

	Email (String email) {
		this.email = email;
	}

	/**
	 * Getter
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
