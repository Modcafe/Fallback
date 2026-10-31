package dev.intensed.fallback.client;
import org.jspecify.annotations.NonNull;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
public class FallbackDataGenerator
implements DataGeneratorEntrypoint{
    public void onInitializeDataGenerator(@NonNull FabricDataGenerator fabricDataGenerator){
        FabricDataGenerator.Pack pack=fabricDataGenerator.createPack();
    }
}

