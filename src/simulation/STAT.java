package simulation;

public class STAT {

	protected double mean(int data[]) {// Calculating mean
		double sum=0;
		int length=data.length;
		for(int i=0; i<length;i++) {
			sum+=data[i];
		}
		return sum/length;
	}
	protected double mean(double data[]) {// Calculating mean
		double sum=0;
		int length=data.length;
		for(int i=0; i<length;i++) {
			sum+=data[i];
		}
		return sum/length;
	}
	protected double stv(int data[]) {// Calculating STDV
		double sum=0;
		double mean=mean(data);
		int length=data.length;
		for(int i=0; i<length;i++) {
			sum+=Math.pow(data[i]-mean,2);
		}
		double stv=Math.sqrt(sum/(length-1));
		return stv;
	}
	protected int median(int data[]) {
		return data[data.length/2];
	}

}
