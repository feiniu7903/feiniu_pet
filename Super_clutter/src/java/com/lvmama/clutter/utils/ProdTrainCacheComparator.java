package com.lvmama.clutter.utils;

import java.util.Comparator;

import com.lvmama.comm.pet.po.search.ProdTrainCache;

public class ProdTrainCacheComparator implements Comparator<ProdTrainCache>{

	@Override
	public int compare(ProdTrainCache o1, ProdTrainCache o2) {
		return o1.getSeatType().compareTo(o2.getSeatType());
	}

}
