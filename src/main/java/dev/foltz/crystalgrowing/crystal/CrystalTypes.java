package dev.foltz.crystalgrowing.crystal;

import net.minecraft.block.Blocks;
import net.minecraft.state.property.IntProperty;

public class CrystalTypes {
    public static final IntProperty GROWTH_STAGES_5 = IntProperty.of("growth_stage", 0, 4);

    public static final CrystalType REDSTONE_CRYSTAL_TYPE = new CrystalType(Blocks.REDSTONE_ORE, 5, 1, GROWTH_STAGES_5);
}
