package com.jaulinex.createbbplus.effect;

import com.jaulinex.createbbplus.CreateBrokenBadPlus;
import com.jaulinex.createbbplus.register.CBBPlusDamageTypes;
import com.jaulinex.createbbplus.register.CBBPlusDataAttachments;
import com.jaulinex.createbbplus.register.CBBPlusMobEffects;
import com.mojang.logging.LogUtils;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;


public class WhiteRush extends MobEffect {

    public WhiteRush(MobEffectCategory category, int color, ParticleOptions particle) {
        super(category, color, particle);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED,
                ResourceLocation.fromNamespaceAndPath(CreateBrokenBadPlus.MODID, "white_rush_speed"),
                1.0F,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.BLOCK_BREAK_SPEED,
                ResourceLocation.fromNamespaceAndPath(CreateBrokenBadPlus.MODID, "white_rush_haste"),
                1.5F,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.JUMP_STRENGTH,
                ResourceLocation.fromNamespaceAndPath(CreateBrokenBadPlus.MODID, "white_rush_jump_boost"),
                0.5F,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        // Check if they are currently suffering withdrawal from a previous dose
        if (entity.hasEffect(CBBPlusMobEffects.WHITE_WITHDRAWAL)) {
            entity.removeEffect(CBBPlusMobEffects.WHITE_WITHDRAWAL);
        }
        // Calculating how long it's been since the last dose
        int timeSinceLD = entity.getServer().getTickCount() - entity.getData(CBBPlusDataAttachments.LAST_DOSE);
        //Clearing dose data if a server restart happened or the specified time has passed
        if (entity.getServer().getTickCount() < entity.getData(CBBPlusDataAttachments.LAST_DOSE) || timeSinceLD >= 1200) {
            entity.setData(CBBPlusDataAttachments.DOSE_COUNT, 1);
        } else {
            entity.setData(CBBPlusDataAttachments.DOSE_COUNT, entity.getData(CBBPlusDataAttachments.DOSE_COUNT) + 1);
        }
        //Overdose warnings & death logic
        double ovchance = Math.random();
        LogUtils.getLogger().info("Chance of Overdose is {}", ovchance); //Just debugging
        DamageSource ovdmg = new DamageSource(entity.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(CBBPlusDamageTypes.OVERDOSE));
        switch (entity.getData(CBBPlusDataAttachments.DOSE_COUNT)) {
            case 1:
                if (entity instanceof Player) {
                    entity.sendSystemMessage(Component.translatable("effect.createbbplus.overdose", "40%").withStyle(ChatFormatting.YELLOW));
                }
                break;
            case 2:
                if (ovchance <= 0.4) {
                    entity.hurt(ovdmg, Float.MAX_VALUE);
                }
                if (entity.isAlive() && entity instanceof Player) {
                    entity.sendSystemMessage(Component.translatable("effect.createbbplus.overdose", "60%").withStyle(ChatFormatting.GOLD));
                }
                break;
            case 3:
                if (ovchance <= 0.6) {
                    entity.hurt(ovdmg, Float.MAX_VALUE);
                }
                if (entity.isAlive() && entity instanceof Player) {
                    entity.sendSystemMessage(Component.translatable("effect.createbbplus.overdose", "90%").withStyle(ChatFormatting.DARK_RED));
                }
                break;
            default:
                if (ovchance <= 0.9) {
                    entity.hurt(ovdmg, Float.MAX_VALUE);
                }
                break;
        }

        //Tracking the time of this dose
        entity.setData(CBBPlusDataAttachments.LAST_DOSE, entity.getServer().getTickCount());
    }

    // Check if the effect is about to end.
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration == 1;
    }

    //If shouldApplyEffectTickThisTick returns true then run applyEffectTick
    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        //Withdrawal effect logic
        if (!entity.level().isClientSide) {
            MobEffectInstance WHInstance = null;
            switch (entity.getData(CBBPlusDataAttachments.DOSE_COUNT)) {
                case 1:
                    WHInstance = new MobEffectInstance(CBBPlusMobEffects.WHITE_WITHDRAWAL, 600);
                    break;
                case 2:
                    WHInstance = new MobEffectInstance(CBBPlusMobEffects.WHITE_WITHDRAWAL, 600, 1);
                    break;
                case 3:
                    WHInstance = new MobEffectInstance(CBBPlusMobEffects.WHITE_WITHDRAWAL, 600, 2);
                    break;
                default:
                    break;

            }
            entity.addEffect(WHInstance);
        }
        return true;
    }

}
