package nl.townsandkingdoms.resonantarcana;

import static nl.townsandkingdoms.resonantarcana.Spell.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.commands.Commands;

@Mod(ResonantArcana.ID)
public final class ResonantArcana {
    public static final String ID="resonant_arcana";
    public static final org.slf4j.Logger LOG=LogUtils.getLogger();
    public static final DeferredRegister.Items ITEMS=DeferredRegister.createItems(ID);
    public static final DeferredItem<WandItem> DEBUG=ITEMS.register("debug_wand",()->new WandItem("debug_wand",Spell.enabledValues()));
    public static final DeferredItem<LegendarySpellBook> RUNIC_ECHOES=ITEMS.register("runic_echoes_spell_book",()->new LegendarySpellBook(LegendarySpellBook.Style.RUNIC));
    public static final DeferredItem<LegendarySpellBook> PHOENIX=ITEMS.register("phoenix_spell_book",()->new LegendarySpellBook(LegendarySpellBook.Style.PHOENIX));
    public static final DeferredItem<LegendarySpellBook> DEATHCHILL=ITEMS.register("deathchill_spell_book",()->new LegendarySpellBook(LegendarySpellBook.Style.DEATHCHILL));
    public static final DeferredRegister<CreativeModeTab> TABS=DeferredRegister.create(Registries.CREATIVE_MODE_TAB,ID);
    static {
        TABS.register("equipment",()->CreativeModeTab.builder().title(Component.literal("Resonant Arcana · Equipment"))
            .icon(()->RUNIC_ECHOES.toStack()).displayItems((params,out)->ITEMS.getEntries().forEach(entry->out.accept(entry.get()))).build());
        TABS.register("spells",()->CreativeModeTab.builder().title(Component.literal("Resonant Arcana · Spells"))
            .icon(()->IronsCompat.scroll(Spell.SPARK))
            .displayItems((params,out)->{for(Spell spell:SpellIndex.ordered())if(io.redspace.ironsspellbooks.api.registry.SpellRegistry.REGISTRY.get(IronsCompat.id(spell)).isEnabled())out.accept(IronsCompat.scroll(spell));}).build());
    }
    public ResonantArcana(IEventBus bus,ModContainer container) {
        IntegrationCondition.register(bus);ExplosionRitual.SOUNDS.register(bus);
        AdvancedSpells.register();
        VoidExile.register();CavesCompat.register(bus);
        NeoForge.EVENT_BUS.addListener(SpellIndex::tooltip);
        SpellCentrepieces.register(bus);
        BossesRiseCompat.register(bus);IronsCompat.register(bus);
        ArcanaEffects.REGISTER.register(bus);ITEMS.register(bus);TABS.register(bus);container.registerConfig(ModConfig.Type.SERVER,ArcanaConfig.SERVER,"resonantarcana-server.toml");
        container.registerConfig(ModConfig.Type.CLIENT,ArcanaConfig.CLIENT,"resonantarcana-client.toml");
        SpellCatalogue.validateRegistry();
        bus.addListener(WandNetwork::register);
        NeoForge.EVENT_BUS.addListener((ServerTickEvent.Post e)->SpellEngine.tick());
        NeoForge.EVENT_BUS.addListener((ServerStoppedEvent e)->SpellEngine.clear());
        NeoForge.EVENT_BUS.addListener(ResonantArcana::commands);
        NeoForge.EVENT_BUS.addListener(IntegrationChecks::start);
    }
    private static void commands(RegisterCommandsEvent event) {
        var root=Commands.literal("resonantarcana");
        root.then(Commands.literal("status").executes(c->{c.getSource().sendSuccess(()->Component.literal("Resonant Arcana 0.2.0: 3 legendary spellbooks, 1 debug wand, "+Spell.enabledValues().length+" spells; active="+SpellEngine.activeCount()),false);return 1;}));
        var give=Commands.literal("give").requires(c->c.hasPermission(2));
        for(var entry:ITEMS.getEntries())give.then(Commands.literal(entry.getId().getPath()).executes(c->{var p=c.getSource().getPlayerOrException();var stack=entry.get().getDefaultInstance();if(!p.addItem(stack))p.drop(stack,false);return 1;}));
        root.then(give);
        root.then(Commands.literal("info").executes(c->{
            var p=c.getSource().getPlayerOrException();var stack=p.getMainHandItem();
            if(!(stack.getItem() instanceof WandItem wand)){c.getSource().sendFailure(Component.literal("Hold the debug wand to inspect its selected spell."));return 0;}
            for(var line:SpellInfo.details(wand.selected(stack),wand.level(stack)))c.getSource().sendSuccess(()->line,false);return 1;
        }));
        var spells=Commands.literal("spell");var scrolls=Commands.literal("scroll").requires(c->c.hasPermission(2));
        for(Spell spell:Spell.enabledValues()){
            spells.then(Commands.literal(spell.bookId()).executes(c->{for(var line:SpellInfo.details(spell,1))c.getSource().sendSuccess(()->line,false);return 1;})
                .then(Commands.argument("level",com.mojang.brigadier.arguments.IntegerArgumentType.integer(1,spell.levelCap())).executes(c->{int level=com.mojang.brigadier.arguments.IntegerArgumentType.getInteger(c,"level");for(var line:SpellInfo.details(spell,level))c.getSource().sendSuccess(()->line,false);return 1;})));
            scrolls.then(Commands.literal(spell.bookId()).then(Commands.argument("level",com.mojang.brigadier.arguments.IntegerArgumentType.integer(1,spell.levelCap())).executes(c->{
                var p=c.getSource().getPlayerOrException();var item=IronsCompat.scroll(spell,com.mojang.brigadier.arguments.IntegerArgumentType.getInteger(c,"level"));if(!p.addItem(item))p.drop(item,false);return 1;
            })));
        }
        root.then(spells);root.then(scrolls);event.getDispatcher().register(root);
    }
}
