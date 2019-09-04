package org.hunter.skeleton.repository;

import org.hunter.pocket.criteria.Criteria;
import org.hunter.pocket.model.BaseEntity;
import org.hunter.skeleton.annotation.Track;
import org.hunter.skeleton.constant.OperateEnum;
import org.hunter.skeleton.controller.FilterView;
import org.hunter.skeleton.service.PageList;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wujianchuan
 */
public abstract class AbstractCommonRepository<T extends BaseEntity> extends AbstractRepository{
    private Class clazz;

    public AbstractCommonRepository() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.clazz = (Class) params[0];
    }

    public Object findOne(Serializable uuid) throws SQLException {
        return super.getSession().findDirect(this.clazz, uuid);
    }

    public int save(T obj, boolean cascade) throws SQLException {
        return super.getSession().save(obj);
    }

    public int update(T obj, boolean cascade) throws SQLException {
        return super.getSession().update(obj);
    }

    public int delete(T obj) throws SQLException, IllegalAccessException {
        return super.getSession().delete(obj);
    }

    @Track(data = "#obj", operateName = "#trackDescription", operator = "#trackOperator", operate = OperateEnum.ADD)
    public int saveWithTrack(T obj, boolean cascade, String trackOperator, String trackDescription) throws SQLException {
        return this.save(obj, cascade);
    }

    @Track(data = "#obj", operateName = "#trackDescription", operator = "#trackOperator", operate = OperateEnum.EDIT)
    public int updateWithTrack(T obj, boolean cascade, String trackOperator, String trackDescription) throws SQLException {
        return this.update(obj, cascade);
    }

    @Track(data = "#obj", operateName = "#trackDescription", operator = "#trackOperator", operate = OperateEnum.DELETE)
    public int deleteWithTrack(T obj, String operator, String trackDescription) throws SQLException, IllegalAccessException {
        return this.delete(obj);
    }

    public PageList loadPage(FilterView filterView) throws SQLException {
        Criteria criteria = filterView.createCriteria(this.getSession(), clazz);
        List list = criteria.listNotCleanRestrictions();
        return PageList.newInstance(list, criteria.count());
    }
}
