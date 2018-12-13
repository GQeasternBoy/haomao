package com.github.haomao.jpa.repository;

import com.github.haomao.jpa.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:ggq
 * @Date:2018/12/12
 * @Description:
 */
@Repository
public interface JpaUserRepository extends JpaRepository<User,Long>{

    @Query("from user where user_name=?1 and note=?2")
    List<User> findUsers(String userName,String note);

    /**
     * 按照用户名称模糊查询
     * @param userName
     * @return
     */
    List<User> findByUserNameLike(String userName);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 根据用户名称或备注模糊查询
     * @param userName
     * @param note
     * @return
     */
    List<User> findByUserNameLikeOrNoteLike(String userName,String note);
}
