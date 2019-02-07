package simulation;

import java.sql.Date;

public class Foreman extends Employee {
	WorkOrder[] Wo;
	Date d;
	
	public Foreman(int ID, String name, Employee suporisvor) {
		this.ID=ID;
		this.name=name;
		this.Supervisor=suporisvor;
	}
	// create crew
	Crew A1 = new Crew(1,"3",3,"4",4,self);
	Crew A2 = new Crew(1,"5",5,"6",6,self);
	Crew A3 = new Crew(1,"7",7,"8",8,self);
	Crew A4 = new Crew(1,"9",9,"10",10,self);
	Crew A5 = new Crew(1,"11",11,"12",12,self);
	Crew A6 = new Crew(1,"13",13,"14",14,self);
	
	public  void receive(WorkOrder[] wO) {
		this.Wo=wO;
	}
	
	protected void work(WorkOrder wo) {
		if(wo.isPlan()==false) {
			this.Supervisor.Assign(wo);
		}
		else Schedule();
	}
	protected void addself(Employee self) {
		this.self=self;
	}
	private void Schedule() {
		WO.get(1).schedule(d);//TODO need change to desired date.
	}
}