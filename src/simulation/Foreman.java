package simulation;

import java.sql.Date;

public class Foreman extends Employee {

	Date d;
	public Foreman(int ID, String name, Employee suporisvor) {
		this.ID=ID;
		this.name=name;
		this.Shift=simulation.Shift.Day;
		this.Supervisor=suporisvor;
	}

	protected void work() {
		if(WO[0].isPlan()==false) {
			this.Supervisor.Assign(WO);
		}
		else Schedule();
	}
	private void Schedule() {
		WO[0].schedule(d);//TODO need change to desired date.
	}
}