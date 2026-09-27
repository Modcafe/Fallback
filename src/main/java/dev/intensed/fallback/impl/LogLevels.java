package dev.intensed.fallback.impl;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public final class LogLevels {
    private LogLevels() {}

    public static LogLevel getLevel(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
        String input = StringArgumentType.getString(context,name);
        try {
            return LogLevel.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new SimpleCommandExceptionType(Component.literal("Invalid LogLevel type: %s".formatted(input))).create();
        }
    }

    public static <S> CompletableFuture<Suggestions> suggest(CommandContext<S> context, SuggestionsBuilder builder) {
        String remaining = builder.getRemainingLowerCase();
        for (LogLevel logLevelType : LogLevel.values()) {
            String name = logLevelType.name().toLowerCase(Locale.ROOT);
            if (name.contains(remaining))  {
                builder.suggest(name);
            }
        }
        return builder.buildFuture();
    }

    public enum LogLevel {
        INFO,
        ERROR,
        WARN,
        SUCCESS,
    }



}
