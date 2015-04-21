package com.lvmama.search.lucene.score;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.search.Query;

public class LuceneCustomScoreQuery extends CustomScoreQuery {

	@Override
	protected CustomScoreProvider getCustomScoreProvider(
			AtomicReaderContext context) throws IOException {
		return new LuceneScoreProvider(context);
	}

	public LuceneCustomScoreQuery(Query subQuery) {
		super(subQuery);
	}

}



