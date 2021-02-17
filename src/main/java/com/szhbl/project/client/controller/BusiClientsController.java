package com.szhbl.project.client.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szhbl.common.constant.Constants;
import com.szhbl.common.utils.IdUtils;
import com.szhbl.common.utils.RandomUtil;
import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.StringUtils;
import com.szhbl.framework.email.IMailService;
import com.szhbl.project.client.domain.BusiClientsByTjr;
import com.szhbl.project.client.dto.BusiClientsDto;
import com.szhbl.project.client.dto.CleientNum;
import com.szhbl.project.client.form.ClientForm;
import com.szhbl.project.client.form.EmailForm;
import com.szhbl.project.system.VO.SysUserVO;
import com.szhbl.project.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.client.domain.BusiClients;
import com.szhbl.project.client.service.IBusiClientsService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;

/**
 * 客户管理Controller
 * 
 * @author jhm
 * @date 2020-01-06
 */
@RestController
@RequestMapping("/clients/clients")
@Api(tags = "客户管理")
public class BusiClientsController extends BaseController
{
    @Autowired
    private IBusiClientsService busiClientsService;
    @Autowired
    private IMailService iMailService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询客户管理列表
     */
//    @PreAuthorize("@ss.hasPermi('clients:clients:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusiClients busiClients)
    {
        startPage();
//        if(!SecurityUtils.getUsername().equals("admin")) {
//            busiClients.setClientTjrId(SecurityUtils.getUsername());
//        }
        if(StringUtils.isNotEmpty(busiClients.getClientUnit())){
            busiClients.setClientUnit((busiClients.getClientUnit()).trim());
        }
        List<BusiClients> list = busiClientsService.selectBusiClientsList(busiClients);
        return getDataTable(list);
    }

    /**
     * 导出客户管理列表
     */
    @PreAuthorize("@ss.hasPermi('clients:clients:export')")
    @Log(title = "客户管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出客户管理列表")
    public AjaxResult export(BusiClients busiClients)
    {
        if(StringUtils.isNotBlank(busiClients.getClientIds())){
            String[] clients = busiClients.getClientIds().split(",");
            busiClients.setClients(clients);
        }
        List<BusiClients> list = busiClientsService.selectBusiClientsList(busiClients);
        ExcelUtil<BusiClients> util = new ExcelUtil<BusiClients>(BusiClients.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        return util.exportExcel(list, "客户信息列表汇总"+date);
    }

    /**
     * 导出客户合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('clients:contract:export')")
    @Log(title = "客户管理", businessType = BusinessType.EXPORT)
    @GetMapping("/exportContract")
    @ApiOperation("导出客户管理列表")
    public AjaxResult exportContract(BusiClients busiClients)
    {
        if(StringUtils.isNotBlank(busiClients.getClientIds())){
            String[] clients = busiClients.getClientIds().split(",");
            busiClients.setClients(clients);
        }
        List<BusiClients> list = busiClientsService.selectBusiClientsList(busiClients);
        List bcdtoList=new ArrayList<BusiClientsDto>();
        BusiClientsDto  bcdto=null;
        for(int i=0;i<list.size();i++){
            bcdto=new BusiClientsDto();
            BeanUtils.copyProperties(list.get(i),bcdto);
            bcdtoList.add(bcdto);
        }
        ExcelUtil<BusiClientsDto> util = new ExcelUtil<BusiClientsDto>(BusiClientsDto.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        return util.exportExcel(bcdtoList, "客户合同信息列表"+date);
    }

    /**
     * 获取客户管理详细信息
     */
//    @PreAuthorize("@ss.hasPermi('clients:clients:query')")
    @GetMapping(value = "/{clientId}")
    public AjaxResult getInfo(@PathVariable("clientId") String clientId)
    {
        return AjaxResult.success(busiClientsService.selectBusiClientsById(clientId));
    }

    /**
     * 新增客户管理
     */
    @PreAuthorize("@ss.hasPermi('clients:clients:add')")
    @Log(title = "客户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusiClients busiClients)
    {
        //測試
        String clientId = IdUtils.randomUUID();
        busiClients.setClientId(clientId);
        return toAjax(busiClientsService.insertBusiClients(busiClients));
    }

    /**
     * 修改客户管理
     */
    @PreAuthorize("@ss.hasPermi('clients:clients:edit')")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusiClients busiClients) throws JsonProcessingException {
        return toAjax(busiClientsService.updateBusiClients(busiClients));
    }

    /**
     * 删除客户管理
     */
    @PreAuthorize("@ss.hasPermi('clients:clients:remove')")
    @Log(title = "客户管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{clientIds}")
    public AjaxResult remove(@PathVariable String[] clientIds)
    {
        return toAjax(busiClientsService.deleteBusiClientsByIds(clientIds));
    }
    /**
     * 审核失败/成功
     */
    @PreAuthorize("@ss.hasPermi('clients:toAudit:check')")
    @PutMapping(value = "/audit")
    @ApiOperation("审核客户（成功或失败）")
    public AjaxResult auditFail(@RequestBody BusiClients busiClients) throws JsonProcessingException {
        busiClients.setClientExamperson(SecurityUtils.getLoginUser().getUser().getNickName());
       //根据推荐人id查找
       //审核失败，发送邮件到客户端和对应的推荐人
//        SysUser u =  userService.selectUserById(Long.parseLong(busiClients.getClientTjrId()));
      if(StringUtils.isEmpty(busiClients.getIsexamline())){
            return  AjaxResult.error("请选择审核状态");
        }
      if(StringUtils.isNotEmpty(busiClients.getClientLoginuser())){
          busiClients.setClientLoginuser((busiClients.getClientLoginuser()).trim());
      }
      if(StringUtils.isNotEmpty(busiClients.getClientLoginpwd())){
          busiClients.setClientLoginpwd((busiClients.getClientLoginpwd()).trim());
      }
        //判断账号是否重复
        String clientIdForm = busiClients.getClientId();
        String clientLoginuser = busiClients.getClientLoginuser();
        BusiClients clientInfo = null;
        String isexamline = "";  //注册审核发送邮件
        if(StringUtils.isNotEmpty(clientLoginuser)){
            List<BusiClients> clientList = busiClientsService.selectBusiClientsListByUser(clientLoginuser);
            if(clientList.size()>1){
                return  AjaxResult.error("该账号重复，请修改账号");
            }
            if(clientList.size()==1){
                clientInfo = clientList.get(0);
                isexamline = clientInfo.getIsexamline();
                String clientId = clientList.get(0).getClientId();
                if(!StringUtils.equals(clientIdForm,clientId)){
                    return  AjaxResult.error("该账号重复，请修改账号");
                }
                //锁定之后可以在启用，不能注册，注销可在注册，不能启用,不能锁定  0启用 1锁定 2删除 3注销
                if("2".equals(clientList.get(0).getCancelaccount())){
                    return  AjaxResult.error("该账号已锁定，暂时无法注册");
                }
            }
        }

        int i = 0;
        if(busiClients.getIsexamline().equals(Constants.NO_PASS)){
           i =  busiClientsService.updateBusiClients(busiClients);
            if(i==1){
                if("0".equals(isexamline)){
                    busiClientsService.simpleEmail(busiClients);
                }
                return AjaxResult.success();
            }else{
                return AjaxResult.error();
            }
        }else if(busiClients.getIsexamline().equals(Constants.IS_PASS)){
            if(StringUtils.isNull(busiClients.getClientValiditydate())||StringUtils.isNull(busiClients.getClientDisableddate())){
                //审核通过客户有效期从当前日期顺延到下一年
                Date validateStartTime = new Date();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, 1);//增加一年
                Date validateEndTime = cal.getTime();
                busiClients.setClientValiditydate(validateStartTime);
                busiClients.setClientDisableddate(validateEndTime);
            }
            //生成随机密码
            if(StringUtils.isEmpty(busiClients.getClientLoginpwd())){
                String password = RandomUtil.getPasswordSimple(1,9);
                busiClients.setClientLoginpwd(password);
            }
             i = busiClientsService.updateBusiClients(busiClients);
            //审核通过，发送邮件到客户端和对应的推荐人
            if (i == 1) {
                if("0".equals(isexamline)){
                    busiClientsService.sendHtmlMail(busiClients);
                }
                return AjaxResult.success();
            }else {
                return AjaxResult.error();
            }
        }else{
            return AjaxResult.error();
    }


    }

    /**
     * 审核通过
     */
    /*@PreAuthorize("@ss.hasPermi('clients:clients:query')")
    @GetMapping(value = "/pass")
    @ApiOperation("审核通过")
    public AjaxResult pass(BusiClients busiClients) {

        //根据推荐人id查找
        busiClients.setIsexamline(Constants.IS_PASS);
        int i = busiClientsService.updateBusiClients(busiClients);
        //审核通过，发送邮件到客户端和对应的推荐人
        if (i == 1) {
            return busiClientsService.sendHtmlMail(busiClients);
        }else {
            return AjaxResult.error();
        }
    }*/


    /**
     * 编辑vip
     *
     */
    /*public AjaxResult vipStatus(String id,String isVip){
        BusiClients busiClients = new BusiClients();
        busiClients.setClientId(id);
        busiClients.setIsVip(isVip);
        busiClientsService.updateBusiClients(busiClients);

    }*/

    /**
     * 批量发送邮件
     */
    /*@PreAuthorize("@ss.hasPermi('clients:clients:query')")
    @GetMapping(value = "/sendMore")
    public AjaxResult sendMoreEmail(@Validated @RequestBody EmailForm emailForm){
     String[] clientIds = emailForm.getClientIds();
     //查询所有客户的邮箱信息
     return busiClientsService.selectBusiClientsWithIds(emailForm);
    }*/

    /**
     * 根据客户id,首先校验所选客户的跟单员是否一致，不一致提醒重新选，一致的话，将跟单员进行分割展示
     */
    @GetMapping("/merchandiserList")
    public AjaxResult merchandiserList(@RequestBody ClientForm clientForm){
        //根据clientIds
      return busiClientsService.selectBusiClientWithClientIds(clientForm.getType(),clientForm.getClientIds());
    }

    /**
     * 修改弹窗加载时，查询就旧跟单员id，工号，姓名
     *type 0按姓名查的，1按工号查
     */
//    @PreAuthorize("@ss.hasPermi('clients:clients:query')")
    @GetMapping("/getMerchandisersInfo")
    @ApiOperation("修改弹窗加载时，查询就旧跟单员id，工号，姓名")
    public AjaxResult getMerchandisersInfo(String type,String numberOrName){
        List<SysUserVO> userList = new ArrayList<SysUserVO>();
        if(type.equals("0")){
        //根据姓名查找跟单员信息
        userList = userService.selectMerchandiserWithName(numberOrName);

        }else if(type.equals("1")){
        userList = userService.selectMerchandiserWithJobNumber(numberOrName);
        }else{
            return AjaxResult.error("请输入正确参数");
        }

        if(userList.size() == 0){
           return AjaxResult.error("跟单员不存在");
        }else {
            return AjaxResult.success("成功",userList.get(0));

        }
    }

    /**
     * 批量修改跟单员（根据推荐人）
     */
    @PreAuthorize("@ss.hasPermi('clients:clients:editGd')")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/editMerByTjr")
    @ApiOperation("批量修改跟单")
    public AjaxResult editMerByTjr(@RequestBody BusiClientsByTjr busiClients){
        String clientIds = busiClients.getClientIds();
        if(StringUtils.isNotEmpty(clientIds)){
            return toAjax(busiClientsService.updateWMerchandiserByTjr(busiClients));
        }else {
            return AjaxResult.error("未选中客户记录");
        }
    }

    /**
     * 批量替换跟单员
     * type 0西向跟单员，1东向跟单员
     */
    @PreAuthorize("@ss.hasPermi('clients:clients:exportMore')")
    @Log(title = "批量修改跟单员", businessType = BusinessType.UPDATE)
    @GetMapping("/updateMerchandiser")
    @ApiOperation("批量修改跟单员")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "西向跟单员id",name = "wMerchandiserId",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "西向跟单员姓名",name = "wMerchandiser",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "东向跟单员id",name = "eMerchandiserId",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "东向跟单员姓名",name = "eMerchandiser",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "西向跟单工号",name = "wMerchandiserNumber",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "东向跟单工号",name = "eMerchandiserNumber",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "0,西向，1东向",name = "type",paramType = "query",dataType = "int",required = true)

    })
    public AjaxResult updateMerchandiser(ClientForm clientForm) throws JsonProcessingException {
        if(StringUtils.isEmpty(clientForm.getClientIds())){
            return AjaxResult.error("请先勾选要更换的数据");
        }
        //西向跟单员id,姓名，工号都不能为空
        if (StringUtils.isEmpty(clientForm.getNewWMerchandiser()) || StringUtils.isEmpty(clientForm.getNewWMerchandiserId()) || StringUtils.isEmpty(clientForm.getNewWMerchandiserNumber())) {
            return AjaxResult.error("西向跟单员等信息不能为空");
        }
        AjaxResult ajaxResultW = busiClientsService.updateWMerchandiser(clientForm);
        int codeW = (int) ajaxResultW.get("code");
        String msgW = (String) ajaxResultW.get("msg");
        //东向跟单员id,姓名，工号都不能为空
        if(StringUtils.isEmpty(clientForm.getNewEMerchandiser())|| StringUtils.isEmpty(clientForm.getNewEMerchandiserId())||StringUtils.isEmpty(clientForm.getNewEMerchandiserNumber())){
            return AjaxResult.error("东向跟单员等信息不能为空");
        }
        AjaxResult ajaxResultE = busiClientsService.updateEMerchandiser(clientForm);
        int codeE = (int) ajaxResultE.get("code");
        String msgE = (String) ajaxResultE.get("msg");
        if(codeW==200 || codeE==200){
            return AjaxResult.success("更新成功");
        }else{
            return AjaxResult.error("更新失败，请再次确认");
        }

//            return busiClientsService.updateEMerchandiser(clientForm);
//        if(clientForm.getType().equals("0")) {
//            //西向跟单员id,姓名，工号都不能为空
//            if (StringUtils.isEmpty(clientForm.getNewWMerchandiser()) || StringUtils.isEmpty(clientForm.getNewWMerchandiserId()) || StringUtils.isEmpty(clientForm.getNewWMerchandiserNumber())) {
//                return AjaxResult.error("西向跟单员等信息不能为空");
//            }
//            return busiClientsService.updateWMerchandiser(clientForm);
//        }
//        else {
//            if(StringUtils.isEmpty(clientForm.getNewEMerchandiser())|| StringUtils.isEmpty(clientForm.getNewEMerchandiserId())||StringUtils.isEmpty(clientForm.getNewEMerchandiserNumber())){
//                return AjaxResult.error("东向跟单员等信息不能为空");
//            }
//            return busiClientsService.updateEMerchandiser(clientForm);
//        }
//        AjaxResult ajaxResult1 = busiClientsService.updateEMerchandiser(clientForm);
//        String msg = (String) ajaxResult1.get("msg");
//        return AjaxResult.error(msg);
    }


/**
 * 批量删除跟单员
 */
@PreAuthorize("@ss.hasPermi('clients:clients:deleteMore')")
@Log(title = "批量删除跟单员", businessType = BusinessType.UPDATE)
@GetMapping("/deleteMore")
@ApiOperation("批量删除跟单员")
public AjaxResult deleteWMoreMerchandiser(ClientForm clientForm){
    //西向跟单员id,姓名，工号都不能为空
    if (StringUtils.isEmpty(clientForm.getOldWMerchandiserId()) || StringUtils.isEmpty(clientForm.getOldWMerchandiser()) || StringUtils.isEmpty(clientForm.getOldWMerchandiserNumber())) {
        return AjaxResult.error("西向跟单员等信息不能为空");
    }
    AjaxResult ajaxResultW = busiClientsService.deleteMoreWMerchandiser(clientForm);
    int codeW = (int) ajaxResultW.get("code");
    String msgW = (String) ajaxResultW.get("msg");
    if(StringUtils.isEmpty(clientForm.getOldEMerchandiserId())|| StringUtils.isEmpty(clientForm.getOldEMerchandiser())||StringUtils.isEmpty(clientForm.getOldEMerchandiserNumber())){
        return AjaxResult.error("东向跟单员等信息不能为空");
    }
    AjaxResult ajaxResultE = busiClientsService.deleteMoreEMerchandiser(clientForm);
    int codeE = (int) ajaxResultE.get("code");
    String msgE = (String) ajaxResultE.get("msg");
    if(codeW==200 || codeE==200){
        return AjaxResult.success("删除成功");
    }else{
        return AjaxResult.error("删除失败，请再次确认");
    }

//    if(clientForm.getType().equals("0")) {
//        //西向跟单员id,姓名，工号都不能为空
//        if (StringUtils.isEmpty(clientForm.getOldWMerchandiserId()) || StringUtils.isEmpty(clientForm.getOldWMerchandiser()) || StringUtils.isEmpty(clientForm.getOldWMerchandiserNumber())) {
//            return AjaxResult.error("西向跟单员等信息不能为空");
//        }
//        return busiClientsService.deleteMoreWMerchandiser(clientForm);
//    }
//    else {
//        if(StringUtils.isEmpty(clientForm.getOldEMerchandiserId())|| StringUtils.isEmpty(clientForm.getOldEMerchandiser())||StringUtils.isEmpty(clientForm.getOldEMerchandiserNumber())){
//            return AjaxResult.error("东向跟单员等信息不能为空");
//        }
//        return busiClientsService.deleteMoreEMerchandiser(clientForm);
//    }


}

/**
 * 发送邮件,按注册时间范围内的客户发送
 */
@PreAuthorize("@ss.hasPermi('clients:clients:sendEmail')")
@ApiOperation("发送邮件")
@GetMapping("/sendEmail")
public AjaxResult sendEmail(EmailForm emailForm) {
    //根据注册时间查询，审核通过的客户邮箱
    List<BusiClients> busiClientsList = busiClientsService.selectBusiClientsListWithValidate(emailForm.getRegisterStartTime(),emailForm.getRegisterEndTime());
    //循环遍历，发送邮件
    return  busiClientsService.sendEmailWithSender(busiClientsList,emailForm);

}

    /**
    * 国内外客户数量
    */
    @ApiOperation("国内外客户数量")
    @GetMapping("/calculateEnOrChNum")
    public AjaxResult calculateEnOrChNum(BusiClients busiClients){
        int chNum = busiClientsService.selectBusiClientsGNCountNum(busiClients);
        int enNum = busiClientsService.selectBusiClientsGWCountNum(busiClients);
        CleientNum cleientNum = new CleientNum();
        cleientNum.setChNum(chNum);
        cleientNum.setEnNum(enNum);
        return AjaxResult.success(cleientNum);

    }

    /**
     * 获取客户对应权限
     */
    @GetMapping("/getPowerClients")
    public AjaxResult getPowerClients(Integer powerType){
        List<String> list=busiClientsService.getPowerClients(powerType);
        return AjaxResult.success(list.toArray(new String[list.size()]));
    }
}
