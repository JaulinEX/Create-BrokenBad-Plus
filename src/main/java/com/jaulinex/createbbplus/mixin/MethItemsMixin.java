package com.jaulinex.createbbplus.mixin;


import com.jaulinex.createbbplus.register.CBBPlusMobEffects;
import com.jaulinex.createbbplus.utils;

import com.jetpacker06.CreateBrokenBad.item.MethItem;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class MethItemsMixin {
    //Setting my backdoor to give an item the properties of food
    @Mixin(Item.class)
    public static abstract class MakeEdibleItems implements utils.AddFoodComponent {
        @Mutable
        @Shadow
        private DataComponentMap components;

        @Override
        public void
        setFoodComponent(DataComponentMap nMap) {
            this.components = nMap;
        }
    }

    @Mixin(MethItem.Blue.class)
    public static abstract class onBlueCreation extends MethItem {
        public onBlueCreation(Properties pProperties) {
            super(pProperties);
        }

        @Inject(method = "<init>", at = @At("RETURN"))
        private void becomeEdible(Properties pProperties, CallbackInfo ci) {
            FoodProperties methFood = new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.5f)
                    .alwaysEdible() // Crucial for "medicine" items
                    .build();

            // Made a new Map with the one that already exists and added the food component
            DataComponentMap updatedMap = DataComponentMap.builder()
                    .addAll(this.components()) // components() is a method in the Item class
                    .set(DataComponents.FOOD, methFood)
                    .build();

            // Casting 'this' to the interface implemented into the Item Class via mixin to reach the hidden 'setFoodComponent' method
            ((utils.AddFoodComponent) this).setFoodComponent(updatedMap);
        }
    }

    @Mixin(MethItem.White.class)
    public static abstract class onWhiteCreation extends MethItem {
        public onWhiteCreation(Properties pProperties) {
            super(pProperties);
        }

        @Inject(method = "<init>", at = @At("RETURN"))
        private void becomeEdible(Properties pProperties, CallbackInfo ci) {
            FoodProperties methFood = new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.5f)
                    .alwaysEdible()
                    .effect(() -> new MobEffectInstance(CBBPlusMobEffects.WHITE_RUSH, 300), 1.0F)
                    .build();

            DataComponentMap updatedMap = DataComponentMap.builder()
                    .addAll(this.components())
                    .set(DataComponents.FOOD, methFood)

                    .build();

            ((utils.AddFoodComponent) this).setFoodComponent(updatedMap);
        }
    }

}
