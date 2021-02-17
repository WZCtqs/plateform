package com.szhbl.project.system.service.impl;

import com.szhbl.common.constant.UserConstants;
import com.szhbl.common.core.lang.UUID;
import com.szhbl.common.exception.CustomException;
import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.aspectj.lang.annotation.DataScope;
import com.szhbl.project.client.domain.BusiClients;
import com.szhbl.project.client.domain.SystemDict;
import com.szhbl.project.client.mapper.BusiClientsMapper;
import com.szhbl.project.client.mapper.SystemDictMapper;
import com.szhbl.project.system.VO.SysUserVO;
import com.szhbl.project.system.domain.*;
import com.szhbl.project.system.mapper.*;
import com.szhbl.project.system.service.ISysConfigService;
import com.szhbl.project.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户 业务层处理
 *
 * @author szhbl
 */
@Service
public class SysUserServiceImpl implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private BusiClientsMapper busiClientsMapper;

    @Autowired
    private SystemDictMapper systemDictMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName)
    {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName)
    {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName)
    {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user)
    {
        log.info("StringUtils.isEmpty(user.getPhonenumber())------------------" + StringUtils.isEmpty(user.getPhonenumber()));
        if(StringUtils.isEmpty(user.getPhonenumber())){
            return UserConstants.UNIQUE;
        }
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user)
    {
        if(StringUtils.isEmpty(user.getPhonenumber())){
            return UserConstants.UNIQUE;
        }
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new CustomException("不允许操作超级管理员用户");
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 判断是否是推荐人
        if (user.getReferenceType().equals("1")) {
            SystemDict systemDict = new SystemDict();
            systemDict.setFid(5L);
            systemDict.setState("1");
            systemDict.setYuyan("0");
            systemDict.setSort("0");
            systemDict.setMcheng(user.getNickName() + (user.getEmail() != null ? ("/" + user.getEmail()) : ""));
            systemDictMapper.insertSystemDict(systemDict);
            user.setTjrId(systemDict.getId() + "");
        }
        user.setDcUserId(UUID.fastUUID().toString());
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user)
    {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        if(user.getReferenceType().equals("1")){
            String oldName = userMapper.selectUserById(user.getUserId()).getNickName();
            Long oldDeptId = userMapper.selectUserById(user.getUserId()).getDeptId();
            //部门或者姓名更改，更新客户表推荐人姓名
            if (!oldName.equals(user.getNickName())||oldDeptId!=user.getDeptId()) {
                BusiClients busiClients = new BusiClients();
                busiClients.setClientTjrId(user.getTjrId());
                busiClients.setClientTjr(deptMapper.selectDeptById(user.getDeptId()).getDeptName()+"-"+user.getNickName());
                busiClientsMapper.updateBusiClientsTjrWithTjrId(busiClients);
            }
        }
        // 更新推荐人表
        if (user.getReferenceType().equals("1")) {
            // 查找推荐人表是否存在
            SystemDict systemDict = new SystemDict();
            systemDict.setFid(5L);
            systemDict.setState("1");
            systemDict.setYuyan("0");
            systemDict.setSort("0");
            systemDict.setMcheng(user.getNickName() + (user.getEmail() != null ? ("/" + user.getEmail()) : ""));
            if ("0".equals(user.getTjrId())) {
                systemDictMapper.insertSystemDict(systemDict);
                user.setTjrId(systemDict.getId() + "");
            } else if (user.getTjrId() != null) {
                systemDict.setId(Long.parseLong(user.getTjrId()));
                systemDictMapper.updateSystemDict(systemDict);
            }
        }
        if (user.getReferenceType().equals("0") && (!"0".equals(user.getTjrId()))) {
            // 查找推荐人表是否存在
            SystemDict systemDict = new SystemDict();
            systemDict.setState("0");
            systemDict.setId(Long.parseLong(user.getTjrId()));
            systemDictMapper.updateSystemDict(systemDict);
        }
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param
     * @param avatar 头像地址
     * @return 结果
     */
    public boolean updateUserAvatar(String userName, String avatar)
    {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password)
    {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user)
    {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts))
        {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds)
    {
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
        }
        for (Long userId : userIds)
        {
            // 删除用户与角色关联
            userRoleMapper.deleteUserRoleByUserId(userId);
            // 删除用户与岗位表
            userPostMapper.deleteUserPostByUserId(userId);
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u))
                {
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }


     public List<SysUser> selectReference(){
        return userMapper.selectReference();
    }

    @Override
    public List<SysUser> selectAllMerchandiser(Map<String,String> map) {
        return userMapper.selectAllMerchandiser(map);
    }

    @Override
    public List<SysUserVO> selectMerchandiserWithName(String name) {
        return userMapper.selectMerchandiserWithName(name);
    }

    @Override
    public List<SysUserVO> selectMerchandiserWithJobNumber(String jobNumber) {
        return userMapper.selectMerchandiserWithJobNumber(jobNumber);
    }

    @Override
    public int updateInvoiceInfo(SysUser sysUser) {
        return userMapper.updateInvoiceInfo(sysUser);
    }

    @Override
    public List<SysUser> selectUsersByDcIds(String[] dcIds) {
        return userMapper.selectUsersByDcIds(dcIds);
    }
}
