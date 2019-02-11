package simulation;
import java.io.IOException;


public class Main {
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		System.out.println("Start!");
		Run r =new Run(100);
		r.sim();
		System.out.println("Done!");
	}
}
