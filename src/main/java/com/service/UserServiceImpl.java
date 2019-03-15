package com.service;

import com.bean.UserInformation;
import com.bean.UserMenu;
import com.dao.UserDAOImpl;
import com.util.MessageBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserDetailsService, SecurityService, UserService{
    @Autowired
    private UserDAOImpl userDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println(username);
        User user = userDao.findByUser(username);
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		/*
		 * for (Role role : user.getRoles()){ grantedAuthorities.add(new
		 * SimpleGrantedAuthority(role.getName())); }
		 */

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

	@Override
	public String findLoggedInUsername() {
	  	System.out.println("5");
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }

		return null;
	}

	@Override
	public void autoLogin(String username, String password) {
		System.out.println("113");
		UserDetails userDetails = this.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
	}

	@Override
	public void save(UserInformation user) {
		// TODO Auto-generated method stub
		System.out.println("3");
		 user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
	  	System.out.println("6");
		return userDao.findByUser(username);
	}
	
	public MessageBean saveCartInfo(String productIDs, HttpSession session){
			MessageBean message = userDao.saveCartInfo(productIDs, session);
	        return message;  
	 } 
	
	public MessageBean getSystemInfo(Map<String,String> systemMessage,List<UserMenu> systemMenu){
		MessageBean resultMessage = userDao.getSystemInfo(systemMessage, systemMenu);
		if(systemMessage.size() == 0) {
			 System.out.println("error");
			 resultMessage.setMessageCode("ERROR_LOADING_SYSTEM_MESSAGE");
		 }
		 systemMenu= resultMessage.getUserinfo().getMenu();
		 if(systemMenu.size() == 0) {
			 System.out.println("error  menu");
			 resultMessage.setMessageCode("ERROR_LOADING_SYSTEM_MENU");
			 resultMessage.setStatusMessage(systemMessage.get(resultMessage.getMessageCode()));
		 }
		 
		 UserInformation loginInfo = new UserInformation();
		 loginInfo.setMenu(systemMenu);
		 resultMessage.setUserinfo(loginInfo);
        return resultMessage;  
	}  
	
	public MessageBean validateUser(String name, String pwd){
		MessageBean resultMessage = userDao.validateUser(name, pwd);
        return resultMessage;  
	}
	
	public MessageBean registerUser(String name, String pwd){
		MessageBean resultMessage = new MessageBean();
		List<UserInformation> userInfo = userDao.searchUserByName(name);
		if(userInfo.size() > 0) {
			resultMessage.setStatusMessage("User name already in use. Please choose another username");
			resultMessage.setStatusCode(304);
			return resultMessage;
		}
		resultMessage = userDao.registerUser(name, pwd);
        return resultMessage;  
	}
	
}