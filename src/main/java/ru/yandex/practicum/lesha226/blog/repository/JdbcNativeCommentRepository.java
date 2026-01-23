package ru.yandex.practicum.lesha226.blog.repository;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.lesha226.blog.model.Comment;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcNativeCommentRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Comment> mapper = (rs, rowNum) -> new Comment(
            rs.getLong("id"),
            rs.getLong("post_id"),
            rs.getString("text"));

    public JdbcNativeCommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return jdbcTemplate.query("""
                select id, post_id, text
                from comments
                where post_id = ?""", mapper, postId);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        try {
            Comment comment = jdbcTemplate.queryForObject("""
                    select id, post_id, text
                    from comments
                    where id = ?""", mapper, id);

            return Optional.ofNullable(comment);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long save(Comment comment) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement("""
                            insert into comments(text, post_id)
                            values (?, ?)""", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, comment.getText());
                    ps.setLong(2, comment.getPostId());
                    return ps;
                },
                holder);

        return holder.getKeyAs(Long.class);
    }

    @Override
    public void update(Comment comment) {
        jdbcTemplate.update("""
                update comments
                set text = ?
                where id = ?""", comment.getText(), comment.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("""
                delete from comments
                where id = ?""", id);
    }
}
