package com.dudu.dao;

import com.dudu.domain.LearnResouce;
import com.dudu.tools.StringUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by tengj on 2017/4/22.
 * Component注解不添加也没事，只是不加service那边引入LearnMapper会有错误提示，但不影响
 */
@Mapper //声明为一个Mapper接口
public interface LearnMapper {

    @Insert("insert into learn_resource(author, title,url) values(#{author},#{title},#{url})")
    int add(LearnResouce learnResouce);

    @Update("update learn_resource set author=#{author},title=#{title},url=#{url} where id = #{id}")
    int update(LearnResouce learnResouce);

    @DeleteProvider(type = LearnSqlBuilder.class, method = "deleteByids")
    int deleteByIds(@Param("ids") String[] ids);


    /**
     * @param id
     * @return
     * @Results是结果映射列表，
     * @Result中property是domain类的属性名， colomn 是数据库表的字段名
     */
    @Select("select * from learn_resource where id = #{id}")
    @Results(id = "learnMap", value = {
            @Result(column = "id", property = "id", javaType = Long.class),
            @Result(property = "author", column = "author", javaType = String.class),
            @Result(property = "title", column = "title", javaType = String.class)
    })
    LearnResouce queryLearnResouceById(@Param("id") Long id);


    /**
     *
     * @SelectProvider注解用于生成查询用的sql语句，type 参数指定的Class类，必须要能够通过无参的构造函数来初始化。
     * 有别于@Select注解，@SelectProvide指定一个Class及其方法，并且通过调用Class上的这个方法来获得sql语句。
     *
     *
     * 在超过一个参数的情况下，@SelectProvide方法必须接受Map<String, Object>做为参数，
     * 如果参数使用了@Param注解，那么参数在Map中以@Param的值为key，也可以方法中使用 @Param 获取key 的值
     * 如果参数没有使用@Param注解，那么参数在Map中以参数的顺序为key，
     *
     * @param params
     * @return
     */
    @SelectProvider(type = LearnSqlBuilder.class, method = "queryLearnResouceByParams")
    List<LearnResouce> queryLearnResouceList(Map<String, Object> params);

    class LearnSqlBuilder {
        public String queryLearnResouceByParams(final Map<String, Object> params) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from learn_resource where 1=1");

            if (!StringUtil.isNull((String) params.get("author"))) {
                sql.append(" and author like '%").append((String) params.get("author")).append("%'");
            }
            if (!StringUtil.isNull((String) params.get("title"))) {
                sql.append(" and title like '%").append((String) params.get("title")).append("%'");
            }
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        //删除的方法
        public String deleteByids(@Param("ids") final String[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM learn_resource WHERE id in(");
            for (int i = 0; i < ids.length; i++) {
                if (i == ids.length - 1) {
                    sql.append(ids[i]);
                } else {
                    sql.append(ids[i]).append(",");
                }
            }
            sql.append(")");
            return sql.toString();
        }
    }
}
