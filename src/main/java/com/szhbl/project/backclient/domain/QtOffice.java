package com.szhbl.project.backclient.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.szhbl.framework.aspectj.lang.annotation.Excel;
import com.szhbl.framework.web.domain.BaseEntity;

/**
 * 办事处信息对象 qt_office
 *
 * @author szhbl
 * @date 2020-04-02
 */
public class QtOffice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer officeId;

    /** 办事处 */
    @Excel(name = "办事处")
    private String office;

    /** 英文办事处 */
    @Excel(name = "英文办事处")
    private String enOffice;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contacts;

    /** 英文联系人 */
    @Excel(name = "英文联系人")
    private String enContacts;

    /** 负责区域 */
    @Excel(name = "负责区域")
    private String area;

    /** 英文区域 */
    @Excel(name = "英文区域")
    private String enArea;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String tel;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 语言0中文1英文2俄语3德语 */
    private String language;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    public void setOfficeId(Integer officeId)
    {
        this.officeId = officeId;
    }

    public Integer getOfficeId()
    {
        return officeId;
    }
    public void setOffice(String office)
    {
        this.office = office;
    }

    public String getOffice()
    {
        return office;
    }
    public void setEnOffice(String enOffice)
    {
        this.enOffice = enOffice;
    }

    public String getEnOffice()
    {
        return enOffice;
    }
    public void setContacts(String contacts)
    {
        this.contacts = contacts;
    }

    public String getContacts()
    {
        return contacts;
    }
    public void setEnContacts(String enContacts)
    {
        this.enContacts = enContacts;
    }

    public String getEnContacts()
    {
        return enContacts;
    }
    public void setArea(String area)
    {
        this.area = area;
    }

    public String getArea()
    {
        return area;
    }
    public void setEnArea(String enArea)
    {
        this.enArea = enArea;
    }

    public String getEnArea()
    {
        return enArea;
    }
    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getTel()
    {
        return tel;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getLanguage()
    {
        return language;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("officeId", getOfficeId())
                .append("office", getOffice())
                .append("enOffice", getEnOffice())
                .append("contacts", getContacts())
                .append("enContacts", getEnContacts())
                .append("area", getArea())
                .append("enArea", getEnArea())
                .append("tel", getTel())
                .append("email", getEmail())
                .append("language", getLanguage())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
