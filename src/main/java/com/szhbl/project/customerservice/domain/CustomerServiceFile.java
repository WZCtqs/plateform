package com.szhbl.project.customerservice.domain;

import com.szhbl.project.customerservice.vo.FileVo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

import java.util.List;

/**
 * 售后文件对象 customer_service_file
 *
 * @author b
 * @date 2020-04-10
 */
public class CustomerServiceFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 0情况说明1回复函2赔付报告3通报 */
    @Excel(name = "0情况说明1回复函2赔付报告3通报")
    private String type;

    /** 舱位号 */
    @Excel(name = "舱位号")
    private String ordernumber;

    /** 文件名 */
    @Excel(name = "文件名")
    private String name;

    /** 文件本地路径 */
    @Excel(name = "文件本地路径")
    private String url;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 客户推荐人 */
    @Excel(name = "客户推荐人")
    private String clientTjr;

    /** 客户推荐人id */
    @Excel(name = "客户推荐人id")
    private String clientTjrId;

    /** 责任部门 */
    @Excel(name = "责任部门")
    private String department;

    private List<FileVo> list;

    /**
     * 数据权限查询条件
     */
    private String deptCode;
    private String readType;
    private String userId;
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<FileVo> getList() {
        return list;
    }

    public void setList(List<FileVo> list) {
        this.list = list;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setOrdernumber(String ordernumber)
    {
        this.ordernumber = ordernumber;
    }

    public String getOrdernumber()
    {
        return ordernumber;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setClientTjr(String clientTjr)
    {
        this.clientTjr = clientTjr;
    }

    public String getClientTjr()
    {
        return clientTjr;
    }
    public void setClientTjrId(String clientTjrId)
    {
        this.clientTjrId = clientTjrId;
    }

    public String getClientTjrId()
    {
        return clientTjrId;
    }
    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getDepartment()
    {
        return department;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("type", getType())
                .append("ordernumber", getOrdernumber())
                .append("name", getName())
                .append("url", getUrl())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .append("clientTjr", getClientTjr())
                .append("clientTjrId", getClientTjrId())
                .append("department", getDepartment())
                .toString();
    }
}
