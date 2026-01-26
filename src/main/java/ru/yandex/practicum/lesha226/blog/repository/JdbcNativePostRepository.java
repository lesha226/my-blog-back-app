package ru.yandex.practicum.lesha226.blog.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.lesha226.blog.model.Post;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcNativePostRepository implements PostRepository {

    private final JdbcTemplate template;

    private final RowMapper<Post> mapper = (rs, rowNum) -> new Post(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("text"),
            List.of(), // TODO : get tags
            rs.getInt("likes_count"),
            rs.getInt("comments_count")
    );

    public JdbcNativePostRepository(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    public List<Post> findAll(int offset, int size) {
        return template.query("""
                select id, title, text, likes_count
                     , (select count(*) from comments where post_id = p.id) as comments_count
                from posts p
                order by id
                limit ? offset ?""", mapper, size, offset);
    }

    @Override
    public Optional<Post> findById(Long id) {
        try {
            Post post = template.queryForObject("""
                    select id, title, text, likes_count
                        , (select count(*) from comments where post_id = p.id) as comments_count
                    from posts p
                    where id = ?""", mapper, id);

            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int size() {
        SqlRowSet rs = template.queryForRowSet("""
                select count(*) from posts""");

        if (rs.first()) {
            return rs.getInt(1);
        } else {
            return 0;
        }
    }

    @Override
    public Long save(Post post) {
        String sql = "insert into posts(title, text) values (?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();

        template.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, post.getTitle());
                    ps.setString(2, post.getText());
                    return ps;
                },
                holder);

        return holder.getKey().longValue();
    }

    @Override
    public void update(Post post) {
        template.update("""
                update posts
                set title = ?,
                    text = ?
                where id = ?""", post.getTitle(), post.getText(), post.getId());
    }

    @Override
    public void delete(Long id) {
        template.update("""
                delete from posts
                where id = ?""", id);

    }

    @Override
    public void like(Long id) {
        template.update("""
                update posts
                set likes_count = likes_count + 1
                where id = ?""", id);
    }

}
