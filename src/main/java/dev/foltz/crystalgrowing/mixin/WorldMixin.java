package dev.foltz.crystalgrowing.mixin;

import dev.foltz.crystalgrowing.CrystalGrowingMod;
import dev.foltz.crystalgrowing.entity.CrystalPowderItemEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
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
//        else if (entity instanceof LightningEntity lightningEntity) {
//            System.out.println("Got a lightning bolt: " + lightningEntity);
//            Vec3d vel = lightningEntity.getVelocity();
//            BlockPos affectedPos = new BlockPos(vel.x, vel.y - 1.0E-6D, vel.z);
//            System.out.println("Affected block: " + affectedPos);
//        }
        return entity;
    }
}
