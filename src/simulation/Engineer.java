package simulation;
//Engineer for DCB

import java.util.Random;

public class Engineer extends Employee {
	//Construction
	int time=0;
	public Engineer(int ID, String name, Employee suporisvor) {
		this.ID=ID;
		this.name=name;
		this.Supervisor=suporisvor;
	}

	protected void work(WorkOrder wo) {
		makePlan(wo);
	}

	private void makePlan(WorkOrder wo) {
		Random r = new Random();
		time=(int) Math.round(Distributions.Triangular(5, 6, 7));
		if(wo.getStatus()==Status.TESTCOMP) {
			int Percent= r.nextInt(100);
			if(wo.getTypes()==Types.SHCIP & Percent>20) {
				time=(int) Math.round(Distributions.Triangular(12, 15, 20));
			}
			else if(wo.getTypes()==Types.SHENG & Percent>50) {
				time=(int) Math.round(Distributions.Triangular(12, 15, 20));

			}
			else if(wo.getTypes()==Types.SHDEV & Percent>40) {
				time=(int) Math.round(Distributions.Triangular(12, 15, 20));

			}
			else if(wo.getTypes()==Types.SHSR & Percent>90) {
				time=(int) Math.round(Distributions.Triangular(12, 15, 20));
			}
			else if(wo.getTypes()==Types.SHMTR & Percent>90) {
				time=(int) Math.round(Distributions.Triangular(12, 15, 20));
			}
		}
		wo.setPlan(time);
	}
}
