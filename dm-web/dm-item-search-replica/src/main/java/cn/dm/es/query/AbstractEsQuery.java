package cn.dm.es.query;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEsQuery {
    private Integer pageNo;

    private Integer pageSize;

    private String keyword;

    private String asc;

    private String desc;

    
    //精确匹配, term
    private Map<String,Object> matchParams = new HashMap<String, Object>();

    //模糊匹配
    private Map<String,Object> likeMatchParams = new HashMap<String, Object>();
    // range
    private Map<String,Object> gteParams = new HashMap<String, Object>();

    private Map<String,Object> lteParams = new HashMap<String, Object>();

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAsc() {
        return asc;
    }

    public void setAsc(String asc) {
        this.asc = asc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, Object> getMatchParams() {
        return matchParams;
    }

    public void setMatchParams(String key, Object value) {
        matchParams.put(key, value);
    }

    public Map<String, Object> getLikeMatchParams() {
        return likeMatchParams;
    }

    public void setLikeMatchParams(String key, Object value) {
        likeMatchParams.put(key, value);
    }

    public Map<String, Object> getGteParams() {
        return gteParams;
    }

    public void setGteParams(String key, Object value) {
        gteParams.put(key, value);
    }

    public Map<String, Object> getLteParams() {
        return lteParams;
    }

    public void setLteParams(String key, Object value) {
        this.lteParams.put(key, value);
    }

    public abstract String getIndexName();

    public abstract String getTypeName();

    public abstract Class getModuleClass();
}
