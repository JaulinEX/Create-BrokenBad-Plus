package com.jaulinex.createbbplus.register;


import com.jaulinex.createbbplus.CreateBrokenBadPlus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;


public class CBBPlusDamageTypes {

    public static final DeferredRegister<DamageType> DAMAGETYPE =
            DeferredRegister.create(Registries.DAMAGE_TYPE, CreateBrokenBadPlus.MODID);

    public static final ResourceKey<DamageType> OVERDOSE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(CreateBrokenBadPlus.MODID, "overdose"));

    public static void register(final IEventBus modEventBus) {
        DAMAGETYPE.register(modEventBus);
    }
}
