package com.szhbl.project.documentcenter.service;

import com.szhbl.project.documentcenter.domain.DocOrderSort;

public interface IDocOrderSortService
{
    public int getSortByOrderidDocType(String orderId, String fileTypeKey);

    public int insertDocOrderSort(DocOrderSort DocOrderSort);
}
