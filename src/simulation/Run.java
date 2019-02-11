package simulation;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class Run {
	Boolean Demo = true;//trigger of Demo mode.//false
	int replication;//Number of replications.
	Run() {}
	Run(int r) {
		this.replication=r;
		this.Demo=false;
	}
	public void sim() throws IOException, CloneNotSupportedException {
		if(Demo) {
			Current c= new Current();
			c.run();
			c.toExcel();
		}
		int[] CIP= new int[replication];int[] CIPC= new int[replication];int[] CIPS= new int[replication];int[] CIPT= new int[replication];
		int[] ENG= new int[replication];int[] ENGC= new int[replication];int[] ENGS= new int[replication];int[] ENGT= new int[replication];
		int[] DEV= new int[replication];int[] DEVC= new int[replication];int[] DEVS= new int[replication];int[] DEVT= new int[replication];
		int[] SR= new int[replication];int[] SRC= new int[replication];int[] SRS= new int[replication];int[] SRT= new int[replication];
		int[] MTR= new int[replication];int[] MTRC= new int[replication];int[] MTRS= new int[replication];int[] MTRT= new int[replication];
		int[] INV= new int[replication];int[] INVC= new int[replication];int[] INVS= new int[replication];int[] INVT= new int[replication];
		int[] Total = new int[replication];int[] TotalC = new int[replication];int[] TotalS = new int[replication];int[] TotalT = new int[replication];
		int[] CIPU= new int[replication];int[] ENGU= new int[replication];int[] DEVU= new int[replication];int[] TotalU = new int[replication];
		int[] SRU= new int[replication];int[] MTRU= new int[replication];int[] INVU= new int[replication];
		long t = System.currentTimeMillis(); 
		STAT S= new STAT();
		double[][] days= new double[7][replication];
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
						long d=ChronoUnit.DAYS.between(LocalDate.parse(wo[i].getReport().toString()),LocalDate.parse(wo[i].getFinish().toString()));
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
						long d=ChronoUnit.DAYS.between(LocalDate.parse(wo[i].getReport().toString()),LocalDate.parse(wo[i].getFinish().toString()));
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
						long d=ChronoUnit.DAYS.between(LocalDate.parse(wo[i].getReport().toString()),LocalDate.parse(wo[i].getFinish().toString()));
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
						long d=ChronoUnit.DAYS.between(LocalDate.parse(wo[i].getReport().toString()),LocalDate.parse(wo[i].getFinish().toString()));
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
						long d=ChronoUnit.DAYS.between(LocalDate.parse(wo[i].getReport().toString()),LocalDate.parse(wo[i].getFinish().toString()));
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
						long d=ChronoUnit.DAYS.between(LocalDate.parse(wo[i].getReport().toString()),LocalDate.parse(wo[i].getFinish().toString()));
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
			double[] result=days(wo, SHCIPC,SHENGC,SHDEVC,SHSRC,SHMTRC,SHINVC,SHCIPC+SHENGC+SHDEVC+SHSRC+SHMTRC+SHINVC);
			CIP[y]=SHCIP;CIPC[y]=SHCIPC;CIPS[y]=SHCIPS;CIPT[y]=SHCIPT;CIPU[y]=SHCIP-SHCIPC;
			ENG[y]=SHENG;ENGC[y]=SHENGC;ENGS[y]=SHENGS;ENGT[y]=SHENGT;ENGU[y]=SHENG-SHENGC;
			DEV[y]=SHDEV;DEVC[y]=SHDEVC;DEVS[y]=SHDEVS;DEVT[y]=SHDEVT;DEVU[y]=SHDEV-SHDEVC;
			SR[y]=SHSR;SRC[y]=SHSRC;SRS[y]=SHSRS;SRT[y]=SHSRT;SRU[y]=SHSR-SHSRC;
			MTR[y]=SHMTR;MTRC[y]=SHMTRC;MTRS[y]=SHMTRS;MTRT[y]=SHMTRT;MTRU[y]=SHMTR-SHMTRC;
			INV[y]=SHINV;INVC[y]=SHINVC;INVS[y]=SHINVS;INVT[y]=SHINVT;INVU[y]=SHINV-SHINVC;
			Total[y]=SHCIP+SHENG+SHDEV+SHSR+SHMTR+SHINV;TotalU[y]=SHCIP+SHENG+SHDEV+SHSR+SHMTR+SHINV-(SHCIPC+SHENGC+SHDEVC+SHSRC+SHMTRC+SHINVC);
			TotalC[y]=SHCIPC+SHENGC+SHDEVC+SHSRC+SHMTRC+SHINVC;
			TotalS[y]=SHCIPS+SHENGS+SHDEVS+SHSRS+SHMTRS+SHINVS;
			TotalT[y]=SHCIPT+SHENGT+SHDEVT+SHSRT+SHMTRT+SHINVT;
			double x = 100.0*y/replication;
			if(x%5==0& x!=0) {
				String xstr = String.format("%3.3g", x);
				System.out.println(xstr+ "% complete");
			}
			for(int q=0;q<result.length;q++) {
			days[q][y]=result[q];
			}
		}
		System.out.println("100 % complete");
		String output=" Type "+" imput"+" input STD"+" Completed"+ " Shut-off" +" Tested"+" Unfinished"+" avg finish time";
		System.out.println(output);
		output="SHCIP  "+ Double.toString(S.mean(CIP))+ "\t" + String.format( "%.2f",S.stv(CIP))+"\t"+ Double.toString(S.mean(CIPC))+"\t  " 
				+  Double.toString(S.mean(CIPS))+ "\t  " + Double.toString(S.mean(CIPT))+ "\t  " + Double.toString(S.mean(CIPU))+ "\t\t" +String.format( "%.2f",S.mean(days[0]));
		System.out.println(output);
		output="SHENG   "+ Double.toString(S.mean(ENG))+ "\t" + String.format( "%.2f",S.stv(ENG))+"\t"+ Double.toString(S.mean(ENGC))+ "\t  "
				+Double.toString(S.mean(ENGS))+ "\t  " +Double.toString(S.mean(ENGT))+ "\t  " +Double.toString(S.mean(ENGU))+ "\t\t" +String.format( "%.2f",S.mean(days[1]));
		System.out.println(output);
		output="SHDEV  "+ Double.toString(S.mean(DEV))+ "\t" + String.format( "%.2f",S.stv(DEV))+"\t"+ Double.toString(S.mean(DEVC))+ "\t  "
				+ Double.toString(S.mean(DEVS))+ "\t  "+ Double.toString(S.mean(DEVT))+ "\t  "+ Double.toString(S.mean(DEVU))+ "\t\t" +String.format( "%.2f",S.mean(days[2]));
		System.out.println(output);
		output="SHSR   "+ Double.toString(S.mean(SR))+ "\t" + String.format( "%.2f",S.stv(SR))+"\t"+ Double.toString(S.mean(SRC))+ "\t  " 
				+ Double.toString(S.mean(SRS))+ "\t  " + Double.toString(S.mean(SRT))+ "\t  " + Double.toString(S.mean(SRU))+ "\t\t" +String.format( "%.2f",S.mean(days[3]));
		System.out.println(output);
		output="SHMTR  "+ Double.toString(S.mean(MTR))+ "\t" + String.format( "%.2f",S.stv(MTR))+"\t"+ Double.toString(S.mean(MTRC))+ "\t  "
				+ Double.toString(S.mean(MTRS))+ "\t  " + Double.toString(S.mean(MTRT))+ "\t  " + Double.toString(S.mean(MTRU))+ "\t\t" +String.format( "%.2f",S.mean(days[4]));
		System.out.println(output);
		output="SHINV  "+ Double.toString(S.mean(INV))+ "\t" + String.format( "%.2f",S.stv(INV))+"\t"+ Double.toString(S.mean(INVC))+ "\t  "
				+ Double.toString(S.mean(INVS))+ "\t  "+ Double.toString(S.mean(INVT))+ "\t  "+ Double.toString(S.mean(INVU))+ "\t\t" +String.format( "%.2f",S.mean(days[5]));
		System.out.println(output);
		output="Total "+ Double.toString(S.mean(Total))+ "\t" + String.format( "%.2f",S.stv(Total))+"\t"+ Double.toString(S.mean(TotalC))+ "\t  " 
				+ Double.toString(S.mean(TotalS))+ "\t  " + Double.toString(S.mean(TotalT))+ "\t  " + Double.toString(S.mean(TotalU))+ "\t\t" +String.format( "%.2f",S.mean(days[6]));
		System.out.println(output);
		System.out.print("\n"+replication+" runs done, operation took: " + (1.0*(System.currentTimeMillis()-t)/1000) + " seconds to complete.");
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