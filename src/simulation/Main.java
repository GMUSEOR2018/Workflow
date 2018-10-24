package simulation;

import java.sql.Date;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Hello!");;
		
		Engineer E= new Engineer(1,2,"E");
		
		E.Assign(new WorkOrder(1,Types.SHNOT,Location.NE,Date.from(null)));
	}

}
