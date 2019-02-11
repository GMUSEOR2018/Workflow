package simulation;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class Current {
	Boolean Demo = true;//trigger of Demo mode.
	int replication;//Number of replications.
	int x=0,c=3;int count=0;
	int crewNum=8,DayCrew=6,Evening=1,Night=1;
	List<WorkOrder> WO =new ArrayList<WorkOrder>();
	WorkOrder[] wo;Integer[] queue;int[] Queue;
	List<Integer> queueCount = new ArrayList<Integer>();
	Engineer E = new Engineer(1, "Kens", null);
	Foreman F= new Foreman(2,"Andre",E);

	@SuppressWarnings("deprecation")
	public void run() throws IOException, CloneNotSupportedException {
		WOgen g = new WOgen();
		g.setUp();//generate WO for entire year
		this.wo=g.Output();
		Date d = new Date(117,9,1);//Start date
		Date end = new Date(118,8,30);//End date
		while(d.compareTo(end)<0) {
			if(d.getDay()!=6 && d.getDay()!=0) {
				count=0;
			}
			Day(d);
			Evening(d);
			Night(d);
			for(int i=0;i<wo.length;i++) {
				if( wo[i].getNext().compareTo(d)==0 & wo[i].getStatus()!=Status.COMP) {//find unfinished order
					wo[i].updateNext(1);
					count++;
				}	
			}
			queueCount.add(count);
			x +=1;
			d=  new Date(117,9,1);
			d.setDate(x);
		}
		queue =queueCount.toArray(new Integer[queueCount.size()]);
		Queue =new int[queue.length];
		for (int i = 0; i < queue.length; i++) {
		    Queue[i] = queueCount.get(i);
		}
		queueCount.clear();
	}

	public void Day(Date d) throws IOException, CloneNotSupportedException {//day crew operation
		Crew[] crews = new Crew[DayCrew];
		for(int i=0;i<DayCrew;i++){
			crews[i]= new Crew(i, Integer.toString(c),c,Integer.toString(c+1),c+1,F);
			c+=2;
		}
		F.addCrew(crews);//add crew to foreman
		for(int i=0;i<wo.length;i++) {
			if(wo[i].getReport().compareTo(d)==0 & wo[i].getStatus()==Status.WAPPR ) {//for making test plan
				E.work(wo[i]);
			}
			else if(wo[i].getNext().compareTo(d)==0 & wo[i].getStatus()!=Status.COMP & wo[i].getStatus()!=Status.PENDING) {
				WO.add(wo[i]);
			}
			else if(wo[i].getNext().compareTo(d)==0 & wo[i].getStatus()==Status.PENDING & wo[i].getTypes()==Types.SHINV) {
				F.inquiry(wo[i]);
			}
		}
		WorkOrder[] queue =WO.toArray(new WorkOrder[WO.size()]);
		if(queue.length>0) {
			F.receive(queue);// add new WO to Foreman
		}
		WO.clear();
	}

	public void Evening(Date d) throws IOException, CloneNotSupportedException {//Evening crew operation
		Crew[] crews = new Crew[Evening];
		int x=0;
		for(int i=DayCrew;i<DayCrew+Evening;i++){
			crews[x]= new Crew(i, Integer.toString(c),c,Integer.toString(c+1),c+1,F);
			crews[x].changeShift(Shift.Afternoon);
			x++;
			c+=2;
		}
		F.addCrew(crews);//add crew to foreman
		for(int i=0;i<wo.length;i++) {
			if(wo[i].getReport().compareTo(d)==0 & wo[i].getStatus()==Status.WAPPR ) {//for making test plan
				E.work(wo[i]);
			}
			else if(wo[i].getNext().compareTo(d)==0 & wo[i].getStatus()!=Status.COMP & wo[i].getStatus()!=Status.PENDING) {
				WO.add(wo[i]);
			}
			else if(wo[i].getNext().compareTo(d)==0 & wo[i].getStatus()==Status.PENDING & wo[i].getTypes()==Types.SHINV) {
				F.inquiry(wo[i]);
			}
		}
		WorkOrder[] queue =WO.toArray(new WorkOrder[WO.size()]);
		if(queue.length>0) {
			F.receive(queue);// add new WO to Foreman
		}
		WO.clear();
	}

	public void Night(Date d) throws IOException, CloneNotSupportedException {//Night crew operation
		Crew[] crews = new Crew[Night];
		int x=0;
		for(int i=DayCrew+Evening;i<crewNum;i++){
			crews[x]= new Crew(i, Integer.toString(c),c,Integer.toString(c+1),c+1,F);
			crews[x].changeShift(Shift.Night);
			c+=2;
			x++;
		}
		F.addCrew(crews);//add crew to foreman
		for(int i=0;i<wo.length;i++) {
			if(wo[i].getReport().compareTo(d)==0 & wo[i].getStatus()==Status.WAPPR ) {//for making test plan
				E.work(wo[i]);
			}
			else if(wo[i].getNext().compareTo(d)==0 & wo[i].getStatus()!=Status.COMP & wo[i].getStatus()!=Status.PENDING) {
				WO.add(wo[i]);
			}
			else if(wo[i].getNext().compareTo(d)==0 & wo[i].getStatus()==Status.PENDING & wo[i].getTypes()==Types.SHINV) {
				F.inquiry(wo[i]);
			}
		}
		WorkOrder[] queue =WO.toArray(new WorkOrder[WO.size()]);
		if(queue.length>0) {
			F.receive(queue);// add new WO to Foreman
		}
		WO.clear();
	}
	
	public  WorkOrder[] Output() {
		return wo;
	}
	
	public void toExcel() throws IOException {
		FileWriter FW2 = new FileWriter("WorkOrder.csv");
		FW2.write("Work Order,Location,Status, Reported Date, Work Type,Priority,Test,Scheduled Start,Shut,Actual Finish,Crew,Last,Next\n");
		for(int i=0;i<wo.length;i++) {
			FW2.write(wo[i].toString());
		}
		FW2.flush();
		FW2.close();
	} 

	protected int[] Delay(){
		return Queue;
	} 
}
