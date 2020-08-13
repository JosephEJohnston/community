package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface _UserMapper {

    @Insert("INSERT INTO user (name, account_id, token, gmt_create, gmt_modified, avatar_url) " +
            "VALUES (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    // 等号前映射表列，等号后为对象属性
    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}" +
            ", avatar_url = #{avatarUrl} where id = #{id} ")
    void update(User user);
}
