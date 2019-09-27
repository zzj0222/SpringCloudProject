package com.demo.core.abstractbean;

import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {

    /**
     * 持久化
     * @param model
     */
    void save(T model);

    /**
     * 批量持久化
     * @param models
     */
    void save(List<T> models);

    /**
     * 通过主鍵刪除
     * @param id
     */
    void deleteById(Object id);

    /**
     * 根据多个主键批量刪除 eg：ids -> “1,2,3,4”
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 通过条件删除
     * @param fieldName
     * @param value
     */
    void deleteBy(String fieldName, Object value);

    /**
     * 更新
     * @param model
     */
    void update(T model);

    /**
     * 根据实体查找，单个对象
     * @param model
     * @return
     */
    T findModel(T model) ;

    /**
     * 通过主键查找
     * @param id
     * @return
     */
    T findById(Object id);

    /**
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     * @param fieldName
     * @param value
     * @return
     */
    T findBy(String fieldName, Object value);

    /**
     * 通过多个ID查找//eg：ids -> “1,2,3,4”
     * @param ids
     * @return
     */
    List<T> findByIds(String ids);

    /**
     * 根据条件查找List
     * @param condition
     * @return
     */
    List<T> findByCondition(Condition condition);

    /**
     * 根据实体类查找List
     * @param model
     * @return
     */
    List<T> findList(T model);

    /**
     * 查询所有
     * @return
     */
    List<T> findAll();
}
