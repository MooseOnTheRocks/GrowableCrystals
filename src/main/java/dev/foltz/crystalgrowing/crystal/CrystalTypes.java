package dev.foltz.crystalgrowing.crystal;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import dev.foltz.crystalgrowing.block.BaseCrystalBlock;
import dev.foltz.crystalgrowing.block.CrystalBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleType;
import net.minecraft.state.property.IntProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrystalTypes {
    public static final IntProperty GROWTH_STAGES_5 = IntProperty.of("growth_stage", 0, 4);

    public static final Set<CrystalType> ALL_CRYSTAL_TYPES = new HashSet<>();

    public static final CrystalType REDSTONE_CRYSTAL_TYPE = createCrystalType(
            List.of(Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.REDSTONE_BLOCK),
            5, 2, GROWTH_STAGES_5, CrystalGrowingMod.REDSTONE_CRYSTAL_PARTICLE,
            8, 14, 2, 10
    );

    public static final CrystalType COAL_CRYSTAL_TYPE = createCrystalType(
            List.of(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.COAL_BLOCK),
            5, 1.5f, GROWTH_STAGES_5, CrystalGrowingMod.COAL_CRYSTAL_PARTICLE,
            8, 13, 2, 12
    );

    public static final CrystalType IRON_CRYSTAL_TYPE = createCrystalType(
            List.of(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.IRON_BLOCK, Blocks.RAW_IRON_BLOCK),
            5, 3, GROWTH_STAGES_5, CrystalGrowingMod.IRON_CRYSTAL_PARTICLE,
            8, 12, 2, 9
    );

    public static final CrystalType GOLD_CRYSTAL_TYPE = createCrystalType(
            List.of(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.RAW_GOLD_BLOCK, Blocks.GOLD_BLOCK),
            5, 2, GROWTH_STAGES_5, CrystalGrowingMod.GOLD_CRYSTAL_PARTICLE,
            8, 12, 2, 7
    );

    public static final CrystalType LAPIS_CRYSTAL_TYPE = createCrystalType(
            List.of(Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.LAPIS_BLOCK),
            5, 1.5f, GROWTH_STAGES_5, CrystalGrowingMod.LAPIS_CRYSTAL_PARTICLE,
            8, 14, 2, 8
    );

    public static final CrystalType DIAMOND_CRYSTAL_TYPE = createCrystalType(
            List.of(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DIAMOND_BLOCK),
            5, 5f, GROWTH_STAGES_5, CrystalGrowingMod.DIAMOND_CRYSTAL_PARTICLE,
            8, 13, 2, 7
    );

    public static final CrystalType COPPER_CRYSTAL_TYPE = createCrystalType(
            List.of(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER, Blocks.RAW_COPPER_BLOCK),
            5, 2f, GROWTH_STAGES_5, CrystalGrowingMod.COPPER_CRYSTAL_PARTICLE,
            8, 14, 2, 9
    );

    public static final CrystalType EMERALD_CRYSTAL_TYPE = createCrystalType(
            List.of(Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.EMERALD_BLOCK),
            5, 3.5f, GROWTH_STAGES_5, CrystalGrowingMod.EMERALD_CRYSTAL_PARTICLE,
            8, 13, 2, 10
    );

    public static CrystalType createCrystalType(List<Block> substrates, int growthStages, float daysToGrow, IntProperty growthStagesProperty, ParticleType<?> particleType, int minWidth, int maxWidth, int minHeight, int maxHeight) {
        CrystalType crystalType = new CrystalType(substrates, growthStages, daysToGrow, growthStagesProperty, particleType);
        crystalType.generateBoundingBoxes(minWidth, maxWidth, minHeight, maxHeight);
        ALL_CRYSTAL_TYPES.add(crystalType);
        return crystalType;
    }
}
