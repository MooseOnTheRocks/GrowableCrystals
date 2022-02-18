package dev.foltz.crystalgrowing;

import dev.foltz.crystalgrowing.block.GrowingCrystalBlock;
import dev.foltz.crystalgrowing.item.SeedCrystalItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrystalGrowingMod implements ModInitializer {
	public static final String MODID = "crystal_growing";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Block GROWING_CRYSTAL_BLOCK = new GrowingCrystalBlock();
	public static final Item SEED_CRYSTAL_ITEM = new SeedCrystalItem();

	public static final DefaultParticleType CRYSTAL_PARTICLE = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {
		LOGGER.info("Hello " + MODID + "!");
		registerBlock("growing_crystal", GROWING_CRYSTAL_BLOCK);
		registerItem("seed_crystal", SEED_CRYSTAL_ITEM);
		registerParticleType("crystal_particle", CRYSTAL_PARTICLE);
	}

	public static void registerParticleType(String name, ParticleType<?> type) {
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MODID, name), type);
	}

	public static void registerBlockWithItem(String name, Block block) {
		registerBlock(name, block);
		registerItem(name, new BlockItem(block, new FabricItemSettings().group(ItemGroup.MISC)));
	}

	public static void registerBlock(String name, Block block) {
		LOGGER.info("Registering block: " + name);
		Registry.register(Registry.BLOCK, new Identifier(MODID, name), block);
	}

	public static void registerItem(String name, Item item) {
		LOGGER.info("Registering item: " + name);
		Registry.register(Registry.ITEM, new Identifier(MODID, name), item);
	}
}
