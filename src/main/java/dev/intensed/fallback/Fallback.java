package dev.intensed.fallback;

import dev.intensed.fallback.api.LoggerAPI;
import dev.intensed.fallback.impl.FallbackCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fallback
implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Fallback");

    public void onInitialize() {
        LOGGER.info("Loading...");
        LOGGER.info("Intializing API Command...");
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> FallbackCommand.register(dispatcher));
        ServerLifecycleEvents.SERVER_STARTED.register(LoggerAPI::initialize);
        LOGGER.info("Intialized Command!");
    }
}

