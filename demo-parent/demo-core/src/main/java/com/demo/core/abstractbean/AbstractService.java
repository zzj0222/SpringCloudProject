package com.demo.core.abstractbean;

import com.demo.core.enums.ResultCode;
import com.demo.core.exception.BusinessException;
import com.demo.core.util.ParameterUtils;
import com.demo.core.util.ReflectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

	@Autowired
	protected Mapper<T> mapper;

	/*
	当前泛型真实类型的Class
	 */
	private Class<T> modelClass;


	public AbstractService() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		modelClass = (Class<T>) pt.getActualTypeArguments()[0];
	}

	@Override
	public void save(T model) {
		Date createTime = new Date();
		ReflectUtils.setFieldValue(model, "gmtCreate", createTime);
		ReflectUtils.setFieldValue(model, "gmtModified", createTime);
		Object creatorId = ReflectUtils.getFieldValue(model,"creatorId");
		if(null==creatorId){
			ReflectUtils.setFieldValue(model, "creatorId", ParameterUtils.getCurrentUserId());
		}
		Object modifierId = ReflectUtils.getFieldValue(model,"modifierId");
		if(null==modifierId){
			ReflectUtils.setFieldValue(model, "modifierId", ParameterUtils.getCurrentUserId());
		}
		mapper.insertSelective(model);
	}

	@Override
	public void save(List<T> models) {
		if (null != models && models.size() > 0) {
			Date createTime = new Date();
			Integer currentUserId = ParameterUtils.getCurrentUserId();
			for (T model : models) {
				ReflectUtils.setFieldValue(model, "gmtCreate", createTime);
				ReflectUtils.setFieldValue(model, "gmtModified", createTime);
				ReflectUtils.setFieldValue(model, "creatorId", currentUserId);
				ReflectUtils.setFieldValue(model, "modifierId", currentUserId);
			}
			mapper.insertList(models);
		}else{
			//models为null，不处理；
		}
	}

	@Override
	public void deleteById(Object id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(String ids) {
		mapper.deleteByIds(ids);
	}

	@Override
	public void update(T model) {
		ReflectUtils.setFieldValue(model, "gmtModified", new Date());
		Object modifierId = ReflectUtils.getFieldValue(model,"modifierId");
		if(null==modifierId){
			ReflectUtils.setFieldValue(model, "modifierId", ParameterUtils.getCurrentUserId());
		}
		mapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public T findById(Object id) {
		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * @author adjiang 根据条件删除
	 * @param fieldName
	 * @param value
	 */
	@Override
	public void deleteBy(String fieldName, Object value) {
			Condition condition = new Condition(modelClass);
			Condition.Criteria criteria = condition.createCriteria();
			criteria.andEqualTo(fieldName, value);
			mapper.deleteByCondition(condition);
	}

	@Override
	public T findBy(String fieldName, Object value){
		T model = null;
		try {
			model = modelClass.newInstance();
			Field field = modelClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(model, value);
		} catch (Exception e) {
			throw new BusinessException(ResultCode.ERROR.getCode(),e.getMessage());
		}
		return mapper.selectOne(model);
	}

	@Override
	public T findModel(T model){
		return mapper.selectOne(model);
	}

	@Override
	public List<T> findByIds(String ids) {
		return mapper.selectByIds(ids);
	}

	@Override
	public List<T> findByCondition(Condition condition) {
		return mapper.selectByCondition(condition);
	}

	@Override
	public List<T> findList(T obj){
		Class cls = obj.getClass();
		Condition condition = new Condition(cls);
		Condition.Criteria criteria = condition.createCriteria();
		Field[] fields = cls.getDeclaredFields();
        String orderFiledName = "";
		for(int i=0; i<fields.length; i++){
			Field f = fields[i];
			f.setAccessible(true);
			try {
				String filedName = f.getName();
				Object value= f.get(obj);
				if(null!=value&&!"".equals(value)){
					criteria.andEqualTo(filedName, value);
				}
                if("gmtModified".equals(filedName)){
                    if(!"".equals(orderFiledName)){
                        orderFiledName = new StringBuilder(orderFiledName).append(",gmt_modified desc").toString();
                    }else{
                        orderFiledName  = "gmt_modified desc";
                    }
                }else if("gmtCreate".equals(filedName)){
                    if(!"".equals(orderFiledName)){
                        orderFiledName = new StringBuilder(orderFiledName).append(",gmt_create desc").toString();
                    }else{
                        orderFiledName  = "gmt_create desc";
                    }

                }


            }catch (Exception e){
			}
		}
        if(!"".equals(orderFiledName)){
            condition.setOrderByClause(orderFiledName);
        }

        return mapper.selectByCondition(condition);
	}

	@Override
	public List<T> findAll() {
		return mapper.selectAll();
	}
}
