package com.lvmama.search.lucene.score;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.queries.CustomScoreProvider;
import org.apache.lucene.queries.CustomScoreQuery;
import org.apache.lucene.search.Query;

public class VerLuceneCustomScoreQuery extends CustomScoreQuery {

	@Override
	protected CustomScoreProvider getCustomScoreProvider(
			AtomicReaderContext context) throws IOException {
		return new VerLuceneScoreProvider(context);
	}

	public VerLuceneCustomScoreQuery(Query subQuery) {
		super(subQuery);
	}

}



