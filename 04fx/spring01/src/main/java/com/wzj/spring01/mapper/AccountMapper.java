package com.wzj.spring01.mapper;

import com.wzj.spring01.entity.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface AccountMapper {

    List<Account> list();
}
