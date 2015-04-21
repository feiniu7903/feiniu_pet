package com.lvmama.search.lucene.search;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.search.NRTManagerReopenThread;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lvmama.search.lucene.LuceneContext;


public class Searcher {
	
	Log log  = LogFactory.getLog(this.getClass());
	
	private Analyzer analyzer;

	/**索引路径*/
	private String indexPath;
	
	private IndexWriter iw = null ;
	private TrackingIndexWriter writer = null;
	
    private NRTManager nrtMgr = null;
    
    private NRTManagerReopenThread reopen;
    /** 索引器 */
	private IndexSearcher indexSearcher = null;
    
    public IndexSearcher openSearcher(){
    	try {
			indexSearcher = nrtMgr.acquire();
    	} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return this.indexSearcher;
    }
    public Searcher(String indexPath,Analyzer analyzer){
    	this.indexPath = indexPath;
    	this.analyzer = analyzer;
    	this.init();
    }
    
    /**
     * 创建IndexWriter
     */
    private void init(){
		try {
			
			IndexWriterConfig iwc = new IndexWriterConfig(LuceneContext.LUCENE_VERSION, new IKAnalyzer(true));
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			iw= new IndexWriter(FSDirectory.open(new File(indexPath)),iwc);
			writer = new NRTManager.TrackingIndexWriter(iw);
			nrtMgr = new NRTManager(writer,new SearcherFactory(),true);
			reopen = new NRTManagerReopenThread(nrtMgr, 5.0,0.025);
            reopen.setDaemon(true);
            reopen.setName("NrtManager Reopen Thread");
            reopen.start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    public void destroy(){
    	reopen.close();
    	try {
    		if(indexSearcher!=null){
    			nrtMgr.release(indexSearcher);
    		}
    		iw.close();
			nrtMgr.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
	public TrackingIndexWriter getWriter() {
		return writer;
	}
	public void setWriter(TrackingIndexWriter writer) {
		this.writer = writer;
	}
}
