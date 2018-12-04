package simulation;
//Engineer for DCB
public class Engineer extends Employee {
//Construction
	public Engineer(int ID, String name, Employee suporisvor) {
		this.ID=ID;
		this.name=name;
		this.Shift=simulation.Shift.Day;
		this.Supervisor=suporisvor;
	}

	protected void work() {
		makePlan();
	}
	private void makePlan() {
		WO[0].setPlan();
	}
}
