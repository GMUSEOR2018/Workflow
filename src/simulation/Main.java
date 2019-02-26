package simulation;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;

//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Main {
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		int replication=1000;//Number of run
		//boolean output=false;
		long t = System.currentTimeMillis(); // start time record.
		System.out.println("Start!");
		Run r =new Run(replication);
		System.out.println(replication+" runs done, operation took: " + (1.0*(System.currentTimeMillis()-t)/1000) + " seconds to complete.\n");
		r.results();
		System.out.print(r.ToSring());			
		r.Backlog();
		r.DailyReport();
//		if(output){
//			toExcel(r.output());
//		}
		System.out.print("\nOuptut done, operation took: " + (1.0*(System.currentTimeMillis()-t)/1000) + " seconds to complete.\n");
	}
//	private static void toExcel(WorkOrder[][] wo) throws IOException {
//		String[]  columns= {"Work Order","Status", "Reported", "Type","Assesment","Notify1","Test","Notify2","Shut","Recharge","Next Date"};
//		Workbook workbook = new XSSFWorkbook(); 
//		CreationHelper createHelper = workbook.getCreationHelper();
//		for(int j=0;j<wo.length;j++) {//read data by runs
//			Sheet sheet = workbook.createSheet(String.valueOf(j+1));
//			// Create a Font for styling header cells
//			Font headerFont = workbook.createFont();
//			headerFont.setBold(true);
//			headerFont.setFontHeightInPoints((short) 14);
//			headerFont.setColor(IndexedColors.RED.getIndex());
//
//			// Create a CellStyle with the font
//			CellStyle headerCellStyle = workbook.createCellStyle();
//			headerCellStyle.setFont(headerFont);
//			Row headerRow = sheet.createRow(0);
//			// Create cells
//			for(int i = 0; i < columns.length; i++) {
//				Cell cell = headerRow.createCell(i);
//				cell.setCellValue(columns[i]);
//				cell.setCellStyle(headerCellStyle);
//			}
//			// Create Cell Style for formatting Date
//			CellStyle dateCellStyle = workbook.createCellStyle();
//			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));
//
//			//Create Other rows and cells with employees data
//			int rowNum = 1;
//			for(int i=0;i<wo[j].length;i++) {
//				Row row = sheet.createRow(rowNum++);
//				row.createCell(0)
//				.setCellValue(wo[j][i].getID());
//
//				row.createCell(1)
//				.setCellValue(wo[j][i].getStatus().toString());
//
//				Cell dateOfReport = row.createCell(2);
//				dateOfReport.setCellValue(wo[j][i].getReport());
//				dateOfReport.setCellStyle(dateCellStyle);
//
//				row.createCell(3)
//				.setCellValue(wo[j][i].getTypes().toString());
//
//				Cell dateOfAss = row.createCell(4);
//				if(wo[j][i].getAssesement().compareTo(new Date(117,8,1))==0) {
//					dateOfAss.setCellValue("");
//				}
//				else {
//					dateOfAss.setCellValue(wo[j][i].getAssesement());
//					dateOfAss.setCellStyle(dateCellStyle);
//				}
//				Cell dateOfNotify1 = row.createCell(5);
//				if(wo[j][i].getNotify1().compareTo(new Date(117,8,1))==0) {
//					dateOfNotify1.setCellValue("");
//				}
//				else {
//					dateOfNotify1.setCellValue(wo[j][i].getNotify1());
//					dateOfNotify1.setCellStyle(dateCellStyle);
//				}
//
//				Cell dateOfTest = row.createCell(6);
//				if(wo[j][i].getTest().compareTo(new Date(117,8,1))==0) {
//					dateOfTest.setCellValue("");
//				}
//				else {
//					dateOfTest.setCellValue(wo[j][i].getTest());
//					dateOfTest.setCellStyle(dateCellStyle);
//				}
//
//				Cell dateOfNotify2 = row.createCell(7);
//				if(wo[j][i].getNotify2().compareTo(new Date(117,8,1))==0) {
//					dateOfNotify2.setCellValue("");
//				}
//				else {
//					dateOfNotify2.setCellValue(wo[j][i].getNotify2());
//					dateOfNotify2.setCellStyle(dateCellStyle);
//				}
//
//				Cell dateOfShut = row.createCell(8);
//				if(wo[j][i].getShut().compareTo(new Date(117,8,1))==0) {
//					dateOfShut.setCellValue("");
//				}
//				else{ dateOfShut.setCellValue(wo[j][i].getShut());
//				dateOfShut.setCellStyle(dateCellStyle);}
//
//				Cell dateOfFinish = row.createCell(9);
//				if(wo[j][i].getFinish().compareTo(new Date(117,8,1))==0) {
//					dateOfFinish.setCellValue("");
//				}
//				else {
//					dateOfFinish.setCellValue(wo[j][i].getFinish());
//					dateOfFinish.setCellStyle(dateCellStyle);}
//
//				Cell dateOfNext = row.createCell(10);
//				dateOfNext.setCellValue(wo[j][i].getNext());
//				dateOfNext.setCellStyle(dateCellStyle);
//			}
//			// Resize all columns to fit the content size
//			for(int i = 0; i < columns.length; i++) {
//				sheet.autoSizeColumn(i);
//			}
//		}
//		FileOutputStream fileOut = new FileOutputStream("Result.xlsx");
//		workbook.write(fileOut);
//		fileOut.close();
//		workbook.close();
//	}
}
