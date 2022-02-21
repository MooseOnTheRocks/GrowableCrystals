package dev.foltz.crystalgrowing;

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

	public static final Item SEED_CRYSTAL_ITEM = registerItem("seed_crystal", new SeedCrystalItem());
	public static final DefaultParticleType CRYSTAL_PARTICLE = (DefaultParticleType) registerParticleType("crystal_particle", FabricParticleTypes.simple());

	@Override
	public void onInitialize() {
		LOGGER.info("Hello " + MODID + "!");
	}

	public static ParticleType<?> registerParticleType(String name, ParticleType<?> type) {
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(MODID, name), type);
		return type;
	}

	public static Block registerBlockWithItem(String name, Block block) {
		registerBlock(name, block);
		registerItem(name, new BlockItem(block, new FabricItemSettings().group(ItemGroup.MISC)));
		return block;
	}

	public static Block registerBlock(String name, Block block) {
		Registry.register(Registry.BLOCK, new Identifier(MODID, name), block);
		return block;
	}

	public static Item registerItem(String name, Item item) {
		Registry.register(Registry.ITEM, new Identifier(MODID, name), item);
		return item;
	}
}
