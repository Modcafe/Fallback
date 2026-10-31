package dev.intensed.fallback.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public final class RandomDialogAPI {
    private static final String OBJECTIVE_NAME = "fallback.dialog";

    private RandomDialogAPI() {
    }

    public static int execute(CommandSourceStack source, int from, int to, String prefix, String player, List<Integer> blocked) {
        String scoreName;
        if (from > to) {
            source.sendFailure(Component.literal("'from' cannot be greater than 'to'."));
            return 0;
        }
        MinecraftServer server = source.getServer();
        ServerScoreboard scoreboard = server.getScoreboard();
        Objective objective = scoreboard.getObjective(OBJECTIVE_NAME);
        if (objective == null) {
            objective = scoreboard.addObjective(OBJECTIVE_NAME, ObjectiveCriteria.DUMMY, Component.literal("Fallback Dialogs"), ObjectiveCriteria.RenderType.INTEGER, false, null);
        }
        ArrayList<Integer> available = new ArrayList<Integer>();
        for(int number=from;number<=to;++number){
            int score;
            if(blocked.contains(number)){continue;}
            scoreName=RandomDialogAPI.getScoreName(prefix,number);
            if(scoreName==null){continue;} // you can put smth else here if you want to handle null differently; this exists cuz i noticed your code would just have a heart attack and explode if scoreName was null so i added this to prevent that ^w^
            ScoreHolder holder=ScoreHolder.forNameOnly(scoreName); //also i expanded your giant unreadable line into multiple lines for better readabilnessility 
            score=scoreboard.getOrCreatePlayerScore(holder,objective).get();
            if(score!=0){continue;}
            available.add(number);
        }
        if (available.isEmpty()) {
            source.sendFailure(Component.literal(("No unused dialogs are available for " + prefix + ".")));
            return 0;
        }
        int selected = available.get(ThreadLocalRandom.current().nextInt(available.size()));
        scoreName = RandomDialogAPI.getScoreName(prefix, selected);
        scoreboard.getOrCreatePlayerScore(ScoreHolder.forNameOnly(scoreName), objective).set(1);
        String command = "dialog show " + player + " " + prefix + ":" + selected;
        server.getCommands().performPrefixedCommand(source, command);
        return 1;
    }

    private static String getScoreName(String prefix, int number) {
        return prefix + ":" + number;
    }

    public static List<Integer> parseBlocked(String input) {
        String[] values;
        ArrayList<Integer> blocked = new ArrayList<Integer>();
        if (input == null || input.isBlank()) {
            return blocked;
        }
        for (String value : values = input.split(",")) {
            try {
                blocked.add(Integer.parseInt(value.trim()));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return blocked;
    }
}

