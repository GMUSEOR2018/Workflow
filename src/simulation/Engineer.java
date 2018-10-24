package simulation;
//Engineer for DCB
public class Engineer extends Employee {
//Construction
	public Engineer(int ID, int suporisvorID, String name) {
		this.ID=ID;
		this.supervisorID=suporisvorID;
		this.name=name;
		this.Shift=Shift.Day;
	}
	public void Assign(WorkOrder WO) {
		this.WO=WO;
		makePlan();
	}
	private void makePlan() {
		WO.setPlan();
	}
}
