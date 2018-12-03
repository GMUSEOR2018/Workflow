package simulation;

//import java.sql.Date;

public class Main {

	public static void main(String[] args) {
	
		int check=0;
		while(check<=365) {
		double test= Distributions.Beta(0.269, 1.06)*42.5+0.999;
		int t=(int) Math.round(test);
		check+=t;
		System.out.println(t);
		}
		
		System.out.println("  "+check);
	}

}
