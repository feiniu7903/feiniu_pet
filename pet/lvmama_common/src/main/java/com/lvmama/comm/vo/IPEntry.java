package com.lvmama.comm.vo;

public class IPEntry implements Comparable<IPEntry> {

	private final long start;
	private final long end;

	public IPEntry(long ip) {
		this.start = ip;
		this.end = ip;
	}

	public IPEntry(long start, long end) {
		this.start = start;
		this.end = end;
	}

	public int compareTo(IPEntry t) {
		long t1 = start - t.start;
		if (t1 < 0) {
			return -1;
		}
		long t2 = end - t.end;
		if (t1 >= 0 && t2 <= 0) {
			return 0;
		}
		return 1;
	}

}