package com.jaulinex.createbbplus.effect;

import com.jaulinex.createbbplus.CreateBrokenBadPlus;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;


public class WhiteWithdrawal extends MobEffect {

    public WhiteWithdrawal(MobEffectCategory category, int color, ParticleOptions particle) {
        super(category, color, particle);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED,
                ResourceLocation.fromNamespaceAndPath(CreateBrokenBadPlus.MODID, "white_withdrawal_speed"),
                -0.3F,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.BLOCK_BREAK_SPEED,
                ResourceLocation.fromNamespaceAndPath(CreateBrokenBadPlus.MODID, "white_withdrawal_haste"),
                -0.4F,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED,
                ResourceLocation.fromNamespaceAndPath(CreateBrokenBadPlus.MODID, "white_withdrawal_haste"),
                -0.3F,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    //Remove Saturation
    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            player.getFoodData().setSaturation(0);
        }
    }

    // Always apply hunger .
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    //Hunger Effect
    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            switch (amplifier) {
                case 0:
                    player.causeFoodExhaustion(0.055F);
                    break;
                case 1:
                    player.causeFoodExhaustion(0.08F);
                    break;
                default:
                    player.causeFoodExhaustion(0.12F);
                    break;
            }
        }
        return true;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }


}
