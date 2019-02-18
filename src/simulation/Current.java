package simulation;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class Current {
	Boolean Demo = true;//trigger of Demo mode.
	int replication;//Number of replications.
	int x=0,c=3;int count=0;
	List<WorkOrder> WO =new ArrayList<WorkOrder>();
	WorkOrder[] wo;Integer[] queue;int[] Queue;
	//List<Integer> queueCount = new ArrayList<Integer>();
	Engineer E = new Engineer(1, "Kens", null);
	Foreman F= new Foreman(2,"Andre",E);
	int crewNum=8,DayCrew=6,Evening=1,Night=1;//crew staffing
	int[][] backLog = new int[7][];
	
	@SuppressWarnings("deprecation")
	public void run(int duration) throws CloneNotSupportedException {
		backLog = new int[7][duration];
		WOgen g = new WOgen();
		g.setUp();//generate WO for entire year
		this.wo=g.Output();
		Date d = new Date(117,9,1);//Start date
		Date end = new Date(117,9,1);//End date
		end.setDate(duration);
		while(d.compareTo(end)<0) {
			Day(d);
			Evening(d);
			Night(d);
			for(int i=0;i<wo.length;i++) {
				if( wo[i].getNext().compareTo(d)==0 & wo[i].getStatus()!=Status.COMP) {//find unfinished order
					wo[i].updateNext(1);
					wo[i].delay();
					backLog[6][x]++;
					if(wo[i].getStatus()==Status.PLANCOMP) {
						backLog[0][x]++;
					}
					else if(wo[i].getStatus()==Status.APPR) {
						backLog[1][x]++;
					}
					else if(wo[i].getStatus()==Status.TESTING) {
						backLog[2][x]++;
					}
					else if(wo[i].getStatus()==Status.TECHCOMP) {
						backLog[3][x]++;
					}
					else if(wo[i].getStatus()==Status.A) {
						backLog[4][x]++;
					}
					else if(wo[i].getStatus()==Status.B) {
						backLog[5][x]++;
					}
				}	
			}
			x +=1;
			d=  new Date(117,9,1);
			d.setDate(x);
		}
	}

	public void Day(Date d) throws CloneNotSupportedException {//day crew operation
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
			else if(wo[i].getNext().compareTo(d)==0 & wo[i].getStatus()==Status.PENDING & wo[i].getTypes()==Types.SHMTR) {
				F.inquiry(wo[i]);
			}
		}
		WorkOrder[] queue =WO.toArray(new WorkOrder[WO.size()]);
		if(queue.length>0) {
			F.receive(queue);// add new WO to Foreman
		}
		WO.clear();
	}

	public void Evening(Date d) throws CloneNotSupportedException {//Evening crew operation
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

	public void Night(Date d) throws CloneNotSupportedException {//Night crew operation
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

	protected int[][] Delay(){
		return backLog;
	} 
}
