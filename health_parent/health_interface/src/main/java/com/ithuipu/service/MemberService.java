package com.ithuipu.service;

import com.ithuipu.pojo.Member;

import java.util.List;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/28---16:37
 * @描述信息
 */

public interface MemberService {
    Member findByTelephone(String telephone);

    /**注册会员*/
    void add(Member member);

    /**统计报表*/
    List<Integer> findMemberCountByMonth(List<String> list);
}
