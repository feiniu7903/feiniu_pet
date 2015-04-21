package com.lvmama.report.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;

public class ExcelExportUtil {

	private final String TEMP_DIR = "/tmp";
	
	private final String MAIN_SQL_TEMPLATE = "set linesize 32767\n"
			+"set pagesize 0\n"
			+"set feedback off\n"
			+"set termout off\n"
			+"set heading off\n"
			+"set trimspool on\n"
			+"set echo off\n"
			+"SET SQLBLANKLINES ON\n"
			+"spool ${RESULT_FILE}\n"
			+"@${QUERY_SQL_FILE}\n"
			+"spool off\n"
			+"exit";
	
	private final String EXEC_TEMPLATE = "#!/bin/bash\n"
			+"source /home/oracle/.bash_profile\n"
			+"sqlplus ";
	
	private String fileIdentity;
	private String whereStatement;
	private String queryTemplateFile;
	
	public ExcelExportUtil(String fileIdentity, String whereStatement, String queryTemplateFile) {
		this.fileIdentity = fileIdentity;
		this.whereStatement = whereStatement;
		this.queryTemplateFile = queryTemplateFile;
	}
	
	public File create() {
		try{
			String theTime = String.valueOf(System.currentTimeMillis());
			String resultFile = TEMP_DIR + "/report_" +  fileIdentity + "_" + theTime + ".csv";
			String querySqlFile = TEMP_DIR + "/report_" +  fileIdentity + "_" + theTime + "_query.sql";
			String mainSqlFile = TEMP_DIR + "/report_" +  fileIdentity + "_" + theTime + "_main.sql";
			String mainExecFile = TEMP_DIR + "/report_" +  fileIdentity + "_" + theTime + "_main.sh";
			
			String mainSql = StringUtils.replace(MAIN_SQL_TEMPLATE, "${RESULT_FILE}", resultFile);
			mainSql = StringUtils.replace(mainSql, "${QUERY_SQL_FILE}", querySqlFile);
			FileWriter mainWriter = new FileWriter(mainSqlFile); 
			mainWriter.write(mainSql);
			mainWriter.close();
			
			String queryContent = "";
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(queryTemplateFile), "GBK"));
			String line = reader.readLine();
			while(line!=null) {
				line = line.trim();
				queryContent+=line+"\n";
				line = reader.readLine();
			}
			reader.close();
			queryContent += whereStatement;
			PrintWriter queryWriter = new PrintWriter(querySqlFile,"GBK"); 
			queryWriter.write(queryContent);
			queryWriter.close();
			String execContent = EXEC_TEMPLATE + Constant.getInstance().getProperty("connString").trim() + " @" + mainSqlFile;
			FileWriter mainExecWriter = new FileWriter(mainExecFile); 
			mainExecWriter.write(execContent);
			mainExecWriter.close();
			
			Runtime runtime = Runtime.getRuntime();
			Process chmodProcess = runtime.exec("/bin/chmod +x "+mainExecFile);
			int val = chmodProcess.waitFor();
			System.out.println("chmod process Exit, status="+val);
			
			Process process = runtime.exec(mainExecFile);
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String output;
			while ((output = in.readLine()) != null) {
				System.out.println(output);
			}
			int exitVal = process.waitFor();
			System.out.println("export process Exit, status="+exitVal);
			
			new File(querySqlFile).delete();
			new File(mainSqlFile).delete();
			new File(mainExecFile).delete();
			
			File file = new File(resultFile);
			if (file.exists()){
				System.out.println("create xls success: " + file.getAbsolutePath());
				return file;
			}
			System.out.println("create xls failed: " + file.getAbsolutePath());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		ExcelExportUtil util = new ExcelExportUtil("alex", "where create_time>sysdate-1;", "order_report.sql");
		util.create();
	}
	
}
