package simulation;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class Run {
	Boolean Demo =false ;//trigger of Demo mode.//false
	int replication;//Number of replications.
	int duration=426;// for setting date range
	double[][] avgDelay;
	double[][] SDDelay;
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
	int[][][] backlogs = new int [replication][7][duration];
	WorkOrder[][] WorkOrders;//Main stream work order storage
	STAT S= new STAT();

	Run(int r) throws CloneNotSupportedException {
		this.replication=r;
		if(r==1) {
			this.Demo=true;
		}
		sim();
	}

	private void sim() throws CloneNotSupportedException {
		WorkOrders= new WorkOrder[replication][];
		backlogs = new int [replication][7][];
		if(Demo) {		
			Current c= new Current();
			c.run(duration);
			WorkOrders[0]=c.Output();
			backlogs[0]=c.Delay();
		}
		else {
			for(int y = 0; y<replication;y++) {
				Current c= new Current();
				c.run(duration);
				WorkOrders[y]=c.Output();
				backlogs[y]=c.Delay();
				double x = 100.0*y/replication;
				if(x%5==0& x!=0) {
					String xstr = String.format("%3.3g", x);
					System.out.println(xstr+ "% complete");
				}
			}
			System.out.println("100 % complete\n");
		}
	}

	protected WorkOrder[][] output() {
		return WorkOrders;
	}

	protected void results() throws IOException {
		WorkOrder = new int[7][replication];
		Complete = new int[7][replication];Unfinished = new int[7][replication];
		Shut = new int[7][replication];Test = new int[7][replication];
		avgDays= new double[7][replication];stdDays= new double[7][replication];
		minDays= new int[7][replication];maxDays= new int[7][replication];
		medianDays= new double[7][replication];	
		int[][] result=new int[7][];
		String output;
		Types[] Type= {Types.SHCIP,Types.SHENG,Types.SHDEV,Types.SHSR,Types.SHMTR,Types.SHINV,null};
		System.out.println(WorkOrders[0].length);
		
		for(int h=0;h<WorkOrders.length;h++) {//replication
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
		FileWriter FW4 = new FileWriter("Run-detail.csv");
		output="run,In,Completed,Unfinished,duration";
		FW4.write(output+ "\n");
		for(int s=0;s<replication;s++) {
			output=s+1+","+WorkOrder[6][s]+","+Complete[6][s]+","+Unfinished[6][s]+","+avgDays[6][s];
			FW4.write(output+ "\n");
		}
		FW4.flush();
		FW4.close();
	}

	protected String ToSring() {
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
					"\t\t" +S.min(minDays[h])+ "\t\t" +S.max(maxDays[h])+"\n";//Check STD
		}
		return output;
	}

	protected void Backlog() throws IOException {
		String output;
		avgDelay= new double[7][backlogs[0][0].length];
		SDDelay= new double[7][backlogs[0][0].length];
		double[][] minDelay= new double[7][backlogs[0][0].length];
		double[][] maxDelay= new double[7][backlogs[0][0].length];
		int[][] temp=new int[backlogs[0][0].length][backlogs.length];
		for(int f=0; f<backlogs[0].length;f++) {//types
			for(int d=0;d<backlogs[0][f].length;d++) {//duration
				for(int n=0;n<backlogs.length;n++) {//replication
					temp[d][n]=backlogs[n][f][d];
				}	
				avgDelay[f][d]=S.mean(temp[d]);	
				minDelay[f][d]=S.min(temp[d]);
				maxDelay[f][d]=S.max(temp[d]);
				SDDelay[f][d]=S.stv(temp[d]);
			}
		}
		FileWriter FW2 = new FileWriter("Delay.csv");
		output= "Date,avg-Assessment,SD-Assessment,min-Assessment,max-Assessment";
		output+=",avg-notify1,SD-notify1,min-notify1,max-notify1";
		output+=",avg-test,SD-test,min-test,max-test";
		output+=",avg-Notify2,SD-Notify2,min-Notify2,max-Notify2";
		output+=",avg-shut,SD-shut,min-shut,max-shut";
		output+=",avg-recharge,SD-recharge,min-recharge,max-recharge";
		output+=",avg-total,SD-total,min-total,max-total";
		FW2.write(output+ "\n");
		for(int i=0;i<avgDelay[0].length;i++) {
			int day=i+1;
			output=String.valueOf(day);
			for(int k=0;k<avgDelay.length;k++) {
				output+=","+String.format( "%.2f",avgDelay[k][i])+","+String.format( "%.2f",SDDelay[k][i])+","+String.format( "%.2f",minDelay[k][i])+","+String.format( "%.2f",maxDelay[k][i]);
			}
			FW2.write(output+ "\n");
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

	protected void DailyReport() throws IOException {
		int x=0;
		String output;
		int[][][] Daily=new int[365][8][WorkOrders.length];
		double[][][] Summary= new double[365][8][4];
		Date d = new Date(117,9,1);//Start date
		Date end = new Date(117,9,1);//End date
		end.setDate(365);
		while(d.compareTo(end)<0) {
			for(int l=0;l<WorkOrders.length;l++) {//Replication			
				for(int u=0;u<WorkOrders[l].length;u++) {	
					if(WorkOrders[l][u].getReport().compareTo(d)==0) {
						Daily[x][0][l]++;
						Daily[x][7][l]++;
					}
					else if(WorkOrders[l][u].getAssesement().compareTo(d)==0) {
						Daily[x][1][l]++;
						Daily[x][7][l]++;
					}
					else if(WorkOrders[l][u].getNotify1().compareTo(d)==0) {
						Daily[x][2][l]++;
						Daily[x][7][l]++;
					}
					else if(WorkOrders[l][u].getTest().compareTo(d)==0) {
						Daily[x][3][l]++;
						Daily[x][7][l]++;
					}
					else if(WorkOrders[l][u].getNotify2().compareTo(d)==0) {
						Daily[x][4][l]++;
						Daily[x][7][l]++;
					}
					else if(WorkOrders[l][u].getShut().compareTo(d)==0) {
						Daily[x][5][l]++;
						Daily[x][7][l]++;
					}
					else if(WorkOrders[l][u].getFinish().compareTo(d)==0) {
						Daily[x][6][l]++;
						Daily[x][7][l]++;
					}		
				}
			}
			x +=1;
			d=  new Date(117,9,1);
			d.setDate(x);
		}
		for(int t=0;t<Daily.length;t++) {
			for(int c=0;c<Daily[t].length;c++) {
				Summary[t][c][0]=S.mean(Daily[t][c]);
				Summary[t][c][1]=S.stv(Daily[t][c]);
				Summary[t][c][2]=S.min(Daily[t][c]);
				Summary[t][c][3]=S.max(Daily[t][c]);			
			}
		}
		FileWriter FW3 = new FileWriter("DailyReport.csv");
		output="Date,avg-In,SD-In,min-In,max-In";
		output+=",avg-Assessment,SD-Assessment,min-Assessment,max-Assessment";
		output+=",avg-Notify1,SD-Notify1,min-Notify1,max-Notify1";
		output+=",avg-Test,SD-Test,min-Test,max-Test";
		output+=",avg-Notify2,SD-Motify2,min-Notify2,max-Notify2";
		output+=",avg-Shut,SD-Shut,min-Shut,max-Shut";
		output+=",avg-Recharge,SD-Recharge,min-Recharge,max-Recharge";
		output+=",avg-Total,SD-Total,min-Total,max-Total";
		FW3.write(output+ "\n");
		for(int i=0;i<Summary.length;i++) {
			int day=i+1;
			output=String.valueOf(day);
			for(int k=0;k<Summary[i].length;k++) {
				output+=","+String.format( "%.2f",Summary[i][k][0])+","+String.format( "%.2f",Summary[i][k][1])+","+String.format( "%.2f",Summary[i][k][2])+","+String.format( "%.2f",Summary[i][k][3]);
			}
			FW3.write(output+ "\n");
		}
		FW3.flush();
		FW3.close();
	}
}