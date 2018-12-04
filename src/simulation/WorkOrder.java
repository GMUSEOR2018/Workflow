package simulation;
import java.sql.Date;

public class WorkOrder {
	private int ID;
	private Types Type;
	private Status Status;
	private Location Location;
	private boolean plan;
	private Date Report, Schedule, Finish;
	@SuppressWarnings("deprecation")
	Date ASSIGN = new Date(117,9,1);

	//Constructor
	@SuppressWarnings("deprecation")
	public WorkOrder(int id, Types t, Location l,int d, boolean P ) {
		this.ID=id;
		this.Type=t;
		this.Location=l;
		this.Status= simulation.Status.APPR;//TODO: Check if initial with APPR.
		this.plan=P;
		if (d!=0) {
			ASSIGN.setDate(d);
		}
		this.Report=ASSIGN; this.Schedule=null; this.Finish=null;
	}
	
	protected void schedule(Date Status) {
		this.Schedule=Status;
	}
	protected void updateStatus(Status s) {
		this.Status=s;
	}
	protected void Finsih(Date F) {
		this.Status=simulation.Status.COMP; //update status after case close
		this.Finish=F;
	}
	protected void setPlan() {
		this.plan=true;
	}
	//return order info
	protected int getID() {return this.ID;}
	protected Types getTypes() {return this.Type;}
	protected Location getLoation() {return this.Location;}
	protected Status getStatus() {return this.Status;}
	protected boolean isPlan() {return this.plan;}
	protected Date getReport() {return this.Report;}
	protected Date getSchedule() {return this.Schedule;}
	protected Date getFinish() {return this.Finish;}
	
	public  String toString() {
		String S= ID+"," + Location.toString()+ ","+ Status.toString()+","+ Report.toString()+ "," +Type.toString()+"\n";
		return S;}
}
