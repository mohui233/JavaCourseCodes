package com.wzj.spring01.controller;

import com.wzj.spring01.entity.Account;
import com.wzj.spring01.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangzhijie
 */
@RestController
@EnableAutoConfiguration
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping("/account/list")
    List<Account> list() {
        return accountService.list();
    }
}
