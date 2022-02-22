package dev.foltz.crystalgrowing.block;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import dev.foltz.crystalgrowing.crystal.CrystalType;
import dev.foltz.crystalgrowing.crystal.CrystalTypes;
import net.minecraft.block.Block;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;

import java.util.List;

public class CrystalBlocks {
    public static final Block REDSTONE_CRYSTAL_BLOCK = registerCrystalBlock("redstone_crystal", CrystalTypes.REDSTONE_CRYSTAL_TYPE);
    public static final Block COAL_CRYSTAL_BLOCK = registerCrystalBlock("coal_crystal", CrystalTypes.COAL_CRYSTAL_TYPE);
    public static final Block IRON_CRYSTAL_BLOCK = registerCrystalBlock("iron_crystal", CrystalTypes.IRON_CRYSTAL_TYPE);
    public static final Block GOLD_CRYSTAL_BLOCK = registerCrystalBlock("gold_crystal", CrystalTypes.GOLD_CRYSTAL_TYPE);

    public static BaseCrystalBlock createBlockFromType(CrystalType crystalType) {
        IntProperty growthStages = crystalType.growthStageProperty;
        return new BaseCrystalBlock(crystalType) {
            @Override
            protected List<Property<?>> dynamicProperties() {
                return List.of(growthStages);
            }
        };
    }

    public static Block registerCrystalBlock(String name, CrystalType crystalType) {
        return CrystalGrowingMod.registerBlock(name, createBlockFromType(crystalType));
    }
}
