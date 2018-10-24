package simulation;
import java.util.*;

public class WorkOrder {
	private int ID;
	private Types Type;
	private Status Status;
	private Location Location;
	private boolean plan;
	private Date Report;
	private Date Schedule;
	private Date Finish;
	//Constructor
	public WorkOrder(int id, Types t, Location l,Date d ) {
		this.ID=id;
		this.Type=t;
		this.Location=l;
		this.Status= simulation.Status.APPR;//TODO: Initial with APPR.
		this.plan=false;
		this.Report=d; this.Schedule=null; this.Finish=null;
	}

	private void addPlan() {
		this.plan=true;
	}
	private void schedule(Date Status) {
		this.Schedule=Status;
	}
	private void updateStatus(Status s) {
		this.Status=s;
	}
	private void Finsih(Date F) {
		this.Finish=F;
	}
	//return order info
	private int getID() {return this.ID;}
	private Types getTypes() {return this.Type;}
	private Location getLoation() {return this.Location;}
	private Status getStatus() {return this.Status;}
	private boolean isPlan() {return this.plan;}
	private Date getReport() {return this.Report;}
	private Date getSchedule() {return this.Schedule;}
	private Date getFinish() {return this.Finish;}
}
