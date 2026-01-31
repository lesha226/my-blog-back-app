package ru.yandex.practicum.lesha226.blog.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.lesha226.blog.model.Post;
import ru.yandex.practicum.lesha226.blog.repository.PostRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcNativePostRepository implements PostRepository {

    private final JdbcTemplate template;

    private final RowMapper<Post> mapper = (rs, rowNum) -> new Post(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("text"),
            Arrays.stream(((Object[]) rs.getArray("tags").getArray())).map(Object::toString).toList(),
            rs.getInt("likes_count"),
            rs.getInt("comments_count")
    );

    public JdbcNativePostRepository(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    public List<Post> findAll(String searchTitleString, List<String> searchTagList, int offset, int size) {

        return template.query("""
                select id, title, text, tags, likes_count
                     , (select count(*) from comments where post_id = p.id) as comments_count
                from posts p
                where title like concat('%', ?, '%')
                  and not exists(select 1
                                 from unnest(?) as search_tags(tag)
                                 where not array_contains(p.tags, search_tags.tag))
                order by id
                limit ? offset ?""", mapper, searchTitleString, searchTagList.toArray(), size, offset);
    }

    @Override
    public Optional<Post> findById(Long id) {
        try {
            Post post = template.queryForObject("""
                    select id, title, text, tags, likes_count
                        , (select count(*) from comments where post_id = p.id) as comments_count
                    from posts p
                    where id = ?""", mapper, id);

            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int size(String searchTitleString, List<String> searchTagList) {
        Integer result = template.queryForObject("""
                select count(*)
                from posts p
                where title like concat('%', ?, '%')
                  and not exists(select 1
                                 from unnest(?) as search_tags(tag)
                                 where not array_contains(p.tags, search_tags.tag))
                """, Integer.class, searchTitleString, searchTagList.toArray());
        return result == null ? 0 : result;
    }

    @Override
    public Long save(Post post) {
        KeyHolder holder = new GeneratedKeyHolder();

        template.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement("""
                            insert into posts(title, text, tags)
                            values (?, ?, ?)
                            """, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, post.getTitle());
                    ps.setString(2, post.getText());
                    ps.setArray(3, con.createArrayOf("varchar", post.getTags().toArray()));
                    return ps;
                },
                holder);

        return holder.getKey().longValue();
    }

    @Override
    public void update(Post post) {
        template.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement("""
                            update posts
                            set title = ?,
                                text = ?,
                                tags = ?
                            where id = ?""");
                    ps.setString(1, post.getTitle());
                    ps.setString(2, post.getText());
                    ps.setArray(3, con.createArrayOf("varchar", post.getTags().toArray()));
                    ps.setLong(4, post.getId());
                    return ps;
                });
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
