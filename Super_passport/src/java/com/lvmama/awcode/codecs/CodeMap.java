package com.lvmama.awcode.codecs;

public class CodeMap {

	private static CodeMap instance;
	
	private String VALUES = "0123456789ZNB\n";
	private String PHOTOS = "ACFHLSTUXYZNB\n";
	private String CN_PHOTOS = "ＡＣＦＨＬＳＴＵＸＹＺＮ■\n";
	
	public static int LINES = 6;
	public static int LETTERS_PER_LINE = 6;
	
	public static CodeMap getInstance() {
		if(instance == null) {
			instance=new CodeMap();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param src
	 */
	public String encode(String src) {
		if(checkStr(src)) {
			if(src.length()!=11) {
				for(int i=src.length();i<11;i++) {
					src += 'N';
				}
	 		}
			char[][] resultMap = new char[LETTERS_PER_LINE][LINES];
			char[] arr = src.toCharArray();
			
			resultMap[0][1]=arr[0];
			resultMap[0][2]=arr[1];
			resultMap[0][3]=arr[2];
			resultMap[0][4]=arr[3];
				       
			resultMap[1][0]=arr[4];
			resultMap[1][1]=arr[5];
			resultMap[1][2]=arr[6];
			resultMap[1][3]=arr[7];
			resultMap[1][4]=arr[8];
			resultMap[1][5]=createSum(getLine(resultMap,1));
				       
			resultMap[2][1]=arr[9];
			resultMap[2][2]=arr[10];
			resultMap[2][4]=arr[0];
			resultMap[2][5]=createSum(getLine(resultMap,2));
				       
			resultMap[3][0]=arr[1];
			resultMap[3][1]=arr[2];
			resultMap[3][2]=arr[3];
			resultMap[3][3]=arr[4];
			resultMap[3][4]=arr[5];
			resultMap[3][5]=createSum(getLine(resultMap,3));
				       
			resultMap[4][0]=arr[6];
			resultMap[4][1]=arr[7];
			resultMap[4][2]=arr[8];
			resultMap[4][3]=arr[9];
			resultMap[4][4]=arr[10];
			resultMap[4][5]=createSum(getLine(resultMap,4));
				       
			resultMap[5][1]=createSum(getColumns(resultMap,1));
			resultMap[5][2]=createSum(getColumns(resultMap,2));
			resultMap[5][3]=createSum(getColumns(resultMap,3));
			resultMap[5][4]=createSum(getColumns(resultMap,4));

			resultMap[0][0]=PHOTOS.charAt(12);
			resultMap[0][5]=PHOTOS.charAt(12);
			resultMap[2][0]=PHOTOS.charAt(12);
			resultMap[2][3]='Z';
			resultMap[5][0]=PHOTOS.charAt(12);
			resultMap[5][5]=PHOTOS.charAt(12);
			
			return toString(resultMap);
		}
		return null;
	}
	
	private String toString(char[][] charMap) {
		StringBuilder strBuilder = new StringBuilder();
		for(int i=0;i<LINES;i++) {
			String line = this.getLine(charMap, i);
			for(int j=0;j<line.length();j++) {
				int idx = VALUES.indexOf(line.charAt(j));
				if(idx!=-1) {
					strBuilder.append(CN_PHOTOS.charAt(idx));					
				}
			}
			strBuilder.append('\r');
			strBuilder.append('\n');
		}
		return strBuilder.toString();
	}
	
	private void toInteger(char[][] charMap) {
		for(int i=0;i<LINES;i++) {
			for(int j=0;j<LETTERS_PER_LINE;j++) {
				charMap[i][j] = VALUES.charAt(PHOTOS.indexOf(charMap[i][j]));
			}
		}
	}
	
	private boolean checkStr(String src) {
		if(src.length()>11){
			return false;
		}
		char[] srcArr = src.toCharArray();
		for(int i=0;i<src.length();i++) {
			if(VALUES.indexOf(srcArr[i])==-1) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 解码
	 * @param src
	 * @return
	 */
	public String decode(String src) {
		String[] lines = src.split("\n");
		if(lines.length!=LINES) {
			return null;
		}
		
		char[][] resultMap = new char[6][6];
		for(int j=0;j<lines.length;j++) {
			String line = lines[j];
			if(j==0 || j==5) {
				if(line.length()!=4) {
					return null;
				}else{
					line="B"+line+"B";
				}
			}
			if(j==1 || j==3 || j==4) {
				if(line.length()!=6) {
					return null;
				}
			}
			if(j==2) {
				if(line.length()!=5) {
					return null;
				}else{
					line="B"+line;
				}
			}
			for(int i=0;i<line.length();i++) {
				resultMap[j][i] = line.charAt(i);
			}
		}
		toInteger(resultMap);
		if(checkSum(resultMap)) {
			return merge(resultMap);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param charMap
	 * @return
	 */
	private boolean checkSum(char[][] charMap) {
		if(charMap[2][3]!='Z') {
			return false;
		}
		String l1 = String.valueOf(charMap[1]);
		if(!checkSum(l1)) {
			return false;
		}
		String l2 = String.valueOf(charMap[2]).replaceAll("B", "");
		if(!checkSum(l2)) {
			return false;
		}
		String l3 = String.valueOf(charMap[3]);
		if(!checkSum(l3)) {
			return false;
		}
		String l4 = String.valueOf(charMap[4]);
		if(!checkSum(l4)) {
			return false;
		}
		String c1 = getColumns(charMap, 1);
		if(!checkSum(c1)) {
			return false;
		}
		String c2 = getColumns(charMap, 2);
		if(!checkSum(c2)) {
			return false;
		}
		String c3 = getColumns(charMap, 3);
		if(!checkSum(c3)) {
			return false;
		}
		String c4 = getColumns(charMap, 4);
		if(!checkSum(c4)) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param charMap
	 * @param col
	 * @return
	 */
	private String getColumns(char[][] charMap, int col) {
		StringBuilder strBuilder = new StringBuilder();
 		for(int i=0;i<LINES;i++) {
 			strBuilder.append(charMap[i][col]);
		}
 		return strBuilder.toString();
	}
	
	/**
	 * @param charMap
	 * @param col
	 * @return
	 */
	private String getLine(char[][] charMap, int line) {
		StringBuilder strBuilder = new StringBuilder();
 		for(int i=0;i<LINES;i++) {
 			strBuilder.append(charMap[line][i]);
		}
 		return strBuilder.toString();
	}
	
	private boolean checkSum(String str) {
		char last = str.charAt(str.length()-1);
		int vl = createSum(str);
		return vl==last;
	}
	
	private char createSum(String str) {
		int s = 0;
		char[] arr = str.toCharArray();
		for(int i=0;i<str.length()-1;i++) {
			if(arr[i]!=0 && arr[i]!='N' && arr[i]!='Z') { 
				s+=Integer.parseInt(String.valueOf(arr[i]));
			}
		}
		String v = String.valueOf(s);
		char vl = v.charAt(v.length()-1);
		return vl;
	}
	
	private String merge(char[][] charMap) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(charMap[0],1,4);
		strBuilder.append(charMap[1],0,5);
		strBuilder.append(charMap[2],1,2);
		return strBuilder.toString().replaceAll("N", "");
	}
	
	public static void main(String[] args) {
//		System.out.println(CodeMap.getInstance().encode("71920484050"));
//		System.out.println(CodeMap.getInstance().encode("736496312"));
//
//		System.out.println(CodeMap.getInstance().decode("UCYF\nALXLAT\nSAZUF\nCYFALT\nXLASAU\nYCXH"));
//		System.out.println(CodeMap.getInstance().decode("UHTL\nYTHCFC\nJJZUU\nHTLYTX\nHCFJJT\nAFTY"));
	System.out.println(CodeMap.getInstance().encode("0123456"));
	System.out.println(CodeMap.getInstance().encode("01234567"));
	System.out.println(CodeMap.getInstance().encode("012345678"));
	System.out.println(CodeMap.getInstance().encode("0123456789"));
	System.out.println(CodeMap.getInstance().encode("01234567890"));

//	System.out.println(CodeMap.getInstance().decode("UCYF\nALXLAT\nSAZUF\nCYFALT\nXLASAU\nYCXH"));
//	System.out.println(CodeMap.getInstance().decode("UHTL\nYTHCFC\nJJZUU\nHTLYTX\nHCFJJT\nAFTY"));
	}

}
