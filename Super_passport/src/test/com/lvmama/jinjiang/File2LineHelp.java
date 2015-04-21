package com.lvmama.jinjiang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class File2LineHelp {
	private String line = null;
	private BufferedReader br = null;
	private boolean isEnd = false;
	
	public File2LineHelp() {
	}
	
	public File2LineHelp(String filePath) throws FileNotFoundException {
		init(filePath);
	}
	
	public File2LineHelp(File file) throws FileNotFoundException {
		init(file);
	}
	
	private void init(File file) throws FileNotFoundException {
		if (br != null) {
			close();
		}
		br = new BufferedReader(new FileReader(file));
		isEnd = false;
	}
	
	private void init(String filePath) throws FileNotFoundException {
		init(new File(filePath));
	}
	
	public void setFile(String filePath) throws FileNotFoundException {
		init(filePath);
	}
	
	public void setFile(File file) throws FileNotFoundException {
		init(file);
	}
	
	private void readLine() throws IOException {
		if (br != null) {
			line = br.readLine();
			if (line == null) {
				isEnd = true;
			}
		} else {
			if (!isEnd) {
				isEnd = true;
			}
		}
	}
	
	public String getLine() {
		return line;
	}
	
	public boolean nextLine() throws IOException {
		if (isEnd()) {
			return false;
		} else {
			readLine();
			return true;
		}
	}
	
	public boolean isEnd() {
		return isEnd;
	}
	
	
	public void close() {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			br = null;
		}
	}
}
