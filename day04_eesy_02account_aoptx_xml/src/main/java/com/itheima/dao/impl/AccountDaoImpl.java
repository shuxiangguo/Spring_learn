package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.utils.ConnectionUitls;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 账户的持久层实现类
 */

public class AccountDaoImpl implements IAccountDao {

	private QueryRunner runner;

	private ConnectionUitls connectionUitls;

	public void setConnectionUitls(ConnectionUitls connectionUitls) {
		this.connectionUitls = connectionUitls;
	}

	public void setRunner(QueryRunner runner) {
		this.runner = runner;
	}

	public List<Account> findAllAccount() {
		try {
			return runner.query(connectionUitls.getThreadConnection(),"select * from account", new BeanListHandler<Account>(Account.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Account findAccountById(Integer accountId) {
		try {
			return runner.query(connectionUitls.getThreadConnection(),"select * from account where id = ?", new BeanHandler<Account>(Account.class), accountId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void saveAccount(Account account) {
		try {
			runner.update(connectionUitls.getThreadConnection(),"insert into account(name, money) values(?,?)", account.getName(), account.getMoney());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateAccount(Account account) {
		try {
			runner.update(connectionUitls.getThreadConnection()," update account set name = ?, money = ? where id = ?", account.getName(), account.getMoney(),account.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteAccount(Integer accountId) {
		try {
			runner.update(connectionUitls.getThreadConnection(),"delete from account where id = ?", accountId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Account findAccountByName(String accountName) {
		try {
			List<Account> accounts = runner.query(connectionUitls.getThreadConnection(),"select * from account where name = ?", new BeanListHandler<Account>(Account.class), accountName);
			if (accounts == null || accounts.size() == 0) {
				return null;
			}
			if (accounts.size() > 1) {
				throw new RuntimeException("结果集不唯一，数据有问题");
			}
			return accounts.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
