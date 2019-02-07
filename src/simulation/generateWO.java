package simulation;
import java.io.IOException;
//generate WO with 2 mode
//mode 1 Demo, it outputs as excel file 
//mode 2 run, it gives statistical result base on the number of replication prompt.
class generateWO {
	Boolean Demo = true;//trigger of Demo mode.
	int replication;//Number of replications.
	generateWO() {}
	generateWO(int r) {
		this.replication=r;
		this.Demo=false;
	}
	
	public void run() throws IOException {
		if(Demo) {
			WOgen g = new WOgen();
			g.setUp();
			g.toExcel();
		}
		else {
			int[] CIP= new int[replication];
			int[] ENG= new int[replication];
			int[] DEV= new int[replication];
			int[] SR= new int[replication];
			int[] MTR= new int[replication];
			int[] INV= new int[replication];
			int[] Total = new int[replication];
			STAT S= new STAT();
			for(int y = 0; y<replication;y++) {
				WOgen g = new WOgen();
				int SHCIP = 0,SHENG = 0,SHDEV = 0,SHSR = 0,SHMTR = 0,SHINV = 0;
				g.setUp();
				WorkOrder[] wo=g.Output();
				for(int i=0;i<wo.length;i++) {
					if(wo[i].getTypes()==Types.SHCIP){
						SHCIP++;
					}
					else if(wo[i].getTypes()==Types.SHENG){
						SHENG++;
					}
					else if(wo[i].getTypes()==Types.SHDEV){
						SHDEV++;
					}
					else if(wo[i].getTypes()==Types.SHSR){
						SHSR++;
					}
					else if(wo[i].getTypes()==Types.SHMTR){
						SHMTR++;
					}
					else if(wo[i].getTypes()==Types.SHINV){
						SHINV++;
					}
					else System.out.println("No type assigned.");
				}
				CIP[y]=SHCIP;ENG[y]=SHENG;DEV[y]=SHDEV;SR[y]=SHSR;MTR[y]=SHMTR;INV[y]=SHINV;
				Total[y]=SHCIP+SHENG+SHDEV+SHSR+SHMTR+SHINV;
			}

			String output=" Type "+"  mean  "+"  STD";
			System.out.println(output);
			output="SHCIP "+ Double.toString(S.mean(CIP))+ " " + Double.toString(S.stv(CIP));
			System.out.println(output);
			output="SHENG "+ Double.toString(S.mean(ENG))+ " " + Double.toString(S.stv(ENG));
			System.out.println(output);
			output="SHDEV "+ Double.toString(S.mean(DEV))+ " " + Double.toString(S.stv(DEV));
			System.out.println(output);
			output="SHSR "+ Double.toString(S.mean(SR))+ " " + Double.toString(S.stv(SR));
			System.out.println(output);
			output="SHMTR "+ Double.toString(S.mean(MTR))+ " " + Double.toString(S.stv(MTR));
			System.out.println(output);
			output="SHINV "+ Double.toString(S.mean(INV))+ " " + Double.toString(S.stv(INV));
			System.out.println(output);
			output="Total "+ Double.toString(S.mean(Total))+ " " + Double.toString(S.stv(Total));
			System.out.println(output);
		}

	}
}


