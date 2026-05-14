package com.jaulinex.createbbplus.register;

import com.jaulinex.createbbplus.CreateBrokenBadPlus;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CBBPlusDataAttachments {
    // Create the DeferredRegister for attachment types
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CreateBrokenBadPlus.MODID);
    // Serialization via codec
    public static final Supplier<AttachmentType<Integer>> DOSE_COUNT = ATTACHMENT_TYPES.register(
            "dose_count", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> LAST_DOSE = ATTACHMENT_TYPES.register(
            "last_dose", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    public static void register(final IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
    }
}
