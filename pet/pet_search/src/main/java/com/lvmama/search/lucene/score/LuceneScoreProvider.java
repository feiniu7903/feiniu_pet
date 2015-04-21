package com.lvmama.search.lucene.score;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.search.FieldCache;

import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.util.LuceneCommonDic;

public class LuceneScoreProvider extends CustomScoreProvider {
	FieldCache.Doubles scores=null;
	FieldCache.Floats lucenefactors=null;
	public LuceneScoreProvider(AtomicReaderContext context) {
		super(context);
		try {
			scores=FieldCache.DEFAULT.getDoubles(context.reader(),PlaceDocument.NORMALSCORE,true);
			lucenefactors=FieldCache.DEFAULT.getFloats(context.reader(),PlaceDocument.LUCENEFACTOR,true);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	@Override
	public float customScore(int doc, float subQueryScore, float valSrcScore)
			throws IOException {
		double score=scores.get(doc);
		float tmpscore=(float)score;
		float lucenefactor=lucenefactors.get(doc);
		return lucenefactor*subQueryScore+tmpscore;
	}
}
