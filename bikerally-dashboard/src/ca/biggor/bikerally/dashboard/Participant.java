package ca.biggor.bikerally.dashboard;

public class Participant {

	private String id;
	private String firstName;
	private String lastName;
	private String riderNumber;
	private String teamName;

	public Participant(String id, String firstName, String lastName, String riderNumber, String teamName) {
		this.id = (id != null && !id.trim().isEmpty()) ? id: "";
		this.firstName = (firstName != null && !firstName.trim().isEmpty()) ? firstName: "";
		this.lastName = (lastName != null && !lastName.trim().isEmpty()) ? lastName: "";
		this.riderNumber = (riderNumber != null && !riderNumber.trim().isEmpty()) ? riderNumber: "";
		this.teamName = (teamName != null && !teamName.trim().isEmpty()) ? teamName: "";
	}

}
