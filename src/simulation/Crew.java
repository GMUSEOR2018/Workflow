package simulation;

public class Crew {
	String CrewName;
	Crewman CrewmanA;
	Crewman CrewmanB;
	Employee suporisvor;
	//Constructor for A Crew with two Crewman.
	public Crew(int CrewID, String nameA, int IDA, String nameB, int IDB, Employee suporisvor) {
		this.suporisvor=suporisvor;
		updateCrew(nameA, IDA, nameB, IDB, CrewID);
		this.CrewName= "A"+CrewID;
	}
	// return crew name
	private String getCrewName() {
		return CrewName;
	}
	//add crewman to this crew.
	private void updateCrew(String nameA, int IDA, String nameB, int IDB, int CrewID) {
		this.CrewmanA=new Crewman(IDA,  nameA,Shift.Day, CrewID,suporisvor);
		this.CrewmanB=new Crewman(IDB,  nameB,Shift.Day, CrewID, suporisvor);
	}
	// return the crewman info AS Array
	private Crewman[] getCrewman() {
		Crewman C[]= {CrewmanA, CrewmanB};
		return C;
	}

	protected void changeShift(Shift S) {
		this.CrewmanA.changeShift(S);
		this.CrewmanB.changeShift(S);
	}

	protected double Assesment(WorkOrder WO) {
		WO.updateLast();
		WO.updateNext(1);
		WO.updateStatus(Status.APPR);
		double duration=Distributions.exponential(1)+ Distributions.Triangular(0.083, 0.33, 1);//Assessment time + travel time
		return duration;
	}
	//Crew man send notification to customer
	protected  double Notify(WorkOrder WO) {
		if(WO.getStatus()==Status.TESTCOMP) {//notify for shut-off
			WO.schedule(2);//TODO check how many days in advance
		}
		else {//notify for testing
			WO.updateStatus(Status.TESTING);
			WO.updateLast();
			WO.updateNext(2);//TODO check how many days in advance
		}
		double duration=Distributions.exponential(0.5)+ Distributions.Triangular(0.083, 0.33, 1);//Notify time + travel time
		return duration;
	}

	// crew man testing valves
	protected double TestShut(WorkOrder WO){
		int comfirm=(int) Math.round(Distributions.exponential(0.5));
		WO.test(comfirm);
		double duration=Distributions.Triangular(1, 2, 4)+ Distributions.Triangular(0.083, 0.33, 1);//Testing time + travel time;
		return duration;
	}

	// Shut for construction
	protected double Shut(WorkOrder WO) {
		if(CrewmanA.Shift==Shift.Day) {
			WO.shut(0);//evening crew may able to recharge it.
		}
		WO.shut(1);//check time
		double duration=Distributions.Triangular(1, 2, 4)+ Distributions.Triangular(0.083, 0.33, 1);//shut-off time + travel time;
		return duration;
	}

	//Recharges 
	protected double Recharge(WorkOrder WO) {
		WO.Finsih();//TODO update the finished time.
		double duration=Distributions.exponential(1)+ Distributions.Triangular(0.083, 0.33, 1);//shut-off time + travel time;
		return duration;
	}

}
