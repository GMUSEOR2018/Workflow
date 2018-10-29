package simulation;

import java.util.Date;

public class Crewman extends Employee {
	int CrewID;
	public Crewman(int ID, String name, Shift S, int CrewID,Employee suporisvor) {
		this.ID=ID;
		this.name=name;
		this.CrewID=CrewID;
		this.Shift=S;
		this.Supervisor=suporisvor;
	}
	
	protected void work() {
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

	//Crew man send notification to customer
	private WorkOrder Notify() {
		if(WO.getStatus()==Status.TESTCOMP) {
			WO.updateStatus(Status.A);//TODO change name of this new Status			
		}
		else WO.updateStatus(Status.TESTING);
		WorkOrder TEMP=WO;
		this.WO=null;
		return TEMP;
	}
	
	// crew man testing valves
	private void TestShut(){
		WO.updateStatus(Status.TESTCOMP);
		if(WO.getSchedule()!=null) {
		Notify();
		}
		else ;
	}
	
	// Shut for construction
	private void Shut() {
		WO.updateStatus(Status.B);
		this.WO=null;
	}
	
	//Recharges main
	private void Recharge() {
		WO.Finsih(Date.from(null));//TODO update the finished time.
		this.WO=null;
	}
}
