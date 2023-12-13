package edu.hw8.task3;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SequentialPasswordHacker {
    private final PasswordGenerator generator = new SimplePasswordGenerator();

    public Map<String, String> hackPasswords(Map<String, String> encodedPasswords) {
        Map<String, String> passHashAndUsername = encodedPasswords.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        Map<String, String> usernameAndPassword = new HashMap<>();

        String password = "";
        while (!passHashAndUsername.isEmpty()) {
            password = generator.generateNextPassword(password);
            String md5Hash = Md5HashEncoder.getMd5Hash(password);
            String username = passHashAndUsername.get(md5Hash);
            if (username != null) {
                usernameAndPassword.put(username, password);
                passHashAndUsername.remove(md5Hash);
            }
        }
        return usernameAndPassword;
    }
}
