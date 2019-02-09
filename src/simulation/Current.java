package simulation;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class Current {
	int SHCIP=0,SHDEV=0,SHENG=0,SHINV=0,SHSR=0,SHMTR=0,x=0,c=3;
	int CIP,DEV,ENG,INV,SR,MTR;
	int NumCIP,NumDEV,NumENG,NumINV,NumSR,NumMTR;
	List<WorkOrder> WO =new ArrayList<WorkOrder>();
	WorkOrder[] wo;
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
			int count=0;
			Day(d);
			//Evening(d);
			//Night(d);
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
			System.out.println(d.toString());
		}
		Integer[] queue =queueCount.toArray(new Integer[queueCount.size()]);
		queueCount.clear();
		toExcel();
	}

	public void Day(Date d) throws IOException, CloneNotSupportedException {
		int crewNum=8;
		Crew[] crews = new Crew[crewNum];
		for(int i=0;i<crewNum;i++){
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

	public void Evening(Date d) throws IOException, CloneNotSupportedException {
		Crew[] crews = new Crew[1];
		crews[0]=new Crew(7, "15",15,"16",16,F);
		crews[0].changeShift(Shift.Afternoon);
		F.addCrew(crews);//add crew to foreman

		for(int i=0;i<wo.length;i++) {
			if(wo[i].getStatus()==Status.WAPPR & wo[i].getReport().compareTo(d)==0) {//for making plan
				E.work(wo[i]);
			}
			else if(wo[i].getStatus()!=Status.WAPPR & wo[i].getNext().compareTo(d)==0) {//for assign assessment
				WO.add(wo[i]);
			}
			WorkOrder[] queue =WO.toArray(new WorkOrder[WO.size()]);
			WO.clear();		
			F.receive(queue);// add new WO to Foreman		
		}
	}

	public void Night(Date d) throws IOException, CloneNotSupportedException {
		Crew[] crews = new Crew[1];
		crews[0]=new Crew(8, "17",17,"18",18,F);
		crews[0].changeShift(Shift.Night);
		F.addCrew(crews);//add crew to foreman

		for(int i=0;i<wo.length;i++) {
			if(wo[i].getStatus()==Status.WAPPR & wo[i].getReport().compareTo(d)==0) {//for making plan
				E.work(wo[i]);
			}
			else if(wo[i].getStatus()==Status.PLANCOMP & wo[i].getNext().compareTo(d)==0) {//for assign assessment
				WO.add(wo[i]);
			}
			else if(wo[i].getStatus()==Status.TESTING & wo[i].getNext().compareTo(d)==0) {//for Notify
				WO.add(wo[i]);
			}
			else if(wo[i].getStatus()==Status.PENDING& wo[i].getNext().compareTo(d)==0) {//for test
				WO.add(wo[i]);
			}
			else if(wo[i].getStatus()==Status.TESTCOMP & wo[i].getNext().compareTo(d)==0) {//for 2nd notify
				WO.add(wo[i]);
			}
			else if(wo[i].getStatus()==Status.A & wo[i].getNext().compareTo(d)==0) {//for shut-off
				WO.add(wo[i]);
			}
			else if(wo[i].getStatus()==Status.B & wo[i].getNext().compareTo(d)==0) {//for recharges
				WO.add(wo[i]);
			}
			WorkOrder[] queue =WO.toArray(new WorkOrder[WO.size()]);
			WO.clear();		
			F.receive(queue);// add new WO to Foreman
		}
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
}
