package com.study.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

/**
 * Lucene全文检索工具
 * 
 * 依赖lucene3.1 IKAnalyzer3.2.8
 * 相关库：lucene-core-3.1.0.jar;lucene-test-framework-3.1.0.jar; lucene-analyzers-3.1.0.jar;lucene-highlighter-3.1.0.jar; lucene-memory-3.1.0.jar;IKAnalyzer3.2.8.jar
 * 需在src目录中放置IKAnalyzer.cfg.xml; ext_stopword.dic
 */
public class LuceneUtility {

	/**
	 * 索引文件的存储目录（绝对路径）
	 */
	private String indexDir;
	
	/**
	 * 初始化全文搜索工具
	 * @param indexDir 引文件的存储目录
	 */
	public LuceneUtility(String indexDir) {
		
		this.indexDir = indexDir;
	}
	
	/**
	 * 建立索引
	 * @param id 编号
	 * @param name 名称
	 * @param text 内容
	 * @throws IOException
	 */
	public void createIndex(String id, String name, String text) throws IOException {
		
		IndexWriter indexWriter = null;
		Directory dir = new SimpleFSDirectory(new File(indexDir));  
		
		indexWriter = new IndexWriter(dir,new IKAnalyzer(),!dir.fileExists("segments.gen"),IndexWriter.MaxFieldLength.UNLIMITED);
		indexWriter.deleteDocuments(new Term("id",id));
		
		Document doc = new Document();
        doc.add(new Field("id", id, Field.Store.YES, Field.Index.NOT_ANALYZED));  		
        doc.add(new Field("name", name, Field.Store.YES, Field.Index.NOT_ANALYZED));		
		doc.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED));
		
		indexWriter.addDocument(doc);
		indexWriter.optimize();
		
		indexWriter.close(); 
	}
	
	/**
	 * 删除索引
	 * @param id 编号
	 * @throws IOException
	 */
	public void removeIndex(String id) throws IOException {
		
		IndexWriter indexWriter = null;
		Directory dir = new SimpleFSDirectory(new File(indexDir));  
		
		indexWriter = new IndexWriter(dir,new IKAnalyzer(),!dir.fileExists("segments.gen"),IndexWriter.MaxFieldLength.UNLIMITED);
		indexWriter.deleteDocuments(new Term("id",id));

		indexWriter.optimize();		
		indexWriter.close(); 
	}	
	
	/**
	 * 搜索关键词
	 * @param key 关键词
	 * @param maxNum 搜索结果的最多条数
	 * @return 搜索结果（编号：名称）
	 * @throws IOException
	 */
	public List<LuceneSearchResult> search(String key, Integer maxNum) throws IOException {
	
		List<LuceneSearchResult> rt = new ArrayList<LuceneSearchResult>();
		
		Directory dir = new SimpleFSDirectory(new File(indexDir));
		
		IndexSearcher indexSearch = new IndexSearcher(dir); 
		indexSearch.setSimilarity(new IKSimilarity());
		
		Query query = IKQueryParser.parse("text", key); 
		TopDocs hits = indexSearch.search(query, maxNum); 
		
        for (int i = 0; i < hits.scoreDocs.length; i++) {
        	
            ScoreDoc sdoc = hits.scoreDocs[i];   
            Document doc = indexSearch.doc(sdoc.doc);
            rt.add(new LuceneSearchResult(doc.get("id"), doc.get("name")));
        }    
        
        indexSearch.close();
        
		return rt;
	}
	
	
	public static void main(String[] args) throws Exception {
		
		LuceneUtility luceneUtility = new LuceneUtility("D:\\SystemDataPath\\FullIndex");
		
		//luceneUtility.createIndex("id1", "name1", "text1text1text1text1text1");
		//luceneUtility.createIndex("id2", "name3", "text3text3text3text3text3");
		
		List<LuceneSearchResult> list = luceneUtility.search("逻辑", 100000);
		for (LuceneSearchResult item : list) {
			System.out.println("id=" + item.getId() + "&name=" + item.getName());
		}
		
		//luceneUtility.removeIndex("adaf");
	}
}
