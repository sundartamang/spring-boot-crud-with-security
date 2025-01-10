package com.blog;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role normalUser = new Role();
			normalUser.setId(AppConstants.NORMAL_USER);
			normalUser.setName("NORMAL");

			Role adminUser = new Role();
			adminUser.setId(AppConstants.ADMIN_ROLE);
			adminUser.setName("ADMIN");

			List<Role> roles = List.of(normalUser, adminUser);
			List<Role> result = roleRepo.saveAll(roles);

			result.forEach((r) -> {
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
