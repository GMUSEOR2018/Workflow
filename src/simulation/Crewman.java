package simulation;

import java.util.Date;

public class Crewman extends Employee {

	public Crewman(int ID, int suporisvorID, String name) {
		this.ID=ID;
		this.supervisorID=suporisvorID;
		this.name=name;
		this.Shift=simulation.Shift.Day;
	}
	protected void Assign(WorkOrder WO) {
		this.WO=WO;
		switch(WO.getStatus()) {
		case PENDING:      Notify();       	break;
		case TESTCOMP:     Notify();        break;
		case TESTING:      TestShut();     	break;
		case A:        	   Shut();        	break;
		case B: 		   Recharge();      break;
		default:
			System.out.println("ERROR: Assign to wrong employee(s)");
			System.exit(1); //Fail-fast
		}
	}

	private WorkOrder Notify() {
		if(WO.getStatus()==Status.TESTCOMP) {
			WO.updateStatus(Status.A);//TODO change name of this new Status			
		}
		else WO.updateStatus(Status.TESTING);
		WorkOrder TEMP=WO;
		this.WO=null;
		return TEMP;
	}
	
	private void TestShut(){
		WO.updateStatus(Status.TESTCOMP);
		if(WO.getSchedule()!=null) {
		Notify();
		}
		else ;
	}
	
	private void Shut() {
		WO.updateStatus(Status.B);
		this.WO=null;
	}
	
	private void Recharge() {
		WO.Finsih(Date.from(null));//TODO update the finished time.
		this.WO=null;
	}
}
