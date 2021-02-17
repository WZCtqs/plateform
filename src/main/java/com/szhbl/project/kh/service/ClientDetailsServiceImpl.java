package com.szhbl.project.kh.service;

import com.szhbl.common.enums.UserStatus;
import com.szhbl.common.exception.BaseException;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.project.client.domain.BusiClients;
import com.szhbl.project.client.service.IBusiClientsService;
import com.szhbl.project.kh.domain.KhMenuPermission;
import com.szhbl.project.kh.domain.KhUser;
import com.szhbl.project.kh.domain.LoginClient;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 客户验证处理
 *
 * @author szhbl
 */
@Service
public class ClientDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(ClientDetailsServiceImpl.class);

    @Autowired
    private IBusiClientsService busiClientsService;
    @Autowired
    private IKhUserService khUserService;
    @Autowired
    private IKhMenuPermissionService khMenuPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        List<BusiClients> busiClients = busiClientsService.selectBusiClientsListByName(username);
        KhUser khUser;
        if (CollectionUtils.isEmpty(busiClients))
        {
            khUser = khUserService.selectKhUserByPhone(username);
            if (StringUtils.isNull(khUser)) {
                log.info("登录用户：{} 不存在.", username);
                throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
            } else if (UserStatus.DELETED.getCode().equals(khUser.getDelFlag()))
            {
                log.info("登录用户：{} 已被删除.", username);
                throw new BaseException("对不起，您的账号：" + username + " 已被删除");
            }
            // 获取菜单权限
            KhMenuPermission khMenuPermission = khMenuPermissionService.selectKhMenuPermissionById(khUser.getMpId());
            Set<String> menus = new HashSet<>();
            if(StringUtils.isNotEmpty(khMenuPermission.getMenuId())){
                String[] ms = khMenuPermission.getMenuId().split(",");
                menus = new HashSet<>(Arrays.asList(ms));
            }
            return createLoginKhUser(khUser,menus);
        } else {
            if (busiClients.size() == 1){
                if (UserStatus.DISABLE.getCode().equals(busiClients.get(0).getCancelaccount()) ||
                        busiClients.get(0).getClientDisableddate().before(new Date())) {
                    log.info("登录用户：{} 已被停用.", username);
                    throw new BaseException("对不起，您的账号：" + username + " 已停用");
                }
                return createLoginClient(busiClients.get(0));
            } else {
                for (BusiClients busiClient : busiClients) {
                    if (UserStatus.OK.getCode().equals(busiClient.getCancelaccount()) &&
                            busiClient.getClientDisableddate().after(new Date())) {
                        return createLoginClient(busiClient);
                    }
                }
                log.info("登录用户：{} 已被停用.", username);
                throw new BaseException("对不起，您的账号：" + username + " 已停用");
            }
        }
    }
    public UserDetails loadUserByUsername2(String username) throws UsernameNotFoundException
    {
        List<BusiClients> busiClients = busiClientsService.selectBusiClientsListByName(username);
        KhUser khUser;
        if (CollectionUtils.isEmpty(busiClients))
        {
            khUser = khUserService.selectKhUserByPhone(username);
            if (StringUtils.isNull(khUser)) {
                return null;
            } else if (UserStatus.DELETED.getCode().equals(khUser.getDelFlag()))
            {
                return null;
            }
            // 获取菜单权限
            KhMenuPermission khMenuPermission = khMenuPermissionService.selectKhMenuPermissionById(khUser.getMpId());
            Set<String> menus = new HashSet<>();
            if(StringUtils.isNotEmpty(khMenuPermission.getMenuId())){
                String[] ms = khMenuPermission.getMenuId().split(",");
                menus = new HashSet<>(Arrays.asList(ms));
            }
            return createLoginKhUser(khUser,menus);
        }else {
            if (busiClients.size() == 1){
                if (UserStatus.DISABLE.getCode().equals(busiClients.get(0).getCancelaccount()) ||
                        busiClients.get(0).getClientDisableddate().before(new Date())) {
                    return null;
                }
                return createLoginClient(busiClients.get(0));
            } else {
                for (BusiClients busiClient : busiClients) {
                    if (UserStatus.OK.getCode().equals(busiClient.getCancelaccount()) &&
                            busiClient.getClientDisableddate().after(new Date())) {
                        return createLoginClient(busiClient);
                    }
                }
                return null;
            }
        }
    }

    /**
     * 主账号
     * @param busiClients
     * @return
     */
    public UserDetails createLoginClient(BusiClients busiClients)
    {
        return new LoginClient(busiClients, null);
    }

    /**
     * 子账号
     * @param khUser
     * @return
     */
    public UserDetails createLoginKhUser(KhUser khUser,Set<String> permissions)
    {
        return new LoginClient(khUser, permissions);
    }
}
