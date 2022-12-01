package com.ithuipu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ithuipu.constant.MessageConstant;
import com.ithuipu.entity.Result;
import com.ithuipu.service.MemberService;
import com.ithuipu.service.ReportService;
import com.ithuipu.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    /**
     * 路径
     * report/getMemberReport.do
     */
    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;

    /**
     * 会员的数量统计
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        //获得当前日期之前12个月的日期
        LocalDate localDate = LocalDate.now().plusMonths(-12);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            LocalDate localDate1 = localDate.plusMonths(i);
            list.add(localDate1.format(DateTimeFormatter.ofPattern("yyyy-MM")));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("months", list);

        List<Integer> memberCount = memberService.findMemberCountByMonth(list);
        map.put("memberCount", memberCount);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);

    }
}
