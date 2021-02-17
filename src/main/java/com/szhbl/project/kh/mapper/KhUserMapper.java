package com.szhbl.project.kh.mapper;

import com.szhbl.project.kh.domain.KhUser;
import java.util.List;

/**
 * 客户端用户Mapper接口
 * 
 * @author jhm
 * @date 2020-03-21
 */
public interface KhUserMapper 
{
    /**
     * 查询客户端用户
     * 
     * @param id 客户端用户ID
     * @return 客户端用户
     */
    public KhUser selectKhUserById(Long id);

    KhUser selectKhUserByPhone(String phone);

    /**
     * 查询客户端用户列表
     * 
     * @param khUser 客户端用户
     * @return 客户端用户集合
     */
    public List<KhUser> selectKhUserList(KhUser khUser);

    /**
     * 新增客户端用户
     * 
     * @param khUser 客户端用户
     * @return 结果
     */
    public int insertKhUser(KhUser khUser);

    /**
     * 修改客户端用户
     * 
     * @param khUser 客户端用户
     * @return 结果
     */
    public int updateKhUser(KhUser khUser);

    /**
     * 删除客户端用户
     * 
     * @param id 客户端用户ID
     * @return 结果
     */
    public int deleteKhUserById(Long id);

    /**
     * 批量删除客户端用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteKhUserByIds(Long[] ids);

}
