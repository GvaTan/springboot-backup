package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(check = false)
	private DubboCartService cartService;
	
	
	/**
	 * 业务需求:展现购物车列表页面
	 * url: http://www.jt.com/cart/show.html
	 * 参数:无
	 * 返回值: cart.jsp页面
	 * 页面取值: ${cartList}
	 */
	@RequestMapping("/show")
	public String findCartList(Model model,HttpServletRequest request) {
		User user = (User) request.getAttribute("JT_USER");
		Long userId = user.getId();
		List<Cart> cartList = 
				cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	
	/**
	 * 购物车新增
	 * 1.url:http://www.jt.com/cart/add/562379.html
	 * 2.参数:  form表单的形式提交   Cart对象接收
	 * 3.返回值: 重定向到购物车列表页面
	 * 注意事项:如果购物车中有记录,则更新数量.
	 */
	@RequestMapping("/add/{itemId}") //可以自动的为属性赋值
	public String saveCart(Cart cart,HttpServletRequest request) {
		
		 User user = (User) request.getAttribute("JT_USER");
		 Long userId = user.getId();
		 cart.setUserId(userId);
		 cartService.saveCart(cart);
		 return "redirect:/cart/show.html";
	 }
	
	
	 /**
	  * 购物车删除操作
	  * 1.url地址:http://www.jt.com/cart/delete/562379.html
	  * 2.参数:  562379 商品id号
	  * 3.返回值: 重定向到购物车列表页面
	  */
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart,HttpServletRequest request) {
		User user = (User) request.getAttribute("JT_USER");
		Long userId = user.getId();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	
	
	/**
	 * 购物车数量的修改
	 * 1.url地址:http://www.jt.com/cart/update/num/562379/12
	 * 2.参数 562379ItemId,  12 表示购买数量
	 * 3.返回值:操作成功之后利用SySresult对象返回
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody	//让ajax正确返回
	public SysResult updateCartNum(Cart cart,HttpServletRequest request) {
		User user = (User) request.getAttribute("JT_USER");
		Long userId = user.getId();
		cart.setUserId(userId);
		
		cartService.updateCartNum(cart);
		return SysResult.success();
	}
	
	
	
	
	
	
	
	
}
