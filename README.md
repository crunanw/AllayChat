## What is AllayChat?
🔰 AllayChat is a modern & powerful chat plugin for PaperMC (and it's forks).\
It allows server owners to create custom chat filters, formats, placeholders and more.

*(Folia support is planned but not implemented yet.)*

⚠️ Allay does not support chat channels.\
I have no plans to add it in the future either.\
I have never seen anyone use it and I don't see a reason to add it.\
It just complicates the chat system and makes it harder to manage.

-------------------

## 🚀 Features
- Custom chat filters
- Custom chat formats
- Custom placeholders (replacements)
- PlaceholderAPI
- Item in chat
- Inventory in chat
- Shulker in chat
- Staff chat
- Private messaging
- Spy
- And more
-------------------

## 💻 Developers
Allay is designed to be compatible with almost any plugin.\
We use Paper's ChatRenderer to render messages. It means that Allay will not break your existing plugins.\
(as long as your plugin does not do anything weird or outdated)

However, we do not support use of any 3rd party chat (including coloring and formatting) plugin.\
If you are a developer and want to support Allay, feel free to contribute.

-------------------

## 🔮 Cross-Server

AllayChat supports cross-server setups.\
You can use AllayChat to sync chat messages between multiple servers.\
This is useful for large networks where you want to have a unified chat experience across all servers.

Allay uses Redis (lettuce) to sync chat messages between servers.\
Thanks to this, AllayChat is very fast and efficient.\
Many other plugins use Plugin Messaging for cross-server support, but it is no way fast as AllayChat's Redis implementation.

Cross-Server feature itself is not included in this repository.\
[You can find it in here.](https://github.com/VoxelArcStudios/AllayChat-Multi)

It is a separate JAR, you put it inside AllayChat/modules.

-------------------

## 🔒 Open Source Does Not Mean Free
AllayChat is an open source project, but it does not mean that it is free.\
If you want to use AllayChat, you need to pay for it.\
[You can buy it on BuiltByBit](https://google.com)

No support will be provided for users who do not pay for AllayChat.\
And no, we do not help you build AllayChat from source if you do not pay for it.
-------------------

## 🔨 Contributing

If you want to contribute to AllayChat, feel free to open a pull request.\
We are always looking for new contributors and ideas.

If you want to report a bug or suggest a feature, feel free to open an issue.\
We will try to respond as soon as possible.

Please keep in mind the modularity of this plugin.\
We are trying to keep the codebase clean and modular, so please try to follow the existing structure.\
If you are not sure how to do something, feel free to ask our [Discord server](https://discord.gg/ha8Fg9qYRn).

-------------------