package simulation;

public class Crew {
	String CrewName;
	Crewman CrewmanA;
	Crewman CrewmanB;
	Employee suporisvor;
	//Constructor for A Crew with two Crewman.
	private Crew(int CrewID, String nameA, int IDA, String nameB, int IDB, Employee suporisvor) {
		this.suporisvor=suporisvor;
		updateCrew(nameA, IDA, nameB, IDB, CrewID);
		this.CrewName= "A"+CrewID;
	}
	// return crew name
	private String getCrewName() {
		return CrewName;
	}
	//add crewman to this crew.
	private void updateCrew(String nameA, int IDA, String nameB, int IDB, int CrewID) {
		this.CrewmanA=new Crewman(IDA,  nameA,Shift.Day, CrewID,suporisvor);
		this.CrewmanB=new Crewman(IDB,  nameB,Shift.Day, CrewID, suporisvor);
	}
	// return the crewman info AS Array
	private Crewman[] getCrew() {
		Crewman C[]= {CrewmanA, CrewmanB};
		return C;
	}
	
	private void changeShift(Shift S) {
		this.CrewmanA.changeShift(S);
		this.CrewmanB.changeShift(S);
	}
	
	
}
