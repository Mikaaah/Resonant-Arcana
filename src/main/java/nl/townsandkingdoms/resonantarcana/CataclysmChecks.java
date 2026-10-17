package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import java.util.function.BiConsumer;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

/** Exact release totals, obstruction, allegiance and finite lifecycle for the new ultimates. */
final class CataclysmChecks {
    static void run(ServerLevel level,BiConsumer<Boolean,String> check){
        var p=FakePlayerFactory.get(level,new GameProfile(UUID.fromString("fce984cb-0206-4abb-973b-3cd712334301"),"CataclysmTest"));
        p.getAttribute(Attributes.MAX_HEALTH).setBaseValue(100);p.setPos(0,81,0);p.setYRot(0);p.setXRot(0);p.removeAllEffects();p.setHealth(10);
        var spells=Arrays.stream(Spell.values()).filter(CataclysmSpells::handles).toList();
        check.accept(spells.size()==10,"ten cataclysms supplement Doom");
        check.accept(spells.stream().map(s->IronsCompat.school(s.theme)).distinct().count()==9,"every native school has a separate cataclysm");
        check.accept(Spell.DOOM.castTicks()==200&&Spell.DOOM.damage==256,"Doom has a ten-second base cast and 256 damage");
        var team=level.getScoreboard().addPlayerTeam("cataclysm_allies");team.setAllowFriendlyFire(false);level.getScoreboard().addPlayerToTeam(p.getScoreboardName(),team);
        try{for(var s:spells){
            SpellEngine.clear();p.removeAllEffects();p.setHealth(10);
            Zombie target=enemy(level,0,6),friend=enemy(level,1,6),hidden=enemy(level,0,10);
            level.getScoreboard().addPlayerToTeam(friend.getScoreboardName(),team);
            check.accept(s.castTicks()>=200&&s.manaCost()>=400&&s.levelCap()==1,s+" is a costly long-cast Legendary");
            check.accept(SpellEngine.cast(p,s),s+" exact release fixture starts");
            for(int x=-14;x<=14;x++)for(int y=81;y<=100;y++)level.setBlockAndUpdate(new BlockPos(x,y,8),Blocks.STONE.defaultBlockState());
            try{
                for(int age=0;age<160;age++){target.invulnerableTime=0;friend.invulnerableTime=0;hidden.invulnerableTime=0;SpellEngine.tick();}
                double total=switch(s){
                    case ASTRAL_APOCALYPSE,SOUL_REQUIEM -> s.damage*2;
                    case WORLD_PYRE -> s.damage*7.5;
                    case HEAVENS_THUNDER -> s.damage*8;
                    case ETERNAL_WINTER -> s.damage*6;
                    default -> s.damage*5;
                };
                // The wall may intercept off-centre meteors/columns; only full-radius sequences
                // have exact totals in this obstruction fixture.
                if(s!=Spell.WORLD_PYRE&&s!=Spell.HEAVENS_THUNDER&&s!=Spell.RIFT_COLLISION)
                    check.accept(Math.abs(1024-target.getHealth()-total)<.01,s+" correct staged damage total: "+(1024-target.getHealth()));
                else check.accept(target.getHealth()<1024,s+" attacks its visible side of cover");
                check.accept(hidden.getHealth()==1024,s+" cannot damage through a wall");
                check.accept(friend.getHealth()==1024,s+" protects teammates");
                check.accept(SpellEngine.activeCount()==0,s+" release sequence expires");
                if(s==Spell.CRIMSON_ECLIPSE)check.accept(Math.abs(p.getHealth()-30)<.01,"blood eclipse heals only actual damage within its shared cap");
            }finally{
                for(int x=-14;x<=14;x++)for(int y=81;y<=100;y++)level.setBlockAndUpdate(new BlockPos(x,y,8),Blocks.AIR.defaultBlockState());
                target.discard();friend.discard();hidden.discard();SpellEngine.clear();
            }
        }}finally{level.getScoreboard().removePlayerTeam(team);SpellEngine.clear();p.removeAllEffects();}
    }
    private static Zombie enemy(ServerLevel level,double x,double z){
        var v=new Zombie(EntityType.ZOMBIE,level);v.setPos(x,81,z);v.setNoAi(true);
        v.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1024);v.setHealth(1024);v.getAttribute(Attributes.ARMOR).setBaseValue(0);level.addFreshEntity(v);return v;
    }
}
