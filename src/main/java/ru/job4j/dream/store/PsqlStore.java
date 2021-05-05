package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(
                            new Post(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("description")));
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return posts;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO post(name, description) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return post;
    }

    private void update(Post post) {
        String sql = "update post set name = ?, description = ? where id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    @Override
    public Post findPostById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM post WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error", e);
        }
        return null;
    }

    @Override
    public void delete(Post post) {
        if (post.getId() == 0) {
            return;
        }
        String sql = "delete from post where id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Candidate can = new Candidate(
                            it.getInt("id"),
                            it.getString("name"),
                            it.getInt("photo_id"));
                    can.setCityId(it.getInt("city_id"));
                    candidates.add(can);
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return candidates;
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidate(name, city_id) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return candidate;
    }

    private void update(Candidate candidate) {
        String sql = "update candidate set name = ?, city_id = ? where id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setInt(3, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    @Override
    public Candidate findCandidateById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM candidate WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Candidate(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("photo_id"));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error", e);
        }
        return null;
    }

    @Override
    public void delete(Candidate candidate) {
        if (candidate.getId() == 0) {
            return;
        }
        String sql = "delete from candidate photo where id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    @Override
    public Collection<City> findAllCities() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    cities.add(
                            new City(
                                    it.getInt("id"),
                                    it.getString("name")));
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return cities;
    }

    @Override
    public void save(Photo photo, int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps1 = cn.prepareStatement(
                     "INSERT INTO photo(image) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement ps2 = cn.prepareStatement(
                     "UPDATE candidate SET photo_id = ? WHERE id = ?")
        ) {
            ps1.setBytes(1, photo.getImage());
            ps1.execute();
            try (ResultSet photoId = ps1.getGeneratedKeys()) {
                if (photoId.next()) {
                    photo.setId(photoId.getInt(1));
                }
            }
            ps2.setInt(1, photo.getId());
            ps2.setInt(2, id);
            ps2.execute();
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    @Override
    public Photo findPhotoById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement st = cn.prepareStatement(
                     "select * from photo where id = ?")) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Photo(
                            rs.getInt("id"),
                            rs.getBytes("image")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return null;
    }

    @Override
    public Collection<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from users")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(
                            new User(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("email"),
                                    it.getString("password")
                            ));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error", e);
        }
        return users;
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    private void create(User user) {
        String sql = "insert into users(name, email, password) values (?, ?, ?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     sql, PreparedStatement.RETURN_GENERATED_KEYS
             )
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    private void update(User user) {
        String sql = "update users set name = ?, email = ?, password = ? where id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    @Override
    public User findUserById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error", e);
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email = ?")) {
            ps.setString(1, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error", e);
        }
        return null;
    }
}