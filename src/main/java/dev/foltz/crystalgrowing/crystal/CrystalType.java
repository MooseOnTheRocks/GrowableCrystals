package dev.foltz.crystalgrowing.crystal;

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
    public final int numDaysToGrow;
    public final IntProperty growthStageProperty;
    public final ParticleType<?> particleType;
    private Map<Direction, VoxelShape[]> boundingBoxPerStage;

    public CrystalType(List<Block> substrates, int growthStages, int daysToGrow, IntProperty growthStageProperty, ParticleType<?> particleType) {
        this.substrates = substrates;
        this.numGrowthStages = growthStages;
        this.numDaysToGrow = daysToGrow;
        this.growthStageProperty = growthStageProperty;
        this.particleType = particleType;
    }

    public void generateBoundingBoxes(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        boundingBoxPerStage = new HashMap<>();
        boundingBoxPerStage.put(Direction.UP, new VoxelShape[numGrowthStages]);
        boundingBoxPerStage.put(Direction.DOWN, new VoxelShape[numGrowthStages]);
        boundingBoxPerStage.put(Direction.NORTH, new VoxelShape[numGrowthStages]);
        boundingBoxPerStage.put(Direction.EAST, new VoxelShape[numGrowthStages]);
        boundingBoxPerStage.put(Direction.SOUTH, new VoxelShape[numGrowthStages]);
        boundingBoxPerStage.put(Direction.WEST, new VoxelShape[numGrowthStages]);

        for (int i = 0; i < numGrowthStages; i++) {
            double p = (double) i / (double) (numGrowthStages - 1);
            double width = MathHelper.lerp(p, minWidth, maxWidth);
            double height = MathHelper.lerp(p, minHeight, maxHeight);
            double dw = (16 - width) / 2;
            boundingBoxPerStage.get(Direction.UP)[i] = Block.createCuboidShape(dw, 16 - height, dw, 16 - dw, 16, 16 - dw);
            boundingBoxPerStage.get(Direction.DOWN)[i] = Block.createCuboidShape(dw, 0, dw, 16 - dw, height, 16 - dw);
            boundingBoxPerStage.get(Direction.NORTH)[i] = Block.createCuboidShape(dw, dw, 0, 16 - dw, 16 - dw, height);
            boundingBoxPerStage.get(Direction.EAST)[i] = Block.createCuboidShape(16 - height, dw, dw, 16, 16 - dw, 16 - dw);
            boundingBoxPerStage.get(Direction.SOUTH)[i] = Block.createCuboidShape(dw, dw, 16 - height, 16 - dw, 16 - dw, 16);
            boundingBoxPerStage.get(Direction.WEST)[i] = Block.createCuboidShape(0, dw, dw, height, 16 - dw, 16 - dw);
        }
    }

    public VoxelShape getBoundingBox(Direction direction, int growthStage) {
        return boundingBoxPerStage.get(direction)[growthStage];
    }
}
