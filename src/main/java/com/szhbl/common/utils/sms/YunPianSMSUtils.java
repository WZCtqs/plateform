package com.szhbl.common.utils.sms;

import com.alibaba.fastjson.JSONObject;
import com.szhbl.common.constant.SmsConstants;
import com.szhbl.common.utils.FastJsonUtils;
import com.szhbl.framework.web.domain.AjaxResult;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信http接口的java代码调用示例
 * 基于Apache HttpClient 4.3
 *
 * @author songchao
 * @since 2015-04-03
 */

public class YunPianSMSUtils {

    private static final Logger log = LoggerFactory.getLogger(YunPianSMSUtils.class);

    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

    //模板发送接口的http地址
    private static String URI_TPL_SEND_SMS =
            "https://sms.yunpian.com/v2/sms/tpl_single_send.json";

    /**
     * 功能描述: 司机接单通知短信方法
     *
     * @param phone   手机号
     * @param orderId 订单编号
     */
    public static AjaxResult sendDriverNewOrder(String phone, String orderId) throws IOException {
        /*设置模板id*/
        long tpl_id = SmsConstants.TPL_ID_NEW;
        String orderType = orderId.substring(0, 2).equals("CN") ? "集装箱" : "普货";
        //设置对应的模板变量值
        String tpl_value =
                URLEncoder.encode("#orderType#", ENCODING) + "=" + URLEncoder.encode(orderType, ENCODING)
                        + "&" + URLEncoder.encode("#orderId#", ENCODING) + "=" + URLEncoder.encode(orderId, ENCODING);
        String resultCode = YunPianSMSUtils.tplSendSms(SmsConstants.APIKEY, tpl_id, tpl_value, phone);
        JSONObject obj = JSONObject.parseObject(resultCode);
        /*获取返回消息*/
        String code = obj.getString("code");
        System.out.println(obj.toString());
        if (code == "0" || code.equals("0")) {
            return AjaxResult.success(YunPianResultCodeEmnu.getName(Integer.valueOf(code)));
        }
        return AjaxResult.error(YunPianResultCodeEmnu.getName(Integer.valueOf(code)));
    }

    /**
     * 单证类型发送消息提醒
     * @param phone
     * @param orderNumber
     * @param classDate
     * @throws IOException
     */
    public static SmsResult docRemindSMS(String phone, String orderNumber, String classDate) throws IOException {
        /*设置模板id*/
        long tpl_id = SmsConstants.TPL_ID_DOC;
        //设置对应的模板变量值
        String tpl_value =
                URLEncoder.encode("#orderNumber#", ENCODING) + "=" + URLEncoder.encode(orderNumber, ENCODING)
                        + "&" + URLEncoder.encode("#classDate#", ENCODING) + "=" + URLEncoder.encode(classDate, ENCODING);
        String resultCode = YunPianSMSUtils.tplSendSms(SmsConstants.APIKEY, tpl_id, tpl_value, phone);
        return FastJsonUtils.json2Bean(resultCode ,SmsResult.class);
    }

    /**
     * 通过模板发送短信(不推荐)
     *
     * @param apikey    apikey
     * @param tpl_id    　模板id
     * @param tpl_value 　模板变量值
     * @param mobile    　接受的手机号
     */
    public static String tplSendSms(String apikey, long tpl_id, String tpl_value,
                                    String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return post(URI_TPL_SEND_SMS, params);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<
                        NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(),
                            param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList,
                        ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, ENCODING);
            }
        } catch (Exception e) {
            log.error("基于HttpClient 4.3的通用POST方法失败：{}",e.toString(),e.getStackTrace());
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                log.error("基于HttpClient 4.3的通用POST方法--response.close()失败：{}",e.toString(),e.getStackTrace());
            }
        }
        return responseText;
    }
}
