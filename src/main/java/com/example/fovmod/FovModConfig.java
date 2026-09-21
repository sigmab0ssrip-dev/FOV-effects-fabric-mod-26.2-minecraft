package com.example.fovmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FovModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("fov_customizer.json").toFile();

    // FOV multipliers from 0.0 (0%) to 1.0 (100%)
    public float sprintFov = 1.0f;
    public float potionFov = 1.0f;
    public float witherFov = 1.0f;
    public float swimmingFov = 1.0f;
    public float dolphinGraceFov = 1.0f;
    public float poisonFov = 1.0f;
    public float flyingFov = 1.0f;
    public float nauseaFov = 1.0f;

    public static FovModConfig INSTANCE = load();

    public static FovModConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, FovModConfig.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FovModConfig config = new FovModConfig();
        config.save();
        return config;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
