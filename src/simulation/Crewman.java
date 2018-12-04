package simulation;

import java.sql.Date;

public class Crewman extends Employee {
	int CrewID;
	Date d;
	public Crewman(int ID, String name, Shift S, int CrewID,Employee suporisvor) {
		this.ID=ID;
		this.name=name;
		this.CrewID=CrewID;
		this.Shift=S;
		this.Supervisor=suporisvor;
	}
	
	protected void work() {
		switch(WO[0].getStatus()) {
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
		if(WO[0].getStatus()==Status.TESTCOMP) {
			WO[0].updateStatus(Status.A);//TODO change name of this new Status			
		}
		else WO[0].updateStatus(Status.TESTING);
		WorkOrder TEMP=WO[0];
		this.WO=null;
		return TEMP;
	}
	
	// crew man testing valves
	private void TestShut(){
		WO[0].updateStatus(Status.TESTCOMP);
		if(WO[0].getSchedule()!=null) {
		Notify();
		}
		else ;
	}
	
	// Shut for construction
	private void Shut() {
		WO[0].updateStatus(Status.B);
		this.WO=null;
	}
	
	//Recharges main
	private void Recharge() {
		WO[0].Finsih(d);//TODO update the finished time.
		this.WO=null;
	}
}
