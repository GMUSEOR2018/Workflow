package simulation;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		int replication=1000;
		long t = System.currentTimeMillis(); // start time record.
		System.out.println("Start!");
		Run r =new Run(replication);
		r.sim();
		r.Backlog();
		System.out.print("\n"+replication+" runs done, operation took: " + (1.0*(System.currentTimeMillis()-t)/1000) + " seconds to complete.\n");
	}
}
