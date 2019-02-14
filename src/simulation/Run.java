package simulation;
import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class Run {
	Boolean Demo = true;//trigger of Demo mode.//false
	int replication;//Number of replications.
	double[] avgDelay;
	int[][] delay;
	String[] type ={"SHCIP","SHENG","SHDEV","SHSR","SHMTR","SHINV","Total"};
	//			1.CIP
	//			2.ENG
	//			3.DEV
	//			4.SR
	//			5.MTR
	//			6.INV
	//			7.Total
	int[][] WorkOrder = new int[7][replication];
	int[][] Complete = new int[7][replication];
	int[][] Shut = new int[7][replication];
	int[][] Test = new int[7][replication];
	int[][] Unfinished = new int[7][replication];
	double[][] days= new double[7][replication];
	STAT S= new STAT();
	
	Run() {}
	
	Run(int r) {
		this.replication=r;
		this.Demo=false;
	}
	
	public void sim() throws IOException, CloneNotSupportedException {
		long t = System.currentTimeMillis(); // start time record.
		if(Demo) {
			Current c= new Current();
			c.run();
			c.toExcel();
		}
		WorkOrder = new int[7][replication];
		Complete = new int[7][replication];
		Shut = new int[7][replication];
		Test = new int[7][replication];
		Unfinished = new int[7][replication];
		days= new double[7][replication];
		delay=new int[replication][];
		for(int y = 0; y<replication;y++) {
			int SHCIP = 0,SHCIPC =0,SHCIPS = 0,SHCIPT = 0;
			int SHENG = 0,SHENGC = 0,SHENGS = 0,SHENGT = 0;
			int SHDEV = 0,SHDEVC = 0,SHDEVS = 0,SHDEVT = 0;
			int SHSR = 0,SHSRC = 0,SHSRS = 0,SHSRT = 0;
			int SHMTR = 0,SHMTRC = 0,SHMTRS = 0,SHMTRT = 0;
			int SHINV = 0,SHINVC = 0,SHINVS = 0,SHINVT = 0; 
			Current c= new Current();
			c.run();
			WorkOrder[] wo=c.Output();			
			for(int i=0;i<wo.length;i++) {
				if(wo[i].getTypes()==Types.SHCIP){
					SHCIP++;
					if(wo[i].getStatus()==Status.COMP) {
						SHCIPC++;
					}
					else if(wo[i].getStatus()==Status.B) {
						SHCIPS++;
					}
					else if(wo[i].getStatus()==Status.TESTCOMP) {
						SHCIPT++;
					}
				}
				else if(wo[i].getTypes()==Types.SHENG){
					SHENG++;
					if(wo[i].getStatus()==Status.COMP) {
						SHENGC++;
					}
					else if(wo[i].getStatus()==Status.B) {
						SHENGS++;
					}
					else if(wo[i].getStatus()==Status.TESTCOMP) {
						SHENGT++;
					}
				}
				else if(wo[i].getTypes()==Types.SHDEV){
					SHDEV++;
					if(wo[i].getStatus()==Status.COMP) {
						SHDEVC++;
					}
					else if(wo[i].getStatus()==Status.B) {
						SHDEVS++;
					}
					else if(wo[i].getStatus()==Status.TESTCOMP) {
						SHDEVT++;
					}
				}
				else if(wo[i].getTypes()==Types.SHSR){
					SHSR++;
					if(wo[i].getStatus()==Status.COMP) {
						SHSRC++;
					}
					else if(wo[i].getStatus()==Status.B) {
						SHSRS++;
					}
					else if(wo[i].getStatus()==Status.TESTCOMP) {
						SHSRT++;
					}
				}
				else if(wo[i].getTypes()==Types.SHMTR){
					SHMTR++;
					if(wo[i].getStatus()==Status.COMP) {
						SHMTRC++;
					}
					else if(wo[i].getStatus()==Status.B) {
						SHMTRS++;
					}
					else if(wo[i].getStatus()==Status.TESTCOMP) {
						SHMTRT++;
					}
				}
				else if(wo[i].getTypes()==Types.SHINV){
					SHINV++;
					if(wo[i].getStatus()==Status.COMP) {
						SHINVC++;
					}
					else if(wo[i].getStatus()==Status.B) {
						SHINVS++;
					}
					else if(wo[i].getStatus()==Status.TESTCOMP) {
						SHINVT++;
					}
				}
				else System.out.println("No type assigned.");
			}
			double[] result=days(wo, SHCIPC,SHENGC,SHDEVC,SHSRC,SHMTRC,SHINVC,SHCIPC+SHENGC+SHDEVC+SHSRC+SHMTRC+SHINVC);//TODO:Change to array 
		
			WorkOrder[0][y]=SHCIP;Complete[0][y]=SHCIPC;Shut[0][y]=SHCIPS;Test[0][y]=SHCIPT;Unfinished[0][y]=SHCIP-SHCIPC;
			WorkOrder[1][y]=SHENG;Complete[1][y]=SHENGC;Shut[1][y]=SHENGS;Test[1][y]=SHENGT;Unfinished[1][y]=SHENG-SHENGC;
			WorkOrder[2][y]=SHDEV;Complete[2][y]=SHDEVC;Shut[2][y]=SHDEVS;Test[2][y]=SHDEVT;Unfinished[2][y]=SHDEV-SHDEVC;
			WorkOrder[3][y]=SHSR;Complete[3][y]=SHSRC;Shut[3][y]=SHSRS;Test[3][y]=SHSRT;Unfinished[3][y]=SHSR-SHSRC;
			WorkOrder[4][y]=SHMTR;Complete[4][y]=SHMTRC;Shut[4][y]=SHMTRS;Test[4][y]=SHMTRT;Unfinished[4][y]=SHMTR-SHMTRC;
			WorkOrder[5][y]=SHINV;Complete[5][y]=SHINVC;Shut[5][y]=SHINVS;Test[5][y]=SHINVT;Unfinished[5][y]=SHINV-SHINVC;
			WorkOrder[6][y]=SHCIP+SHENG+SHDEV+SHSR+SHMTR+SHINV;Unfinished[6][y]=SHCIP+SHENG+SHDEV+SHSR+SHMTR+SHINV-(SHCIPC+SHENGC+SHDEVC+SHSRC+SHMTRC+SHINVC);
			Complete[6][y]=SHCIPC+SHENGC+SHDEVC+SHSRC+SHMTRC+SHINVC;Shut[6][y]=SHCIPS+SHENGS+SHDEVS+SHSRS+SHMTRS+SHINVS;Test[6][y]=SHCIPT+SHENGT+SHDEVT+SHSRT+SHMTRT+SHINVT;

			double x = 100.0*y/replication;
			if(x%5==0& x!=0) {
				String xstr = String.format("%3.3g", x);
				System.out.println(xstr+ "% complete");
			}
			for(int q=0;q<result.length;q++) {
				days[q][y]=result[q];
			}
			delay[y]=c.Delay();
		}
		System.out.println("100 % complete");
		avgDelay= new double[delay[0].length];
		int[] temp=new int[delay.length];
		for(int f=0; f<delay[0].length;f++) {
			for(int d=0;d<delay.length;d++) {
				temp[d]=delay[d][f];
			}
			avgDelay[f]=S.mean(temp);	
		}

		System.out.print(output());
		System.out.print("\n"+replication+" runs done, operation took: " + (1.0*(System.currentTimeMillis()-t)/1000) + " seconds to complete.");
		//System.out.println(output);
		//		output="SHENG   "+ Double.toString(S.mean(WorkOrder[1]))+ "\t" + String.format( "%.2f",S.stv(WorkOrder[0]))+"\t"+ Double.toString(S.mean(ENGC))+ "\t  "
		//				+Double.toString(S.mean(ENGS))+ "\t  " +Double.toString(S.mean(ENGT))+ "\t  " +Double.toString(S.mean(ENGU))+ "\t\t" +String.format( "%.2f",S.mean(days[1]));
		//		System.out.println(output);
		//		output="SHDEV  "+ Double.toString(S.mean(WorkOrder[2]))+ "\t" + String.format( "%.2f",S.stv(WorkOrder[0]))+"\t"+ Double.toString(S.mean(DEVC))+ "\t  "
		//				+ Double.toString(S.mean(DEVS))+ "\t  "+ Double.toString(S.mean(DEVT))+ "\t  "+ Double.toString(S.mean(DEVU))+ "\t\t" +String.format( "%.2f",S.mean(days[2]));
		//		System.out.println(output);
		//		output="SHSR   "+ Double.toString(S.mean(WorkOrder[3]))+ "\t" + String.format( "%.2f",S.stv(SR))+"\t"+ Double.toString(S.mean(SRC))+ "\t  " 
		//				+ Double.toString(S.mean(SRS))+ "\t  " + Double.toString(S.mean(SRT))+ "\t  " + Double.toString(S.mean(SRU))+ "\t\t" +String.format( "%.2f",S.mean(days[3]));
		//		System.out.println(output);
		//		output="SHMTR  "+ Double.toString(S.mean(WorkOrder[4]))+ "\t" + String.format( "%.2f",S.stv(MTR))+"\t"+ Double.toString(S.mean(MTRC))+ "\t  "
		//				+ Double.toString(S.mean(MTRS))+ "\t  " + Double.toString(S.mean(MTRT))+ "\t  " + Double.toString(S.mean(MTRU))+ "\t\t" +String.format( "%.2f",S.mean(days[4]));
		//		System.out.println(output);
		//		output="SHINV  "+ Double.toString(S.mean(WorkOrder[5]))+ "\t" + String.format( "%.2f",S.stv(INV))+"\t"+ Double.toString(S.mean(INVC))+ "\t  "
		//				+ Double.toString(S.mean(INVS))+ "\t  "+ Double.toString(S.mean(INVT))+ "\t  "+ Double.toString(S.mean(INVU))+ "\t\t" +String.format( "%.2f",S.mean(days[5]));
		//		System.out.println(output);
		//		output="Total "+ Double.toString(S.mean(WorkOrder[6]))+ "\t" + String.format( "%.2f",S.stv(Total))+"\t"+ Double.toString(S.mean(TotalC))+ "\t  " 
		//				+ Double.toString(S.mean(TotalS))+ "\t  " + Double.toString(S.mean(TotalT))+ "\t  " + Double.toString(S.mean(TotalU))+ "\t\t" +String.format( "%.2f",S.mean(days[6]));
		//		System.out.println(output);
	}

	private String output() {
		String output=" Type "+" imput"+" input STD"+" Completed"+ " Shut-off" +" Tested"+" Unfinished"+" avg finish time\n";
		for(int h=0; h<7;h++) {
			output+=type[h]+ Double.toString(S.mean(WorkOrder[h]))+ "\t" + String.format( "%.2f",S.stv(WorkOrder[h]))+"\t"+ Double.toString(S.mean(Complete[h]))+"\t  " 
					+  Double.toString(S.mean(Shut[h]))+ "\t  " + Double.toString(S.mean(Test[h]))+ "\t  " + Double.toString(S.mean(Unfinished[h]))+ "\t\t" +String.format( "%.2f",S.mean(days[h]))+"\n";
		}
		return output;
	}
	
	public void Backlog() throws IOException {
		FileWriter FW2 = new FileWriter("Delay.csv");
		for(int i=0;i<avgDelay.length;i++) {
			int day=i+1;
			FW2.write(day+","+ String.format( "%.2f",avgDelay[i])+"\n");
		}
		FW2.flush();
		FW2.close();
	} 

	private double[] days(WorkOrder[] wo,int SHCIPC,int SHENGC,int SHDEVC,int SHSRC,int SHMTRC,int SHINVC,int TotalC) {
		double[] result=new double[7];
		int[] CIPA= new int[SHCIPC];int[] ENGA= new int[SHENGC];int[] DEVA= new int[SHDEVC];int[] TotalA = new int[TotalC];
		int[] SRA= new int[SHSRC];int[] MTRA= new int[SHMTRC];int[] INVA= new int[SHINVC];
		int cip=0,eng=0,dev=0,sr=0,total=0,mtr=0,inv=0;
		STAT S= new STAT();
		for(int x = 0;x<wo.length;x++) {
			long d=0;
			if(wo[x].getStatus()==Status.COMP) {
				if(wo[x].getTypes()==Types.SHCIP) {
					d=ChronoUnit.DAYS.between(LocalDate.parse(wo[x].getReport().toString()),LocalDate.parse(wo[x].getFinish().toString()));
					CIPA[cip]=(int) d;
					cip++;
				}
				else if(wo[x].getTypes()==Types.SHENG) {
					d=ChronoUnit.DAYS.between(LocalDate.parse(wo[x].getReport().toString()),LocalDate.parse(wo[x].getFinish().toString()));
					ENGA[eng]=(int) d;
					eng++;
				}
				else if(wo[x].getTypes()==Types.SHDEV) {
					d=ChronoUnit.DAYS.between(LocalDate.parse(wo[x].getReport().toString()),LocalDate.parse(wo[x].getFinish().toString()));
					DEVA[dev]=(int) d;
					dev++;
				}
				else if(wo[x].getTypes()==Types.SHSR) {
					d=ChronoUnit.DAYS.between(LocalDate.parse(wo[x].getReport().toString()),LocalDate.parse(wo[x].getFinish().toString()));
					SRA[sr]=(int) d;
					sr++;
				}
				else if(wo[x].getTypes()==Types.SHMTR) {
					d=ChronoUnit.DAYS.between(LocalDate.parse(wo[x].getReport().toString()),LocalDate.parse(wo[x].getFinish().toString()));
					MTRA[mtr]=(int) d;
					mtr++;
				}
				else if(wo[x].getTypes()==Types.SHINV) {
					d=ChronoUnit.DAYS.between(LocalDate.parse(wo[x].getReport().toString()),LocalDate.parse(wo[x].getFinish().toString()));
					INVA[inv]=(int) d;
					inv++;
				}
				TotalA[total]=(int) d;
				total++;
			}
		}
		result[0]=S.mean(CIPA);result[1]=S.mean(ENGA);result[2]=S.mean(DEVA);
		result[3]=S.mean(SRA);result[4]=S.mean(MTRA);result[5]=S.mean(INVA);result[6]=S.mean(TotalA);
		return result;
	}
}