package dev.foltz.crystalgrowing.crystal;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.particle.ParticleType;
import net.minecraft.state.property.IntProperty;

import java.util.List;

public class CrystalTypes {
    public static final IntProperty GROWTH_STAGES_5 = IntProperty.of("growth_stage", 0, 4);

    public static final CrystalType REDSTONE_CRYSTAL_TYPE = createCrystalType(
        List.of(Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.REDSTONE_BLOCK), 5, 2, GROWTH_STAGES_5, CrystalGrowingMod.REDSTONE_CRYSTAL_PARTICLE,
        8, 14, 2, 10
    );

    public static final CrystalType COAL_CRYSTAL_TYPE = createCrystalType(
        List.of(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.COAL_BLOCK), 5, 1.5f, GROWTH_STAGES_5, CrystalGrowingMod.COAL_CRYSTAL_PARTICLE,
        8, 13, 2, 12
    );

    public static final CrystalType IRON_CRYSTAL_TYPE = createCrystalType(
            List.of(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.IRON_BLOCK), 5, 3, GROWTH_STAGES_5, CrystalGrowingMod.IRON_CRYSTAL_PARTICLE,
            8, 12, 2, 9
    );

    public static final CrystalType GOLD_CRYSTAL_TYPE = createCrystalType(
            List.of(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.RAW_GOLD_BLOCK, Blocks.GOLD_BLOCK), 5, 2, GROWTH_STAGES_5, CrystalGrowingMod.GOLD_CRYSTAL_PARTICLE,
            8, 12, 2, 7
    );

    public static CrystalType createCrystalType(List<Block> substrates, int growthStages, float daysToGrow, IntProperty growthStagesProperty, ParticleType<?> particleType, int minWidth, int maxWidth, int minHeight, int maxHeight) {
        CrystalType crystalType = new CrystalType(substrates, growthStages, daysToGrow, growthStagesProperty, particleType);
        crystalType.generateBoundingBoxes(minWidth, maxWidth, minHeight, maxHeight);
        return crystalType;
    }
}
