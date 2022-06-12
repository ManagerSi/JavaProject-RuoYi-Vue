package com.ruoyi.system.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TblCalendarMapper;
import com.ruoyi.system.domain.TblCalendar;
import com.ruoyi.system.service.ITblCalendarService;

/**
 * 日历Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-11
 */
@Service
public class TblCalendarServiceImpl implements ITblCalendarService 
{
    @Autowired
    private TblCalendarMapper tblCalendarMapper;

    /**
     * 查询日历
     * 
     * @param id 日历主键
     * @return 日历
     */
    @Override
    public TblCalendar selectTblCalendarById(Long id)
    {
        return tblCalendarMapper.selectTblCalendarById(id);
    }

    /**
     * 查询日历列表
     * 
     * @param tblCalendar 日历
     * @return 日历
     */
    @Override
    public List<TblCalendar> selectTblCalendarList(TblCalendar tblCalendar)
    {
        if(tblCalendar == null || tblCalendar.getDate() == null)
            return tblCalendarMapper.selectTblCalendarList(null);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return tblCalendarMapper.selectTblCalendarList(dateFormat.format(tblCalendar.getDate()));
    }

    /**
     * 新增日历
     * 
     * @param tblCalendar 日历
     * @return 结果
     */
    @Override
    public int insertTblCalendar(TblCalendar tblCalendar)
    {
        return tblCalendarMapper.insertTblCalendar(tblCalendar);
    }

    /**
     * 修改日历
     * 
     * @param tblCalendar 日历
     * @return 结果
     */
    @Override
    public int updateTblCalendar(TblCalendar tblCalendar)
    {
        return tblCalendarMapper.updateTblCalendar(tblCalendar);
    }

    /**
     * 批量删除日历
     * 
     * @param ids 需要删除的日历主键
     * @return 结果
     */
    @Override
    public int deleteTblCalendarByIds(Long[] ids)
    {
        return tblCalendarMapper.deleteTblCalendarByIds(ids);
    }

    /**
     * 删除日历信息
     * 
     * @param id 日历主键
     * @return 结果
     */
    @Override
    public int deleteTblCalendarById(Long id)
    {
        return tblCalendarMapper.deleteTblCalendarById(id);
    }
}
