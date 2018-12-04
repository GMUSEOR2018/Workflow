package simulation;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		int replaction=1000;
		int[] CIP= new int[replaction];
		int[] ENG= new int[replaction];
		int[] DEV= new int[replaction];
		int[] SR= new int[replaction];
		int[] MTR= new int[replaction];
		int[] INV= new int[replaction];
		STAT S= new STAT();
		for(int y = 0; y<replaction;y++) {
			Current C = new Current();
			int SHCIP = 0,SHENG = 0,SHDEV = 0,SHSR = 0,SHMTR = 0,SHINV = 0;
			C.setUp();
			//C.toExcel();
			WorkOrder[] wo=C.Output();
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
				else {System.out.println("No type assigned.");}
			}
			CIP[y]=SHCIP;
			ENG[y]=SHENG;
			DEV[y]=SHDEV;
			SR[y]=SHSR;
			MTR[y]=SHMTR;
			INV[y]=SHINV;
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
		System.out.print("Done!!");
	}

}
