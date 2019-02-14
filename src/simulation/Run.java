package simulation;
import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.Arrays;

public class Run {
	Boolean Demo =false ;//trigger of Demo mode.//false
	int replication;//Number of replications.
	int duration=365;// for setting date range
	double[] avgDelay;
	int[][] delay;

	String[] type ={"SHCIP","SHENG","SHDEV","SHSR","SHMTR","SHINV","Total"};
	int[][] WorkOrder = new int[7][replication];
	int[][] Complete = new int[7][replication];
	int[][] Shut = new int[7][replication];
	int[][] Test = new int[7][replication];
	int[][] Unfinished = new int[7][replication];
	double[][] avgDays= new double[7][replication];	
	double[][] stdDays= new double[7][replication];
	double[][] minDays= new double[7][replication];
	double[][] maxDays= new double[7][replication];
	double[][] medianDays= new double[7][replication];
	STAT S= new STAT();

	Run(int r) {
		this.replication=r;
		if(r==1) {
			this.Demo=true;
		}
	}

	public void sim() throws IOException, CloneNotSupportedException {
		if(Demo) {
			Current c= new Current();
			c.run(duration);
			c.toExcel();
		}
		else {
			WorkOrder = new int[7][replication];
			Complete = new int[7][replication];Unfinished = new int[7][replication];
			Shut = new int[7][replication];Test = new int[7][replication];
			avgDays= new double[7][replication];stdDays= new double[7][replication];minDays= new double[7][replication];maxDays= new double[7][replication];
			medianDays= new double[7][replication];
			delay=new int[replication][];
			int[][] result=new int[7][];
			for(int y = 0; y<replication;y++) {
				Current c= new Current();
				c.run(duration);
				WorkOrder[] wo=c.Output();			
				Types[] Type= {Types.SHCIP,Types.SHENG,Types.SHDEV,Types.SHSR,Types.SHMTR,Types.SHINV,null};
				for(int i=0;i<wo.length;i++) {
					for(int w=0;w<6;w++) {
						if(wo[i].getTypes()==Type[w]){
							WorkOrder[w][y]++;
							if(wo[i].getStatus()==Status.COMP) {
								Complete[w][y]++;
							}
							else if(wo[i].getStatus()==Status.B) {
								Shut[w][y]++;
								Unfinished[w][y]++;
							}
							else if(wo[i].getStatus()==Status.TESTCOMP) {
								Test[w][y]++;
								Unfinished[w][y]++;
							}
							else Unfinished[w][y]++;
						}
					}
					WorkOrder[6][y]++;
				}
				Complete[6][y]=Complete[0][y]+Complete[1][y]+Complete[2][y]+Complete[3][y]+Complete[4][y]+Complete[5][y];
				Shut[6][y]=Shut[0][y]+Shut[1][y]+Shut[2][y]+Shut[3][y]+Shut[4][y]+Shut[5][y];
				Test[6][y]=Test[0][y]+Test[1][y]+Test[2][y]+Test[3][y]+Test[4][y]+Test[5][y];
				Unfinished[6][y]=WorkOrder[6][y]-Complete[6][y];	
				for(int z=0; z<7;z++) {
					result[z]=day(wo, Type[z],Complete[z][y]);
				}
				for(int q=0;q<7;q++) {
					avgDays[q][y]=S.mean(result[q]);
					stdDays[q][y]=S.stv(result[q]);
					Arrays.sort(result[q]);
					minDays[q][y]=result[q][0];
					maxDays[q][y]=result[q][result[q].length-1];
					medianDays[q][y]=S.median(result[q]);
				}

				delay[y]=c.Delay();
				double x = 100.0*y/replication;
				if(x%5==0& x!=0) {
					String xstr = String.format("%3.3g", x);
					System.out.println(xstr+ "% complete");
				}
			}
			System.out.println("100 % complete\n");
			System.out.print(output());
		}
	}

	private String output() {
		String output=" Type\t"+"input-avg\t"+"input-SD\t"+"Completed-AVG\t"+"Completed-SD\n";
		for(int h=0; h<7;h++) {
			output+=type[h]+ "\t  "+String.format( "%.2f",S.mean(WorkOrder[h]))+ "      " + String.format( "%.2f",S.stv(WorkOrder[h]))+"\t\t"+ String.format( "%.2f",S.mean(Complete[h]))+"\t\t"+ String.format( "%.2f",S.stv(Complete[h]))+"\n";
		}
		output+="\n Type\t"+"Unfinished-avg\t"+"Unfinished-SD\t"+"Shut-off-AVG\t" +"Shut-off-SD\t" +"Tested-AVG\t"+"Tested-SD\n";
		for(int h=0; h<7;h++) {
			output+=type[h]+  "\t"+String.format( "%.2f",S.mean(Unfinished[h]))+"\t\t" + String.format( "%.2f",S.stv(Unfinished[h]))+"\t\t" + 
					String.format( "%.2f",S.mean(Shut[h]))+"\t\t" + String.format( "%.2f",S.stv(Shut[h]))+"\t\t" +String.format( "%.2f",S.mean(Test[h]))+"\t\t" + String.format( "%.2f",S.stv(Test[h]))+"\n";
		}
		output +="\n Type "+" duration-AVG "+" Duration-SD "+" median-duraion "+" minium-duraion "+" maximun-duration\n";
		for(int h=0; h<7;h++) {
			output+=type[h]+ "\t"+ String.format( "%.2f",S.mean(avgDays[h])) + "\t\t" +String.format( "%.2f",S.mean(stdDays[h]))+ "\t\t" +String.format( "%.2f",S.mean(medianDays[h]))+ 
					"\t\t" +String.format( "%.2f",S.mean(minDays[h]))+ "\t\t" +String.format( "%.2f",S.mean(maxDays[h]))+"\n";
		}
		return output;
	}

	public void Backlog() throws IOException {
		avgDelay= new double[delay[0].length];
		int[] temp=new int[delay.length];
		for(int f=0; f<delay[0].length;f++) {
			for(int d=0;d<delay.length;d++) {
				temp[d]=delay[d][f];
			}
			avgDelay[f]=S.mean(temp);	
		}
		FileWriter FW2 = new FileWriter("Delay.csv");
		for(int i=0;i<avgDelay.length;i++) {
			int day=i+1;
			FW2.write(day+","+ String.format( "%.2f",avgDelay[i])+"\n");
		}
		FW2.flush();
		FW2.close();
	} 
	public void Duration() throws IOException {
		FileWriter FW2 = new FileWriter("Duration.csv");
		for(int i=0;i<avgDelay.length;i++) {
			int day=i+1;
			FW2.write(day+","+ String.format( "%.2f",avgDelay[i])+"\n");
		}
		FW2.flush();
		FW2.close();
	} 
	private int[] day(WorkOrder[] wo, Types t, int Complete) {
		int[] days=new int[Complete];
		int f=0;
		if(t==null) {
			for(int x = 0;x<wo.length;x++) {
				long d=0;
				if(wo[x].getStatus()==Status.COMP) {
					d=ChronoUnit.DAYS.between(LocalDate.parse(wo[x].getReport().toString()),LocalDate.parse(wo[x].getFinish().toString()));
					days[f]=(int) d;
					f++;
				}
			}
		}
		else {
			for(int x = 0;x<wo.length;x++) {
				long d=0;
				if(wo[x].getStatus()==Status.COMP) {
					if(wo[x].getTypes()==t) {
						d=ChronoUnit.DAYS.between(LocalDate.parse(wo[x].getReport().toString()),LocalDate.parse(wo[x].getFinish().toString()));
						days[f]=(int) d;
						f++;
					}
				}
			}
		}
		return days;
	}
}