package com.szhbl.project.documentcenter.mapper;


import com.szhbl.project.documentcenter.domain.DocOrderSort;

public interface DocOrderSortMapper
{
    public int getSortByOrderidDocType(String orderidDocType);

    public int insertDocOrderSort(DocOrderSort DocOrderSort);
}
