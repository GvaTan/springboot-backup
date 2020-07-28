package cn.dm.service.impl;

import cn.dm.client.RestDmCinemaClient;
import cn.dm.client.RestDmImageClient;
import cn.dm.client.RestDmItemClient;
import cn.dm.common.*;
import cn.dm.es.query.AbstractEsQuery;
import cn.dm.es.query.ItemQuery;
import cn.dm.es.query.common.EsUtils;
import cn.dm.item.ItemEsQuery;
import cn.dm.item.ItemSearchVo;
import cn.dm.pojo.DmCinema;
import cn.dm.pojo.DmImage;
import cn.dm.pojo.DmItem;
import cn.dm.service.ItemSearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Component
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private EsUtils esUtils;
    @Autowired
    private RestDmItemClient restDmItemClient;
    @Autowired
    private RestDmImageClient restDmImageClient;
    @Autowired
    private RestDmCinemaClient restDmCinemaClient;
    private org.apache.log4j.Logger logger =
            org.apache.log4j.Logger.getLogger(ItemSearchServiceImpl.class);
    @Override
    @Scheduled(cron = "0 0/1 * * * ?")
    public void importItemList() throws Exception {
        System.out.println("update es @ " + Calendar.getInstance());
        Map<String, Object> params = new HashMap<>();
        String lastUpdateTime = getLastUpdateTime();
        if (EmptyUtils.isNotEmpty(lastUpdateTime)) {
            params.put("lastUpdatedTime", lastUpdateTime);
        }
        List<DmItem> dmItemList = restDmItemClient.getDmItemListByMap(params);
        List<ItemSearchVo> itemSearchVoList = new ArrayList<>();

        if (EmptyUtils.isNotEmpty(dmItemList)) {
            for (DmItem dmItem : dmItemList) {
                ItemSearchVo itemSearchVo = new ItemSearchVo();
                BeanUtils.copyProperties(dmItem, itemSearchVo);

                List<DmImage> dmImages = restDmImageClient.queryDmImageList(dmItem.getId(),
                        Constants.Image.ImageType.normal, Constants.Image.ImageCategory.item);
                itemSearchVo.setImgUrl(EmptyUtils.isEmpty(dmImages)?null:dmImages.get(0).getImgUrl());

                itemSearchVo.setItemTypeId1(dmItem.getItemType1Id());
                itemSearchVo.setItemTypeId2(dmItem.getItemType2Id());
                itemSearchVo.setStartTime(DateUtil.format(dmItem.getStartTime()));
                itemSearchVo.setEndTime(DateUtil.format(dmItem.getEndTime()));

                DmCinema dmCinema = restDmCinemaClient.getDmCinemaById(dmItem.getCinemaId());
                itemSearchVo.setAreaId(dmCinema.getAreaId());
                itemSearchVo.setAddress(dmCinema.getAddress());
                itemSearchVo.setAreaName(dmCinema.getAreaName());

                itemSearchVo.setCreatedTimeLong(dmItem.getCreatedTime().getTime());
                itemSearchVo.setCreatedTime(DateUtil.format(dmItem.getCreatedTime()));
                itemSearchVo.setStartTimeLong(dmItem.getStartTime().getTime());
                itemSearchVo.setEndTimeLong(dmItem.getEndTime().getTime());
                itemSearchVo.setCommentCount(dmItem.getCommentCount());

                itemSearchVoList.add(itemSearchVo);
            }
        }
        logger.info("+++++++++++++++++"+DateUtil.format(new Date())+"更新了"
                +itemSearchVoList.size()+"数据+++++++++++++++++++++");
        lastUpdateTime = DateUtil.format(new Date());
        FileUtils.writeInFile(lastUpdateTimeFile, lastUpdateTime);

        if (EmptyUtils.isNotEmpty(itemSearchVoList)) {
            esUtils.addBatchEsModule(itemSearchVoList);
        }
    }

    
    
    @Value("${lastUpdateTimeFile}")
    private String lastUpdateTimeFile;

    public String getLastUpdateTime() throws IOException {
        FileUtils.createIfNotExist(lastUpdateTimeFile);
        return FileUtils.readFileByLine(lastUpdateTimeFile);
    }

    @Override
    public Page<ItemSearchVo> queryItemList(ItemQuery itemQuery) throws Exception {
        AbstractEsQuery itemEsQuery = new ItemEsQuery();
        if (EmptyUtils.isNotEmpty(itemQuery.getItemTypeId1()) && itemQuery.getItemTypeId1() != 0)
            itemEsQuery.setMatchParams("itemTypeId1", itemQuery.getItemTypeId1());
        if (EmptyUtils.isNotEmpty(itemQuery.getItemTypeId2()) && itemQuery.getItemTypeId2() != 0)
            itemEsQuery.setMatchParams("itemTypeId2", itemQuery.getItemTypeId2());
        if (EmptyUtils.isNotEmpty(itemQuery.getAreaId()) && itemQuery.getAreaId() != 0)
            itemEsQuery.setMatchParams("areaId", itemQuery.getAreaId());
        if (EmptyUtils.isNotEmpty(itemQuery.getKeyword()))
            itemEsQuery.setLikeMatchParams("itemName", itemQuery.getKeyword());
        if (EmptyUtils.isNotEmpty(itemQuery.getStartTime())) {
            Long startTimeLong = DateUtil.parse(itemQuery.getStartTime(), "yyyy-MM-dd").getTime();
            itemEsQuery.setGteParams("startTimeLong", startTimeLong);
        }
        if (EmptyUtils.isNotEmpty(itemQuery.getEndTime())) {
            Long endTimeLong = DateUtil.parse(itemQuery.getEndTime(), "yyyy-MM-dd").getTime();
            itemEsQuery.setLteParams("startTimeLong", endTimeLong);
        }
        if (EmptyUtils.isNotEmpty(itemQuery.getSort())) {
            if (itemQuery.getSort().equals("recommend")) {// 推荐度
                itemEsQuery.setDesc("commentCount");
            } else if (itemQuery.getSort().equals("recentShow")) { // 最近开始
                itemEsQuery.setDesc("createdTimeLong");
            } else if (itemQuery.getSort().equals("recentSell")) { // 最新上架
                itemEsQuery.setDesc("startTimeLong");
            }
        }
        itemEsQuery.setPageNo(itemQuery.getCurrentPage());
        itemEsQuery.setPageSize(itemQuery.getPageSize());
        return esUtils.queryPage(itemEsQuery);
    }
}
