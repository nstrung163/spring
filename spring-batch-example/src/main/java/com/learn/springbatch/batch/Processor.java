package com.learn.springbatch.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.learn.springbatch.entity.User;

@Component
public class Processor implements ItemProcessor<User, User> {
	
	private static final Map<String, String> DEPT_NAMES = new HashMap<String, String>();
	
	// 1: Set value for attribute DEPT_NAMES
	public Processor() {
		DEPT_NAMES.put("001", "Teacher");
		DEPT_NAMES.put("002", "TA");
		DEPT_NAMES.put("003", "Marketing");
		DEPT_NAMES.put("004", "IT");
	}

	// 6: Convert dept from users.csv --> Department name in DEPT_NAMES by getting value from DEPT_NAMES and set it for User
	@Override
	public User process(User user) throws Exception {
		String deptId = user.getDept();
		String deptName = DEPT_NAMES.get(deptId);
		user.setDept(deptName);
		user.setTime(new Date());
		System.out.println(String.format("Converted from [%s] to [%s]", deptId, deptName));
		return user;
	}

}
