package dev.intensed.fallback.api;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

public final class LoggerAPI {
    private static MinecraftServer server;

    private LoggerAPI() {
    }

    public static void initialize(MinecraftServer minecraftServer) {
        server = minecraftServer;
    }

    public static void info(String author, String message) {
        LoggerAPI.send(Component.literal(("[" + DateAPI.time() + "] ")).withStyle(style -> style.withColor(5476815)).append(Component.literal("[main/INFO] ").withStyle(style -> style.withColor(6919485)).append(Component.literal(("(" + author + ") ")).withStyle(style -> style.withColor(4759714)).append(Component.literal(message).withStyle(style -> style.withColor(12369603))))));
    }

    public static void success(String author, String message) {
        LoggerAPI.send(Component.literal(("[" + DateAPI.time() + "] ")).withStyle(style -> style.withColor(5476815)).append(Component.literal("[main/SUCCESS] ").withStyle(style -> style.withColor(6919485)).append(Component.literal(("(" + author + ") ")).withStyle(style -> style.withColor(4759714)).append(Component.literal(message).withStyle(style -> style.withColor(12369603))))));
    }

    public static void warn(String author, String message) {
        LoggerAPI.send(Component.literal(("[" + DateAPI.time() + "] ")).withStyle(style -> style.withColor(5476815)).append(Component.literal("[main/WARN] ").withStyle(style -> style.withColor(10586927)).append(Component.literal(("(" + author + ") ")).withStyle(style -> style.withColor(4759714)).append(Component.literal(message).withStyle(style -> style.withColor(12369603))))));
    }

    public static void error(String author, String message) {
        LoggerAPI.send(Component.literal(("[" + DateAPI.time() + "] ")).withStyle(style -> style.withColor(5476815)).append(Component.literal("[main/ERROR] ").withStyle(style -> style.withColor(14572886)).append(Component.literal(("(" + author + ") ")).withStyle(style -> style.withColor(4759714)).append(Component.literal(message).withStyle(style -> style.withColor(12369603))))));
    }

    private static void send(Component message) {
        if (server == null) {
            return;
        }
        server.getPlayerList().broadcastSystemMessage(message, false);
    }
}

