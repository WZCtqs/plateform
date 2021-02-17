package com.szhbl.project.documentcenter.service.impl;

import com.szhbl.project.documentcenter.domain.DocOrderSort;
import com.szhbl.project.documentcenter.mapper.DocOrderSortMapper;
import com.szhbl.project.documentcenter.service.IDocOrderSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author HP
 */
@Service
public class DocOrderSortServiceImpl implements IDocOrderSortService
{
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private DocOrderSortMapper docOrderSortMapper;


    @Override
    public int getSortByOrderidDocType(String orderId, String fileTypeKey) {
        String key = "sort:" +orderId+":"+fileTypeKey;
        Integer sort = (Integer) redisTemplate.opsForValue().get(key);
        if(sort == null){
            sort = docOrderSortMapper.getSortByOrderidDocType(key);
            redisTemplate.opsForValue().set(key, sort, 20, TimeUnit.DAYS);
        }
        return sort;
    }

    @Override
    public int insertDocOrderSort(DocOrderSort docOrderSort) {
        String key = "sort:" + docOrderSort.getOrderId() + ":" + docOrderSort.getFileTypeKey();
        docOrderSort.setOrderidDoctype(key);
        docOrderSortMapper.insertDocOrderSort(docOrderSort);
        Integer sort = docOrderSortMapper.getSortByOrderidDocType(key);
        redisTemplate.opsForValue().set(key, sort, 20, TimeUnit.DAYS);
        return sort;
    }
}
