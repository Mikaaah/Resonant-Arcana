package nl.townsandkingdoms.kingdomwands;

import static nl.townsandkingdoms.kingdomwands.Spell.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.commands.Commands;

@Mod(KingdomWands.ID)
public final class KingdomWands {
    public static final String ID="kingdomwands";
    public static final org.slf4j.Logger LOG=LogUtils.getLogger();
    public static final DeferredRegister.Items ITEMS=DeferredRegister.createItems(ID);
    public static final DeferredItem<WandItem> EMPIRE=ITEMS.register("empire_wand",()->new WandItem("empire_wand",SPARK,EMPIRE_SPARK,CONFUSE,STUN,CAPTURE,EMPIRE_COMET,EMPIRE_PULSE,LAUNCH,LEAP,ARCANE_VOLLEY));
    public static final DeferredItem<WandItem> BLOOD=ITEMS.register("blood_wand",()->new WandItem("blood_wand",BLOOD_WAVE,BLOOD_SPARK,BLOOD_EXPLODE,BLOOD_STUN,BLOOD_BLOCK,SANGUINE_DRAIN,BLOOD_LANCE));
    public static final DeferredItem<WandItem> CELCRIUS=ITEMS.register("celcrius_wand",()->new WandItem("celcrius_wand",FIRE_COMET,FIRE_TWISTER,FLAME_WAVE,FLAME_SHOCK_WAVE,FLAME_THROWER,FIREBALL,IGNITE,EMBER_VEIL));
    public static final DeferredItem<WandItem> DARK=ITEMS.register("dark_wand",()->new WandItem("dark_wand",SHADOW_BOLT,SOUL_SEEKER,DARK_WAVE,NIGHTFALL,SHADOW_CAGE,SOUL_AURA));
    public static final DeferredItem<WandItem> THUNDER=ITEMS.register("thunder_wand",()->new WandItem("thunder_wand",THUNDER_BOLT,CHAIN_LIGHTNING,THUNDER_WAVE,STORM_CALL,THUNDER_PULSE,STATIC_SHOCK,LIGHTNING,SMITE));
    public static final DeferredItem<WandItem> MEPHI=ITEMS.register("mephi_wand",()->new WandItem("mephi_wand",GRAVITY_BOLT,GRAVITY_WELL,FORCE_PUSH,FORCE_PULL,GRAVITY_CHOKE,SINGULARITY));
    public static final DeferredItem<WandItem> SOUL=ITEMS.register("soul_wand",()->new WandItem("soul_wand",ECHO_BOLT,SCULK_WAVE,SONIC_BOOM,SOUL_SIPHON,SOUL_PRISON,SHRIEKING_PULSE,AMETHYST_NOVA,ECHO_WARD,SOUL_CANNON));
    public static final DeferredItem<WandItem> VENOM=ITEMS.register("venom_wand",()->new WandItem("venom_wand",POISON_SPARK,POISON_WAVE,VENOM_BOLT,MIASMA_FIELD,WITHERING_HEX));
    public static final DeferredRegister<CreativeModeTab> TABS=DeferredRegister.create(Registries.CREATIVE_MODE_TAB,ID);
    static {
        TABS.register("wands",()->CreativeModeTab.builder().title(Component.literal("Kingdom Wands"))
            .icon(()->EMPIRE.toStack()).displayItems((params,out)->{ITEMS.getEntries().forEach(entry->out.accept(entry.get()));}).build());
    }
    public static final ModConfigSpec CONFIG;
    public static final ModConfigSpec.BooleanValue PVP,MOUSE_CONTROLS,CASTER_IMPACTS,AAA_PARTICLES;
    public static final ModConfigSpec.DoubleValue DAMAGE,COOLDOWNS,SOUND_VOLUME,IMPACT_VOLUME;
    public static final ModConfigSpec.IntValue PARTICLES,MAX_ACTIVE,VISUAL_BUDGET,AAA_BUDGET;
    static {
        var b=new ModConfigSpec.Builder();
        PVP=b.comment("Allow spells to affect other players. Server PvP and scoreboard teams are also respected.").define("allowPvp",true);
        MOUSE_CONTROLS=b.comment("Use classic left-click casting. Disable on both sides if another combat mod intercepts attack inputs; use the cast key instead.").define("classicMouseCasting",true);
        DAMAGE=b.defineInRange("damageMultiplier",1.0,0.0,10.0);
        SOUND_VOLUME=b.comment("Spell sound volume multiplier; normal player sound settings still apply.").defineInRange("soundVolume",1.0,0.0,2.0);
        IMPACT_VOLUME=b.comment("Impact audio multiplier, applied in addition to soundVolume.").defineInRange("impactVolume",1.0,0.0,3.0);
        CASTER_IMPACTS=b.comment("Play your own impact feedback directly so ranged hits remain audible. Nearby players hear positional impacts.").define("casterImpactFeedback",true);
        COOLDOWNS=b.defineInRange("cooldownMultiplier",1.0,0.1,10.0);
        AAA_PARTICLES=b.comment("Use optional AAAParticles spell visuals on this client when installed. Vanilla particles remain enabled.").define("aaaParticles",true);
        AAA_BUDGET=b.comment("Maximum new AAAParticles effects per client tick. Each effect contains multiple animated particles.").defineInRange("maxAaaEffectsPerTick",32,8,96);
        PARTICLES=b.comment("Trail density, 1 to 3. Spell collision is independent of visuals.").defineInRange("particleDensity",2,1,3);
        VISUAL_BUDGET=b.comment("Maximum detailed trail points per server tick, shared by active spells. Gameplay is independent of this visual budget.").defineInRange("maxParticlePointsPerTick",256,64,2048);
        MAX_ACTIVE=b.comment("Maximum active timed spells per server; additional casts fail without starting cooldown.").defineInRange("maxActiveSpells",128,8,512);
        CONFIG=b.build();
    }
    public KingdomWands(IEventBus bus,ModContainer container) {
        ITEMS.register(bus);TABS.register(bus);container.registerConfig(ModConfig.Type.COMMON,CONFIG);
        bus.addListener(WandNetwork::register);
        NeoForge.EVENT_BUS.addListener((ServerTickEvent.Post e)->SpellEngine.tick());
        NeoForge.EVENT_BUS.addListener((ServerStoppedEvent e)->SpellEngine.clear());
        NeoForge.EVENT_BUS.addListener(KingdomWands::commands);
        NeoForge.EVENT_BUS.addListener(IntegrationChecks::start);
    }
    private static void commands(RegisterCommandsEvent event) {
        var root=Commands.literal("kingdomwands");
        root.then(Commands.literal("status").executes(c->{c.getSource().sendSuccess(()->Component.literal("Kingdom Wands 0.7.0-alpha: 8 wands, "+Spell.values().length+" spells; active="+SpellEngine.activeCount()),false);return 1;}));
        var give=Commands.literal("give").requires(c->c.hasPermission(2));
        for(var entry:ITEMS.getEntries())give.then(Commands.literal(entry.getId().getPath()).executes(c->{var p=c.getSource().getPlayerOrException();var stack=entry.get().getDefaultInstance();if(!p.addItem(stack))p.drop(stack,false);return 1;}));
        root.then(give);event.getDispatcher().register(root);
    }
}
