package simulation;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class WOgen {
	int SHCIP=0,SHDEV=0,SHENG=0,SHINV=0,SHSR=0,SHMTR=0;
	int CIP,DEV,ENG,INV,SR,MTR;
	int NumCIP,NumDEV,NumENG,NumINV,NumSR,NumMTR;
	List<WorkOrder> WO =new ArrayList<WorkOrder>();
	int x =1;//track WO ID
	int Range;//range need to generate work order

	public void setUp(int range){
		this.Range=range;
		SHCIP=SHCIP();SHENG=SHENG();SHINV=SHINV();SHDEV=SHDEV();SHSR=SHSR();SHMTR=SHMTR();

		while(SHCIP<=Range || SHENG<=Range || SHINV<=Range || SHDEV<=Range || SHSR<=Range || SHMTR<=Range) {
			if(SHCIP<=SHENG & SHCIP<=SHINV & SHCIP<=SHDEV & SHCIP<=SHSR & SHCIP<=SHMTR & SHCIP<=Range) {
				WO.add(new WorkOrder(x,Types.SHCIP,SHCIP, false));
				x++;
				SHCIP=SHCIP();
				NumCIP++;
			}
			if(SHENG<=SHCIP & SHENG<=SHINV & SHENG<=SHDEV & SHENG<=SHSR & SHENG<=SHMTR & SHENG<=Range) {
				WO.add(new WorkOrder(x,Types.SHENG,SHENG, false));
				x++;
				SHENG=SHENG();
				NumENG++;
			}
			if(SHINV<=SHCIP & SHENG>=SHINV & SHINV<=SHDEV & SHINV<=SHSR & SHINV<=SHMTR & SHINV<=Range) {
				WO.add(new WorkOrder(x,Types.SHINV,SHINV, false));
				x++;
				SHINV=SHINV();
				NumINV++;
			}
			if(SHDEV<=SHCIP & SHDEV<=SHINV & SHENG>=SHDEV & SHDEV<=SHSR & SHDEV<=SHMTR & SHDEV<=Range) {
				WO.add(new WorkOrder(x,Types.SHDEV,SHDEV, false));
				x++;
				SHDEV=SHDEV();
				NumDEV++;
			}
			if(SHSR<=SHCIP & SHSR<=SHINV & SHSR<=SHDEV & SHSR<=SHDEV & SHSR<=SHMTR & SHSR<=Range) {
				WO.add(new WorkOrder(x,Types.SHSR,SHSR, false));
				SHSR=SHSR();
				NumSR++;
			}
			if(SHMTR<=SHCIP & SHMTR<=SHINV & SHMTR<=SHDEV & SHMTR<=SHSR & SHSR>=SHMTR & SHMTR<=Range) {
				WO.add(new WorkOrder(x,Types.SHMTR,SHMTR, false));
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

	//Generate the WO with respected distribution expression.
	@SuppressWarnings("deprecation")
	private int SHCIP() {
		Date d = new Date(117,7,1);//Create a new date
		CIP += (int) Math.round(Distributions.Beta(0.193, 1.4)*66-0.5);
		d.setDate(CIP);//Change Date.
		if (d.getDay()==0) {CIP+=1;}//Skip the weekend.
		else if (d.getDay()==6) {CIP+=2;}
		return CIP;
	}
	@SuppressWarnings("deprecation")
	private int SHDEV() {
		Date d = new Date(117,7,1);
		DEV += (int) Math.round(Distributions.poisson(0.55));//0.52
		//DEV += (int) Math.round(Distributions.exponential(1.23)-0.5);
		d.setDate(DEV);
		if (d.getDay()==0) {DEV+=1;}
		else if (d.getDay()==6) {DEV+=2;}
		return DEV;
	}
	@SuppressWarnings("deprecation")
	private int SHENG() {
		Date d = new Date(117,7,1);
		ENG += (int) Math.round(Distributions.Beta(0.374,1.1)*55.5+0.999);
		d.setDate(ENG);
		if (d.getDay()==0) {ENG+=1;}
		else if (d.getDay()==6) {ENG+=2;}
		return ENG;
	}
	@SuppressWarnings("deprecation")
	private int SHINV() {
		Date d = new Date(117,7,1);
		INV += (int) Math.round(Distributions.exponential(1.325)-0.5);
		d.setDate(INV);	
		if (d.getDay()==0) {INV+=1;}
		else if (d.getDay()==6) {INV+=2;}
		return INV;
	}
	@SuppressWarnings("deprecation")
	private int SHSR() {
		Date d = new Date(117,7,1);	
		SR += (int) Math.round(Distributions.poisson(0.37));
		//SR += (int) Math.round(Distributions.exponential(0.987)-0.5);
		d.setDate(SR);
		if (d.getDay()==0){SR+=1;}
		else if (d.getDay()==6) {SR+=2;}
		return SR;
	}
	@SuppressWarnings("deprecation")
	private int SHMTR() {
		Date d = new Date(117,7,1);	
		MTR += (int) Math.round(Distributions.Beta(0.301, 5.08)*29-0.5);
		d.setDate(MTR);
		if (d.getDay()==0) {MTR+=1;}
		else if (d.getDay()==6) {MTR+=2;}
		return MTR;
	}
}
