package dev.foltz.crystalgrowing.crystal;

import dev.foltz.crystalgrowing.block.BaseCrystalBlock;
import net.minecraft.block.Block;
import net.minecraft.particle.ParticleType;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
