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

	protected void work(WorkOrder wo) {
		if(wo.isPlan()==false) {
			this.Supervisor.Assign(wo);
		}
		else Schedule();
	}
	
	private void Schedule() {
		WO.get(1).schedule(d);//TODO need change to desired date.
	}
}