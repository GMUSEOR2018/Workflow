package simulation;
import java.io.IOException;
import java.sql.Date;

public class Main {

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
	//generateWO g = new generateWO(10000);
	//g.run();
	Current c= new Current();
	c.run();
	System.out.print("Done!!");

	}

}
