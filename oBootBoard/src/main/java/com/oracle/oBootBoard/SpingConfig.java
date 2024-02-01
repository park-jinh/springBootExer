package com.oracle.oBootBoard;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dao.JdbcDao;

@Configuration
public class SpingConfig {
	private final DataSource dataSource;
	public SpingConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Bean
	public BDao jdbcDao() {
		return new JdbcDao(dataSource);
//		return new MemoryMemberRepository();
	}
	
}
