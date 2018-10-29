package simulation;

import java.sql.Date;

public class Current {

	public Current() {
		// TODO Auto-generated constructor stub
		Engineer E= new Engineer(1,"E",null);
		
		E.Assign(new WorkOrder(1,Types.SHINV,Location.NE,Date.from(null), false));
	}

}
