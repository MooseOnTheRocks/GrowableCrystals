package dev.foltz.growablecrystals.crystal;

import dev.foltz.growablecrystals.block.BaseCrystalBlock;
import net.minecraft.block.Block;
import net.minecraft.particle.ParticleType;
import net.minecraft.state.property.IntProperty;

import java.util.List;

public class CrystalType {
    public final List<Block> substrates;
    public final int numGrowthStages;
    public final float numDaysToGrow;
    public final IntProperty growthStageProperty;
    public final ParticleType<?> particleType;
    public BaseCrystalBlock crystalBlock;
    public final int minWidth, maxWidth;
    public final int minHeight, maxHeight;

    public CrystalType(List<Block> substrates, int growthStages, float daysToGrow, IntProperty growthStageProperty, ParticleType<?> particleType, int minWidth, int maxWidth, int minHeight, int maxHeight) {
        this.substrates = substrates;
        this.numGrowthStages = growthStages;
        this.numDaysToGrow = daysToGrow;
        this.growthStageProperty = growthStageProperty;
        this.particleType = particleType;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }
}
