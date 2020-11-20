package com.minecraft.plugin;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Plugin extends JavaPlugin {
    public static Socket socket;
    public static Plugin instance;

    @Override
    public void onEnable() {
        Plugin.instance = this;

        this.getServer().getScheduler().runTask(this, new Runnable() {
            @Override
            public void run() {
                try {
                    Plugin.socket = IO.socket(Plugin.instance.getConfig().getString("server_url"));

                    Plugin.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Plugin.instance.getLogger().info("Connected to master server.");
                        }
                    }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            for (Object obj : args) {
                                Plugin.instance.getLogger().info(obj.toString());
                            }
                        }
                    });

                    Plugin.socket.on("gameMode", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            final String gameMode = ((String) args[0]).toUpperCase();

                            Plugin.instance.getServer().getScheduler().runTask(Plugin.instance, new Runnable() {
                                @Override
                                public void run() {
                                    for (World world : Plugin.instance.getServer().getWorlds()) {
                                        for (Player player : world.getPlayers()) {
                                            player.setGameMode(GameMode.valueOf(gameMode));
                                        }
                                    }
                                }
                            });
                        }
                    });

                    Plugin.socket.on("time", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            final long time = Long.valueOf((int) args[0]);

                            Plugin.instance.getServer().getScheduler().runTask(Plugin.instance, new Runnable() {
                                @Override
                                public void run() {
                                    for (World world : Plugin.instance.getServer().getWorlds()) {
                                        world.setTime(time);
                                    }
                                }
                            });
                        }
                    });

                    Plugin.socket.on("gravity", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            final boolean gravity = (boolean) args[0];

                            Plugin.instance.getServer().getScheduler().runTask(Plugin.instance, new Runnable() {
                                @Override
                                public void run() {
                                    for (World world : Plugin.instance.getServer().getWorlds()) {
                                        for (Entity entity : world.getEntities()) {
                                            entity.setGravity(gravity);
                                        }
                                    }
                                }
                            });
                        }
                    });

                    Plugin.socket.connect();
                } catch (Exception e) {
                    Plugin.instance.getLogger().info(e.toString());
                }
            }
        });
    }

    @Override
    public void onDisable() {
        if (Plugin.socket != null)
            Plugin.socket.close();
    }
}