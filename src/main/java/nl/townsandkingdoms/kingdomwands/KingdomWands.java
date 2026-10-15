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
    public static final DeferredItem<WandItem> EMPIRE=ITEMS.register("empire_wand",()->new WandItem(
        SPARK,EMPIRE_SPARK,BLOOD_SPARK,POISON_SPARK,BLOOD_WAVE,POISON_WAVE,FLAME_WAVE,
        CONFUSE,STUN,CAPTURE,EMPIRE_COMET,FIRE_COMET,EMPIRE_PULSE,FIRE_PULSE,FIREBALL,IGNITE,LAUNCH,LEAP,LIGHTNING,SMITE));
    public static final DeferredItem<WandItem> BLOOD=ITEMS.register("blood_wand",()->new WandItem(BLOOD_WAVE,BLOOD_SPARK,BLOOD_EXPLODE,BLOOD_STUN,BLOOD_BLOCK));
    public static final DeferredItem<WandItem> CELCRIUS=ITEMS.register("celcrius_wand",()->new WandItem(FIRE_COMET,FIRE_TWISTER,FLAME_WAVE,FLAME_SHOCK_WAVE,FLAME_THROWER,FIREBALL));
    public static final DeferredRegister<CreativeModeTab> TABS=DeferredRegister.create(Registries.CREATIVE_MODE_TAB,ID);
    static {
        TABS.register("wands",()->CreativeModeTab.builder().title(Component.literal("Kingdom Wands"))
            .icon(()->EMPIRE.toStack()).displayItems((params,out)->{out.accept(EMPIRE);out.accept(BLOOD);out.accept(CELCRIUS);}).build());
    }
    public static final ModConfigSpec CONFIG;
    public static final ModConfigSpec.BooleanValue PVP,MOUSE_CONTROLS;
    public static final ModConfigSpec.DoubleValue DAMAGE,COOLDOWNS;
    public static final ModConfigSpec.IntValue PARTICLES,MAX_ACTIVE;
    static {
        var b=new ModConfigSpec.Builder();
        PVP=b.comment("Allow spells to affect other players. Server PvP and scoreboard teams are also respected.").define("allowPvp",true);
        MOUSE_CONTROLS=b.comment("Use classic left-click casting. Disable on both sides if another combat mod intercepts attack inputs; use the cast key instead.").define("classicMouseCasting",true);
        DAMAGE=b.defineInRange("damageMultiplier",1.0,0.0,10.0);
        COOLDOWNS=b.defineInRange("cooldownMultiplier",1.0,0.1,10.0);
        PARTICLES=b.comment("Trail density, 1 to 3. Spell collision is independent of visuals.").defineInRange("particleDensity",2,1,3);
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
        root.then(Commands.literal("status").executes(c->{c.getSource().sendSuccess(()->Component.literal("Kingdom Wands 0.1.0-alpha: 3 wands, "+Spell.values().length+" spells; active="+SpellEngine.activeCount()),false);return 1;}));
        var give=Commands.literal("give").requires(c->c.hasPermission(2));
        for(var entry:ITEMS.getEntries())give.then(Commands.literal(entry.getId().getPath()).executes(c->{var p=c.getSource().getPlayerOrException();var stack=entry.get().getDefaultInstance();if(!p.addItem(stack))p.drop(stack,false);return 1;}));
        root.then(give);event.getDispatcher().register(root);
    }
}
