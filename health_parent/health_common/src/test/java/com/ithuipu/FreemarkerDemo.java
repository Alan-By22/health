package com.ithuipu;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/21---17:24
 * @描述信息
 */

public class FreemarkerDemo {

    @Test
    public void demo1() throws IOException, TemplateException {

        //1.需要模板
        //2.需要数据
        //3.生成静态页面
        //1.配置
        Configuration configuration = new Configuration(Configuration.getVersion());
        //2.模板所在路径
        configuration.setDirectoryForTemplateLoading(new File("D:\\ftl"));
        //3.设置字符集
        configuration.setDefaultEncoding("utf-8");
        //4.加载模板
        Template template = configuration.getTemplate("test.ftl");
        //5.创建数据模型
        //Map map = new HashMap();
        //map.put("name","李四");
        User user = new User("王五", 18);
        //6.创建Writer对象
        //FileWriter writer = new FileWriter(new File("D:\\ftl\\test.html"));
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File("D:\\ftl\\test.html")), "utf-8");
        //7.
        template.process(user, writer);
        //8.关流
        writer.close();
    }
}
