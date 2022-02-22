package dev.foltz.crystalgrowing.block;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import dev.foltz.crystalgrowing.crystal.CrystalType;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.List;
import java.util.Random;

public class BaseCrystalBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.FACING;
    public final IntProperty GROWTH_STAGE;
    public final CrystalType crystalType;

    public BaseCrystalBlock(CrystalType crystalType) {
        super(FabricBlockSettings.of(Material.AMETHYST).noCollision().solidBlock((state, world, pos) -> false).nonOpaque());
        this.crystalType = crystalType;
        GROWTH_STAGE = crystalType.growthStageProperty;
        setDefaultState(this.getStateManager().getDefaultState()
                .with(WATERLOGGED, false)
                .with(FACING, Direction.UP)
                .with(GROWTH_STAGE, 0)
        );
    }

    public float numDaysToGrow() {
        return crystalType.numDaysToGrow;
    }

    public int numGrowthStages() {
        return crystalType.numGrowthStages;
    }

    protected List<Property<?>> dynamicProperties() {
        return List.of();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
        dynamicProperties().forEach(builder::add);
    }

    public void spawnParticles(World world, BlockPos pos, Random random) {
        double range = 4.5f;
        double sourceX = pos.getX() + 0.5;
        double sourceY = pos.getY() + 0.5;
        double sourceZ = pos.getZ() + 0.5;
        double offsetX = range / 2 - random.nextDouble() * range;
        double offsetY = range / 2 - random.nextDouble() * range;
        double offsetZ = range / 2 - random.nextDouble() * range;
        double x = sourceX + offsetX;
        double y = sourceY + offsetY;
        double z = sourceZ + offsetZ;
        FluidState fluidState = world.getFluidState(new BlockPos(x, y, z));
        // Max water-level is 8.
        if (fluidState.isEmpty() || fluidState.getFluid() != Fluids.WATER || fluidState.getLevel() != 8) {
            return;
        }

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
            world.addParticle((DefaultParticleType) crystalType.particleType, x, y, z, velX, velY, velZ);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!canGrow(state)) {
            return;
        }

        spawnParticles(world, pos, random);
    }

    public float chanceToGrowPerRandomTick() {
        float randomTicksPerDay = 3f / 4096f * 24000f;
        return numGrowthStages() / (numDaysToGrow() * randomTicksPerDay);
    }

    public boolean canGrow(BlockState state) {
        return state.get(WATERLOGGED);
    }

    public void tryGrow(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (canGrow(state) && random.nextFloat() < chanceToGrowPerRandomTick()) {
            int currentGrowth = state.get(GROWTH_STAGE);
            if (currentGrowth < numGrowthStages() - 1) {
                world.setBlockState(pos, state.with(GROWTH_STAGE, currentGrowth + 1));
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        tryGrow(state, world, pos, random);
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
        Direction direction = state.get(FACING);
        int growthStage = state.get(GROWTH_STAGE);
        return crystalType.getBoundingBox(direction, growthStage);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction);
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(GROWTH_STAGE) < numGrowthStages() - 1;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }
}
