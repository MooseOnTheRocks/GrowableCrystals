package dev.foltz.crystalgrowing.client;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import dev.foltz.crystalgrowing.block.CrystalBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class CrystalGrowingModClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(CrystalGrowingMod.MODID);

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(CrystalGrowingMod.CRYSTAL_POWDER_ENTITY_TYPE, ItemEntityRenderer::new);

        putBlockInRenderLayer(CrystalBlocks.REDSTONE_CRYSTAL_BLOCK, RenderLayer.getCutout());
        putBlockInRenderLayer(CrystalBlocks.COAL_CRYSTAL_BLOCK, RenderLayer.getCutout());
        putBlockInRenderLayer(CrystalBlocks.IRON_CRYSTAL_BLOCK, RenderLayer.getCutout());
        putBlockInRenderLayer(CrystalBlocks.GOLD_CRYSTAL_BLOCK, RenderLayer.getCutout());

        registerParticle(CrystalGrowingMod.REDSTONE_CRYSTAL_PARTICLE, CrystalParticle.Factory.withColor(1f, 0f, 0f));
        registerParticle(CrystalGrowingMod.COAL_CRYSTAL_PARTICLE, CrystalParticle.Factory.withColor(0.2f, 0.2f, 0.2f));
        registerParticle(CrystalGrowingMod.IRON_CRYSTAL_PARTICLE, CrystalParticle.Factory.withColor(0.85f, 0.70f, 0.60f));
        registerParticle(CrystalGrowingMod.GOLD_CRYSTAL_PARTICLE, CrystalParticle.Factory.withColor(1f, 0.9f, 0.2f));
    }

    public static <T extends ParticleEffect> void registerParticle(ParticleType<T> particleType, ParticleFactoryRegistry.PendingParticleFactory<T> particleFactory) {
        ParticleFactoryRegistry.getInstance().register(particleType, particleFactory);
    }

    public static void putBlockInRenderLayer(Block block, RenderLayer layer) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, layer);
    }
}
