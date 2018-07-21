package com.eBay.util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ExecutionLog {	
	
	 public static void Log(String text){
		 ExecutionLog executionLog = new ExecutionLog();	
		 String dateTime = executionLog.getDate();
		 String fileName = executionLog.getFileName();
		 try{			 
		    // Create file 
		    FileWriter fstream = new FileWriter("ExecutionLog//"+fileName+".txt",true);
		    BufferedWriter out = new BufferedWriter(fstream);
		    text = dateTime +" [info]  "+ text;
		    out.write(text);
		    out.newLine();
		    
		    //Close the output stream
		    out.close();
		    
		 }catch (Exception e){//Catch exception if any
		    System.err.println("Error: " + e.getMessage());
		  }
	 }
	 
	 public static void LogExceptionMessage(Exception e) throws IOException{
		 ExecutionLog executionLog = new ExecutionLog();
		 String dateTime = executionLog.getDate();
		 ExecutionLog.Log(dateTime +" [info]  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Error message >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");		 
		 String fileName = executionLog.getFileName();
		 PrintWriter pw;		
		 try {
			pw = new PrintWriter(new FileWriter("ExecutionLog//"+fileName+".txt", true));
			e.printStackTrace(pw);
			pw.close();
		 } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }		
	 }
	 
	 public static void LogErrorMessage(Error e) throws IOException{
		 ExecutionLog executionLog = new ExecutionLog();
		 String dateTime = executionLog.getDate();
		 ExecutionLog.Log(dateTime +" [info]  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Error message >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");		 
		 String fileName = executionLog.getFileName();
		 PrintWriter pw;
		 try {
			pw = new PrintWriter(new FileWriter("ExecutionLog//"+fileName+".txt", true));
			e.printStackTrace(pw);
			pw.close();
		 } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }		
	 }
	 
	 public  String getFileName(){
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar cal = Calendar.getInstance();		 
		 String fileName = "Report-"+dateFormat.format(cal.getTime());
		 return fileName;
	 }
	 
	 
	 public  String getDate(){
		 DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
		 Calendar cal = Calendar.getInstance();	
		 String dateTime =  dateFormat.format(cal.getTime());
		 return dateTime;
	 }
	 
	 public static void LogAddClass(String text){
		 ExecutionLog executionLog = new ExecutionLog();	
		 String dateTime = executionLog.getDate();
		 String fileName = executionLog.getFileName();
		 try{			 
		    // Create file 
		    FileWriter fstream = new FileWriter("ExecutionLog//"+fileName+".txt",true);
		    BufferedWriter out = new BufferedWriter(fstream);
		    text = dateTime +" [info]  "+" Execution Started of Test Class "+ text;
		    out.newLine();
		    out.write("*****************************************************************************");
		    out.newLine();
		    out.write(text);
		    out.newLine();
		    out.write("*****************************************************************************");
		    out.newLine();
		    //Close the output stream
		    out.close();
		    
		 }catch (Exception e){//Catch exception if any
		   System.err.println("Error: " + e.getMessage());
		 }
	 }
	 
	 public static void LogEndClass(String text){
		 ExecutionLog executionLog = new ExecutionLog();	
		 String dateTime = executionLog.getDate();
		 String fileName = executionLog.getFileName();
		 try{			 
		    // Create file 
		    FileWriter fstream = new FileWriter("ExecutionLog//"+fileName+".txt",true);
		    BufferedWriter out = new BufferedWriter(fstream);
		    text = dateTime +" [info]  End Execution of Test Class "+ text;		    
		    out.write(text);
		    out.newLine();
		    out.write("*****************************************************************************");
		    out.newLine();
		    //Close the output stream
		    out.close();
		    
		 }catch (Exception e){//Catch exception if any
		   System.err.println("Error: " + e.getMessage());
		 }
	 }
	 
	 public static void main(String[] str){	 
		 Log("Test execution");		 
	 }
	  
}
