package com.github.haomao.jdbctemplate;

import com.github.haomao.jdbctemplate.dto.SexEnum;
import com.github.haomao.jdbctemplate.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * @Author:ggq
 * @Date:2018/12/10
 * @Description:
 */
@Service
public class JdbcTemplateUserServiceImpl implements JdbcTemplateUserService{

    @Autowired
    private JdbcTemplate jdbcTemplate = null;

    private RowMapper<User> getUserMapper(){

        RowMapper<User> userRowMapper = (ResultSet rs,int rownum) ->{
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserName(rs.getString("user_name"));
            int sexId = rs.getInt("sex");
            SexEnum sex = SexEnum.getEnumById(sexId);
            user.setSex(sex);
            user.setNote(rs.getString("note"));
            return user;
        };
        return userRowMapper;
    }

    /**
     * 一个连接执行一条sql
     * @param id
     * @return
     */
    @Override
    public User getUser(Long id) {
        String sql = "select id,user_name,sex,note from t_user where id = ?";
        Object [] params = new Object[]{id};

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, params, getUserMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    /**
     * 一个连接执行多条sql
     *  使用StatementCallback
     * @param id
     * @return
     */
    @Override
    public User getUser2(Long id) {
        //通过lambda表达式使用StatementCallback

        User result = jdbcTemplate.execute((Statement stmt) ->{
            String sql1 = "select count(*) total from t_user where id="+id;
            ResultSet rs1 = stmt.executeQuery(sql1);
            while(rs1.next()){
                int total = rs1.getInt("total");
                System.out.println("total----------->"+total);
            }

            String sql2 = "select id,user_name,sex,note from t_user where id="+id;
            ResultSet rs2 = stmt.executeQuery(sql2);
            User user = null;
            while (rs2.next()){
                int rowNum = rs2.getRow();
                user = getUserMapper().mapRow(rs2, rowNum);
            }
            return user;
        });
        return result;
    }

    /**
     * 一个连接执行多条sql
     *  使用ConnectionCallbkck
     * @param id
     * @return
     */
    @Override
    public User getUser3(Long id) {
        User result = jdbcTemplate.execute((Connection con) ->{
            String sql1 = "select count(*) total from t_user where id=?";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setLong(1,id);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()){
                System.out.println(rs1.getInt("total"));
            }

            String sql2 = "select id,user_name,sex,note from t_user where id =?";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setLong(1,id);
            ResultSet rs2 = ps2.executeQuery();
            User user = null;
            while (rs2.next()){
                int row = rs2.getRow();
                user = getUserMapper().mapRow(rs2, row);
            }
            return user;
        });
        return result;
    }

    @Override
    public List<User> findUsers() {
        String sql = "select * from t_user";

        List<User> users = jdbcTemplate.query(sql, getUserMapper());
        return users;
    }

    @Override
    public int insertUser(User user) {
        String sql = "insert into t_user (user_name,sex,note) values (?,?,?)";
        return jdbcTemplate.update(sql,user.getUserName(),user.getSex().getId(),user.getNote());
    }

    @Override
    public int updateUser(User user) {
        String sql = "update t_user set user_name=? ,sex=? ,note=?";
        return jdbcTemplate.update(sql,user.getUserName(),user.getSex().getId(),user.getNote());
    }

    @Override
    public int deleteUser(Long id) {
        String sql = "delete from t_user where id=?";
        return jdbcTemplate.update(sql,id);
    }
}
