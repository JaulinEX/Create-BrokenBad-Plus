package com.jaulinex.createbbplus.register;

import com.jaulinex.createbbplus.CreateBrokenBadPlus;
import com.jaulinex.createbbplus.effect.WhiteRush;
import com.jaulinex.createbbplus.effect.WhiteWithdrawal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CBBPlusMobEffects {
    //Creating a Deferred Register to hold MobEffects which will all be registered under the "createbbplus" namespace
    public static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(Registries.MOB_EFFECT, CreateBrokenBadPlus.MODID);
    //Registering a MobEffect on the mod and putting it in a Holder to use it somewhere else
    public static final DeferredHolder<MobEffect, MobEffect> WHITE_RUSH = MOB_EFFECT.register("white_rush",
            () -> new WhiteRush(MobEffectCategory.BENEFICIAL, 0x00FBFF, ParticleTypes.WHITE_ASH));
    public static final DeferredHolder<MobEffect, MobEffect> WHITE_WITHDRAWAL = MOB_EFFECT.register("white_withdrawal",
            () -> new WhiteWithdrawal(MobEffectCategory.HARMFUL, 0x00FBFF, ParticleTypes.ANGRY_VILLAGER));

    public static void register(final IEventBus modEventBus) {
        MOB_EFFECT.register(modEventBus);
    }
}

