package com.szhbl.project.kh.mapper;

import com.szhbl.project.kh.domain.KhMenuPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户端---菜单权限Mapper接口
 * 
 * @author jhm
 * @date 2020-03-21
 */
@Repository
public interface KhMenuPermissionMapper 
{
    /**
     * 查询客户端---菜单权限
     * 
     * @param id 客户端---菜单权限ID
     * @return 客户端---菜单权限
     */
    public KhMenuPermission selectKhMenuPermissionById(Long id);

    /**
     * 查询客户端---菜单权限列表
     * 
     * @param khMenuPermission 客户端---菜单权限
     * @return 客户端---菜单权限集合
     */
    public List<KhMenuPermission> selectKhMenuPermissionList(KhMenuPermission khMenuPermission);

    /**
     * 新增客户端---菜单权限
     * 
     * @param khMenuPermission 客户端---菜单权限
     * @return 结果
     */
    public int insertKhMenuPermission(KhMenuPermission khMenuPermission);

    /**
     * 修改客户端---菜单权限
     * 
     * @param khMenuPermission 客户端---菜单权限
     * @return 结果
     */
    public int updateKhMenuPermission(KhMenuPermission khMenuPermission);

    /**
     * 删除客户端---菜单权限
     * 
     * @param id 客户端---菜单权限ID
     * @return 结果
     */
    public int deleteKhMenuPermissionById(Long id);

    /**
     * 批量删除客户端---菜单权限
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteKhMenuPermissionByIds(Long[] ids);
}
