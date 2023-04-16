package org.betterx.datagen.bclib;

import org.betterx.bclib.BCLib;
import org.betterx.datagen.bclib.advancement.BCLAdvancementDataProvider;
import org.betterx.datagen.bclib.advancement.RecipeDataProvider;
import org.betterx.datagen.bclib.preset.WorldPresetDataProvider;
import org.betterx.datagen.bclib.tests.TestBiomes;
import org.betterx.datagen.bclib.tests.TestWorldgenProvider;
import org.betterx.datagen.bclib.worldgen.BCLibRegistriesDataProvider;

import net.minecraft.core.RegistrySetBuilder;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BCLibDatagen implements DataGeneratorEntrypoint {
    public static final boolean ADD_TESTS = false;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        BCLib.LOGGER.info("Bootstrap onInitializeDataGenerator");
        final FabricDataGenerator.Pack pack = dataGenerator.createPack();

        if (ADD_TESTS) {
            TestBiomes.ensureStaticallyLoaded();

            pack.addProvider(TestWorldgenProvider::new);
            pack.addProvider(TestBiomes::new);
            RecipeDataProvider.createTestRecipes();
        }


        pack.addProvider(RecipeDataProvider::new);
        pack.addProvider(WorldPresetDataProvider::new);
        pack.addProvider(BCLibRegistriesDataProvider::new);
        pack.addProvider(BCLAdvancementDataProvider::new);
    }


    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        BCLRegistrySupplier.INSTANCE.bootstrapRegistries(registryBuilder);
    }
}
