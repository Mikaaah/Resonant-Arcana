package nl.townsandkingdoms.resonantarcana;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

/** Optional boundary: common spell registration never links an Alex's Caves API class. */
final class CavesCompat {
    static final String MOD="alexscaves";
    static boolean optional(Spell spell){return "alexscaves".equals(spell.definition.integration());}
    static boolean available(){return ModList.get().getModContainerById(MOD).map(m->m.getModInfo().getVersion().toString().equals("2.0.10")).orElse(false);}
    static boolean cataclysm(Spell s){return optional(s)&&"CATACLYSM".equals(s.definition.tier());}
    static void register(IEventBus bus){if(available()){
        CaveVisual.register(bus);
        if(net.neoforged.fml.loading.FMLEnvironment.dist==net.neoforged.api.distmarker.Dist.CLIENT)CavesClient.register(bus);
    }}
    static int beamVariant(net.minecraft.world.entity.player.Player player){var id=net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(player.getOffhandItem().getItem()).getPath();return id.equals("amber_curiosity")?1:id.equals("tectonic_shard")?2:0;}
    static String chargeSound(Spell s){return MOD+":"+switch(s){
        case POLARITY_BOLT,MAGNETAR_ANNIHILATION -> "raygun_start";case EMP_SURGE -> "magnetron_assemble";
        case AMBER_TALON,TECTONIC_STAMPEDE -> "extinction_spear_summon";case LUX_EXTINCTION -> "luxtructosaurus_summon";
        case ACID_GLOB -> "acid_corrosion";case TREMORZILLA_BREATH -> "tremorzilla_charge_normal";case NUCLEAR_STRIKE -> "nuclear_furnace_active_supercritical";
        case BRINE_BUBBLE,ABYSSAL_DELUGE -> "deep_one_mage_attack";case HULLBREAKER_SURGE -> "hullbreaker_idle";
        case DESOLATE_DAGGER -> "forsaken_grab";case FORSAKEN_SONAR -> "forsaken_screech";case FORLORN_RECKONING -> "forsaken_spawn";
        case GUMBALL_VOLLEY -> "gumball_bounce";case FROSTMINT_HEX -> "sugar_staff_cast_hex";default -> "licowitch_cast_peppermint";
    };}
    static String castSound(Spell s){return MOD+":"+switch(s){
        case POLARITY_BOLT,MAGNETAR_ANNIHILATION -> "raygun_start";case EMP_SURGE -> "magnetron_attack";
        case AMBER_TALON -> "extinction_spear_throw";case TECTONIC_STAMPEDE -> "tremorsaurus_roar";case LUX_EXTINCTION -> "luxtructosaurus_roar";
        case ACID_GLOB -> "acid_swim";case TREMORZILLA_BREATH -> "tremorzilla_beam_start";case NUCLEAR_STRIKE -> "nuclear_bomb_place";
        case BRINE_BUBBLE -> "deep_one_attack";case HULLBREAKER_SURGE -> "hullbreaker_attack";case ABYSSAL_DELUGE -> "deep_one_mage_attack";
        case DESOLATE_DAGGER -> "forsaken_grab";case FORSAKEN_SONAR,FORLORN_RECKONING -> "forsaken_aoe";
        case GUMBALL_VOLLEY -> "gumball_launch";case FROSTMINT_HEX -> "sugar_staff_cast_hex";default -> "gum_worm_attack";
    };}
    static void charge(ServerPlayer caster,Spell spell,double progress,int elapsed){
        if(spell==Spell.TREMORZILLA_BREATH&&elapsed%8==0)SpellLoops.sync(caster,spell,true,false,beamVariant(caster));
        if(elapsed%4!=0)return;
        Vec3 feet=caster.position().add(0,.05,0),focus=SpellEngine.clipLoaded(caster.serverLevel(),caster.getEyePosition(),caster.getLookAngle(),2.5,caster);
        SpellVisuals.send(caster.serverLevel(),spell,SpellVisuals.Stage.RITUAL_TARGET,feet,feet.add(caster.getLookAngle()),cataclysm(spell)?5+progress:2.3);
        SpellVisuals.send(caster.serverLevel(),spell,SpellVisuals.Stage.CHARGE,focus,focus.add(caster.getLookAngle()),.6+progress+(spell==Spell.TREMORZILLA_BREATH?3*beamVariant(caster):0));
        // Native clips range from short clicks to nine-second calls. Do not stack them every 1.6s.
        int cadence=switch(spell){case LUX_EXTINCTION->184;case BRINE_BUBBLE,ABYSSAL_DELUGE->180;
            case FORSAKEN_SONAR,FORLORN_RECKONING->116;case CANDY_CATASTROPHE->100;
            case HULLBREAKER_SURGE,FROSTMINT_HEX->84;case NUCLEAR_STRIKE->68;case ACID_GLOB->64;
            case EMP_SURGE->60;case TREMORZILLA_BREATH->56;default->32;};
        if(spell!=Spell.TREMORZILLA_BREATH&&elapsed>0&&elapsed%cadence==0)SpellAudio.world(caster.serverLevel(),feet,chargeSound(spell),.8f+(float)progress*.2f,1);
    }
    private CavesCompat(){}
}
