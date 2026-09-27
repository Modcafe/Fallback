package dev.intensed.fallback.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.intensed.fallback.api.LoggerAPI;
import dev.intensed.fallback.api.RandomDialogAPI;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;

public final class FallbackCommand {


    private static LiteralArgumentBuilder<CommandSourceStack> build() {
        return literal("fallback")
                .then(
                        literal("log")
                                .then(literal("test")
                                        .executes(FallbackCommand::runTest)
                                )
                                .then(argument("level",StringArgumentType.word())
                                    .suggests(LogLevels::suggest)
                                    .then(argument("author",StringArgumentType.string())
                                        .then(argument("message",StringArgumentType.greedyString())
                                            .executes(FallbackCommand::runLog)
                                        )
                                    )
                                )
                )
                .then(
                        literal("randomDialog").then(
                            argument("from",IntegerArgumentType.integer())
                                    .then(argument("to",IntegerArgumentType.integer())
                                            .then(argument("prefix",StringArgumentType.word())
                                                    .then(argument("player", StringArgumentType.string()) // maybe use EntityArgument.player() in the future
                                                            .executes(FallbackCommand::runRandomDialog)
                                                            .then(argument("blocked",StringArgumentType.greedyString())
                                                                    .executes(FallbackCommand::runRandomDialog)
                                                            )
                                                    )
                                            )
                                    )
                        )
                );
    }

    private static int runRandomDialog(CommandContext<CommandSourceStack> context) {
        int from = IntegerArgumentType.getInteger(context, "from");
        int to = IntegerArgumentType.getInteger(context, "to");
        String prefix = StringArgumentType.getString(context, "prefix");
        String player = StringArgumentType.getString(context, "player");
        String blockedString;
        List<Integer> blockedList;
        try {
            blockedString = StringArgumentType.getString(context, "blocked");
        } catch (IllegalArgumentException e) {
            blockedString = null;
        }
        if (blockedString != null) {
            blockedList = RandomDialogAPI.parseBlocked(blockedString);
        } else {
            blockedList = List.of();
        }
        return RandomDialogAPI.execute(context.getSource(), from, to, prefix, player, blockedList);
    }


    private static int runLog(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String message = StringArgumentType.getString(context,"message");
        String author = StringArgumentType.getString(context,"author");
        LogLevels.LogLevel logLevel = LogLevels.getLevel(context,"level");
        switch (logLevel) {
            case INFO -> LoggerAPI.info(author,message);
            case ERROR -> LoggerAPI.error(author,message);
            case WARN -> LoggerAPI.warn(author,message);
            case SUCCESS -> LoggerAPI.success(author,message);
        }
        return 1;
    }
    private static int runTest(CommandContext<CommandSourceStack> context) {
        LoggerAPI.info("Fallback", "Test: Info successfully executed.");
        LoggerAPI.success("Fallback", "Test: Success successfully executed.");
        LoggerAPI.warn("Fallback", "Test: Warn successfully executed.");
        LoggerAPI.error("Fallback", "Test: Error successfully executed.");
        return 1;
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(FallbackCommand.build());
    }
}

