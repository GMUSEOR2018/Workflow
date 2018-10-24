package simulation;

public class Engineer extends Employee {

	public Engineer(int ID, int suporisvorID, String name) {
		// TODO Auto-generated constructor stub
		this.ID=ID;
		this.supervisorID=suporisvorID;
		this.name=name;
		this.Shift=simulation.Shift.Day;
	}

}
