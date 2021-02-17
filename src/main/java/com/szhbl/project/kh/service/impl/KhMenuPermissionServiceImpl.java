package com.szhbl.project.kh.service.impl;

import java.util.List;

import com.szhbl.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.kh.mapper.KhMenuPermissionMapper;
import com.szhbl.project.kh.domain.KhMenuPermission;
import com.szhbl.project.kh.service.IKhMenuPermissionService;

/**
 * 客户端---菜单权限Service业务层处理
 * 
 * @author jhm
 * @date 2020-03-21
 */
@Service
public class KhMenuPermissionServiceImpl implements IKhMenuPermissionService 
{
    @Autowired
    private KhMenuPermissionMapper khMenuPermissionMapper;

    /**
     * 查询客户端---菜单权限
     * 
     * @param id 客户端---菜单权限ID
     * @return 客户端---菜单权限
     */
    @Override
    public KhMenuPermission selectKhMenuPermissionById(Long id)
    {
        return khMenuPermissionMapper.selectKhMenuPermissionById(id);
    }

    /**
     * 查询客户端---菜单权限列表
     * 
     * @param khMenuPermission 客户端---菜单权限
     * @return 客户端---菜单权限
     */
    @Override
    public List<KhMenuPermission> selectKhMenuPermissionList(KhMenuPermission khMenuPermission)
    {
        List<KhMenuPermission> khMenuPermissionList = khMenuPermissionMapper.selectKhMenuPermissionList(khMenuPermission);
        for (KhMenuPermission khMenuPermission1:khMenuPermissionList) {
            if(StringUtils.isNotEmpty(khMenuPermission1.getMenuId())){
                khMenuPermission1.setMenuIds(khMenuPermission1.getMenuId().split(","));
            }
        }
        return khMenuPermissionList;
    }

    /**
     * 新增客户端---菜单权限
     * 
     * @param khMenuPermission 客户端---菜单权限
     * @return 结果
     */
    @Override
    public int insertKhMenuPermission(KhMenuPermission khMenuPermission)
    {
        return khMenuPermissionMapper.insertKhMenuPermission(khMenuPermission);
    }

    /**
     * 修改客户端---菜单权限
     * 
     * @param khMenuPermission 客户端---菜单权限
     * @return 结果
     */
    @Override
    public int updateKhMenuPermission(KhMenuPermission khMenuPermission)
    {
        return khMenuPermissionMapper.updateKhMenuPermission(khMenuPermission);
    }

    /**
     * 批量删除客户端---菜单权限
     * 
     * @param ids 需要删除的客户端---菜单权限ID
     * @return 结果
     */
    @Override
    public int deleteKhMenuPermissionByIds(Long[] ids)
    {
        return khMenuPermissionMapper.deleteKhMenuPermissionByIds(ids);
    }

    /**
     * 删除客户端---菜单权限信息
     * 
     * @param id 客户端---菜单权限ID
     * @return 结果
     */
    @Override
    public int deleteKhMenuPermissionById(Long id)
    {
        return khMenuPermissionMapper.deleteKhMenuPermissionById(id);
    }
}
