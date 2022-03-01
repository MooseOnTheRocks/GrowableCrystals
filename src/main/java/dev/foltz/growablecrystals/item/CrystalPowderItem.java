package dev.foltz.growablecrystals.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class CrystalPowderItem extends Item {
    public CrystalPowderItem() {
        super(new FabricItemSettings().group(ItemGroup.MISC));
    }
}
