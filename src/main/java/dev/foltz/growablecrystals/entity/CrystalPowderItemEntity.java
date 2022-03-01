package dev.foltz.growablecrystals.entity;

import dev.foltz.growablecrystals.block.BaseCrystalBlock;
import dev.foltz.growablecrystals.block.CrystalBlocks;
import dev.foltz.growablecrystals.crystal.CrystalType;
import dev.foltz.growablecrystals.crystal.CrystalTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Optional;

public class CrystalPowderItemEntity extends ItemEntity {
    public CrystalPowderItemEntity(EntityType<? extends ItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public CrystalPowderItemEntity(World world, double x, double y, double z, ItemStack stack, double velocityX, double velocityY, double velocityZ) {
        this(EntityType.ITEM, world);
        this.setPosition(x, y, z);
        this.setVelocity(velocityX, velocityY, velocityZ);
        this.setStack(stack);
    }

    @Override
    public void tick() {
        super.tick();
        if (!isTouchingWater()) {
            return;
        }

        int growths = 0;
        int count = this.getStack().getCount();
        for (int i = 0; i < count; i++) {
            if (random.nextFloat() < 0.1 && attemptGrowOnBlock()) {
                growths += 1;
            }
        }
        this.getStack().setCount(count - growths);
    }

    protected Direction randomDirection() {
        return Direction.values()[world.random.nextInt(Direction.values().length)];
    }

    protected boolean canWalkThrough(BlockState blockState) {
        FluidState fluidState = blockState.getFluidState();
        Block block = blockState.getBlock();
        return CrystalBlocks.ALL_CRYSTAL_BLOCKS.contains(block) ||
                (block == Blocks.WATER && fluidState.getFluid() == Fluids.WATER && fluidState.isStill());
    }

    protected BlockPos randomWalkPos(int maxDist) {
        BlockPos pos = getBlockPos();
        for (int i = 0; i < maxDist; i++) {
            Direction dir = randomDirection();
            BlockPos wanted = pos.offset(dir);
            BlockState blockState = world.getBlockState(wanted);
            if (canWalkThrough(blockState)) {
                pos = wanted;
            }
        }
        return pos;
    }

    protected boolean attemptGrowOnBlock() {
        int maxDist = 24;
        BlockPos pos = randomWalkPos(random.nextInt(maxDist));
        Direction blockFace = randomDirection();
        BlockPos substratePos = pos.offset(blockFace);
        Block substrate = world.getBlockState(substratePos).getBlock();
        Block block = world.getBlockState(pos).getBlock();
        FluidState fluidState = world.getFluidState(pos);
        if (!(block == Blocks.WATER && fluidState.getFluid() == Fluids.WATER && fluidState.isStill())) {
            return false;
        }

        Optional<CrystalType> maybeCrystal = CrystalTypes.ALL_CRYSTAL_TYPES.stream()
                .filter(crystal -> crystal.substrates.contains(substrate))
                .findFirst();

        if (maybeCrystal.isPresent()) {
            CrystalType crystalType = maybeCrystal.get();
            BlockState blockState = crystalType.crystalBlock.getDefaultState()
                    .with(BaseCrystalBlock.WATERLOGGED, true)
                    .with(BaseCrystalBlock.FACING, blockFace);
            world.setBlockState(pos, blockState);
            return true;
        }

        return false;
    }

    public static CrystalPowderItemEntity factory(EntityType<? extends ItemEntity> entityType, World world) {
        return new CrystalPowderItemEntity(entityType, world);
    }
}
