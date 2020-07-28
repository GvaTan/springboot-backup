package cn.dm.service;

import cn.dm.common.Page;
import cn.dm.es.query.ItemQuery;
import cn.dm.item.ItemSearchVo;

public interface ItemSearchService {

    public Page<ItemSearchVo> queryItemList(ItemQuery itemQuery) throws Exception;

    public void importItemList() throws Exception;
}
