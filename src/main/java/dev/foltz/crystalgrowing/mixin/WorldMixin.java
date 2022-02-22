package dev.foltz.crystalgrowing.mixin;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import dev.foltz.crystalgrowing.entity.CrystalPowderItemEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerWorld.class)
public class WorldMixin {
    @ModifyVariable(method = "spawnEntity(Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public Entity onSpawnEntity(Entity entity) {
        if (entity instanceof ItemEntity itemEntity) {
            if (itemEntity.getStack().getItem() == CrystalGrowingMod.CRYSTAL_POWDER_ITEM && !(entity instanceof CrystalPowderItemEntity)) {
                Vec3d vel = entity.getVelocity();
                ItemEntity crystalEntity = new CrystalPowderItemEntity(entity.world, entity.getX(), entity.getY(), entity.getZ(), itemEntity.getStack(), vel.x, vel.y, vel.z);
                crystalEntity.setPickupDelay(20);
                return crystalEntity;
            }
        }
        return entity;
    }
}
