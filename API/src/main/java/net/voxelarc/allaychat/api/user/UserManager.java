package net.voxelarc.allaychat.api.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    private final Map<UUID, ChatUser> userMap = new HashMap<>();

    /**
     *
     * @param uniqueId Player UUID
     * @return null if the user not on the server currently
     */
    public ChatUser getUser(UUID uniqueId) {
        return userMap.get(uniqueId);
    }

    public void addUser(ChatUser user) {
        userMap.put(user.getUniqueId(), user);
    }

    public void removeUser(UUID uniqueId) {
        userMap.remove(uniqueId);
    }

    public Collection<ChatUser> getAllUsers() {
        return userMap.values();
    }

}
