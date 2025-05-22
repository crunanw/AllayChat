package net.voxelarc.allaychat.inventory.impl;

import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.inventory.AllayInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AllayInventory implements AllayInventoryHolder {

    private final Inventory inventory;

    public AllayInventory(ItemStack[] items, Component title, int size) {
        this.inventory = Bukkit.createInventory(this, size, title);
        inventory.setContents(items.clone());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

}
