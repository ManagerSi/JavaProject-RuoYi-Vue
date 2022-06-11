package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.TblCalendar;
import com.ruoyi.system.service.ITblCalendarService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 日历Controller
 * 
 * @author ruoyi
 * @date 2022-06-11
 */
@RestController
@RequestMapping("/system/calendar")
public class TblCalendarController extends BaseController
{
    @Autowired
    private ITblCalendarService tblCalendarService;

    /**
     * 查询日历列表
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:list')")
    @GetMapping("/list")
    public TableDataInfo list(TblCalendar tblCalendar)
    {
        startPage();
        List<TblCalendar> list = tblCalendarService.selectTblCalendarList(tblCalendar);
        return getDataTable(list);
    }

    /**
     * 导出日历列表
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:export')")
    @Log(title = "日历", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TblCalendar tblCalendar)
    {
        List<TblCalendar> list = tblCalendarService.selectTblCalendarList(tblCalendar);
        ExcelUtil<TblCalendar> util = new ExcelUtil<TblCalendar>(TblCalendar.class);
        util.exportExcel(response, list, "日历数据");
    }

    /**
     * 获取日历详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tblCalendarService.selectTblCalendarById(id));
    }

    /**
     * 新增日历
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:add')")
    @Log(title = "日历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TblCalendar tblCalendar)
    {
        return toAjax(tblCalendarService.insertTblCalendar(tblCalendar));
    }

    /**
     * 修改日历
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:edit')")
    @Log(title = "日历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TblCalendar tblCalendar)
    {
        return toAjax(tblCalendarService.updateTblCalendar(tblCalendar));
    }

    /**
     * 删除日历
     */
    @PreAuthorize("@ss.hasPermi('system:calendar:remove')")
    @Log(title = "日历", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tblCalendarService.deleteTblCalendarByIds(ids));
    }
}
