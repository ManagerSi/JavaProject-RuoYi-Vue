package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 日历对象 tbl_calendar
 * 
 * @author ruoyi
 * @date 2022-06-11
 */
public class TblCalendar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

    /** 类型: 1工作日, 2节假日, 3加班, 4调休 */
    @Excel(name = "类型: 1工作日, 2节假日, 3加班, 4调休")
    private Integer calendartype;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer isdelete;

    @Excel(name = "创建时间")
    private Date createtime;

    @Excel(name = "修改时间")
    private Date updatetime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDate(Date date) 
    {
        this.date = date;
    }

    public Date getDate() 
    {
        return date;
    }
    public void setCalendartype(Integer calendartype) 
    {
        this.calendartype = calendartype;
    }

    public Integer getCalendartype() 
    {
        return calendartype;
    }
    public void setIsdelete(Integer isdelete) 
    {
        this.isdelete = isdelete;
    }

    public Integer getIsdelete() 
    {
        return isdelete;
    }

    public Date getCreatetime()
    {
        return createtime;
    }

    public Date getUpdatetime()
    {
        return updatetime;
    }
    public void setUpdatetime(Date updatetime)
    {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("date", getDate())
            .append("calendartype", getCalendartype())
            .append("isdelete", getIsdelete())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
