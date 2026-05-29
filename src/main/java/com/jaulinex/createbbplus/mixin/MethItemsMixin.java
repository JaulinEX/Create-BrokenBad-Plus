package com.jaulinex.createbbplus.mixin;


import com.jaulinex.createbbplus.register.CBBPlusMobEffects;
import com.jaulinex.createbbplus.utils;

import com.jetpacker06.CreateBrokenBad.block.TrayBlock;
import com.jetpacker06.CreateBrokenBad.item.MethItem;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

public class MethItemsMixin {
    //Setting my backdoor to give an already registered item custom properties
    @Mixin(Item.class)
    public static abstract class UpdateItemComponents implements utils.EditItemComponents {
        @Mutable
        @Shadow
        private DataComponentMap components;

        @Override
        public void
        setItemComponents(DataComponentMap nMap) {
            this.components = nMap;
        }
    }

    //Fixing Eating when looking at a block
    @Mixin(MethItem.class)
    public static abstract class onMethUse extends Item {
        public onMethUse(Properties properties) {
            super(properties);
        }

        //Letting the item interaction to continue if the user is right-clicking a block in order to be able to eat the item when looking at a block
        @SuppressWarnings("InvalidInjectorMethodSignature")
        //Suppressed the warning because I'm getting a local variable of the method, and it will never match the signature of the og method
        @Inject(method = "useOn", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
        private void onUse(UseOnContext pContext, CallbackInfoReturnable<InteractionResult> cir, Block clickedBlock) {
            if (!(clickedBlock instanceof TrayBlock.Empty)) {
                cir.setReturnValue(InteractionResult.PASS);
            }
        }
    }

    @Mixin(MethItem.Blue.class)
    public static abstract class onBlueCreation extends MethItem {
        public onBlueCreation(Properties pProperties) {
            super(pProperties);
        }

        @Inject(method = "<init>", at = @At("RETURN"))
        private void editMethItemComponents(Properties pProperties, CallbackInfo ci) {
            //Creating the food component
            FoodProperties methFood = new FoodProperties.Builder()
                    .nutrition(0)
                    .saturationModifier(0f)
                    .alwaysEdible()
                    .build();
            //Creating the list of "components" lines to add to the lore of the item
            List<Component> lore = new ArrayList<>();
            //Creating the lines for the lore of the item
            Component loreline = Component.translatable("lore.createbbplus.blue_meth").withStyle(ChatFormatting.AQUA);
            Component warningline = Component.translatable("lore.createbbplus.overdose_warning").withStyle(ChatFormatting.DARK_RED, ChatFormatting.UNDERLINE);
            lore.add(loreline);
            lore.add(Component.empty());// Line jump
            lore.add(warningline);
            //Creating the lore component using the list of lines that I created
            ItemLore methLore = new ItemLore(lore);

            // Made a new Map of components with the one that already exists and added the food and lore component
            DataComponentMap updatedMap = DataComponentMap.builder()
                    .addAll(this.components()) // components() is a method in the Item class
                    .set(DataComponents.FOOD, methFood)
                    .set(DataComponents.LORE, methLore)
                    .build();

            // Casting 'this' to the interface implemented into the Item Class via mixin to reach the hidden 'setFoodComponent' method
            ((utils.EditItemComponents) this).setItemComponents(updatedMap);
        }
    }

    @Mixin(MethItem.White.class)
    public static abstract class onWhiteCreation extends MethItem {
        public onWhiteCreation(Properties pProperties) {
            super(pProperties);
        }

        @Inject(method = "<init>", at = @At("RETURN"))
        private void editMethItemComponents(Properties pProperties, CallbackInfo ci) {
            FoodProperties methFood = new FoodProperties.Builder()
                    .nutrition(0)
                    .saturationModifier(0f)
                    .alwaysEdible()
                    .effect(() -> new MobEffectInstance(CBBPlusMobEffects.WHITE_RUSH, 300), 1.0F)
                    .build();

            List<Component> lore = new ArrayList<>();
            Component loreline = Component.translatable("lore.createbbplus.white_meth").withStyle(ChatFormatting.WHITE);
            Component warningline = Component.translatable("lore.createbbplus.overdose_warning").withStyle(ChatFormatting.DARK_RED, ChatFormatting.UNDERLINE);
            lore.add(loreline);
            lore.add(Component.empty());
            lore.add(warningline);
            ItemLore methLore = new ItemLore(lore);

            DataComponentMap updatedMap = DataComponentMap.builder()
                    .addAll(this.components())
                    .set(DataComponents.FOOD, methFood)
                    .set(DataComponents.LORE, methLore)
                    .build();

            ((utils.EditItemComponents) this).setItemComponents(updatedMap);
        }
    }

}
