package simulation;

//import java.sql.Date;

public class Main {

	public static void main(String[] args) {
	
		int check=0;
		while(check<=365) {
		double test= Distributions.poisson(0.404);
		int t=(int) Math.round(test);
		check+=t;
		System.out.println(t);
		}
		
		System.out.println("  "+check);
	}

}
