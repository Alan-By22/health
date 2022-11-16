package com;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/16---14:50
 * @描述信息
 */

/**
 * 1.任务2.触发器3.调度中心
 * @author 11752
 */
public class App {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-jobs.xml");
    }
}
