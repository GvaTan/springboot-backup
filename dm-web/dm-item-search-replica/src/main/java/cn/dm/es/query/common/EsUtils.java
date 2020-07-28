package cn.dm.es.query.common;

import cn.dm.common.Constants;
import cn.dm.common.EmptyUtils;
import cn.dm.common.Page;
import cn.dm.es.query.AbstractEsQuery;
import cn.dm.item.ItemSearchVo;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EsUtils {

    private static TransportClient client = null;
    Logger logger = Logger.getLogger(EsUtils.class);
    @Autowired
    private EsConnection esConnection;

    public boolean addBatchEsModule(List<ItemSearchVo> esModules) throws Exception {
        boolean flag = false;

        getConnection();
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        for (ItemSearchVo esModule : esModules) {
            String json = JSONObject.toJSONString(esModule);
            // PUT index/type/id
            // {
            // }
            bulkRequestBuilder.add(
                    client.prepareIndex(esModule.getIndexName(),
                                        esModule.getTypeName(), esModule.getEsId())
                            .setSource(json, XContentType.JSON)
                            );
        }
        BulkResponse response = bulkRequestBuilder.execute().actionGet();
        if (response.status().getStatus()==200)
            flag = true;

        return flag;
    }

    public void getConnection() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", esConnection.getClusterName())
                .build();
        if (client == null)
            client = new PreBuiltTransportClient(settings)
                .addTransportAddress(
                        new TransportAddress(
                                InetAddress.getByName(esConnection.getIp()),
                                esConnection.getPort()));
    }

    public Page queryPage(AbstractEsQuery itemEsQuery) throws Exception {
        Page page = null;

        List<MatchQueryBuilder> matchQueryBuilders = new ArrayList<>();
        List<TermQueryBuilder> termQueryBuilders = new ArrayList<>();
        List<RangeQueryBuilder> rangeQueryBuilders = new ArrayList<>();

        if (EmptyUtils.isEmpty(itemEsQuery)) {
            logger.info("================条件未正确设置=================");
            return page;
        }

        getConnection();
        SearchRequestBuilder searchRequestBuilder
                = client.prepareSearch(
                        itemEsQuery.getIndexName());

        
        
        searchRequestBuilder.setTypes(itemEsQuery.getTypeName());
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
        searchRequestBuilder.setExplain(true);

        
        
        if (EmptyUtils.isNotEmpty(itemEsQuery.getAsc())) {
            searchRequestBuilder.addSort(itemEsQuery.getAsc(), SortOrder.ASC);
        }if (EmptyUtils.isNotEmpty(itemEsQuery.getDesc())) {
            searchRequestBuilder.addSort(itemEsQuery.getDesc(), SortOrder.DESC);
        }if (EmptyUtils.isEmpty(itemEsQuery.getPageSize())) {
            itemEsQuery.setPageSize(Constants.DEFAULT_PAGE_SIZE);
        }if (EmptyUtils.isEmpty(itemEsQuery.getPageNo())) {
            itemEsQuery.setPageNo(Constants.DEFAULT_PAGE_NO);
        }
        Integer beginPos = (itemEsQuery.getPageNo() - 1)
                * itemEsQuery.getPageSize();
        searchRequestBuilder.setFrom(beginPos);
        searchRequestBuilder.setSize(itemEsQuery.getPageSize());

        
        // term
        if (EmptyUtils.isNotEmpty(itemEsQuery.getMatchParams())) {
            for (Map.Entry<String, Object> entry : itemEsQuery.getMatchParams().entrySet()) {
                termQueryBuilders.add(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
            }
        }
        // match
        if (EmptyUtils.isNotEmpty(itemEsQuery.getLikeMatchParams())) {
            for (Map.Entry<String, Object> entry : itemEsQuery.getLikeMatchParams().entrySet()) {
                matchQueryBuilders.add(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()));
            }
        }
        // range <=
        if (EmptyUtils.isNotEmpty(itemEsQuery.getLteParams())) {
            for (Map.Entry<String, Object> entry : itemEsQuery.getLteParams().entrySet()) {
                rangeQueryBuilders.add(QueryBuilders.rangeQuery(entry.getKey()).lte(entry.getValue()));
            }
        }
        // range >=
        if (EmptyUtils.isNotEmpty(itemEsQuery.getGteParams())) {
            for (Map.Entry<String, Object> entry : itemEsQuery.getGteParams().entrySet()) {
                rangeQueryBuilders.add(QueryBuilders.rangeQuery(entry.getKey()).gte(entry.getValue()));
            }
        }

        // bool must must_not should filter
        BoolQueryBuilder boolQueryBuilder = null;

        // 匹配度查询
        for (MatchQueryBuilder matchQueryBuilder : matchQueryBuilders) {
            if (EmptyUtils.isEmpty(boolQueryBuilder))
                boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(matchQueryBuilder);
        }
        
        
        // term 
        for (TermQueryBuilder termQueryBuilder : termQueryBuilders) {
            if (EmptyUtils.isEmpty(boolQueryBuilder))
                boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(termQueryBuilder);
        }
        // range
        for (RangeQueryBuilder rangeQueryBuilder : rangeQueryBuilders) {
            if (EmptyUtils.isEmpty(boolQueryBuilder))
                boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(rangeQueryBuilder);
        }

        
        
        
        searchRequestBuilder.setQuery(boolQueryBuilder);
        logger.info("es search: " + searchRequestBuilder);
        
        
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        SearchHits searchHits = response.getHits();
        logger.info("es search: " + searchHits.getTotalHits());
        page = new Page(itemEsQuery.getPageNo(), itemEsQuery.getPageSize(),
                        new Long(searchHits.getTotalHits()).intValue());

        SearchHit[] hits = searchHits.getHits();
        List result = null;

        if (EmptyUtils.isNotEmpty(hits)) {
            result = new ArrayList();
            for (SearchHit hit : hits) {
                String json = hit.getSourceAsString();
                System.out.println(json);
                ItemSearchVo t = (ItemSearchVo) JSONObject.parseObject(json,
                        itemEsQuery.getModuleClass());
                result.add(t);
            }
            page.setRows(result);
        }

        return page;
    }
}
