package simulation;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Current {
	int SHCIP,SHDEV,SHENG,SHINV,SHSR,SHMTR;
	int NumCIP,NumDEV,NumENG,NumINV,NumSR,NumMTR;
	
	List<WorkOrder> WO =new ArrayList<WorkOrder>();
	int x =1;

	public void setUp(){
		SHCIP=SHCIP();
		SHENG=SHENG();
		SHINV=SHINV();
		SHDEV=SHDEV();
		SHSR=SHSR();
		SHMTR=SHMTR();
		
		while(SHCIP<=365 & SHENG<=365 & SHINV<=365 & SHDEV<=365 & SHSR<=365 & SHMTR<=365) {
			if(SHCIP<=SHENG & SHCIP<=SHINV & SHCIP<=SHDEV & SHCIP<=SHSR & SHCIP<=SHMTR & SHCIP<=365) {
				WO.add(new WorkOrder(x,Types.SHCIP,Location.NE,SHCIP, false));
				x++;
				SHCIP=SHCIP();
				NumCIP++;
			}
			if(SHENG<=SHCIP & SHENG<=SHINV & SHENG<=SHDEV & SHENG<=SHSR & SHENG<=SHMTR & SHENG<=365) {
				WO.add(new WorkOrder(x,Types.SHENG,Location.NE,SHENG, false));
				x++;
				SHENG=SHENG();
				NumENG++;
			}
			if(SHINV<=SHCIP & SHENG>=SHINV & SHINV<=SHDEV & SHINV<=SHSR & SHINV<=SHMTR & SHINV<=365) {
				WO.add(new WorkOrder(x,Types.SHINV,Location.NE,SHINV, false));
				x++;
				SHINV=SHINV();
				NumINV++;
			}
			if(SHDEV<=SHCIP & SHDEV<=SHINV & SHENG>=SHDEV & SHDEV<=SHSR & SHDEV<=SHMTR & SHDEV<=365) {
				WO.add(new WorkOrder(x,Types.SHDEV,Location.NE,SHDEV, false));
				x++;
				SHDEV=SHDEV();
				NumDEV++;
			}
			if(SHSR<=SHCIP & SHSR<=SHINV & SHSR<=SHDEV & SHSR<=SHDEV & SHSR<=SHMTR & SHSR<=365) {
				WO.add(new WorkOrder(x,Types.SHSR,Location.NE,SHSR, false));
				x++;
				SHSR=SHSR();
				NumSR++;
			}
			if(SHMTR<=SHCIP & SHMTR<=SHINV & SHMTR<=SHDEV & SHMTR<=SHSR & SHSR>=SHMTR & SHMTR<=365) {
				WO.add(new WorkOrder(x,Types.SHMTR,Location.NE,SHMTR, false));
				x++;
				SHMTR=SHMTR();
				NumMTR++;
			}

		}	
		
	}
	public  WorkOrder[] Output() {
		WorkOrder[] workorder =WO.toArray(new WorkOrder[WO.size()]);
		return workorder;
	}
	public void toExcel() throws IOException {
		FileWriter FW2 = new FileWriter("WorkOrder.csv");
		FW2.write("Work Order,Location,Status, Reported Date, Work Type,Priority, Scheduled Start,Actual Finish,Crew\n");
		for(int i=0;i<WO.size();i++) {
			FW2.write(WO.get(i).toString());
		}
		 FW2.flush();
	     FW2.close();
	} 
	
	
	//Generate the WO with respected distribution expression.
	@SuppressWarnings("deprecation")
	private int SHCIP() {
		Date d = new Date(117,0,1);//Create a new date
		SHCIP += (int) Math.round(Distributions.Beta(0.193, 1.4)*66-0.5);
		d.setDate(SHCIP);//Change Date.
		if (d.getDay()==0) {SHCIP+=1;}//Skip the weekend.
		else if (d.getDay()==6) {SHCIP+=2;}
		return SHCIP;
	}
	@SuppressWarnings("deprecation")
	private int SHDEV() {
		Date d = new Date(117,0,1);
		SHDEV += (int) Math.round(Distributions.poisson(0.404));
		d.setDate(SHDEV);
		if (d.getDay()==0) {SHDEV+=1;}
		else if (d.getDay()==6) {SHDEV+=2;}
		return SHDEV;
	}
	@SuppressWarnings("deprecation")
	private int SHENG() {
		Date d = new Date(117,0,1);
		SHENG += (int) Math.round(Distributions.Beta(0.269,1.06)*42.5+0.999);
		d.setDate(SHENG);
		if (d.getDay()==0) {SHENG+=1;}
		else if (d.getDay()==6) {SHENG+=2;}
		return SHENG;
	}
	@SuppressWarnings("deprecation")
	private int SHINV() {
		Date d = new Date(117,0,1);
		SHINV += (int) Math.round(Distributions.exponential(0.902)-0.5);
		d.setDate(SHINV);	
		if (d.getDay()==0) {SHINV+=1;}
		else if (d.getDay()==6) {SHINV+=2;}
		return SHINV;
	}
	@SuppressWarnings("deprecation")
	private int SHSR() {
		Date d = new Date(117,0,1);		
		SHSR += (int) Math.round(Distributions.exponential(0.987)-0.5);
		d.setDate(SHINV);		
		if (d.getDay()==0){SHSR+=1;}
		else if (d.getDay()==6) {SHSR+=2;}
		return SHSR;
	}
	@SuppressWarnings("deprecation")
	private int SHMTR() {
		Date d = new Date(117,0,1);	
		SHMTR += (int) Math.round(Distributions.Beta(0.301, 5.08)*29-0.5);
		d.setDate(SHINV);
		if (d.getDay()==0) {SHMTR+=1;}
		else if (d.getDay()==6) {SHMTR+=2;}
		return SHMTR;
	}
}
