/**
 * 
 */
package top.anets.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

/**
 * es连接测试
 * @author Administrator
 *
 */
public class EsDemo {
   private TransportClient client = null;
   
   
   @SuppressWarnings("resource")
public void getConnection() throws Exception {
	   client = new PreBuiltTransportClient(Settings.EMPTY)
	   .addTransportAddress(new TransportAddress(InetAddress.getByName("140.143.122.115"),9200));
   }
   
   
   public void addIndex() {
//	   创建一个默认的索引
	   IndicesAdminClient indices = client.admin().indices();
//	   设置索引，设置type(创建表)
	   
	   String json="{\r\n" + 
	   		"	   \"properties\":{\r\n" + 
	   		"	       \"name\":{\r\n" + 
	   		"	           \"type\":\"text\",\r\n" + 
	   		"	           \"analyzer\":\"分词器\"\r\n" + 
	   		"           },\r\n" + 
	   		"	       \"字段\":{\r\n" + 
	   		"	           \"type\":\"字段类型\",\r\n" + 
	   		"	           \"analyzer\":\"分词器类型\"\r\n" + 
	   		"           }\r\n" + 
	   		"       }\r\n" + 
	   		"   }";
	   
	   indices.preparePutMapping("testIndex")
	          .setType("tableName")
	          .setSource(json, XContentType.JSON)
	          .get();
	   client.close();
   }
   
   public void addDocument() {
	   String json=""
	   		+ "{\r\n" + 
	   		"	   \"字段\":\"值\",\r\n" + 
	   		"	   \"字段\":\"值\"\r\n" + 
	   		"  }";
	   
	   
	   client.prepareIndex("库名", "type表名")
	         .setSource(json)
	         .get();
   }
   
   
   
//   public List search() {
//	   SearchRequestBuilder searchRequestBuilder = client.prepareSearch("库名1","库名2");
////	   匹配度查询
//	   searchRequestBuilder.setQuery(QueryBuilders.matchQuery("字段", "匹配值"));
//	   
//	   SearchResponse response = searchRequestBuilder.execute().actionGet();
//	   
//	   SearchHits hits = response.getHits();
//	   SearchHit[] hits2 = hits.getHits();
//	   List<User> result=null;
//	   
//	   if(hits2!=null&&hits2.length!=0) {
//		   result=new ArrayList<>();
//		   for(SearchHit hit : hits2) {
//			   System.out.println(hit.getSourceAsString());
//		   }
//	   }
//	   
////	   term精准查询
////	   searchRequestBuilder.setQuery(QueryBuilders.termQuery("字段", "精确值"));
//////	   range范围查询
////	   searchRequestBuilder.setQuery(QueryBuilders.rangeQuery("字段", "精确值"));
//////	   bool布尔查询
////	   searchRequestBuilder.setQuery(QueryBuilders.boolQuery().must(queryBuilder));
//   } 
   
   public static void main(String[] args) throws Exception {
	   EsDemo demo = new EsDemo();
	   demo.getConnection();
//	   demo.addIndex();
	   
}
}
