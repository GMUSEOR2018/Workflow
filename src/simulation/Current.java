package simulation;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Current {
	int SHCIP,SHDEV,SHENG,SHINV,SHSR,SHMTR;
	@SuppressWarnings("deprecation")
	
	
	//Engineer E= new Engineer(1,"E",null);
	List<WorkOrder> WO =new ArrayList<WorkOrder>();
	int x =1;

	@SuppressWarnings("deprecation")
	public void setUp() throws IOException {
		FileWriter FW2 = new FileWriter("WorkOrder.csv");
		
		while(SHCIP<365) {
			SHCIP=SHCIP();
			WO.add(new WorkOrder(x,Types.SHCIP,Location.NE,SHCIP, false));
			x++;
		}
	
		while(SHENG<=365) {
			SHENG=SHENG();
			WO.add(new WorkOrder(x,Types.SHENG,Location.NE,SHENG, false));
			x++;
		}
		
		while(SHINV<=365) {
			SHINV=SHINV();
			WO.add(new WorkOrder(x,Types.SHINV,Location.NE,SHINV, false));
			x++;
		}
		while(SHDEV<=365) {
			SHDEV=SHDEV();
			WO.add(new WorkOrder(x,Types.SHDEV,Location.NE,SHDEV, false));
			x++;
		}
		while(SHSR<=365) {
			SHSR=SHSR();
			WO.add(new WorkOrder(x,Types.SHSR,Location.NE,SHSR, false));
			x++;
		}
		
		while(SHMTR<=365) {
			SHMTR=SHMTR();
			WO.add(new WorkOrder(x,Types.SHMTR,Location.NE,SHMTR, false));
			x++;
		}
		for(int i=0;i<WO.size();i++) {
			FW2.write(WO.get(i).toString());
		}
        FW2.flush();
        FW2.close();
        System.out.print("Done!!");
	}
	
	
	@SuppressWarnings("deprecation")
	private int SHCIP() {
		Date d = new Date(117,0,1);
		SHCIP += (int) Math.round(Distributions.Beta(0.193, 1.4)*66-0.5);
		d.setDate(SHCIP);
		System.out.println(d.getDay());
		if (d.getDay()==0) {SHCIP+=1;}
		else if (d.getDay()==6) {SHCIP+=2;}
		return SHCIP;
	}
	private int SHDEV() {
		Date d = new Date(117,0,1);
		Calendar cal = Calendar.getInstance() ;
		SHDEV += (int) Math.round(Distributions.poisson(0.404));
		d.setDate(SHDEV);
		cal.setTime(d);
		if (cal.get(Calendar.DAY_OF_WEEK)==1) {SHDEV+=1;}
		else if (cal.get(Calendar.DAY_OF_WEEK)==0) {SHDEV+=2;}
		return SHDEV;
	}
	private int SHENG() {
		Date d = new Date(117,0,1);
		Calendar cal = Calendar.getInstance() ;
		SHENG += (int) Math.round(Distributions.Beta(0.269,1.06)*42.5+0.999);
		d.setDate(SHENG);
		cal.setTime(d);
		if (cal.get(Calendar.DAY_OF_WEEK)==1) {SHENG+=1;}
		else if (cal.get(Calendar.DAY_OF_WEEK)==7) {SHENG+=2;}
		return SHENG;
	}
	private int SHINV() {
		Date d = new Date(117,0,1);
		Calendar cal = Calendar.getInstance() ;
		SHINV += (int) Math.round(Distributions.exponential(0.902)-0.5);
		d.setDate(SHINV);
		cal.setTime(d);
		if (cal.get(Calendar.DAY_OF_WEEK)==1) {SHINV+=1;}
		else if (cal.get(Calendar.DAY_OF_WEEK)==7) {SHINV+=2;}
		return SHINV;
	}
	private int SHSR() {
		Date d = new Date(117,0,1);
		Calendar cal = Calendar.getInstance() ;
		SHSR += (int) Math.round(Distributions.exponential(0.987)-0.5);
		d.setDate(SHINV);
		cal.setTime(d);
		if (cal.get(Calendar.DAY_OF_WEEK)==1) {SHSR+=1;}
		else if (cal.get(Calendar.DAY_OF_WEEK)==7) {SHSR+=2;}
		return SHSR;
	}
	private int SHMTR() {
		Date d = new Date(117,0,1);
		Calendar cal = Calendar.getInstance() ;
		SHMTR += (int) Math.round(Distributions.Beta(0.301, 5.08)*29-0.5);
		d.setDate(SHINV);
		cal.setTime(d);
		if (cal.get(Calendar.DAY_OF_WEEK)==1) {SHMTR+=1;}
		else if (cal.get(Calendar.DAY_OF_WEEK)==7) {SHMTR+=2;}
		return SHMTR;
	}
}
