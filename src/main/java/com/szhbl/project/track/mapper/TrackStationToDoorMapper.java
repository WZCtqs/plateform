package com.szhbl.project.track.mapper;


import com.szhbl.project.track.domain.TrackStationToDoor;

import java.util.List;

/**
 * 站到门/门到站表Mapper接口
 *
 * @author szhbl
 *
 */
public interface TrackStationToDoorMapper
{
    /**
     * 更新站到门/门到站表
     *
     * @param tstd 站到门/门到站实体类
     * @return
     */
    public int updateTstd(TrackStationToDoor tstd);

    /**
     * 新增站到门/门到站
     *
     * @param tstd 站到门/门到站实体类
     * @return
     */
    public int insertTstd(TrackStationToDoor tstd);

    /**
     * 查询站到门/门到站
     *
     * @param tstd 站到门/门到站实体类
     * @return
     */
    public List<TrackStationToDoor> selectByTstd(TrackStationToDoor tstd);

}
