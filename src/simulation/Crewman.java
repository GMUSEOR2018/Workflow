package simulation;

import java.sql.Date;

public class Crewman extends Employee {
	int CrewID;
	Date d;
	public Crewman(int ID, String name, Shift S, int CrewID,Employee suporisvor) {
		this.ID=ID;
		this.name=name;
		this.CrewID=CrewID;
		this.Shift=S;
		this.Supervisor=suporisvor;
	}
}
