package ru.job4j.dream.store;

import ru.job4j.dream.model.*;

import java.util.Collection;

public interface Store {

    Collection<Post> findAllPosts();

    void save(Post post);

    Post findPostById(int id);

    void delete(Post post);

    Collection<Candidate> findAllCandidates();

    void save(Candidate candidate);

    Candidate findCandidateById(int id);

    void delete(Candidate candidate);

    void save(Photo photo, int id);

    Photo findPhotoById(int id);

    Collection<User> findAllUsers();

    void save(User user);

    User findUserById(int id);

    User findUserByEmail(String email);

    Collection<City> findAllCities();
}
