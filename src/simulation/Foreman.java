package simulation;

import java.sql.Date;

public class Foreman extends Employee {
	WorkOrder[] Wo;
	WorkOrder temp;
	Date d;
	Crew[] Crews;  
	public Foreman(int ID, String name, Employee suporisvor) {
		this.ID=ID;
		this.name=name;
		this.Supervisor=suporisvor;
	}
	public void addCrew(Crew[] crews) {
		this.Crews=crews;
	}

	public  void receive(WorkOrder[] wO) throws CloneNotSupportedException {
		this.Wo=wO;
		Schedule();
	}

	protected void inquiry(WorkOrder wO) {
		int d=(int) Math.round(Distributions.Triangular(1, 2, 3));
		wO.updateStatus(Status.PLANCOMP);
		wO.updateLast();
		wO.updateNext(d);
	}
	private void Schedule() throws CloneNotSupportedException {
		int i=0;
		for(int j=0;j<Crews.length;j++) {
			double time=0,t;
			time =Distributions.Triangular(0.667, 1, 1.5)+Distributions.Triangular(0.5, 0.75, 1)
			+Distributions.Triangular(0.0833, 0.333, 1);//Time waiting for assignment + break time+ travel time 
//			time = 0.33 + Distributions.Triangular(0.5, 0.75, 1) + Distributions.Triangular(0.0833, 0.333, 1);//Experiment 2 
			while(time<=8 & i<Wo.length) {
				temp=Wo[i];
				temp.Clone(Wo[i]);
				t=time;
				if(Wo[i].getStatus()==Status.PLANCOMP) {
					time += Crews[j].Assesment(Wo[i]); 
				}
				else if(Wo[i].getStatus()==Status.APPR) {
					time += Crews[j].Notify(Wo[i]);  
				}
				else if(Wo[i].getStatus()==Status.TESTING) {
					time += Crews[j].TestShut(Wo[i]);
					this.Supervisor.work(Wo[i]);
				}
				else if(Wo[i].getStatus()==Status.TESTCOMP) {
					time += Crews[j].Notify(Wo[i]);
				}
				else if(Wo[i].getStatus()==Status.A) {
					time += Crews[j].Shut(Wo[i]); 
				}
				if(Wo[i].getStatus()==Status.B) {
					time += Crews[j].Recharge(Wo[i]);
				}			
				i++;
				if(time>8) {
					i--;
					Wo[i].Clone(temp);
				}
			}
		}
	}
}