package com.example.bookstore.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.domain.Account;
import com.example.bookstore.repository.AccountRepository;

/**
 * @see AccountService
 * 
 * 
 *
 */
@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	@Transactional(readOnly = false)
	public Account save(Account account) {
		return this.accountRepository.save(account);
	}

	@Override
	public Account login(String username, String password) throws AuthenticationException {
		Account account = this.accountRepository.findByUsername(username);
		if (account != null) {
			String pwd = DigestUtils.sha256Hex(password + "{" + username + "}");
			if (!account.getPassword().equalsIgnoreCase(pwd)) {
				throw new AuthenticationException("Wrong username/password combination.", "invalid.password");
			}
		} else {
			throw new AuthenticationException("Wrong username/password combination.", "invalid.username");
		}

		return account;
	}

	@Override
	public Account getAccount(String username) {
		return this.accountRepository.findByUsername(username);
	}
}
