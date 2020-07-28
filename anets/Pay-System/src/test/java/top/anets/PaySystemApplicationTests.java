package top.anets;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lly835.bestpay.enums.BestPayTypeEnum;

import top.anets.dao.PayInfoDao;
import top.anets.domain.Payinfo;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PaySystemApplicationTests {
    @Autowired
    private PayInfoDao payInfoDao;
	
    
	@Test
	public void contextLoads() throws SQLException {
		Payinfo payinfo = new Payinfo();
		
		payinfo.setOrderId("334");
		payinfo.setCreateTime(new Date());
		
		long insert = payInfoDao.insert(payinfo);
		System.out.println("插入id为"+payinfo.getId());
	}

}
