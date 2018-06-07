package com.bdtd.card.registration.modular.order.service.impl;

import com.bdtd.card.registration.modular.system.model.Order;
import com.bdtd.card.registration.modular.system.dao.OrderMapper;
import com.bdtd.card.registration.modular.order.service.IOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
