package simulation;

public class Crew {
	String CrewName;
	Crewman CrewmanA;
	Crewman CrewmanB;
	Employee suporisvor;

	public Crew(int CrewID, String nameA, int IDA, String nameB, int IDB, Employee suporisvor) {	//Constructor for A Crew with two Crewman.
		this.suporisvor=suporisvor;
		updateCrew(nameA, IDA, nameB, IDB, CrewID);
		this.CrewName= "A"+CrewID;
	}
	
	private String getCrewName() {
		return CrewName;
	}
	
	private void updateCrew(String nameA, int IDA, String nameB, int IDB, int CrewID) {//add crewman to this crew.
		this.CrewmanA=new Crewman(IDA,  nameA,Shift.Day, CrewID,suporisvor);
		this.CrewmanB=new Crewman(IDB,  nameB,Shift.Day, CrewID, suporisvor);
	}
	
	private Crewman[] getCrewman() {// return the crewman info AS Array
		Crewman C[]= {CrewmanA, CrewmanB};
		return C;
	}

	protected void changeShift(Shift S) {
		this.CrewmanA.changeShift(S);
		this.CrewmanB.changeShift(S);
	}

	protected double Assesment(WorkOrder WO) {
		WO.Assesment(1);
		double duration=Distributions.Triangular(0.5, 1, 2)+ Distributions.Triangular(0.0833, 0.333, 1);//Assessment time + travel time
		return duration;
	}
	//Crew man send notification to customer
	protected  double Notify(WorkOrder WO) {
		if(WO.getStatus()==Status.TESTCOMP) {//notify for shut-off
			WO.Notify2(2);
		}
		else {//notify for testing
			WO.Notify1(2);
		}
		double duration=Distributions.Triangular(1, 1.5, 3)+ Distributions.Triangular(0.0833, 0.333, 1);//Notify time + travel time
		return duration;
	}

	// crew man testing valves
	protected double TestShut(WorkOrder WO){
		int comfirm=(int) Math.round(Distributions.exponential(0.6667));
		WO.test(comfirm);
		double duration=Distributions.Triangular(1, 2, 4)+ Distributions.Triangular(0.0833, 0.333, 1);//Testing time + travel time;
		return duration;
	}

	// Shut for construction
	protected double Shut(WorkOrder WO) {
		if(CrewmanA.Shift==Shift.Day) {
			WO.shut(0);//evening crew may able to recharges it.
		}
		else WO.shut(1);//check time
		double duration=Distributions.Triangular(0.5, 0.667, 1)+ Distributions.Triangular(0.0833, 0.333, 1);//shut-off time + travel time;
		return duration;
	}

	//Recharges 
	protected double Recharge(WorkOrder WO) {
		WO.Finsih();
		double duration=Distributions.Triangular(0.5, 0.667, 1)+ Distributions.Triangular(0.0833, 0.333, 1);//shut-off time + travel time;
		return duration;
	}

}
