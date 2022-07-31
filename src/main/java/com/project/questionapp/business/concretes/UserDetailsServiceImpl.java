package com.project.questionapp.business.concretes;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.project.questionapp.dataAccess.UserDao;
import com.project.questionapp.entities.User;
import com.project.questionapp.security.JwtUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserDao userDao;
	
	@Autowired
	public UserDetailsServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userDao.getByUserName(username);
		return JwtUserDetails.create(user);
	}

	public UserDetails loadUserById(int id) {
		User user = this.userDao.getById(id);
		return JwtUserDetails.create(user);
		
	}
}
