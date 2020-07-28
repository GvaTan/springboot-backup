/**
 * 
 */
package top.anets.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import top.anets.domain.Payinfo;

/**
 * @author Administrator
 *
 */
@Mapper
public interface PayInfoDao {
	List<Payinfo> getAllPayinfos();
	long insert(Payinfo payinfo);
	/**
	 * 
	 *@param orderId
	 *@return 
	 */
	List<Payinfo> selectByOrderId(String orderId);
	/**
	 * 
	 *@param payinfo 
	 */
	void update(Payinfo payinfo);
}
