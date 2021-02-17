package com.szhbl.project.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.project.client.domain.BusiClientsByTjr;
import com.szhbl.project.client.form.ClientForm;
import com.szhbl.project.client.form.EmailForm;
import com.szhbl.project.client.domain.BusiClients;
import java.util.List;

/**
 * 客户管理Service接口
 * 
 * @author jhm
 * @date 2020-01-06
 */
public interface IBusiClientsService 
{
    /**
     * 查询客户管理
     * 
     * @param clientId 客户管理ID
     * @return 客户管理
     */
    public BusiClients selectBusiClientsById(String clientId);

    /**
     * 查询客户管理
     *
     * @param userName 客户登录账号
     * @return 客户管理
     */
    public BusiClients selectBusiClientsByName(String userName);

    /**
     * 查询客户管理
     *
     * @param userName 客户登录账号
     * @return 客户管理
     */
    public List<BusiClients> selectBusiClientsListByName(String userName);

    /**
     * 查询客户管理列表
     * 
     * @param busiClients 客户管理
     * @return 客户管理集合
     */
    public List<BusiClients> selectBusiClientsList(BusiClients busiClients);

    /**
     * 根据账号查询客户管理列表
     */
    public List<BusiClients> selectBusiClientsListByUser(String clientLoginuser);

    /**
     * 新增客户管理
     * 
     * @param busiClients 客户管理
     * @return 结果
     */
    public int insertBusiClients(BusiClients busiClients);

    /**
     * 修改客户管理
     * 
     * @param busiClients 客户管理
     * @return 结果
     */
    public int updateBusiClients(BusiClients busiClients) throws JsonProcessingException;

    /**
     * 批量删除客户管理
     * 
     * @param clientIds 需要删除的客户管理ID
     * @return 结果
     */
    public int deleteBusiClientsByIds(String[] clientIds);

    /**
     * 删除客户管理信息
     * 
     * @param clientId 客户管理ID
     * @return 结果
     */
    public int deleteBusiClientsById(String clientId);

    /**
     * 查询待审核客户管理列表
     *
     * @param busiClients 客户管理
     * @return 客户管理集合
     */
    public List<BusiClients> selectClientsExamineList(BusiClients busiClients);

    /**
     * 发送邮件
     * @param busiClients
     * @return
     */
     AjaxResult simpleEmail(BusiClients busiClients);


    /**
     * 发送html邮件
     * @param busiClients
     * @return
     */
     AjaxResult sendHtmlMail(BusiClients busiClients);

    /**
     *查询客户邮箱
     * @param emailForm，客户id数组
     * @return
     */

  /*  public AjaxResult selectBusiClientsWithIds(EmailForm emailForm);*/

    /**
     * 批量修改跟单员（根据推荐人）
     * @param busiClients
     * @return
     */
    public int updateWMerchandiserByTjr(BusiClientsByTjr busiClients);

    /**
     * 更新跟单员
     * @param clientForm
     * @return
     */
     AjaxResult updateWMerchandiser(ClientForm clientForm) throws JsonProcessingException;

    /**
     *
     * @param 、、clientForm
     * @return
     */
    AjaxResult updateEMerchandiser(ClientForm clientForm) throws JsonProcessingException;


    AjaxResult selectBusiClientWithClientIds(String type,String clientIds);


    /**
     * 批量删除西向跟单员
     * @param clientForm
     * @return
     */
    AjaxResult deleteMoreWMerchandiser(ClientForm clientForm);

    /**
     * 批量删除东向跟单员
     * @param clientForm
     * @return
     */
    AjaxResult deleteMoreEMerchandiser(ClientForm clientForm);



    List<BusiClients> selectBusiClientsListWithValidate(String registerStartTime,String registerEndTime);


    /**
     * 设置发件人，发送邮件
     */
    AjaxResult sendEmailWithSender(List<BusiClients> busiClientsList,EmailForm emailForm);

    int selectBusiClientsGNCountNum(BusiClients busiClients);

    int selectBusiClientsGWCountNum(BusiClients busiClients);
    List<String> getPowerClients(Integer powerType);
}
