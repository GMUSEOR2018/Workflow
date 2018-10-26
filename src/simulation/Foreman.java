package simulation;

import java.util.Date;

public class Foreman extends Employee {

	public Foreman(int ID, int suporisvorID, String name) {
		this.ID=ID;
		this.supervisorID=suporisvorID;
		this.name=name;
		this.Shift=simulation.Shift.Day;
	}
	protected void Assign(WorkOrder WO) {
		this.WO=WO;
		Schedule();
	}
	private void Schedule() {
		WO.schedule(Date.from(null));//TODO need change to desired date.
	}
}
