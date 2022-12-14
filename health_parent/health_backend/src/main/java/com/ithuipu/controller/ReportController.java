package com.ithuipu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ithuipu.constant.MessageConstant;
import com.ithuipu.entity.Result;
import com.ithuipu.service.MemberService;
import com.ithuipu.service.ReportService;
import com.ithuipu.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/12/1---17:05
 * @描述信息
 */

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;

    /**
     * 路径
     * report/getMemberReport.do
     */
    /**
     * 会员的数量统计
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        //获得当前日期之前12个月的日期
        LocalDate localDate = LocalDate.now().plusMonths(-12);

        //遍历---日期格式
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            LocalDate localDate1 = localDate.plusMonths(i);
            list.add(localDate1.format(DateTimeFormatter.ofPattern("yyyy.MM")));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("months", list);

        //会员数
        List<Integer> memberCount = memberService.findMemberCountByMonth(list);
        map.put("memberCount", memberCount);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }


    /**路径
     * report/getSetmealReport.do
     * */
    /**
     * 套餐数量的统计
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        List<Map<String, Object>> list = setmealService.findSetmealCount();

        Map<String, Object> map = new HashMap<>();
        map.put("setmealCount", list);

        List<String> setmealNames = new ArrayList<>();
        for (Map<String, Object> m : list) {
            String name = (String) m.get("name");
            setmealNames.add(name);
        }

        map.put("setmealNames", setmealNames);

        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
    }

    /**路径report/getBusinessReportData.do*/
    /**
     * 获取运营统计数据
     */
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportService.getBusinessReport();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * 路径/report/exportBusinessReport.do
     * */
    /**
     * 导出Excel报表
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            //远程调用报表服务获取报表数据
            Map<String, Object> result = reportService.getBusinessReport();

            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //获得Excel模板文件绝对路径
            String temlateRealPath = request.getSession().getServletContext().getRealPath("template") +
                    File.separator + "report_template.xlsx";

            //读取模板文件创建Excel表格对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(temlateRealPath)));
            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFRow row = sheet.getRow(2);
            //日期
            row.getCell(5).setCellValue(reportDate);

            row = sheet.getRow(4);
            //新增会员数（本日）
            row.getCell(5).setCellValue(todayNewMember);
            //总会员数
            row.getCell(7).setCellValue(totalMember);

            row = sheet.getRow(5);
            //本周新增会员数
            row.getCell(5).setCellValue(thisWeekNewMember);
            //本月新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);

            row = sheet.getRow(7);
            //今日预约数
            row.getCell(5).setCellValue(todayOrderNumber);
            //今日到诊数
            row.getCell(7).setCellValue(todayVisitsNumber);

            row = sheet.getRow(8);
            //本周预约数
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            //本周到诊数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);

            row = sheet.getRow(9);
            //本月预约数
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            //本月到诊数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

            int rowNum = 12;
            //热门套餐
            for (Map map : hotSetmeal) {
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                //套餐名称
                row.getCell(4).setCellValue(name);
                //预约数量
                row.getCell(5).setCellValue(setmeal_count);
                //占比
                row.getCell(6).setCellValue(proportion.doubleValue());
            }

            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();

            return null;
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
        }
    }
}
