package com.szhbl.project.documentcenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.szhbl.common.utils.DocUrlHandle;
import com.szhbl.common.utils.SecurityUtils;
import com.szhbl.common.utils.poi.ExcelUtil;
import com.szhbl.framework.aspectj.lang.annotation.Log;
import com.szhbl.framework.aspectj.lang.enums.BusinessType;
import com.szhbl.framework.security.LoginUser;
import com.szhbl.framework.web.controller.BaseController;
import com.szhbl.framework.web.domain.AjaxResult;
import com.szhbl.framework.web.page.TableDataInfo;
import com.szhbl.project.documentcenter.domain.*;
import com.szhbl.project.documentcenter.domain.vo.*;
import com.szhbl.project.documentcenter.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 托书单证Controller
 *
 * @author szhbl
 * @date 2020-01-03
 */
@Slf4j
@Api(tags = "托书单证管理")
@RestController
@RequestMapping("/document/order")
public class OrderDocumentController extends BaseController {
    @Autowired
    private IOrderDocumentService orderDocumentService;

    @Autowired
    private IDocBoxingListService boxingListService;

    @Autowired
    private IDocOrderUnpackingagentService unpackingagentService;

    @Autowired
    private IDocPxGoodsInOutService pxGoodsInOutService;

    @Autowired
    private IDocYardLoadedInService yardLoadedInService;

    @Autowired
    private IDocInBoxCheckService inBoxCheckService;

    @Autowired
    private LadingBillService ladingBillService;

    @Autowired
    private IDocOrderInstationService instationService;
    @Autowired
    private IDocOrderService docOrderService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 提箱时间
     *
     * @param orderId
     * @return
     */
    @ApiOperation("提箱时间")
    @GetMapping("/pickGoodsTime")
    public DocOrderDocument pickGoodsTime(String orderId) {
        return orderDocumentService.pickGoodsTime(orderId);
    }

    /**
     * 装柜清单（件重尺）
     *
     * @param orderId
     * @return
     */
    @ApiOperation("装柜清单（件重尺）")
    @GetMapping("/boxingList")
    public PxBoxingList boxingListService(String orderId) {
        return boxingListService.selectDocBoxingListByOrderId(orderId);
    }

    /**
     * 拆箱代理数据对象
     *
     * @param orderId
     * @return
     */
    @ApiOperation("拆箱代理数据对象")
    @GetMapping("/unpackingagent")
    public DocOrderUnpackingagent unpackingagentService(String orderId) {
        return unpackingagentService.selectOrderUnpackingagentByOrderId(orderId);
    }

    /**
     * 拼箱出入库表
     *
     * @param orderId
     * @return
     */
    @ApiOperation("拼箱出入库表")
    @GetMapping("/pxGoodsInOut")
    public PxGoodsInOut pxGoodsInOutService(String orderId) {
        return pxGoodsInOutService.selectDocPxGoodsInOutByOrderId(orderId);
    }

    /**
     * 堆场重箱进展表
     *
     * @param orderId
     * @return
     */
    @ApiOperation("堆场重箱进展表")
    @GetMapping("/yardLoadedIn")
    public List<PxYardLoadedIn> yardLoadedInService(String orderId) {
        return yardLoadedInService.selectDocYardLoadedInByOrderId(orderId);
    }

    /**
     * 入仓核实单、到货信息
     *
     * @param orderId
     * @return
     */
    @ApiOperation("入仓核实单、到货信息")
    @GetMapping("/inBoxCheck")
    public PxInBoxCheck inBoxCheckService(String orderId) {
        return inBoxCheckService.selectDocInBoxCheckByOrderId(orderId);
    }

    /**
     * 提单草单
     *
     * @param orderId
     * @return
     */
    @ApiOperation("提单草单")
    @GetMapping("/ladingBill")
    public LadingBill ladingBillService(String orderId) {
        return ladingBillService.selectLadingBillByOrderId(orderId);
    }

    /**
     * 回程进站统计表对象
     *
     * @param orderId
     * @return
     */
    @ApiOperation("回程进站统计表对象")
    @GetMapping("/instation")
    public DocOrderInstation instationService(String orderId) {
        return instationService.selectDocOrderInstationByOrderId(orderId);
    }

    @ApiOperation(value = "单证页面托书list查询")
    @GetMapping("orderlist")
    @ApiImplicitParams({})
//    @PreAuthorize("@ss.hasPermi('document:order:list')")
    public TableDataInfo list(OrderDocuments orderDocuments) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        orderDocuments.setTjr(loginUser.getUser().getReferenceType());
        orderDocuments.setTjrId(loginUser.getUser().getTjrId());
        orderDocuments.setUserid(loginUser.getUser().getUserId());
        orderDocuments.setDeptCode(loginUser.getUser().getDeptCode());
        List<OrderDocuments> list = orderDocumentService.orderDocumentOrderList(orderDocuments);
        return getDataTable(list);
    }

    @GetMapping("/getBeyondDocs")
    public List<BeyondDoc> selectBeyondByOrderId(String orderId) {
        return orderDocumentService.selectBeyondByOrderId(orderId);
    }

    @GetMapping("/getPickUpGoodsTime")
    public DocOrderPickGoodTimeVO getPickUpGoodsTime(String orderId) {
        return orderDocumentService.getPickUpGoodsTime(orderId);
    }

    /**
     * 查询单证列表
     */
    @ApiOperation(value = "单证集合查询")
//    @PreAuthorize("@ss.hasPermi('document:order:list')")
    @GetMapping("/list")
    public AjaxResult getOrderDoccList(DocOrderDocument docOrderDocument) {
        List<DocOrderDocument> list = orderDocumentService.selectOrderDocumentList(docOrderDocument);
        for (DocOrderDocument f : list) {
            f.setFileUrl(DocUrlHandle.urlHandle(f.getFileUrl()));
        }
        return AjaxResult.success(list);
    }

    /**
     * 查询托书单证信息
     */
    @ApiOperation(value = "根据托书信息、单证类型查询单证")
    @GetMapping("/getbyorder")
    public AjaxResult getbyorder(DocOrderDocument docOrderDocument) {
        return AjaxResult.success(orderDocumentService.selectOrderDocumentList(docOrderDocument));
    }


    /**
     * 导出单证列表
     */
    @PreAuthorize("@ss.hasPermi('document:order:export')")
    @Log(title = "托书单证", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DocOrderDocument docOrderDocument) {
        List<DocOrderDocument> list = orderDocumentService.selectOrderDocumentList(docOrderDocument);
        ExcelUtil<DocOrderDocument> util = new ExcelUtil<DocOrderDocument>(DocOrderDocument.class);
        return util.exportExcel(list, "document");
    }

    /**
     * 获取单证详细信息
     */
    @ApiOperation(value = "获取单证详细信息")
//    @PreAuthorize("@ss.hasPermi('document:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(orderDocumentService.selectOrderDocumentById(id));
    }

    /**
     * 新增单证
     */
    @ApiOperation(value = "新增单证")
    @PreAuthorize("@ss.hasPermi('document:order:add')")
    @Log(title = "托书单证", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocOrderDocument docOrderDocument) {
        return toAjax(orderDocumentService.insertOrderDocument(docOrderDocument));
    }

    /**
     * 修改单证
     */
    @ApiOperation(value = "修改单证")
    @PreAuthorize("@ss.hasPermi('document:order:edit')")
    @Log(title = "托书单证", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocOrderDocument docOrderDocument) {
        return toAjax(orderDocumentService.updateOrderDocument(docOrderDocument));
    }

    /**
     * 删除单证
     */
    @ApiOperation(value = "删除单证")
    @PreAuthorize("@ss.hasPermi('document:order:remove')")
    @Log(title = "托书单证", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(orderDocumentService.deleteOrderDocumentByIds(ids));
    }

    @ApiOperation(value = "删除单证")
    @Log(title = "托书单证", businessType = BusinessType.DELETE)
    @GetMapping("/clientDelDoc")
    public AjaxResult clientDelDoc(String orderId, String orderNumber, String fileTypeKey, Long id) {
        int i = orderDocumentService.deleteOrderDocumentById(id);
        if (i == 0) {
            return AjaxResult.error();
        } else {
            List<DocOrderDocument> list = orderDocumentService.getByOrderIdFileKey(orderId, fileTypeKey);
            if (list.size() == 0) {
                DocOrder docOrder = new DocOrder();
                docOrder.setOrderId(orderId);
                docOrder.setFileTypeKey(fileTypeKey);
                docOrder.setActualSupply(0);
                docOrderService.updateActualSupply(docOrder);
            }
            /*通知子系统*/
            //报关材料 通知关务系统  新增二级运踪节点 回程是清关 去程16，回程19
            if ("customs_apply_filec".equals(fileTypeKey)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("file_type_key", "customs_apply_filec");
                jsonObject.put("order_id", orderId);
                jsonObject.put("order_number", orderNumber);
                jsonObject.put("type", "delete");
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend("gw.system.files.topic.exchange", "customs_apply_filec", jsonObject, correlationData);
            }
            //随车文件 -- 发送大口岸系统/关务系统、国外场站系统
            if ("follow_vehicle_filec".equals(fileTypeKey)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("order_id", orderId);
                jsonObject.put("order_number", orderNumber);
                jsonObject.put("type", "delete");
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend("client.topic.exchange", "follow.vehicle.*", jsonObject, correlationData);
            }
            //客户自装箱装箱明细 -- 发送 拼箱系统、关务系统
            if ("boxing_list_filec".equals(fileTypeKey)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "delete");
                jsonObject.put("order_id", orderId);
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend("order.boxing.listc.topic.exchange", "order.boxing.listc.*", jsonObject, correlationData);
            }
            //报关材料正本 --发送关务系统
            if ("declare_customs_formal_file".equals(fileTypeKey)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("order_id", orderId);
                jsonObject.put("type", "delete");
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend("gw.system.files.topic.exchange", "declare_customs_formal", jsonObject, correlationData);
            }
            return AjaxResult.success();
        }
    }

    /**
     * 批量下载
     *
     * @param response
     * @param documents
     */
    @ApiOperation(value = "批量下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileUrl", value = "文件地址"),
            @ApiImplicitParam(name = "fileName", value = "文件名称")
    })
    @PreAuthorize("@ss.hasPermi('document:order:download')")
    @Log(title = "托书单证", businessType = BusinessType.EXPORT)
    @PostMapping("/downloadFile")
    public void downLoadFile(HttpServletResponse response, @RequestBody List<DocOrderDocument> documents) { //获取的对象
        List<File> files = new ArrayList<>();
        // 下载前清空临时文件
        File linshi = new File("E://downloadfile/");
        linshi.delete();
        for (int i = 0; i < documents.size(); i++) {
            String path = documents.get(i).getFileUrl();
            if(documents.get(i).getFileUrl().startsWith("http")){
                path = downloadHttpUrl(documents.get(i).getFileUrl(), "E://downloadfile/", documents.get(i).getFileName());
            }
            File file = new File(path); //filePath 就是自己定义的文件路径//fileSrc是文件名称
            files.add(file);
        }
        downFile(files, response);
    }

    public HttpServletResponse downFile(List<File> list, HttpServletResponse response) {
        try {
            String zipFiles = "E://" + System.currentTimeMillis() + ".zip"; //根据毫秒数生成一个ZIP文件
            File file = new File(zipFiles);
            if (!file.exists()) {
                file.createNewFile(); //创建一个zip文件
            }
            response.reset();//清空response
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            zipFiles(list, zipOutputStream);
            zipOutputStream.close();
            fileOutputStream.close();
            return downloadZip(file, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return response;
    }
    public void zipFiles(List<File> files, ZipOutputStream outputStream) {
        int size = files.size();
        for (int i = 0; i < size; i++) {
            File file = files.get(i);
            zipFile(file, outputStream);
        }
    }

    /**
     * @param inputFile   //文档路径
     * @param ouputStream
     */
    public void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            //File路径
            FileInputStream IN = new FileInputStream(inputFile);
            BufferedInputStream bins = new BufferedInputStream(IN, 1024);
            ZipEntry entry = new ZipEntry(inputFile.getName());
            ouputStream.putNextEntry(entry);
            // 向压缩文件中输出数据
            int nNumber;
            byte[] buffer = new byte[1024];
            while ((nNumber = bins.read(buffer)) != -1) {
                ouputStream.write(buffer, 0, nNumber);
            }
            // 关闭创建的流对象
            bins.close();
            IN.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public HttpServletResponse downloadZip(File file, HttpServletResponse response) {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();

            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            // 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("GB2312"), "ISO8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                File f = new File(file.getPath());
                f.delete();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return response;
    }

    /**
     * 下载文件---返回下载后的文件存储路径
     *  @param url 文件地址
     * @param dir 存储目录
     * @param fileName 存储文件名
     * @return
     */
    public static String downloadHttpUrl(String url, String dir, String fileName) {
        try {
            URL httpurl = new URL(url);
            File dirfile = new File(dir);
            if (!dirfile.exists()) {
                dirfile.mkdirs();
            }
            FileUtils.copyURLToFile(httpurl, new File(dir+fileName));
            return dir+fileName;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return url;
    }
}
