package org.hunter.demo.service;

import org.hunter.demo.model.Order;
import org.hunter.skeleton.controller.FilterView;
import org.hunter.skeleton.service.PageList;
import org.hunter.skeleton.service.Service;

import java.sql.SQLException;

/**
 * @author wujianchuan
 */
public interface OrderService extends Service {

    int save(Order order) throws SQLException, IllegalAccessException;

    int update(Order order) throws SQLException, IllegalAccessException;

    Order findOne(String uuid) throws SQLException;

    PageList loadPageList(FilterView filterView) throws SQLException;
}
