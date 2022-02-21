package dev.foltz.crystalgrowing.crystal;

import net.minecraft.block.Block;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

import java.util.HashMap;
import java.util.Map;

public class CrystalType {
    public final Block substrate;
    public final int numGrowthStages;
    public final int numDaysToGrow;
    public final IntProperty growthStageProperty;

    private final Map<Direction, VoxelShape[]> boundingBoxPerStage;

    public CrystalType(Block substrate, int growthStages, int daysToGrow, IntProperty growthStageProperty) {
        this.substrate = substrate;
        this.numGrowthStages = growthStages;
        this.numDaysToGrow = daysToGrow;
        this.growthStageProperty = growthStageProperty;
        this.boundingBoxPerStage = generateBoundingBoxes(growthStages, 8, 14, 2, 10);
    }

    public static Map<Direction, VoxelShape[]> generateBoundingBoxes(int numStages, int minWidth, int maxWidth, int minHeight, int maxHeight) {
        Map<Direction, VoxelShape[]> boxes = new HashMap<>();
        boxes.put(Direction.UP, new VoxelShape[numStages]);
        boxes.put(Direction.DOWN, new VoxelShape[numStages]);
        boxes.put(Direction.NORTH, new VoxelShape[numStages]);
        boxes.put(Direction.EAST, new VoxelShape[numStages]);
        boxes.put(Direction.SOUTH, new VoxelShape[numStages]);
        boxes.put(Direction.WEST, new VoxelShape[numStages]);

        for (int i = 0; i < numStages; i++) {
            double p = (double) i / (double) (numStages - 1);
            double width = MathHelper.lerp(p, minWidth, maxWidth);
            double height = MathHelper.lerp(p, minHeight, maxHeight);
            double dw = (16 - width) / 2;
            boxes.get(Direction.UP)[i] = Block.createCuboidShape(dw, 16 - height, dw, 16 - dw, 16, 16 - dw);
            boxes.get(Direction.DOWN)[i] = Block.createCuboidShape(dw, 0, dw, 16 - dw, height, 16 - dw);
            boxes.get(Direction.NORTH)[i] = Block.createCuboidShape(dw, dw, 0, 16 - dw, 16 - dw, height);
            boxes.get(Direction.EAST)[i] = Block.createCuboidShape(16 - height, dw, dw, 16, 16 - dw, 16 - dw);
            boxes.get(Direction.SOUTH)[i] = Block.createCuboidShape(dw, dw, 16 - height, 16 - dw, 16 - dw, 16);
            boxes.get(Direction.WEST)[i] = Block.createCuboidShape(0, dw, dw, height, 16 - dw, 16 - dw);
        }

        return boxes;
    }

    public VoxelShape getBoundingBox(Direction direction, int growthStage) {
        return boundingBoxPerStage.get(direction)[growthStage];
    }
}
