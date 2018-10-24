package simulation;
// Employee as Parent class
class Employee {
	private int ID;
	private int supervisorID;
	private String name;
	private WorkOrder wo;
	private Shift S;
	
	private void Assign(WorkOrder WO) {
		this.wo=WO;
	}
	
}
