package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;

public interface Store {

    Collection<Post> findAllPosts();

    void save(Post post);

    Post findPostById(int id);

    Collection<Candidate> findAllCandidates();

    void save(Candidate candidate);

    Candidate findCandidateById(int id);
}
