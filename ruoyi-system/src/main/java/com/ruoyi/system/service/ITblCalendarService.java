package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TblCalendar;

/**
 * 日历Service接口
 * 
 * @author ruoyi
 * @date 2022-06-11
 */
public interface ITblCalendarService 
{
    /**
     * 查询日历
     * 
     * @param id 日历主键
     * @return 日历
     */
    public TblCalendar selectTblCalendarById(Long id);

    /**
     * 查询日历列表
     * 
     * @param tblCalendar 日历
     * @return 日历集合
     */
    public List<TblCalendar> selectTblCalendarList(TblCalendar tblCalendar);

    /**
     * 新增日历
     * 
     * @param tblCalendar 日历
     * @return 结果
     */
    public int insertTblCalendar(TblCalendar tblCalendar);

    /**
     * 修改日历
     * 
     * @param tblCalendar 日历
     * @return 结果
     */
    public int updateTblCalendar(TblCalendar tblCalendar);

    /**
     * 批量删除日历
     * 
     * @param ids 需要删除的日历主键集合
     * @return 结果
     */
    public int deleteTblCalendarByIds(Long[] ids);

    /**
     * 删除日历信息
     * 
     * @param id 日历主键
     * @return 结果
     */
    public int deleteTblCalendarById(Long id);
}
