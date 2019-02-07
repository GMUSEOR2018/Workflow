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
	
	protected void work(WorkOrder wo) {
		switch(wo.getStatus()) {
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
		if(WO.get(1).getStatus()==Status.TESTCOMP) {
			WO.get(1).updateStatus(Status.A);//TODO change name of this new Status			
		}
		else WO.get(1).updateStatus(Status.TESTING);
		WorkOrder TEMP=WO.get(1);
		this.WO=null;
		return TEMP;
	}
	
	// crew man testing valves
	private void TestShut(){
		WO.get(1).updateStatus(Status.TESTCOMP);
		if(WO.get(1).getSchedule()!=null) {
		Notify();
		}
		else ;
	}
	
	// Shut for construction
	private void Shut() {
		WO.get(1).updateStatus(Status.B);
		this.WO=null;
	}
	
	//Recharges main
	private void Recharge() {
		WO.get(1).Finsih(d);//TODO update the finished time.
		this.WO=null;
	}
}
