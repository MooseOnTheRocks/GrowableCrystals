package dev.foltz.growablecrystals.block;

import dev.foltz.growablecrystals.GrowableCrystalsMod;
import dev.foltz.growablecrystals.crystal.CrystalType;
import dev.foltz.growablecrystals.crystal.CrystalTypes;
import net.minecraft.block.Block;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrystalBlocks {
    public static final Set<Block> ALL_CRYSTAL_BLOCKS = new HashSet<>();

    public static final BaseCrystalBlock REDSTONE_CRYSTAL_BLOCK = registerCrystalBlock("redstone_crystal", CrystalTypes.REDSTONE_CRYSTAL_TYPE);
    public static final BaseCrystalBlock COAL_CRYSTAL_BLOCK = registerCrystalBlock("coal_crystal", CrystalTypes.COAL_CRYSTAL_TYPE);
    public static final BaseCrystalBlock IRON_CRYSTAL_BLOCK = registerCrystalBlock("iron_crystal", CrystalTypes.IRON_CRYSTAL_TYPE);
    public static final BaseCrystalBlock GOLD_CRYSTAL_BLOCK = registerCrystalBlock("gold_crystal", CrystalTypes.GOLD_CRYSTAL_TYPE);
    public static final BaseCrystalBlock LAPIS_CRYSTAL_BLOCK = registerCrystalBlock("lapis_crystal", CrystalTypes.LAPIS_CRYSTAL_TYPE);
    public static final BaseCrystalBlock DIAMOND_CRYSTAL_BLOCK = registerCrystalBlock("diamond_crystal", CrystalTypes.DIAMOND_CRYSTAL_TYPE);
    public static final BaseCrystalBlock COPPER_CRYSTAL_BLOCK = registerCrystalBlock("copper_crystal", CrystalTypes.COPPER_CRYSTAL_TYPE);
    public static final BaseCrystalBlock EMERALD_CRYSTAL_BLOCK = registerCrystalBlock("emerald_crystal", CrystalTypes.EMERALD_CRYSTAL_TYPE);

    public static BaseCrystalBlock createCrystalBlockFromType(CrystalType crystalType) {
        IntProperty growthStages = crystalType.growthStageProperty;
        BaseCrystalBlock crystalBlock = new BaseCrystalBlock(crystalType) {
            @Override
            protected List<Property<?>> dynamicProperties() {
                return List.of(growthStages);
            }
        };
        crystalBlock.generateBoundingBoxes(crystalType.minWidth, crystalType.maxWidth, crystalType.minHeight, crystalType.maxHeight);
        crystalType.crystalBlock = crystalBlock;
        ALL_CRYSTAL_BLOCKS.add(crystalBlock);
        return crystalBlock;
    }

    public static BaseCrystalBlock registerCrystalBlock(String name, CrystalType crystalType) {
        return (BaseCrystalBlock) GrowableCrystalsMod.registerBlock(name, createCrystalBlockFromType(crystalType));
    }
}
