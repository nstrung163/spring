package com.learn.springbatch.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.learn.springbatch.entity.User;
import com.learn.springbatch.repository.UserRepository;

@Component
public class DBWriter implements ItemWriter<User>{

	private UserRepository userRepository;

	@Autowired
	public DBWriter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 7: Save data to database
	@Override
	public void write(List<? extends User> users) throws Exception {
		System.out.println("Data saved for Users:" + users.toString());
		userRepository.saveAll(users);
	}
	
}
