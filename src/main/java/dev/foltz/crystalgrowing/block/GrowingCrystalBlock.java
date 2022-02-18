package dev.foltz.crystalgrowing.block;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class GrowingCrystalBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.FACING;
    public static final int GROWTH_MAX = 3;
    public static final IntProperty GROWTH_STAGE = IntProperty.of("growth_stage", 0, 3);

    public static final VoxelShape BOUNDING_BOX = Block.createCuboidShape(3D, 0D, 3D, 13D, 10D, 13D);

    public GrowingCrystalBlock() {
        super(FabricBlockSettings.of(Material.AMETHYST).noCollision().solidBlock((state, world, pos) -> false).nonOpaque());
        setDefaultState(this.getStateManager().getDefaultState()
            .with(WATERLOGGED, false)
            .with(FACING, Direction.UP)
            .with(GROWTH_STAGE, 0)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, GROWTH_STAGE);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.25) {
            return;
        }

        double range = 3f;
        double sourceX = pos.getX() + 0.5;
        double sourceY = pos.getY() + 0.5;
        double sourceZ = pos.getZ() + 0.5;
        double offsetX = range / 2 - random.nextDouble() * range;
        double offsetY = range / 2 - random.nextDouble() * range;
        double offsetZ = range / 2 - random.nextDouble() * range;
        double x = sourceX + offsetX;
        double y = sourceY + offsetY;
        double z = sourceZ + offsetZ;
        double deltaX = sourceX - x;
        double deltaY = sourceY - y;
        double deltaZ = sourceZ - z;
        double dist = Math.sqrt(deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ);
        dist = Math.max(dist, 0.001);
        double unitX = deltaX / dist;
        double unitY = deltaY / dist;
        double unitZ = deltaZ / dist;
        double speed = 0.003;
        double velX = unitX * speed;
        double velY = unitY * speed;
        double velZ = unitZ * speed;

        if (!world.getFluidState(new BlockPos(x, y, z)).isEmpty()) {
            world.addParticle(CrystalGrowingMod.CRYSTAL_PARTICLE, x, y, z, velX, velY, velZ);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!getFluidState(state).isEmpty() && random.nextFloat() < 0.5f) {
            int currentGrowth = state.get(GROWTH_STAGE);
            if (currentGrowth < GROWTH_MAX) {
                world.setBlockState(pos, state.with(GROWTH_STAGE, currentGrowth + 1));
            }
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        Fluid fluid = world.getFluidState(pos).getFluid();
        Direction placementDirection = ctx.getSide().getOpposite();
        return getDefaultState()
                .with(WATERLOGGED, fluid == Fluids.WATER)
                .with(FACING, placementDirection);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (!state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return super.getOutlineShape(state, world, pos, context);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction);
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(GROWTH_STAGE) < GROWTH_MAX;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }
}
