package simulation;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class WOgen {
	int SHCIP=0,SHDEV=0,SHENG=0,SHINV=0,SHSR=0,SHMTR=0;
	int CIP,DEV,ENG,INV,SR,MTR;
	int NumCIP,NumDEV,NumENG,NumINV,NumSR,NumMTR;

	List<WorkOrder> WO =new ArrayList<WorkOrder>();
	int x =1;

	public void setUp(){
		SHCIP=SHCIP();SHENG=SHENG();SHINV=SHINV();SHDEV=SHDEV();SHSR=SHSR();SHMTR=SHMTR();

		while(SHCIP<=365 || SHENG<=365 || SHINV<=365 || SHDEV<=365 || SHSR<=365 || SHMTR<=365) {
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
	Date d = new Date(117,9,1);//Create a new date
	CIP += (int) Math.round(Distributions.Beta(0.193, 1.4)*66-0.5);
	d.setDate(CIP);//Change Date.
	if (d.getDay()==0) {CIP+=1;}//Skip the weekend.
	else if (d.getDay()==6) {CIP+=2;}
	return CIP;
}
@SuppressWarnings("deprecation")
private int SHDEV() {
	Date d = new Date(117,9,1);
	DEV += (int) Math.round(Distributions.poisson(0.55));//0.52
	//DEV += (int) Math.round(Distributions.exponential(1.23)-0.5);
	d.setDate(DEV);
	if (d.getDay()==0) {DEV+=1;}
	else if (d.getDay()==6) {DEV+=2;}
	return DEV;
}
@SuppressWarnings("deprecation")
private int SHENG() {
	Date d = new Date(117,9,1);
	ENG += (int) Math.round(Distributions.Beta(0.374,1.1)*55.5+0.999);
	d.setDate(ENG);
	if (d.getDay()==0) {ENG+=1;}
	else if (d.getDay()==6) {ENG+=2;}
	return ENG;
}
@SuppressWarnings("deprecation")
private int SHINV() {
	Date d = new Date(117,9,1);
	INV += (int) Math.round(Distributions.exponential(1.325)-0.5);
	d.setDate(INV);	
	if (d.getDay()==0) {INV+=1;}
	else if (d.getDay()==6) {INV+=2;}
	return INV;
}
@SuppressWarnings("deprecation")
private int SHSR() {
	Date d = new Date(117,9,1);	
	SR += (int) Math.round(Distributions.poisson(0.37));
	//SR += (int) Math.round(Distributions.exponential(0.987)-0.5);
	d.setDate(SR);
	if (d.getDay()==0){SR+=1;}
	else if (d.getDay()==6) {SR+=2;}
	return SR;
}
@SuppressWarnings("deprecation")
private int SHMTR() {
	Date d = new Date(117,9,1);	
	MTR += (int) Math.round(Distributions.Beta(0.301, 5.08)*29-0.5);
	d.setDate(MTR);
	if (d.getDay()==0) {MTR+=1;}
	else if (d.getDay()==6) {MTR+=2;}
	return MTR;
}
}
