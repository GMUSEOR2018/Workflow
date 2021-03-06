package simulation;

import java.util.Random;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;

public class Distributions {
	static Random rd = new Random();

	//Beta distribution   
	public static double Beta(double alpha, double beta) {
		double u= Math.random();
		BetaDistribution B= new BetaDistribution(alpha,beta);      
		return B.inverseCumulativeProbability(u);
	} 

	//Expo distribution
	public static double exponential(double mean) {
		double u= Math.random();
		ExponentialDistribution e =new ExponentialDistribution(mean);
		return e.inverseCumulativeProbability(u);
	}
	//Poisson distribution
	public static double poisson(double Lambda) {
		PoissonDistribution P= new PoissonDistribution(Lambda);
		return P.sample();
	}

	public static double Triangular(double a, double c, double b) {
		double u= Math.random();
		TriangularDistribution t= new TriangularDistribution( a, c, b);
		return t.inverseCumulativeProbability(u);
	}

	//Computes normal distribution
	public static double normalDist(double mean, double stdev) {
		return rd.nextGaussian()*stdev+mean; 
	}
}
