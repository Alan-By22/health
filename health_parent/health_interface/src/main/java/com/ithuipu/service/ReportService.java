package com.ithuipu.service;

import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/12/1---17:15
 * @描述信息
 */

public interface ReportService {
    /**
     * 获得运营统计数据
     * Map数据格式：
     *      todayNewMember -> number
     *      totalMember -> number
     *      thisWeekNewMember -> number
     *      thisMonthNewMember -> number
     *      todayOrderNumber -> number
     *      todayVisitsNumber -> number
     *      thisWeekOrderNumber -> number
     *      thisWeekVisitsNumber -> number
     *      thisMonthOrderNumber -> number
     *      thisMonthVisitsNumber -> number
     *      hotSetmeals -> List<Setmeal>
     */
    Map<String, Object> getBusinessReport() throws Exception;

}
