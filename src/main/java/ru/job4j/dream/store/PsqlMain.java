package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job1"));
        store.save(new Post(0, "Java Job2"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        System.out.println(store.findPostById(1));
        System.out.println(store.findPostById(2));

        store.save(new Candidate(0, "Candidate 1"));
        store.save(new Candidate(0, "Candidate 2"));
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        System.out.println(store.findCandidateById(1));
        System.out.println(store.findCandidateById(2));
        User user1 = new User(0, "Ivan", "email1", "password1");
        User user2 = new User(0, "Vladimir", "email2", "password2");
        store.save(user1);
        store.save(user2);
        for (User u : store.findAllUsers()) {
            System.out.println(u);
        }
        System.out.println(store.findUserById(1));
        System.out.println(store.findUserByEmail("email2"));
    }
}
