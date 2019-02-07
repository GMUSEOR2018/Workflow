package simulation;

import java.util.ArrayList;
import java.util.List;

// Employee as Parent class
abstract class Employee {
	protected int ID;
	protected String name;
	protected Shift Shift=simulation.Shift.Day;
	protected Employee Supervisor,self;
	List<WorkOrder> WO =new ArrayList<WorkOrder>();
	
	protected void Assign(WorkOrder wo2) {
		WO.add(wo2);
		work(wo2);
	}
	protected void work(WorkOrder wo) {}
	protected void changeShift(Shift S) {
		this.Shift=S;
	}
	protected WorkOrder[] getWork() {
		WorkOrder[] WO2 =WO.toArray(new WorkOrder[WO.size()]);
		return WO2;
	}
}
