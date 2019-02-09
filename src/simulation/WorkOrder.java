package simulation;
import java.sql.Date;

public class WorkOrder {
	private int ID;
	private Types Type;
	private Status Status;
	private Location Location;
	private boolean plan;
	private Date Report, Schedule,Finish, Test, Shut, Last, next;
	@SuppressWarnings("deprecation")
	Date ASSIGN = new Date(117,9,1);

	//Constructor
	@SuppressWarnings("deprecation")
	public WorkOrder(int id, Types t, Location l,int d, boolean P ) {
		this.ID=id;this.Type=t;this.Location=l;this.plan=P;
		this.Status= simulation.Status.WAPPR;
		if (d!=0) {
			ASSIGN.setDate(d);
		}
		this.Report=(Date) ASSIGN.clone(); this.Last=(Date) this.ASSIGN.clone(); 
		this.Schedule=null;this.Finish=null;this.Test=null;this.Shut=null;this.next=(Date) this.ASSIGN.clone();
	}

	protected void setPlan(int d) {
		this.plan=true;
		if(Status==simulation.Status.TESTCOMP) {
			updateLast();
		}
		else if(Status==simulation.Status.WAPPR) {
			if(Type==Types.SHINV) {
				this.Status= simulation.Status.PENDING;
			}
			else this.Status= simulation.Status.PLANCOMP;
		}
		updateNext(d);
	}

	protected void test(int d) {
		updateStatus(simulation.Status.TESTCOMP);
		this.Test=(Date) this.next.clone();
		updateLast();
		updateNext(d);
		this.plan=false;
	}

	protected void schedule(int d) {
		updateStatus(simulation.Status.A);//TODO change name of this new Status
		updateLast();
		updateNext(d);
		this.Schedule=(Date) this.next.clone();
	}


	protected void shut(int d) {
		updateStatus(simulation.Status.B);
		this.Shut=(Date) this.next.clone();
		updateLast();
		updateNext(d);
	}

	protected void Finsih() {
		this.Status=simulation.Status.COMP; //update status after case close
		this.Finish=(Date) this.next.clone();
	}

	protected void updateStatus(Status s) {
		this.Status=s;
	}


	protected void updateLast() {
		this.Last=(Date) this.next.clone();
	}

	@SuppressWarnings("deprecation")
	protected void updateNext(int d) {
		int day=next.getDate()+d;
		Date Temp=(Date) next.clone();
		Temp.setDate(day);
		if (Temp.getDay()==0){
			this.next.setDate(day+1);
		}
		else if (Temp.getDay()==6) {
			this.next.setDate(day+2);
		}
		else this.next=(Date)Temp.clone();
	}

	protected void Clone(WorkOrder wo1) {
		this.ID=wo1.getID();this.Type=wo1.getTypes();this.Location=wo1.getLoation();this.plan=wo1.isPlan();
		this.Status=wo1.getStatus();this.Report=wo1.getReport(); this.Last=wo1.getLast();
		this.Schedule=wo1.getSchedule();this.Finish=wo1.getFinish();this.Test=wo1.getTest();
		this.Shut=wo1.getShut();this.next=wo1.getNext();
	}

	//return Work order info
	protected int getID() {return this.ID;}
	protected Types getTypes() {return this.Type;}
	protected Location getLoation() {return this.Location;}
	protected Status getStatus() {return this.Status;}
	protected boolean isPlan() {return this.plan;}
	protected Date getReport() {return this.Report;}
	protected Date getSchedule() {return this.Schedule;}
	protected Date getFinish() {return this.Finish;}
	protected Date getLast() {return this.Last;}
	protected Date getNext() {return this.next;}
	protected Date getShut() {return this.Shut;}
	protected Date getTest() {return this.Test;}

	public  String toString() {
		String Last=this.Last.toString();
		String report = this.Report.toString();
		String finish,schedule,test,shut,Next;
		if(this.next==null){
			Next=" ";
		}
		else {
			Next = this.next.toString();
		}
		if(this.Finish==null){
			finish=" ";
		}
		else {
			finish = this.Finish.toString();
		}
		if(this.Schedule==null){
			schedule=" ";
		}
		else {
			schedule = this.Schedule.toString();
		}
		if(this.Test==null){
			test=" ";
		}
		else {
			test = this.Test.toString();
		}
		if(this.Shut==null){
			shut=" ";
		}
		else {
			shut = this.Shut.toString();
		}
		String S= ID+"," + Location.toString()+ ","+ Status.toString()+","+ report+ 
				"," +Type.toString()+ ","+","+test+","+schedule+","+shut+","+finish+","+","+Last+","+Next+"\n";

		return S;
	}
}
