package simulation;
import java.sql.Date;

public class WorkOrder {
	private int ID, Delay;
	private Types Type;
	private Status Status;
	//private Location Location;
	private boolean plan;
	private Date Report, Assesment, Notify1, Notify2,Finish, Test, Shut, Last, next;
	Date ASSIGN = new Date(117,7,1);
	//Constructor
	@SuppressWarnings("deprecation")
	public WorkOrder(int id, Types t,int d, boolean P ) {//, Location l) {
		this.ID=id;this.Type=t;this.plan=P;
		//this.Location=l;
		//this.Delay=0;
		this.Status= simulation.Status.WAPPR;
		if (d!=0) {//check for valid date
			ASSIGN.setDate(d);
		}
		this.Report=(Date) ASSIGN.clone(); 
		this.Last=(Date) this.ASSIGN.clone();
		this.next=(Date) this.ASSIGN.clone();
		this.Notify2= new Date(117,6,1);this.Assesment=new Date(117,6,1);this.Notify1=new Date(117,6,1);
		this.Finish=new Date(117,6,1);this.Test=new Date(117,6,1);;this.Shut=new Date(117,6,1);;
	}

	protected void setPlan(int d) {
		this.plan=true;
		if(Status==simulation.Status.TESTCOMP) {
			updateLast();
		}
		else if(Status==simulation.Status.WAPPR) {
			if(Type==Types.SHMTR) {
				updateStatus(simulation.Status.PENDING);
			}
			else updateStatus(simulation.Status.PLANCOMP);
		}
		int day=next.getDate()+d;
		Date Temp=(Date) next.clone();
		Temp.setDate(day);
		if (Temp.getDay()==0){//void Saturday
			d+=2;
		}
		else if (Temp.getDay()==6) {//void Sunday	
			d+=3;
		}
		updateNext(d);
	}
	
	protected void Assesment(int d) {
		updateStatus(simulation.Status.APPR);
		this.Assesment=(Date) this.next.clone();
		updateLast();
		updateNext(d);	
	}
	
	protected void Notify1(int d) {
		updateStatus(simulation.Status.TESTING);
		this.Notify1=(Date) this.next.clone();
		updateLast();
		updateNext(d);
	}

	protected void test(int d) {
		updateStatus(simulation.Status.TESTCOMP);
		this.Test=(Date) this.next.clone();
		updateLast();
		updateNext(d);
		this.plan=false;
	}

	protected void Notify2(int d) {
		updateStatus(simulation.Status.A);//TODO change name of this new Status
		this.Notify2=(Date) this.next.clone();
		updateLast();
		updateNext(d);
	}

	protected void shut(int d) {
		updateStatus(simulation.Status.B);
		updateLast();
		this.Shut=(Date) this.next.clone();
		if(d==1) {
			updateNext(d);
		}
	}

	protected void Finsih() {
		updateStatus(simulation.Status.COMP); //update status after case close
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
		if (Temp.getDay()==0){//void Saturday
			this.next.setDate(day+1);
		}
		else if (Temp.getDay()==6) {//void Sunday
			this.next.setDate(day+2);
		}
		else this.next=(Date)Temp.clone();
	}

	protected void delay(){//counting for delay
		Delay++;
	}
	
	protected void Clone(WorkOrder wo1) {//Clone a WO
		this.ID=wo1.getID();this.Type=wo1.getTypes();
		//this.Location=wo1.getLoation();
		this.plan=wo1.isPlan();
		this.Status=wo1.getStatus();this.Report=wo1.getReport(); this.Last=wo1.getLast();
		this.Notify2=wo1.getNotify2();this.Finish=wo1.getFinish();this.Test=wo1.getTest();
		this.Shut=wo1.getShut();this.next=wo1.getNext();
	}

	//return Work order info
	protected int getID() {return this.ID;}
	
	protected Types getTypes() {return this.Type;}
	
	//protected Location getLoation() {return this.Location;}
	
	protected Status getStatus() {return this.Status;}
	
	protected boolean isPlan() {return this.plan;}
	
	protected Date getReport() {return this.Report;}
	
	protected Date getAssesement() {return this.Assesment;}
	
	protected Date getNotify1() {return this.Notify1;}
	
	protected Date getNotify2() {return this.Notify2;}
	
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
		if(this.Notify2==null){
			schedule=" ";
		}
		else {
			schedule = this.Notify2.toString();
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
		String S= ID+"," + Status.toString()+","+ report+ 
				"," +Type.toString()+ ","+","+test+","+schedule+","+shut+","+finish+","+","+Last+","+Next+"\n";//Location.toString()+ ","+ 
		return S;
	}
}
