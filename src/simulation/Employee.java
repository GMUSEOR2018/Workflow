package simulation;
// Employee as Parent class
abstract class Employee {
	protected int ID;
	protected String name;
	protected WorkOrder[] WO;
	protected Shift Shift;
	protected Employee Supervisor;
	
	protected void Assign(WorkOrder[] WO) {
		this.WO=WO;
		work();
	}
	protected void work() {}
	protected void changeShift(Shift S) {
		this.Shift=S;
	}
	protected WorkOrder[] getWork() {
		
		return WO;
	}
}
