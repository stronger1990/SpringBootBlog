package com.wip.controller.admin;

import com.wip.constant.LogActions;
import com.wip.constant.WebConst;
import com.wip.controller.BaseController;
import com.wip.exception.BusinessException;
import com.wip.model.UserDomain;
import com.wip.service.log.LogService;
import com.wip.service.user.UserService;
import com.wip.utils.APIResponse;
import com.wip.utils.GsonUtils;
import com.wip.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Api("登录相关接口")
@Controller
@RequestMapping("/admin")
public class AuthController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	@ApiOperation("跳转登录页")
	@GetMapping(value = "/login")
	public String login() {
		return "admin/login";
	}

	@ApiOperation("登录")
	@PostMapping(value = "/login")
	@ResponseBody
	public APIResponse toLogin(HttpServletRequest request, HttpServletResponse response, @ApiParam(name = "username", value = "用户名", required = true) @RequestParam(name = "username", required = true) String username, @ApiParam(name = "password", value = "用户名", required = true) @RequestParam(name = "password", required = true) String password, @ApiParam(name = "remember_me", value = "记住我", required = false) @RequestParam(name = "remember_me", required = false) String remember_me) {
		// 直接获取缓存的login_error_count，判断错误超过3次则提示登录失败，但是这样直接取key-value不太好吧，如果一个人登录3次出错的时候另一个人登录一次岂不是就会提示登录出错提示？？？
		// 非严格意义上的每个人都有超过三次错误登录的机会。
		// 对于只有公司的人才会用，不会很多人用的前提下，错误机率不高，或者说不会出现错误，
		// 但是多人用的时候，登录错误次数会累计，这样会有一定概率出现恰好A错误三次，B错误一次就对B提示登录出错的提示了
		Integer error_count = cache.get("login_error_count");
		try {
			// 调用Service登录方法
			UserDomain userInfo = userService.login(username, password);
			// 如果是APP，一般是返回token用于判断身份，详情可看项目springboot-jwt-master
			// 如果是浏览器访问，一般通过设置用户信息session，然后每次从session取信息，有则表示登录了
			request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, userInfo);
			// 判断是否勾选记住我
			if (StringUtils.isNotBlank(remember_me)) {
				TaleUtils.setCookie(response, userInfo.getUid());
			}
			// 写入日志
			logService.addLog(LogActions.LOGIN.getAction(), userInfo.getUsername() + "用户", request.getRemoteAddr(), userInfo.getUid());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			error_count = null == error_count ? 1 : error_count + 1;
			if (error_count > 3) {
				return APIResponse.fail("您输入密码已经错误超过3次，请10分钟后尝试");
			}
			System.out.println(error_count);
			// 设置缓存为10分钟
			cache.set("login_error_count", error_count, 10 * 60);
			String msg = "登录失败";
			if (e instanceof BusinessException) {
				msg = e.getMessage();
			} else {
				LOGGER.error(msg, e);
			}
			return APIResponse.fail(msg);
		}
		// 返回登录成功信息
		return APIResponse.success();
	}

	@RequestMapping(value = "/logout")
	public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		// 移除session
		session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
		// 设置cookie值和时间为空
		Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, "");
		cookie.setValue(null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		try {
			// 跳转到登录页面
			response.sendRedirect("/admin/login");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("注销失败", e);
		}
	}

}
