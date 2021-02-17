package com.szhbl.project.customerservice.controller;

import java.util.Date;
import java.util.List;

import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.framework.security.LoginUser;
import com.szhbl.project.customerservice.domain.CompensationTracking;
import com.szhbl.project.customerservice.service.IBusiShippingordershService;
import com.szhbl.project.customerservice.service.ICompensationTrackingService;
import com.szhbl.project.customerservice.vo.InfoVo;
import com.szhbl.project.customerservice.vo.UserVo;
import com.szhbl.project.system.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.project.customerservice.domain.CustomerServiceMessage;
import com.szhbl.project.customerservice.service.ICustomerServiceMessageService;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 售后Controller
 *
 * @author b
 * @date 2020-03-30
 */
@RestController
@RequestMapping("/customerservice/message")
@Api(tags = " 售后",description = "售后列表")
public class CustomerServiceMessageController extends BaseController
{
    @Autowired
    private ICustomerServiceMessageService customerServiceMessageService;
    @Autowired
    private IBusiShippingordershService busiShippingordershService;
    @Autowired
    private ICompensationTrackingService compensationTrackingService;
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 查询售后列表
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:message:list')")
    @GetMapping("/list")
    @ApiOperation("查询售后列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前记录起始索引",name = "pageNum",paramType = "query",dataType = "int",required = true),
            @ApiImplicitParam(value = "每页显示记录数",name = "pageSize",paramType = "query",dataType = "int",required = true)
       })
    public TableDataInfo list(CustomerServiceMessage customerServiceMessage)
    {
        startPage();
       /* LoginUser loginUser = SecurityUtils.getLoginUser();
        if ("1".equals(loginUser.getUser().getReferenceType())){
            customerServiceMessage.setClientTjrId(loginUser.getUser().getTjrId());
        }*/
        customerServiceMessage.setDeptCode(SecurityUtils.getLoginUser().getUser().getDeptCode());
        customerServiceMessage.setUserId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        List<CustomerServiceMessage> list = customerServiceMessageService.selectCustomerServiceMessageList(customerServiceMessage);
        return getDataTable(list);
    }

    /**
     * 导出售后列表
     */
    @PreAuthorize("@ss.hasPermi('afterSaleManage:afterSaleList:export')")
    @Log(title = "售后", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CustomerServiceMessage customerServiceMessage)
    {
        List<CustomerServiceMessage> list = customerServiceMessageService.selectCustomerServiceMessageList(customerServiceMessage);
        for (CustomerServiceMessage c:list){
            /*if ("0".equals(c.getBookingService())){
                c.setBookingService("门到门 ");
            }else if ("1".equals(c.getBookingService())){
                c.setBookingService("门到站");
            }else if ("2".equals(c.getBookingService())){
                c.setBookingService("站到站");
            }else if ("3".equals(c.getBookingService())){
                c.setBookingService("站到门");
            }
            if ("0".equals(c.getClassEastandwest())){
                c.setClassEastandwest("去程");
            }else if ("1".equals(c.getClassEastandwest())){
                c.setClassEastandwest("回程");
            }*/
            /*if ("0".equals(c.getComplaintsType())){
                c.setComplaintsType("货损");
            }else if ("1".equals(c.getComplaintsType())){
                c.setComplaintsType("包装破损 ");
            }else if ("2".equals(c.getComplaintsType())){
                c.setComplaintsType("货少 ");
            }else if ("3".equals(c.getComplaintsType())){
                c.setComplaintsType("延期 ");
            }else if ("4".equals(c.getComplaintsType())){
                c.setComplaintsType("费用争议 ");
            }else if ("5".equals(c.getComplaintsType())){
                c.setComplaintsType("操作失误 ");
            }else if ("6".equals(c.getComplaintsType())){
                c.setComplaintsType("其他 ");
            }*/
            if ("0".equals(c.getStatus())){
                c.setStatus("处理中 ");
            }else if ("1".equals(c.getStatus())){
                c.setStatus("预结案");
            }else if ("2".equals(c.getStatus())){
                c.setStatus("已结案");
            }else if ("3".equals(c.getStatus())){
                c.setStatus("赔款跟踪");
            }
        }
        ExcelUtil<CustomerServiceMessage> util = new ExcelUtil<CustomerServiceMessage>(CustomerServiceMessage.class);
        return util.exportExcel(list, "message");
    }

    /**
     * 获取售后详细信息
     */
//    @PreAuthorize("@ss.hasPermi('customerservice:message:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(customerServiceMessageService.selectCustomerServiceMessageById(id));
    }

    /**
     * 新增售后
     */
    @PreAuthorize("@ss.hasPermi('customerservice:message:add')")
    @Log(title = "售后", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerServiceMessage customerServiceMessage)
    {
        return toAjax(customerServiceMessageService.insertCustomerServiceMessage(customerServiceMessage));
    }

    /**
     * 修改售后
     */
    @PreAuthorize("@ss.hasPermi('customerservice:message:edit')")
    @Log(title = "售后", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    @ApiOperation("编辑售后列表信息")
    public AjaxResult edit(@RequestBody CustomerServiceMessage customerServiceMessage)
    {
        if ("2".equals(customerServiceMessage.getStatus())){
            customerServiceMessage.setEndDate(new Date());
        }
        if ("3".equals(customerServiceMessage.getStatus())){
            CompensationTracking compensationTracking = new CompensationTracking();
            CustomerServiceMessage c1 = customerServiceMessageService.selectCustomerServiceMessageById(customerServiceMessage.getId());
            BeanUtils.copyProperties(c1,compensationTracking);
            compensationTracking.setComplaintDate(c1.getComplaintsTime());
            compensationTracking.setCreateTime(new Date());
            compensationTrackingService.insertCompensationTracking(compensationTracking);
        }
        return toAjax(customerServiceMessageService.updateCustomerServiceMessage(customerServiceMessage));
    }

    /**
     * 删除售后
     */
    @PreAuthorize("@ss.hasPermi('customerservice:message:remove')")
    @Log(title = "售后", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(customerServiceMessageService.deleteCustomerServiceMessageByIds(ids));
    }

    //信息查询页面点击确定
    @GetMapping("/addCustomerService")
    @ApiOperation("信息查询界面点击确定数据按钮接口")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "舱位号",name = "orderNumber",paramType = "query",dataType = "String",required = true)
    })
    public AjaxResult addCustomerService(String orderNumber)
    {
        CustomerServiceMessage c1 = new CustomerServiceMessage();
        c1.setOrderNumber(orderNumber);
        List<CustomerServiceMessage> list = customerServiceMessageService.selectCustomerServiceMessageList(c1);
        if (list.size()>0){
            return AjaxResult.error(500,"请不要重复添加");
        }
        CustomerServiceMessage customerServiceMessage = new CustomerServiceMessage();
        InfoVo infoVo = busiShippingordershService.selectInfo(orderNumber);
        if (infoVo!=null){
            BeanUtils.copyProperties(infoVo,customerServiceMessage);
            if (customerServiceMessage.getClassEastandwest().equals("0")){
                  customerServiceMessage.setClassEastandwest("去程");
            }else if (customerServiceMessage.getClassEastandwest().equals("0")){
                  customerServiceMessage.setClassEastandwest("回程");
            }
            if ("0".equals(customerServiceMessage.getBookingService())){
                customerServiceMessage.setBookingService("门到门 ");
            }else if ("1".equals(customerServiceMessage.getBookingService())){
                customerServiceMessage.setBookingService("门到站");
            }else if ("2".equals(customerServiceMessage.getBookingService())){
                customerServiceMessage.setBookingService("站到站");
            }else if ("3".equals(customerServiceMessage.getBookingService())){
                customerServiceMessage.setBookingService("站到门");
            }
            UserVo userVo = sysUserMapper.selectclientTjr(infoVo.getClientTjrId());
            customerServiceMessage.setGeneral(userVo.getLeader());
            customerServiceMessage.setYwb(userVo.getName());
            customerServiceMessage.setComplaintsTime(new Date());
            return toAjax(customerServiceMessageService.insertCustomerServiceMessage(customerServiceMessage));
        }
        return toAjax(0);
    }
    /*导入*/
    @PreAuthorize("@ss.hasPermi('afterSaleManage:afterSaleList:import')")
    @Log(title = "售后列表", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @Transactional
    public AjaxResult importData(@RequestParam MultipartFile file) throws Exception
    {
        ExcelUtil<CustomerServiceMessage> util = new ExcelUtil<>(CustomerServiceMessage.class);
        List<CustomerServiceMessage> list = util.importExcel(file.getInputStream());
        for (CustomerServiceMessage c:list){
            CustomerServiceMessage c1 = new CustomerServiceMessage();
            c1.setOrderNumber(c.getOrderNumber());
            List<CustomerServiceMessage> list1 = customerServiceMessageService.selectCustomerServiceMessageList(c1);
            if (list1.size()==0) {
                if ("处理中".equals(c.getStatus())) {
                    c.setStatus("0 ");
                } else if ("预结案".equals(c.getStatus())) {
                    c.setStatus("1");
                } else if ("已结案".equals(c.getStatus())) {
                    c.setStatus("2");
                } else if ("赔款跟踪".equals(c.getStatus())) {
                    c.setStatus("3");
                }
                c.setOrderId(busiShippingordershService.selectOrderId(c.getOrderNumber()));
                int a = customerServiceMessageService.insertCustomerServiceMessage(c);
                if (a==0){
                  return AjaxResult.error("导入失败,请检查数据格式");
              }
            }
        }
        return AjaxResult.success();
    }
}
