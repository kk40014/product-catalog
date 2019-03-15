package com.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bean.UserInformation;
import com.bean.UserMenu;
import com.service.SecurityService;
import com.service.UserService;
import com.service.UserServiceImpl;
import com.util.MessageBean;


@RestController  
public class UserLoginController {
	 @Autowired
	 private UserServiceImpl userService;
	 @Autowired
	 private SecurityService securityService;
	 @Autowired
	 private UserInformation userInfoSession;
	 
	 public static Map<String,String> systemMessage = new HashMap<String,String>();
	 public static List<UserMenu> systemMenu = new ArrayList<UserMenu>();
	 
	 @RequestMapping(value = "/login" , method = { RequestMethod.GET})  
	 @ResponseBody
	 public MessageBean loginUser(Model model, @RequestParam("userName") String name, @RequestParam("pwd") String pwd){  
		 userInfoSession.setUsername(name);
		 userInfoSession.setPwd(pwd);
		 MessageBean validUser = userService.validateUser(name, pwd);
		 if(validUser.getStatusCode() != 200 ) {
			 return validUser;
		 }
		 MessageBean resultMessage = userService.getSystemInfo(systemMessage, systemMenu);
		 UserInformation loginInfo = new UserInformation();
		 
		 return resultMessage;
     } 
	 
	 @RequestMapping(value = "/register" , method = { RequestMethod.GET})  
	 @ResponseBody
	 public MessageBean registerUser(Model model, @RequestParam("userName") String name, @RequestParam("pwd") String pwd){  
		 userInfoSession.setUsername(name);
		 userInfoSession.setPwd(pwd);
		 
		 MessageBean validUser = userService.registerUser(name, pwd);
		 if(validUser.getStatusCode() != 200 ) {
			 return validUser;
		 }
		 MessageBean resultMessage = userService.getSystemInfo(systemMessage, systemMenu);
		 UserInformation loginInfo = new UserInformation();
		 
		 return resultMessage;
     }
	 
	 @RequestMapping(value = "/logout" , method = { RequestMethod.GET})  
	 @ResponseBody
	 public MessageBean logOutUser(Model model){  
		 userInfoSession = new UserInformation();
		 MessageBean validUser = new MessageBean();
		 validUser.setStatusCode(200);
		 return validUser;
     }
	 
	 @RequestMapping(value = "/validateUser" , method = { RequestMethod.GET})  
	 @ResponseBody
	 public MessageBean validateUser(Model model){  
		 MessageBean resultMessage = new MessageBean();
		 String name = userInfoSession.getUsername();
		 String pwd = userInfoSession.getPwd();
		 if(name == null) {
			 resultMessage.setStatusCode(404);
			 return resultMessage; 
		 }
		 MessageBean validUser = userService.validateUser(name, pwd);
		 if(validUser.getStatusCode() != 200 ) {
			 return validUser;
		 }
		 resultMessage = userService.getSystemInfo(systemMessage, systemMenu);
		 systemMenu = resultMessage.getUserinfo().getMenu();
		 UserInformation loginInfo = new UserInformation(); 
		 loginInfo.setPwd(pwd);
		 loginInfo.setUsername(name);
		 loginInfo.setMenu(systemMenu);
		 resultMessage.setUserinfo(loginInfo);
		 return resultMessage;
     }
	 
	 @RequestMapping(value = "/saveCartInfo" , method = { RequestMethod.GET})  
	 @ResponseBody
	 //, @RequestParam("productIDs") String productIDs	
     public MessageBean saveCartInfo(Model model,HttpSession session){
		String productIDs  = "1,2,3";
		MessageBean resultMessage = userService.saveCartInfo(productIDs,session);
		resultMessage.setStatusMessage(systemMessage.get(resultMessage.getMessageCode()));
        return resultMessage;  
     } 
}
