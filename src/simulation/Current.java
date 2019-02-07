package simulation;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class Current {
	int SHCIP=0,SHDEV=0,SHENG=0,SHINV=0,SHSR=0,SHMTR=0;
	int CIP,DEV,ENG,INV,SR,MTR;
	int NumCIP,NumDEV,NumENG,NumINV,NumSR,NumMTR,x=0;
	List<WorkOrder> WO =new ArrayList<WorkOrder>();
	Engineer E = new Engineer(1, "Kens", null);
	Foreman F= new Foreman(2,"Andre",E);
	public void run() throws IOException {
		WOgen g = new WOgen();
		g.setUp();
		g.toExcel();
		WorkOrder[] wo=g.Output();
		Date d = new Date(117,9,1);//Start date
		Date end = new Date(118,8,30);//End date
		while(d.compareTo(end)!=0) {
			for(int i=0;i<wo.length;i++) {
				if(wo[i].getStatus()==Status.WAPPR & wo[i].getReport().compareTo(d)==0) {
					E.work(wo[i]);
				}
				if(wo[i].getStatus()==Status.PLANCOMP & wo[i].getLast().compareTo(d)==0) {
					WO.add(wo[i]);
				}
				if(wo[i].getStatus()==Status.APPR & wo[i].getLast().compareTo(d)==0) {
					WO.add(wo[i]);
				}
				if(wo[i].getStatus()==Status.PENDING& wo[i].getLast().compareTo(d)==0) {
					WO.add(wo[i]);
				}
				if(wo[i].getStatus()==Status.TESTCOMP & wo[i].getLast().compareTo(d)==0) {
					WO.add(wo[i]);
				}
				if(wo[i].getStatus()==Status.A & wo[i].getLast().compareTo(d)==0) {
					WO.add(wo[i]);
				}
				if(wo[i].getStatus()==Status.B & wo[i].getLast().compareTo(d)==0) {
					WO.add(wo[i]);
				}
			}
			WorkOrder[] queue =WO.toArray(new WorkOrder[WO.size()]);
			WO.clear();		
			for(int i=0;i<queue.length;i++) {
				F.work(wo[i]);
			}

			x +=1;
			d=  new Date(117,9,1);
			d.setDate(x);
		}



	}


}
