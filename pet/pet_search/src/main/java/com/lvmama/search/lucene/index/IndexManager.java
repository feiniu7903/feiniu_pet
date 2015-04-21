package com.lvmama.search.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lvmama.comm.search.SearchConstants.LUCENE_INDEX_TYPE;
import com.lvmama.search.lucene.LuceneContext;
import com.lvmama.search.lucene.analyser.AnalyzerUtil;
import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.PlaceHotelDocument;
import com.lvmama.search.lucene.document.ProductBranchDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.search.Searcher;
import com.lvmama.search.lucene.service.index.IndexService;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.ConfigHelper;
import com.lvmama.search.util.PageConfig;

public class IndexManager {

	protected Log log = LogFactory.getLog(this.getClass());

	private IndexService placeIndexService;
	private IndexService placeHotelIndexService;
	private IndexService productIndexService;
	private IndexService productBranchIndexService;
	private IndexService verHotelIndexService;
	private IndexService verPlaceIndexService;
	private Analyzer analyzer= new IKAnalyzer(false);
	private int errornum=1;
	/**
	 * 创建索引
	 * 
	 * 根据传入的TYPE创建不同的索引类型
	 * 
	 * 为了保证创建之后不需要重启服务,需要通过临时目录过渡 
	 * 1.查询对应的数据库数据,创建新索引至 [原索引路径_tmp]文件下
	 * 2.切换IndexReader，IndexWriter至临时索引 
	 * 3.删除原索引路径下的文件，拷贝临时索引至原索引目录下
	 * 4.切换IndexReader，IndexWriter至新索引
	 * 
	 * @param type
	 */
	public void createIndex(LUCENE_INDEX_TYPE type) {
		long beginDate = System.currentTimeMillis();
		IndexService indexService = null;
		String indexPath = null;
		String indexPath_tmp = null;
		LUCENE_INDEX_TYPE indextype=null;
		if (LUCENE_INDEX_TYPE.PLACE.equals(type)) {
			indexService = placeIndexService;
			indextype=LUCENE_INDEX_TYPE.PLACE;
			indexPath = ConfigHelper.getString("INDEX_PATH");
			indexPath_tmp = indexPath + "_tmp";
			log.info("创建景区索引开始...");
		} else if (LUCENE_INDEX_TYPE.PLACE_HOTEL.equals(type)) {
			indexService = placeHotelIndexService;
			indexPath = ConfigHelper.getString("INDEX_PLACE_HOTEL_PATH");
			indexPath_tmp = indexPath + "_tmp";
			log.info("创建酒店索引开始...");
		} else if (LUCENE_INDEX_TYPE.PRODUCT.equals(type)) {
			indexService = productIndexService;
			indextype=LUCENE_INDEX_TYPE.PLACE_HOTEL;
			indexPath = ConfigHelper.getString("INDEX_PRODUCT_PATH");
			indextype=LUCENE_INDEX_TYPE.PRODUCT;
			indexPath_tmp = indexPath + "_tmp";
			log.info("创建产品索引开始...");
		} else if (LUCENE_INDEX_TYPE.PRODUCT_BRANCH.equals(type)) {
			indexService = productBranchIndexService;
			indexPath = ConfigHelper.getString("INDEX_PRODUCT_BRANCH_PATH");
			indextype=LUCENE_INDEX_TYPE.PRODUCT_BRANCH;
			indexPath_tmp = indexPath + "_tmp";
			log.info("创建产品下的产品类别索引开始...");
		} else if (LUCENE_INDEX_TYPE.VER_HOTEL.equals(type)) {
			indexService = verHotelIndexService;
			indexPath = ConfigHelper.getString("INDEX_VER_HOTEL_PATH");
			indextype=LUCENE_INDEX_TYPE.VER_HOTEL;
			indexPath_tmp = indexPath + "_tmp";
			log.info("创建ver酒店索引开始...");
		}else if (LUCENE_INDEX_TYPE.VER_PLACE.equals(type)) {
			indexService = verPlaceIndexService;
			indexPath = ConfigHelper.getString("INDEX_VER_PLACE_PATH");
			indextype=LUCENE_INDEX_TYPE.VER_PLACE;
			indexPath_tmp = indexPath + "_tmp";
			log.info("创建ver地点索引开始...");
		} else if (LUCENE_INDEX_TYPE.ALL.equals(type)) {
			log.info("创建全部索引开始.....");
			indextype=LUCENE_INDEX_TYPE.PLACE;
			createIndex(indextype);
			indextype=LUCENE_INDEX_TYPE.PLACE_HOTEL;
			createIndex(indextype);
			indextype=LUCENE_INDEX_TYPE.PRODUCT;
			createIndex(indextype);
			indextype=LUCENE_INDEX_TYPE.PRODUCT_BRANCH;
			createIndex(indextype);
			indextype=LUCENE_INDEX_TYPE.VER_HOTEL;
			createIndex(indextype);
			indextype=LUCENE_INDEX_TYPE.VER_PLACE;
			createIndex(indextype);
			try {
				CommonUtil.sendMail("创建成功");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return;
		} else {
			throw new RuntimeException("INDEX CREATE TYPE IS ERROR!! TYPE:" + type);
		}
		int pageSize = 600;// 每页数量
		int currentPage = 1;// 当前页
		PageConfig<Document> pageConfig = PageConfig.page(pageSize, currentPage);
		IndexWriterConfig iwc = new IndexWriterConfig(LuceneContext.LUCENE_VERSION, analyzer);
		IndexWriter writer=null;
		Exception ee=null;
		try {
			
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			// 删除TMP目录
			FileUtils.deleteDirectory(new File(indexPath_tmp));
			writer= new IndexWriter(FSDirectory.open(new File(indexPath_tmp)), iwc);
			while (true) {
				pageConfig = indexService.getDocumentPageConfig(pageConfig);
				for (Document doc : pageConfig.getItems()) {
					writer.addDocument(doc);
				}
				log.info("##当前页：" + pageConfig.getCurrentPage());
				log.info("##当前记录数：" + (pageConfig.getCurrentPage() * pageConfig.getPageSize()));
				if (pageConfig.getCurrentPage() == pageConfig.getTotalPageNum()) {
					break;
				}
				pageConfig.setCurrentPage(pageConfig.getCurrentPage() + 1);
			}
//			Document doc= new Document();
//			doc.add(new TextField("test4","东方绿舟",Field.Store.YES));
////			doc.add(new StringField("test2","上海一夜游",Field.Store.YES));
//			writer.addDocument(doc);
			if (writer != null) {
				writer.forceMerge(10);
				writer.commit();
				writer.close();
				writer = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				CommonUtil.sendMail("第"+errornum+"次创建"+indextype+"失败");
				Runtime rt = Runtime.getRuntime();
				rt.exec("rm -rf "+indexPath_tmp);
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
			errornum++;
			if(errornum>2){
				throw new RuntimeException(ee);
			}
			createIndex(indextype);

		}
		
		long endDate = System.currentTimeMillis();
		log.info("创建" + pageConfig.getTotalResultSize() + "条记录,消耗时间(ms)：" + ((endDate - beginDate)));
		if (LUCENE_INDEX_TYPE.PLACE.equals(type)) {
			log.info("创建景区索引完成.");
		} else if (LUCENE_INDEX_TYPE.PRODUCT.equals(type)) {
			log.info("创建产品索引完成.");
		} else if (LUCENE_INDEX_TYPE.PRODUCT_BRANCH.equals(type)) {
			log.info("创建产品下的产品类别索引完成.");
		} else if (LUCENE_INDEX_TYPE.ALL.equals(type)) {
			log.info("创建全部索引完成.");
		} else if (LUCENE_INDEX_TYPE.PLACE_HOTEL.equals(type)) {
			log.info("创建酒店索引完成.");
		}
	}
	
	public void applyNewIndex(LUCENE_INDEX_TYPE type){
		String indexPath = null;
		String indexPath_tmp = null;
		IndexWriterConfig iwc = new IndexWriterConfig(LuceneContext.LUCENE_VERSION, analyzer);
		if (LUCENE_INDEX_TYPE.PLACE.equals(type)) {
			indexPath = ConfigHelper.getString("INDEX_PATH");
			indexPath_tmp = indexPath + "_tmp";
		} else if (LUCENE_INDEX_TYPE.PLACE_HOTEL.equals(type)) {
			indexPath = ConfigHelper.getString("INDEX_PLACE_HOTEL_PATH");
			indexPath_tmp = indexPath + "_tmp";
		} else if (LUCENE_INDEX_TYPE.PRODUCT.equals(type)) {
			indexPath = ConfigHelper.getString("INDEX_PRODUCT_PATH");
			indexPath_tmp = indexPath + "_tmp";
		} else if (LUCENE_INDEX_TYPE.PRODUCT_BRANCH.equals(type)) {
			indexPath = ConfigHelper.getString("INDEX_PRODUCT_BRANCH_PATH");
			indexPath_tmp = indexPath + "_tmp";
		} else if (LUCENE_INDEX_TYPE.VER_HOTEL.equals(type)) {
			indexPath = ConfigHelper.getString("INDEX_VER_HOTEL_PATH");
			indexPath_tmp = indexPath + "_tmp";
		} else if (LUCENE_INDEX_TYPE.VER_PLACE.equals(type)) {
			indexPath = ConfigHelper.getString("INDEX_VER_PLACE_PATH");
			indexPath_tmp = indexPath + "_tmp";
		} else if (LUCENE_INDEX_TYPE.ALL.equals(type)) {
			applyNewIndex(LUCENE_INDEX_TYPE.PLACE);
			applyNewIndex(LUCENE_INDEX_TYPE.PLACE_HOTEL);
			applyNewIndex(LUCENE_INDEX_TYPE.PRODUCT);
			applyNewIndex(LUCENE_INDEX_TYPE.PRODUCT_BRANCH);
			applyNewIndex(LUCENE_INDEX_TYPE.VER_HOTEL);
			applyNewIndex(LUCENE_INDEX_TYPE.VER_PLACE);
			return;
		} else {
			throw new RuntimeException("INDEX CREATE TYPE IS ERROR!! TYPE:" + type);
		}
		try {
			File f_tmp = new File(indexPath_tmp);
			if(f_tmp.exists()){
				File lock_file2 = new File(indexPath_tmp+"/write.lock");
				if(lock_file2.exists()){
					lock_file2.delete();
				}
				//替换搜索上下文为TMP目录
				LuceneContext.replaceSearcher(type,new Searcher(indexPath_tmp, AnalyzerUtil.getAnalyzer(type)));
				// 删除原索引
				FileUtils.deleteDirectory(new File(indexPath));
				
				// 复制TMP目录下的索引文件至原索引目录
				FileUtils.copyDirectory(new File(indexPath_tmp), new File(indexPath));
				File lock_file = new File(indexPath+"/write.lock");
				if(lock_file.exists()){
					lock_file.delete();
				}
				//替换搜索上下文为索引目录
				LuceneContext.replaceSearcher(type,new Searcher(indexPath, AnalyzerUtil.getAnalyzer(type)));
				// 删除TMP目录
				FileUtils.deleteDirectory(new File(indexPath_tmp));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 更新索引
	 * 
	 * 1.通过主键删除索引中的索引数据
	 * 2.查询数据库中的数据添加至索引中
	 * 3.commit变动
	 * 4.重新打开IndexReader
	 * 
	 * @param type
	 *            索引类型
	 * @param id
	 *            更新的数据主键
	 */
	public void updateIndex(LUCENE_INDEX_TYPE type, String... ids) {
		long beginDate = System.currentTimeMillis();
		TrackingIndexWriter writer = LuceneContext.getIndexWriter(type);
		IndexService indexService = null;
		try {
			if (LUCENE_INDEX_TYPE.PLACE.equals(type)) {
				StringBuffer sb = new StringBuffer("更新景区索引,PLACE_ID:");
				indexService = placeIndexService;
				BooleanQuery bq = new BooleanQuery();
				for(String s:ids){
					sb.append(s).append("|");
					bq.add(new TermQuery(new Term(PlaceDocument.ID, s)), BooleanClause.Occur.SHOULD);
				}
				log.info(sb.toString());
				writer.deleteDocuments(bq);
			} else if (LUCENE_INDEX_TYPE.PLACE_HOTEL.equals(type)) {
				StringBuffer sb = new StringBuffer("更新酒店索引,PLACE_ID:");
				indexService = placeHotelIndexService;
				BooleanQuery bq = new BooleanQuery();
				for(String s:ids){
					sb.append(s).append("|");
					bq.add(new TermQuery(new Term(PlaceHotelDocument.ID, s)), BooleanClause.Occur.SHOULD);
				}
				log.info(sb.toString());
				writer.deleteDocuments(bq);
			} else if (LUCENE_INDEX_TYPE.PRODUCT.equals(type)) {
				StringBuffer sb = new StringBuffer("更新产品索引开始,PRODUCT_ID:");
				indexService = productIndexService;
				BooleanQuery bq = new BooleanQuery();
				for(String s:ids){
					sb.append(s).append("|");
					bq.add(new TermQuery(new Term(ProductDocument.PRODUCT_ID, s)), BooleanClause.Occur.SHOULD);
				}
				log.info(sb.toString());
				writer.deleteDocuments(bq);
			} else if (LUCENE_INDEX_TYPE.PRODUCT_BRANCH.equals(type)) {
				StringBuffer sb = new StringBuffer("更新产品下的产品类别索引开始,PROD_BRANCH_ID:");
				indexService = productBranchIndexService;
				BooleanQuery bq = new BooleanQuery();
				for(String s:ids){
					sb.append(s).append("|");
					bq.add(new TermQuery(new Term(ProductBranchDocument.PROD_BRANCH_ID, s)), BooleanClause.Occur.SHOULD);
				}
				log.info(sb.toString());
				writer.deleteDocuments(bq);
			} else {
				throw new RuntimeException("INDEX CREATE TYPE IS ERROR!! TYPE:" + type);
			}
			
			List<Document> docList = indexService.getDocument(ids);
			for(Document doc : docList){
				writer.addDocument(doc);
			}
			IndexWriter w = writer.getIndexWriter();
			w.forceMergeDeletes();
			w.commit();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		long endDate = System.currentTimeMillis();
		log.info("更新完成,消耗时间(ms)：" + ((endDate - beginDate)));
	}

	public void setPlaceIndexService(IndexService placeIndexService) {
		this.placeIndexService = placeIndexService;
	}

	
	public void setPlaceHotelIndexService(IndexService placeHotelIndexService) {
		this.placeHotelIndexService = placeHotelIndexService;
	}

	public void setProductIndexService(IndexService productIndexService) {
		this.productIndexService = productIndexService;
	}

	public void setProductBranchIndexService(IndexService productBranchIndexService) {
		this.productBranchIndexService = productBranchIndexService;
	}

	public void setVerHotelIndexService(IndexService verHotelIndexService) {
		this.verHotelIndexService = verHotelIndexService;
	}

	public void setVerPlaceIndexService(IndexService verPlaceIndexService) {
		this.verPlaceIndexService = verPlaceIndexService;
	}


	
	

}
