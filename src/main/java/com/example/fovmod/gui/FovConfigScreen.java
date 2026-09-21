package com.example.fovmod.gui;

import com.example.fovmod.FovModConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class FovConfigScreen extends Screen {
    private final Screen parent;

    public FovConfigScreen(Screen parent) {
        super(Text.literal("FOV Customizer Menu"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 155;
        int y = 40;

        // Column 1
        this.addDrawableChild(new FovSlider(x, y, "Sprint FOV", FovModConfig.INSTANCE.sprintFov, val -> FovModConfig.INSTANCE.sprintFov = val));
        this.addDrawableChild(new FovSlider(x, y + 24, "Potion FOV", FovModConfig.INSTANCE.potionFov, val -> FovModConfig.INSTANCE.potionFov = val));
        this.addDrawableChild(new FovSlider(x, y + 48, "Wither FOV", FovModConfig.INSTANCE.witherFov, val -> FovModConfig.INSTANCE.witherFov = val));
        this.addDrawableChild(new FovSlider(x, y + 72, "Swimming FOV", FovModConfig.INSTANCE.swimmingFov, val -> FovModConfig.INSTANCE.swimmingFov = val));

        // Column 2
        int x2 = this.width / 2 + 5;
        this.addDrawableChild(new FovSlider(x2, y, "Dolphin Grace FOV", FovModConfig.INSTANCE.dolphinGraceFov, val -> FovModConfig.INSTANCE.dolphinGraceFov = val));
        this.addDrawableChild(new FovSlider(x2, y + 24, "Poison FOV", FovModConfig.INSTANCE.poisonFov, val -> FovModConfig.INSTANCE.poisonFov = val));
        this.addDrawableChild(new FovSlider(x2, y + 48, "Flying FOV", FovModConfig.INSTANCE.flyingFov, val -> FovModConfig.INSTANCE.flyingFov = val));
        this.addDrawableChild(new FovSlider(x2, y + 72, "Nausea FOV", FovModConfig.INSTANCE.nauseaFov, val -> FovModConfig.INSTANCE.nauseaFov = val));

        // Save & Close Button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> {
            FovModConfig.INSTANCE.save();
            this.client.setScreen(this.parent);
        }).dimensions(this.width / 2 - 100, y + 110, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    private static class FovSlider extends SliderWidget {
        private final String label;
        private final Consumer<Float> consumer;

        public FovSlider(int x, int y, String label, float value, Consumer<Float> consumer) {
            super(x, y, 150, 20, Text.literal(label + ": " + (int)(value * 100) + "%"), value);
            this.label = label;
            this.consumer = consumer;
        }

        @Override
        protected void updateMessage() {
            this.setMessage(Text.literal(label + ": " + (int)(this.value * 100) + "%"));
        }

        @Override
        protected void applyValue() {
            this.consumer.accept((float) this.value);
        }
    }
}
