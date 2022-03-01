package dev.foltz.growablecrystals;

import dev.foltz.growablecrystals.entity.CrystalPowderItemEntity;
import dev.foltz.growablecrystals.item.CrystalPowderItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrowableCrystalsMod implements ModInitializer {
	public static final String MODID = "growable_crystals";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Item CRYSTAL_POWDER_ITEM = registerItem("crystal_powder", new CrystalPowderItem());

	public static final EntityType<CrystalPowderItemEntity> CRYSTAL_POWDER_ENTITY_TYPE = Registry.register(
		Registry.ENTITY_TYPE,
		new Identifier(MODID, "crystal_powder"),
		FabricEntityTypeBuilder.create(
			SpawnGroup.MISC,
			CrystalPowderItemEntity::factory
		).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackedUpdateRate(20).trackRangeChunks(6).build()
	);

	public static final DefaultParticleType REDSTONE_CRYSTAL_PARTICLE = registerDefaultParticleType("redstone_crystal_particle");
	public static final DefaultParticleType COAL_CRYSTAL_PARTICLE = registerDefaultParticleType("coal_crystal_particle");
	public static final DefaultParticleType IRON_CRYSTAL_PARTICLE = registerDefaultParticleType("iron_crystal_particle");
	public static final DefaultParticleType GOLD_CRYSTAL_PARTICLE = registerDefaultParticleType("gold_crystal_particle");
	public static final DefaultParticleType LAPIS_CRYSTAL_PARTICLE = registerDefaultParticleType("lapis_crystal_particle");
	public static final DefaultParticleType DIAMOND_CRYSTAL_PARTICLE = registerDefaultParticleType("diamond_crystal_particle");
	public static final DefaultParticleType COPPER_CRYSTAL_PARTICLE = registerDefaultParticleType("copper_crystal_particle");
	public static final DefaultParticleType EMERALD_CRYSTAL_PARTICLE = registerDefaultParticleType("emerald_crystal_particle");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello " + MODID + "!");
	}

	public static DefaultParticleType registerDefaultParticleType(String name) {
		return Registry.register(Registry.PARTICLE_TYPE, new Identifier(MODID, name), FabricParticleTypes.simple());
	}

	public static Block registerBlock(String name, Block block) {
		return Registry.register(Registry.BLOCK, new Identifier(MODID, name), block);
	}

	public static Item registerItem(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(MODID, name), item);
	}
}
