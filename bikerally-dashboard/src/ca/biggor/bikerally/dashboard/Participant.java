package ca.biggor.bikerally.dashboard;

public class Participant {

	private String id;
	private String firstName;
	private String lastName;
	private String riderNumber;
	private String teamName;
	private Boolean steeringCommittee;
	private Boolean teamLead;
	private Boolean trainingRideCoach;

	public Participant(String id, String firstName, String lastName, String riderNumber, String teamName, Boolean steeringCommittee, Boolean teamLead, Boolean trainingRideCoach) {
		this.id = (id != null && !id.trim().isEmpty()) ? id: "";
		this.firstName = (firstName != null && !firstName.trim().isEmpty()) ? firstName: "";
		this.lastName = (lastName != null && !lastName.trim().isEmpty()) ? lastName: "";
		this.riderNumber = (riderNumber != null && !riderNumber.trim().isEmpty()) ? riderNumber: "";
		this.teamName = (teamName != null && !teamName.trim().isEmpty()) ? teamName: "";
		this.steeringCommittee = steeringCommittee;
		this.teamLead = teamLead;
		this.trainingRideCoach = trainingRideCoach;
	}

}
