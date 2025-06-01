package org.example.rentfield.Repository.User;

import org.example.rentfield.Model.User;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class RegistrationDelegate {
    private Set<User> userSet;

    public RegistrationDelegate(Set<User> userSet) {
        this.userSet = userSet;
    }

    public void addUser(User user) {
        this.userSet.add(user);
    }

    public Set<User> getUserSet() {
        return userSet;
    }
}
