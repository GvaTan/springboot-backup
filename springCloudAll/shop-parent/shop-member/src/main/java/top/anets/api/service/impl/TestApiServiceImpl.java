/**
 * 
 */
package top.anets.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.anets.api.service.TestApiService;

/**
 * @author Administrator
 *
 */
@RestController
public class TestApiServiceImpl implements TestApiService{
    
	public Map<String, Object> test(Integer id, String name) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("msg","测试成功");
		return map;
	}

}