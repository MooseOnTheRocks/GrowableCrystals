package dev.foltz.crystalgrowing.client;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import dev.foltz.crystalgrowing.block.CrystalBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class CrystalGrowingModClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(CrystalGrowingMod.MODID);

    @Override
    public void onInitializeClient() {
        putBlockInRenderLayer(CrystalBlocks.REDSTONE_CRYSTAL_BLOCK, RenderLayer.getCutout());
        registerParticle(CrystalGrowingMod.CRYSTAL_PARTICLE, CrystalParticle.Factory::new);
    }

    public static <T extends ParticleEffect> void registerParticle(ParticleType<T> particleType, ParticleFactoryRegistry.PendingParticleFactory<T> particleFactory) {
        LOGGER.info("Registering particle: " + particleType.toString());
        ParticleFactoryRegistry.getInstance().register(particleType, particleFactory);
    }

    public static void putBlockInRenderLayer(Block block, RenderLayer layer) {
        LOGGER.info("Putting block [" + block.toString() + "] in RenderLayer [" + layer.toString() + "]");
        BlockRenderLayerMap.INSTANCE.putBlock(block, layer);
    }
}
