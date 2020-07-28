/**
 * 
 */
package top.anets.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import top.anets.api.service.MemberService;

/**
 * @author Administrator
 *
 */
@Component
@FeignClient("member-service")
public interface MemberFeign extends MemberService{

}
