package dev.foltz.crystalgrowing.item;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import dev.foltz.crystalgrowing.block.BaseCrystalBlock;
import dev.foltz.crystalgrowing.block.CrystalBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SeedCrystalItem extends BlockItem {
    public SeedCrystalItem() {
        super(Blocks.AMETHYST_CLUSTER, new FabricItemSettings().group(ItemGroup.MISC));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        return super.place(context);
    }

    @Override
    protected SoundEvent getPlaceSound(BlockState state) {
        return super.getPlaceSound(state);
    }

    @Nullable
    @Override
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        return super.getPlacementContext(context);
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        return super.postPlacement(pos, world, player, stack, state);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockState = context.getWorld().getBlockState(context.getBlockPos().offset(context.getSide().getOpposite()));
        System.out.println("getPlacementState: " + blockState);
        if (blockState.isOf(Blocks.REDSTONE_ORE)) {
            BlockState crystal = CrystalBlocks.REDSTONE_CRYSTAL_BLOCK.getPlacementState(context);
            FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
            if (!fluidState.isEmpty() && fluidState.getFluid() == Fluids.WATER && crystal != null) {
                crystal.with(BaseCrystalBlock.WATERLOGGED, true);
            }
            return this.canPlace(context, crystal) ? crystal : null;
        }
        return null;
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        return super.canPlace(context, state);
    }

    @Override
    protected boolean checkStatePlacement() {
        return super.checkStatePlacement();
    }

    @Override
    protected boolean place(ItemPlacementContext context, BlockState state) {
        return super.place(context, state);
    }

    @Override
    public String getTranslationKey() {
        return super.getOrCreateTranslationKey();
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            stacks.add(new ItemStack(this));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
    }

    @Override
    public boolean canBeNested() {
        return super.canBeNested();
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        super.onItemEntityDestroyed(entity);
    }
}
