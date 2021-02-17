package com.szhbl.project.client.mapper;

import com.szhbl.project.client.domain.BusiClients;
import com.szhbl.project.client.domain.BusiClientsByTjr;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 客户管理Mapper接口
 *
 * @author jhm
 * @date 2020-01-06
 */
@Repository
public interface BusiClientsMapper {
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
    public int updateBusiClients(BusiClients busiClients);

    /**
     * 删除客户管理
     *
     * @param clientId 客户管理ID
     * @return 结果
     */
    public int deleteBusiClientsById(String clientId);

    /**
     * 批量删除客户管理
     *
     * @param clientIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiClientsByIds(String[] clientIds);

    /**
     * 查询待审核客户管理列表
     *
     * @param busiClients 客户管理
     * @return 客户管理集合
     */
    public List<BusiClients> selectClientsExamineList(BusiClients busiClients);

    /**
     * 查询客户邮箱
     *
     * @param clientIds
     * @return 客户邮箱集合
     */
    List<BusiClients> selectBusiClientsWithIds(String[] clientIds);

    /**
     * 批量修改跟单员（根据推荐人）
     *
     * @param busiClients
     * @return
     */
    public int updateWMerchandiserByTjr(BusiClientsByTjr busiClients);

    /**
     * @param busiClients
     * @return
     */
    int updateWMerchandiser(BusiClients busiClients);

    /**
     * @param busiClients
     * @return
     */
    int updateEMerchandiser(BusiClients busiClients);

    /**
     * @param clientIds
     * @return
     */
    List<BusiClients> selectBusiClientWithClientIds(String[] clientIds);

    List<BusiClients> selectBusiClientsListWithValidate(Map<String, String> map);

    int updateBusiClientsTjrWithTjrId(BusiClients busiClients);

    int selectBusiClientsGNCountNum(BusiClients busiClients);

    int selectBusiClientsGWCountNum(BusiClients busiClients);

    List<BusiClients> selectClinent();

    int selectAorB(String clientId);

    int selectDays(String clientId);

    List<String> getPowerClients(@Param("powerType") Integer powerType);

    public String getPowerById(String clientId);

    List<BusiClients> selectBusiClientsListByName(String userName);

    String selectClientEmailForDoc(String clientId);
}
