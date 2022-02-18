package dev.foltz.crystalgrowing.client;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import dev.foltz.crystalgrowing.block.GrowingCrystalBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Block;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class CrystalGrowingModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        putBlockInRenderLayer(CrystalGrowingMod.GROWING_CRYSTAL_BLOCK, RenderLayer.getCutout());

        CrystalGrowingMod.LOGGER.info("Registering particle: crystal_particle");
        ParticleFactoryRegistry.getInstance().register(CrystalGrowingMod.CRYSTAL_PARTICLE, CrystalGrowingParticle.Factory::new);
    }

    public static void putBlockInRenderLayer(Block block, RenderLayer layer) {
        CrystalGrowingMod.LOGGER.info("Putting block [" + block.toString() + "] in RenderLayer [" + layer.toString() + "]");
        BlockRenderLayerMap.INSTANCE.putBlock(CrystalGrowingMod.GROWING_CRYSTAL_BLOCK, layer);
    }
}
