package com.nowcoder.dao;

import com.nowcoder.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper
public interface CommentDAO {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, created_date, entity_id, entity_type, status, content ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{createdDate},#{entityId},#{entityType},#{status},#{content})"})
    int addComment(Comment comment);


    @Select({"select ", SELECT_FIELDS, " from", TABLE_NAME,
            "where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);


    @Select({"select count(id) from ", TABLE_NAME, "where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);


    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);
}
