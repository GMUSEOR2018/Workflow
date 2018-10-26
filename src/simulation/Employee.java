package simulation;
// Employee as Parent class
abstract class Employee {
	protected int ID;
	protected int supervisorID;
	protected String name;
	protected WorkOrder WO;
	protected Shift Shift;
	
	protected void Assign(WorkOrder WO) {
		this.WO=WO;
	}
	
}
