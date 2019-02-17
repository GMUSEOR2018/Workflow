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
	int[][] minDays= new int[7][replication];
	int[][] maxDays= new int[7][replication];
	double[][] medianDays= new double[7][replication];
	WorkOrder[][] WorkOrders;//Main stream database
	STAT S= new STAT();

	Run(int r) {
		this.replication=r;
		if(r==1) {
			this.Demo=true;
		}
		try {
			sim();
		} catch (IOException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sim() throws IOException, CloneNotSupportedException {
		if(Demo) {		
			delay=new int[1][];WorkOrders= new WorkOrder[1][];
			Current c= new Current();
			c.run(duration);
			WorkOrders[0]=c.Output();
			delay[0]=c.Delay();
			c.toExcel();
			results();
		}
		else {
			delay=new int[replication][];
			WorkOrders= new WorkOrder[replication][];
			for(int y = 0; y<replication;y++) {
				Current c= new Current();
				c.run(duration);
				WorkOrders[y]=c.Output();
				delay[y]=c.Delay();
				double x = 100.0*y/replication;
				if(x%5==0& x!=0) {
					String xstr = String.format("%3.3g", x);
					System.out.println(xstr+ "% complete");
				}
			}
			System.out.println("100 % complete\n");
		
		}
	}

	protected void results() {
		WorkOrder = new int[7][replication];
		Complete = new int[7][replication];Unfinished = new int[7][replication];
		Shut = new int[7][replication];Test = new int[7][replication];
		avgDays= new double[7][replication];stdDays= new double[7][replication];minDays= new int[7][replication];maxDays= new int[7][replication];
		medianDays= new double[7][replication];	
		int[][] result=new int[7][];
		Types[] Type= {Types.SHCIP,Types.SHENG,Types.SHDEV,Types.SHSR,Types.SHMTR,Types.SHINV,null};
		for(int h=0;h<WorkOrders.length;h++) {
			for(int i=0;i<WorkOrders[h].length;i++) {
				for(int w=0;w<6;w++) {
					if(WorkOrders[h][i].getTypes()==Type[w]){
						WorkOrder[w][h]++;
						if(WorkOrders[h][i].getStatus()==Status.COMP) {
							Complete[w][h]++;
						}
						else if(WorkOrders[h][i].getStatus()==Status.B) {
							Shut[w][h]++;
							Unfinished[w][h]++;
						}
						else if(WorkOrders[h][i].getStatus()==Status.TESTCOMP) {
							Test[w][h]++;
							Unfinished[w][h]++;
						}
						else Unfinished[w][h]++;
					}
				}
				WorkOrder[6][h]++;
			}
			Complete[6][h]=Complete[0][h]+Complete[1][h]+Complete[2][h]+Complete[3][h]+Complete[4][h]+Complete[5][h];
			Shut[6][h]=Shut[0][h]+Shut[1][h]+Shut[2][h]+Shut[3][h]+Shut[4][h]+Shut[5][h];
			Test[6][h]=Test[0][h]+Test[1][h]+Test[2][h]+Test[3][h]+Test[4][h]+Test[5][h];
			Unfinished[6][h]=WorkOrder[6][h]-Complete[6][h];
			for(int z=0; z<7;z++) {
				result[z]=day(WorkOrders[h], Type[z],Complete[z][h]);
			}
			for(int q=0;q<7;q++) {
				avgDays[q][h]=S.mean(result[q]);
				stdDays[q][h]=S.stv(result[q]);
				minDays[q][h]=S.min(result[q]);
				maxDays[q][h]=S.max(result[q]);
				medianDays[q][h]=S.median(result[q]);
			}
		}	
	}

	protected String output() {
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
					"\t\t" +S.min(minDays[h])+ "\t\t" +S.max(maxDays[h])+"\n";
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