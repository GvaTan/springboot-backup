/**
 * 
 */
package top.anets.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import top.anets.entity.User;

/**
 * @author Administrator
 *
 */
@Mapper
public interface MemberDao {
	@Select("select  id,username,password,phone,email,created,updated from mb_user where id =#{userId}")
	User findByID(@Param("userId") Long userId);

	@Insert("INSERT  INTO `mb_user`  (username,password,phone,email,created,updated) VALUES (#{username}, #{password},#{phone},#{email},#{created},#{updated});")
	Integer insertUser(User userEntity);

}
