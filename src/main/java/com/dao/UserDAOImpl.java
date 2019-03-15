package com.dao;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.UserInformation;
import com.bean.UserMenu;
import com.util.MessageBean;
@Transactional
@Repository

public class UserDAOImpl {
	@PersistenceContext	
	private EntityManager entityManager; 
	private static int newUser = 3;
	public User findByUser(String username) {
			// TODO Auto-generated method stub
		return null;
	}
		
	public MessageBean saveCartInfo(String productIDs, HttpSession session){
		MessageBean resultMessage = new MessageBean();
		String message = "";
		int statusCode;
		String code;
		resultMessage.setStatusCode(200);
		Query query = entityManager.createQuery("UPDATE UserInformation set cartinfo = :productIDs"+
		" where username = :name and status = :status");
		query.setParameter("productIDs", productIDs);
		query.setParameter("status", "active");
		query.setParameter("name", session.getAttribute("userName"));
		int result = query.executeUpdate();
		if(result > 0) {
			message = "Success";
			statusCode = 200;
			code = "save_cart_info_200";
		}
		else {
			message = "Record Not Found";
			statusCode = 404;
			code = "save_cart_info_404";
		}
		resultMessage.setStatusMessage(message);
		resultMessage.setStatusCode(statusCode);
		resultMessage.setMessageCode(code);
		
	    return resultMessage;  
	} 
	
	@SuppressWarnings("unchecked")
	public MessageBean getSystemInfo(Map<String,String> systemMessage,List<UserMenu> systemMenu){

		MessageBean resultMessage = new MessageBean();
		List<UserMenu> messageList = entityManager.createQuery("from UserMenu u where u.type = :type")
				.setParameter("type", "message")
				.getResultList();
		
		for(UserMenu msg : messageList) {
			systemMessage.put(msg.getMenuname(),msg.getMessage());
		}
		
		systemMenu = entityManager.createQuery("from UserMenu u where u.type = :type")
				.setParameter("type", "menu")
				.getResultList();
		UserInformation menuInfo = new UserInformation();
		menuInfo.setMenu(systemMenu);
		 resultMessage.setUserinfo(menuInfo);
		 resultMessage.setStatusMessage("success");
		 resultMessage.setStatusCode(200);
		 
		return resultMessage;
	}
	
	public MessageBean validateUser(String name, String pwd){
		MessageBean resultMessage = new MessageBean();
		String message = "";
		int statusCode;
		List<UserInformation> validUser = searchUserByName(name);
		int result = validUser.size();
		if(result > 0) {
			String dbpwd = validUser.get(0).getPwd();
			if( dbpwd.equals(pwd)) {
				message = "Success";
				statusCode = 200;
			}else {
				message = "Password does not matched. Please check the password.";
				statusCode = 505;
			}
		}else {
			message = "Sorry, could not able to find username. Please verify.";
			statusCode = 505;
		}
		
		resultMessage.setStatusMessage(message);
		resultMessage.setStatusCode(statusCode);
		
        return resultMessage;  
	}
	
	@SuppressWarnings("unchecked")
	public List<UserInformation> searchUserByName(String name){
		Query query = entityManager.createQuery("from UserInformation where username = :name and status = :status");
		query.setParameter("name", name);
		query.setParameter("status", "active");
		return query.getResultList();
	}
	
	public MessageBean registerUser(String name, String pwd){
		MessageBean resultMessage = new MessageBean();
		UserInformation newUser = new UserInformation();
		
		newUser.setUserid(UserDAOImpl.newUser);
		newUser.setPwd(pwd);
		newUser.setUsername(name);
		newUser.setStatus("active");
		UserDAOImpl.newUser++;
		entityManager.persist(newUser);
		resultMessage.setStatusMessage("success");
		resultMessage.setStatusCode(200);
		return resultMessage;
	}
	
}
