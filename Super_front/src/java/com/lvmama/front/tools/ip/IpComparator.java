package com.lvmama.front.tools.ip;

import java.util.Comparator;

import com.lvmama.comm.pet.po.pub.ComIps;

public class IpComparator implements Comparator<ComIps> {

	@Override
	public int compare(ComIps arg0, ComIps arg1) {
		 return (arg0.getIpStart()-arg0.getIpStart())>Integer.MAX_VALUE?Integer.MAX_VALUE:(int)(arg0.getIpStart()-arg0.getIpStart());
	}

}
