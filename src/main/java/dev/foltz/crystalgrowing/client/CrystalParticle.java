package dev.foltz.crystalgrowing.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class CrystalParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    protected CrystalParticle(ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider, float r, float g, float b) {
        super(clientWorld, x, y, z);
        this.spriteProvider = spriteProvider;
        setColor(r, g, b);
        this.gravityStrength = 0;
        this.collidesWithWorld = false;
        this.velocityMultiplier = 0.998f;
        setMaxAge(100 + (int) (clientWorld.random.nextFloat() * 150));
        setSpriteForAge(spriteProvider);
    }

    @Override
    public void setSpriteForAge(SpriteProvider spriteProvider) {
        if (!dead) {
            // Doing strange indexing to make the last sprite last longer (by appearing earlier).
            setSprite(spriteProvider.getSprite(Math.min(this.age + 8, this.maxAge), this.maxAge));
        }
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.setSpriteForAge(this.spriteProvider);
        }
    }

    @Override
    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    @Override
    public Particle move(float speed) {
        this.velocityX = speed;
        this.velocityY = speed;
        this.velocityZ = speed;
        return this;
    }

    @Override
    public float getSize(float tickDelta) {
        return super.getSize(tickDelta);
    }

    @Override
    protected int getBrightness(float tint) {
        return super.getBrightness(tint);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz) {
            CrystalParticle crystalParticle = new CrystalParticle(clientWorld, x, y, z, spriteProvider, 1f, 0f, 0f);
            crystalParticle.setVelocity(vx, vy, vz);
            return crystalParticle;
        }
    }
}
