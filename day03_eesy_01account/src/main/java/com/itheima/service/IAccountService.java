package com.itheima.service;

import com.itheima.domain.Account;

import java.util.List;

/*
 * 账户的业务层接口
 */
public interface IAccountService {
	/**
	 * 查询所有
	 * @return
	 */
	List<Account> findAllAccount();

	/**
	 * 查询一个
	 * @return
	 */
	Account findAccountById(Integer accountId);

	/**
	 * 保存
	 * @param account
	 */
	void saveAccount(Account account);


	/**
	 * update
	 */

	void updateAccount(Account account);

	/**
	 * delete
	 */
	void deleteAccount(Integer accountId);

	/**
	 * 转账
	 * @param sourceName 转出账户名称
	 * @param targetName 转入账户名称
	 * @param money 金额
	 */
	void transfer(String sourceName, String targetName, Float money);
}
