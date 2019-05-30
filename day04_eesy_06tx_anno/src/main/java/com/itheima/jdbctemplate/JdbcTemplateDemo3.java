package com.itheima.jdbctemplate;

import com.itheima.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * JdbcTemplate的CRUD操作
 */

public class JdbcTemplateDemo3 {
	public static void main(String[] args) {

		// 1.获取容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
		// 2.获取对象
		JdbcTemplate jt = ac.getBean("jdbcTemplate", JdbcTemplate.class);
		// 3.执行操作
		//save
		jt.update("insert into account(name, money)values(?,?)", "eee", 3333f);
		//update
		jt.update("update account set name=?,money=? where id = ?","test", 4567, 7);
		//delete
		jt.update("delete from account where id=?", 8);
		//select all
//		List<Account> accounts = jt.query("select * from account where money > ?", new AccountRowMapper(), 1000);
		List<Account> accounts = jt.query("select * from account where money > ?", new BeanPropertyRowMapper<Account>(Account.class), 1000);
		for (Account account : accounts) {
			System.out.println(account);
		}
		//select one
		List<Account> account = jt.query("select * from account where id = ?", new BeanPropertyRowMapper<Account>(Account.class), 7);
		System.out.println(account.isEmpty() ? "没有内容":account.get(0));

		//查询返回一行一列（使用聚合函数，但不加group字句）
		Long count = jt.queryForObject("select count(*) from account where money > ?", Long.class, 1000f);
		System.out.println(count);
	}
}

/**
 * 定义Account的封装策略
 */
class AccountRowMapper implements RowMapper<Account> {
	/**
	 * 把结果集中的数据封装到Account中，然后由Spring把每个Account加到集合中
	 * @param rs
	 * @param i
	 * @return
	 * @throws SQLException
	 */

	public Account mapRow(ResultSet rs, int i) throws SQLException {
		Account account = new Account();
		account.setId(rs.getInt("id"));
		account.setName(rs.getString("name"));
		account.setMoney(rs.getFloat("money"));
		return account;
	}
}