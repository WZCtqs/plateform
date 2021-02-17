package com.szhbl.project.enquiry.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.szhbl.common.utils.DateUtils;
import com.szhbl.project.enquiry.dto.ZgRailDivisionDto;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.enquiry.mapper.ZgRailDivisionMapper;
import com.szhbl.project.enquiry.domain.ZgRailDivision;
import com.szhbl.project.enquiry.service.IZgRailDivisionService;

/**
 * 整柜铁路运费Service业务层处理
 * 
 * @author jhm
 * @date 2020-03-13
 */
@Service
public class ZgRailDivisionServiceImpl implements IZgRailDivisionService 
{
    @Autowired
    private ZgRailDivisionMapper zgRailDivisionMapper;

    /**
     * 查询整柜铁路运费
     * 
     * @param id 整柜铁路运费ID
     * @return 整柜铁路运费
     */
    @Override
    public ZgRailDivision selectZgRailDivisionById(Long id)
    {
        return zgRailDivisionMapper.selectZgRailDivisionById(id);
    }

    /**
     * 查询整柜铁路运费列表
     * 
     * @param zgRailDivision 整柜铁路运费
     * @return 整柜铁路运费
     */
    @Override
    public List<ZgRailDivision> selectZgRailDivisionList(ZgRailDivision zgRailDivision)
    {
        return zgRailDivisionMapper.selectZgRailDivisionList(zgRailDivision);
    }

    /**
     * 新增整柜铁路运费
     * 
     * @param zgRailDivision 整柜铁路运费
     * @return 结果
     */
    @Override
    public int insertZgRailDivision(ZgRailDivision zgRailDivision)
    {
        zgRailDivision.setCreateTime(DateUtils.getNowDate());
        return zgRailDivisionMapper.insertZgRailDivision(zgRailDivision);
    }

    /**
     * 修改整柜铁路运费
     * 
     * @param zgRailDivision 整柜铁路运费
     * @return 结果
     */
    @Override
    public int updateZgRailDivision(ZgRailDivision zgRailDivision)
    {
        zgRailDivision.setUpdateTime(DateUtils.getNowDate());
        return zgRailDivisionMapper.updateZgRailDivision(zgRailDivision);
    }

    /**
     * 批量删除整柜铁路运费
     * 
     * @param ids 需要删除的整柜铁路运费ID
     * @return 结果
     */
    @Override
    public int deleteZgRailDivisionByIds(Long[] ids)
    {
        return zgRailDivisionMapper.deleteZgRailDivisionByIds(ids);
    }

    /**
     * 删除整柜铁路运费信息
     * 
     * @param id 整柜铁路运费ID
     * @return 结果
     */
    @Override
    public int deleteZgRailDivisionById(Long id)
    {
        return zgRailDivisionMapper.deleteZgRailDivisionById(id);
    }

    @Override
    public int insertMoreZgRailDivision(List<ZgRailDivisionDto> zgRailDivisionDtoList) {

        return zgRailDivisionMapper.insertMoreZgRailDivision(zgRailDivisionDtoList);
    }

    @Override
    public List<ZgRailDivision> selectZgRailDivisionWithMap(Map<String, String> map) {
        return zgRailDivisionMapper.selectZgRailDivisionWithMap(map);
    }

    @Override
    public List<ZgRailDivision> selectZgRailDivisionListWithZG(ZgRailDivision zgRailDivision) {
        return zgRailDivisionMapper.selectZgRailDivisionListWithZG(zgRailDivision);
    }


    public List<ZgRailDivision> getExcelVaule(Workbook wb){
    List<ZgRailDivision> zgRailDivisionList = new ArrayList<>();
    int sheetsNumber = wb.getNumberOfSheets();

    Sheet sheet = wb.getSheetAt(0);
    int allRowNum = sheet.getLastRowNum();//获取所有行数
    int totalCells = 0;
    //得到Excel的列数(前提是有行数)
    if (allRowNum >= 1 && sheet.getRow(0) != null) {
        totalCells = sheet.getRow(6).getPhysicalNumberOfCells();
    }
    for (int i = 1; i <= allRowNum; i++) {
        ZgRailDivision zgRailDivision = new ZgRailDivision();
        for (int j = 0; j <= totalCells; j++) {
            Row row = sheet.getRow(i); //获取第i行
            Cell cell = row.getCell(j);//获取第i行，第j列的值
            switch (j) {
                case 0:
                    if (cell == null) {
                     throw new RuntimeException("请选择线路");
                    } else {
                        cell.setCellType(CellType.STRING);
                        zgRailDivision.setLineType(cell.getStringCellValue());
                    }
                case 1:
                    if (cell == null) {
                        throw new RuntimeException("请填写上货站");
                    } else {
                        zgRailDivision.setOrderUploadSite(cell.getStringCellValue());
                    }
                case 2:
                    if (cell == null) {
                        throw new RuntimeException("请填写下货站");
                    } else {
                        zgRailDivision.setOrderUnloadSite(cell.getStringCellValue());
                    }
                case 3:
                    if (cell == null) {
                        throw new RuntimeException("请填写箱子类型");
                    } else {
                        zgRailDivision.setContainerType(cell.getStringCellValue());
                    }
                case 4:
                    //箱型值
                    if (cell == null) {
                        throw new RuntimeException("请填写箱型值");
                    } else {
                        zgRailDivision.setContainerTypeValue(cell.getStringCellValue());
                    }
                case 5:
                    //是否自备箱
                    if (cell == null) {
                        throw new RuntimeException("请填写是否自备箱");
                    } else {
                        zgRailDivision.setIsContainer(cell.getStringCellValue());
                    }
                case 6:
                    //价格
                    if (cell == null) {
                        throw new RuntimeException("请填写价格");
                    } else {
                        zgRailDivision.setIsContainer(cell.getStringCellValue());
                    }
            }
        }

        zgRailDivisionList.add(zgRailDivision);
    }
    return zgRailDivisionList;

    }

    /**
     *判断文件类型是不是2003版本
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath){
        return filePath.matches("^.+\\.(?i)(xls)$");
    }
    /**
     *判断文件类型是不是2007版本
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath){
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
