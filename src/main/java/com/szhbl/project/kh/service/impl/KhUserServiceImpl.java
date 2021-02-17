package com.szhbl.project.kh.service.impl;

import java.util.List;
import com.szhbl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.szhbl.project.kh.mapper.KhUserMapper;
import com.szhbl.project.kh.domain.KhUser;
import com.szhbl.project.kh.service.IKhUserService;

/**
 * 客户端用户Service业务层处理
 * 
 * @author jhm
 * @date 2020-03-21
 */
@Service
public class KhUserServiceImpl implements IKhUserService 
{
    @Autowired
    private KhUserMapper khUserMapper;

    /**
     * 查询客户端用户
     * 
     * @param id 客户端用户ID
     * @return 客户端用户
     */
    @Override
    public KhUser selectKhUserById(Long id)
    {
        return khUserMapper.selectKhUserById(id);
    }

    /**
     * 查询客户端用户
     *
     * @param phone 客户端用户手机号
     * @return 客户端用户
     */
    @Override
    public KhUser selectKhUserByPhone(String phone) {
        return khUserMapper.selectKhUserByPhone(phone);
    }

    /**
     * 查询客户端用户列表
     * 
     * @param khUser 客户端用户
     * @return 客户端用户
     */
    @Override
    public List<KhUser> selectKhUserList(KhUser khUser)
    {
        return khUserMapper.selectKhUserList(khUser);
    }

    /**
     * 新增客户端用户
     * 
     * @param khUser 客户端用户
     * @return 结果
     */
    @Override
    public int insertKhUser(KhUser khUser)
    {
        khUser.setCreateTime(DateUtils.getNowDate());
        return khUserMapper.insertKhUser(khUser);
    }

    /**
     * 修改客户端用户
     * 
     * @param khUser 客户端用户
     * @return 结果
     */
    @Override
    public int updateKhUser(KhUser khUser)
    {
        khUser.setUpdateTime(DateUtils.getNowDate());
        return khUserMapper.updateKhUser(khUser);
    }

    /**
     * 批量删除客户端用户
     * 
     * @param ids 需要删除的客户端用户ID
     * @return 结果
     */
    @Override
    public int deleteKhUserByIds(Long[] ids)
    {
        return khUserMapper.deleteKhUserByIds(ids);
    }

    /**
     * 删除客户端用户信息
     * 
     * @param id 客户端用户ID
     * @return 结果
     */
    @Override
    public int deleteKhUserById(Long id)
    {
        return khUserMapper.deleteKhUserById(id);
    }
}
