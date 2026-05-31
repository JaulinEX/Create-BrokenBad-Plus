package com.jaulinex.createbbplus.mixin;

import com.jaulinex.createbbplus.Utils;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;


public class UtilsMixin {
    //Setting my backdoor to give an already registered item custom properties
    @Mixin(Item.class)
    public static abstract class UpdateItemComponents implements Utils.Interfaces.EditItemComponents {
        @Mutable
        @Shadow
        private DataComponentMap components;

        @Override
        public void
        setItemComponents(DataComponentMap nMap) {
            this.components = nMap;
        }
    }


}
