package simulation;

import java.util.Date;

public class Foreman extends Employee {

	public Foreman(int ID, String name, Employee suporisvor) {
		this.ID=ID;
		this.name=name;
		this.Shift=simulation.Shift.Day;
		this.Supervisor=suporisvor;
	}

	protected void work() {
		if(WO.isPlan()==false) {
			this.Supervisor.Assign(WO);
		}
		else Schedule();
	}
	private void Schedule() {
		WO.schedule(Date.from(null));//TODO need change to desired date.
	}
}
