package dev.foltz.crystalgrowing.entity;

import dev.foltz.crystalgrowing.block.BaseCrystalBlock;
import dev.foltz.crystalgrowing.block.CrystalBlocks;
import dev.foltz.crystalgrowing.crystal.CrystalTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

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
        if (isTouchingWater() && attemptGrowOnBlock()) {
            this.getStack().setCount(this.getStack().getCount() - 1);
        }
    }

    protected boolean attemptGrowOnBlock() {
        int half = 6;
        double ox = half - 2 * half * random.nextDouble();
        double oy = half - 2 * half * random.nextDouble();
        double oz = half - 2 * half * random.nextDouble();
        double x = getX() + ox;
        double y = getY() + oy;
        double z = getZ() + oz;
        Direction blockFace = Direction.values()[random.nextInt(Direction.values().length)].getOpposite();
        BlockPos pos = new BlockPos(x, y, z);
        BlockPos growthPos = pos.offset(blockFace);
        Fluid fluid = world.getFluidState(pos).getFluid();
        Block growthBlock = world.getBlockState(growthPos).getBlock();
        boolean waterlogged = fluid == Fluids.WATER && world.getFluidState(pos).isStill() && world.getBlockState(pos).getBlock() == Blocks.WATER;
        if (!waterlogged) {
            return false;
        }

        if (CrystalTypes.REDSTONE_CRYSTAL_TYPE.substrates.contains(growthBlock)) {
            BlockState blockState = CrystalBlocks.REDSTONE_CRYSTAL_BLOCK.getDefaultState()
                .with(BaseCrystalBlock.WATERLOGGED, true)
                .with(BaseCrystalBlock.FACING, blockFace);
            world.setBlockState(pos, blockState);
            return true;
        }
        else if (CrystalTypes.COAL_CRYSTAL_TYPE.substrates.contains(growthBlock)) {
            BlockState blockState = CrystalBlocks.COAL_CRYSTAL_BLOCK.getDefaultState()
                .with(BaseCrystalBlock.WATERLOGGED, true)
                .with(BaseCrystalBlock.FACING, blockFace);
            world.setBlockState(pos, blockState);
            return true;
        }
        else if (CrystalTypes.IRON_CRYSTAL_TYPE.substrates.contains(growthBlock)) {
            BlockState blockState = CrystalBlocks.IRON_CRYSTAL_BLOCK.getDefaultState()
                    .with(BaseCrystalBlock.WATERLOGGED, true)
                    .with(BaseCrystalBlock.FACING, blockFace);
            world.setBlockState(pos, blockState);
            return true;
        }
        else if (CrystalTypes.GOLD_CRYSTAL_TYPE.substrates.contains(growthBlock)) {
            BlockState blockState = CrystalBlocks.GOLD_CRYSTAL_BLOCK.getDefaultState()
                    .with(BaseCrystalBlock.WATERLOGGED, true)
                    .with(BaseCrystalBlock.FACING, blockFace);
            world.setBlockState(pos, blockState);
            return true;
        }
        else if (CrystalTypes.LAPIS_CRYSTAL_TYPE.substrates.contains(growthBlock)) {
            BlockState blockState = CrystalBlocks.LAPIS_CRYSTAL_BLOCK.getDefaultState()
                    .with(BaseCrystalBlock.WATERLOGGED, true)
                    .with(BaseCrystalBlock.FACING, blockFace);
            world.setBlockState(pos, blockState);
            return true;
        }
        else if (CrystalTypes.DIAMOND_CRYSTAL_TYPE.substrates.contains(growthBlock)) {
            BlockState blockState = CrystalBlocks.DIAMOND_CRYSTAL_BLOCK.getDefaultState()
                    .with(BaseCrystalBlock.WATERLOGGED, true)
                    .with(BaseCrystalBlock.FACING, blockFace);
            world.setBlockState(pos, blockState);
            return true;
        }
        else if (CrystalTypes.COPPER_CRYSTAL_TYPE.substrates.contains(growthBlock)) {
            BlockState blockState = CrystalBlocks.COPPER_CRYSTAL_BLOCK.getDefaultState()
                    .with(BaseCrystalBlock.WATERLOGGED, true)
                    .with(BaseCrystalBlock.FACING, blockFace);
            world.setBlockState(pos, blockState);
            return true;
        }
        else if (CrystalTypes.EMERALD_CRYSTAL_TYPE.substrates.contains(growthBlock)) {
            BlockState blockState = CrystalBlocks.EMERALD_CRYSTAL_BLOCK.getDefaultState()
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
