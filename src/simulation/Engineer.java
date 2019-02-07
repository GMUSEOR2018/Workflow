package simulation;
//Engineer for DCB
import java.sql.Date;
public class Engineer extends Employee {
//Construction
	public Engineer(int ID, String name, Employee suporisvor) {
		this.ID=ID;
		this.name=name;
		this.Supervisor=suporisvor;
	}

	protected void work(WorkOrder wo) {
		makePlan(wo);
	}
	@SuppressWarnings("deprecation")
	private void makePlan(WorkOrder wo) {
		Date d= wo.getReport();
		d.setDate((int) Math.round(Distributions.Triangular(5, 6, 7)));
		wo.setPlan(d);
		
	}
}
