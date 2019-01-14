package simulation;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
	generateWO g = new generateWO(100);
	g.run();
	System.out.print("Done!!");
	}

}
