package com.ithuipu.dao;

import com.alibaba.dubbo.config.annotation.Service;
import com.ithuipu.entity.Result;
import com.ithuipu.service.OrderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/23---23:35
 * @描述信息 预约的服务
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Override
    public Result order(Map map) {
        return null;
    }
}
