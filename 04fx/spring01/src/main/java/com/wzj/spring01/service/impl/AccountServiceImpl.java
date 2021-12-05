package com.wzj.spring01.service.impl;

import com.wzj.spring01.entity.Account;
import com.wzj.spring01.mapper.AccountMapper;
import com.wzj.spring01.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangzhijie
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;

    @Override
    public List<Account> list(){
        return accountMapper.list();
    }

}
