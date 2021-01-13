package com.tencent.dao;

import com.tencent.pojo.Member;

/**
 * @author PR
 * @package com.tencent.dao
 * @date 2021/1/11 23:05
 */
public interface MemberDao {
    Member findMemberByPhoneNumber(String phoneNumber);

    Integer addMember(Member member);
}
