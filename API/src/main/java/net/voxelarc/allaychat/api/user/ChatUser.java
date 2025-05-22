package net.voxelarc.allaychat.api.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ChatUser {

    private final UUID uniqueId;

    // Yes. Should not be player names. But it has to be in order to work across instances.
    // Bukkit.getOfflinePlayer(String) will not work if the player has never joined that instance on cracked server.
    // And since it is a blocking-operation, it will crash the server.
    private final List<String> ignoredPlayers = new ArrayList<>();

    private boolean msgEnabled = true;
    private boolean spyEnabled = false;
    private boolean staffEnabled = false;
    private boolean mentionsEnabled = true;

}
