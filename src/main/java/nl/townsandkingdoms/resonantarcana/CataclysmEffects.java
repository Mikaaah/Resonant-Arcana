package nl.townsandkingdoms.resonantarcana;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/** Native particles only. Rituals stay at the caster's feet and build gradually. */
final class CataclysmEffects {
    private CataclysmEffects(){}
    static boolean visual(ClientLevel level,Spell s,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double size){
        if(s!=Spell.DOOM&&s.kind!=Spell.Kind.CATACLYSM)return false;
        double time=level.getGameTime()*.08;
        if(stage==SpellVisuals.Stage.CAST)return true; // No burst in the caster's face.
        if(stage==SpellVisuals.Stage.CHARGE){ritual(level,s,p,size,time);return true;}
        if(s==Spell.DOOM){doom(level,stage,p,size,time);return true;}
        if(stage==SpellVisuals.Stage.LINK||stage==SpellVisuals.Stage.STRIKE)return false;
        if(stage==SpellVisuals.Stage.HIT)return true;
        if(stage==SpellVisuals.Stage.IMPACT){
            for(int i=0;i<100;i++){
                double a=i*2.39996,y=1-2*(i+.5)/100,r=Math.sqrt(1-y*y);
                Vec3 dir=new Vec3(Math.cos(a)*r,y*.5,Math.sin(a)*r);
                point(level,themed(s,i),p.add(dir.scale(size*.2)),dir.scale(s==Spell.EXPLOSION?.55:.23),24);
            }
            if(s==Spell.EXPLOSION){
                point(level,ParticleTypes.EXPLOSION_EMITTER,p,Vec3.ZERO,12);
                for(int i=0;i<40;i++){double a=i*2.39996;point(level,ParticleTypes.CAMPFIRE_COSY_SMOKE,p.add(Math.cos(a)*4,1+i%6,Math.sin(a)*4),new Vec3(0,.12,0),60);}
            }
            return true;
        }
        switch(s){
            case VOID_CORONATION -> {
                for(int i=0;i<100;i++){double a=i*2.39996+time,r=size*Math.sqrt((i+.5)/100);
                    Vec3 q=p.add(Math.cos(a)*r,.2+(i%10)*.35,Math.sin(a)*r);
                    point(level,i%3==0?ParticleTypes.SMOKE:i%3==1?ParticleTypes.DRAGON_BREATH:ParticleTypes.REVERSE_PORTAL,q,new Vec3(0,.04,0),18);}
            }
            case SOUL_REQUIEM -> {
                for(int i=0;i<100;i++){double a=i*2.39996+time,r=size*Math.sqrt((i+.5)/100);
                    Vec3 q=p.add(Math.cos(a)*r,.1+(i%5)*.3,Math.sin(a)*r);
                    point(level,i%3==0?ParticleTypes.SCULK_SOUL:i%3==1?ParticleTypes.SOUL_FIRE_FLAME:ParticleTypes.SCULK_CHARGE_POP,q,new Vec3(0,.045,0),20);}
            }
            case WORLDROOT_CATASTROPHE -> {
                for(int i=0;i<140;i++){double a=i*2.39996+time*.1,r=size*Math.sqrt((i+.5)/140);
                    Vec3 q=p.add(Math.cos(a)*r,.08+(i%4)*.13,Math.sin(a)*r);
                    point(level,themed(s,i),q,new Vec3(Math.cos(a)*.015,.04,Math.sin(a)*.015),24);}
            }
            case ETERNAL_WINTER -> {
                // Dense drifting snowfall fills the full storm volume; no geometric snowflake.
                for(int i=0;i<180;i++){double a=i*2.39996+time*.13,r=size*Math.sqrt((i+.5)/180);
                    Vec3 q=p.add(Math.cos(a)*r,1+(i%13)*.6,Math.sin(a)*r);
                    point(level,i%7==0?ParticleTypes.CLOUD:ParticleTypes.SNOWFLAKE,q,new Vec3(-.08,-.13,Math.sin(time)*.035),36);}
                for(int i=0;i<24;i++){double a=i*2.39996;point(level,new BlockParticleOption(ParticleTypes.BLOCK,Blocks.SNOW_BLOCK.defaultBlockState()),p.add(Math.cos(a)*size*.8,.2,Math.sin(a)*size*.8),new Vec3(0,.04,0),16);}
            }
            case FINAL_JUDGEMENT -> {
                for(int i=0;i<75;i++){double a=i*2.39996+time,r=size*Math.sqrt((i+.5)/75);
                    point(level,i%3==0?ParticleTypes.END_ROD:ParticleTypes.WAX_ON,p.add(Math.cos(a)*r,.2+(i%7)*.4,Math.sin(a)*r),new Vec3(0,.04,0),20);}
            }
            case WORLD_PYRE,EXPLOSION -> {
                for(int i=0;i<80;i++){double a=i*2.39996+time,r=size*Math.sqrt((i+.5)/80);
                    point(level,i%5==0?ParticleTypes.LAVA:ParticleTypes.FLAME,p.add(Math.cos(a)*r,s==Spell.EXPLOSION?.15:7+i%5,Math.sin(a)*r),new Vec3(0,s==Spell.EXPLOSION?.025:-.18,0),22);}
            }
            case ASTRAL_APOCALYPSE -> scatter(level,s,p,size,time,ParticleTypes.ENCHANT,ParticleTypes.END_ROD);
            case CRIMSON_ECLIPSE -> scatter(level,s,p,size,time,SpellParticles.particle(Spell.Theme.BLOOD),ParticleTypes.CRIMSON_SPORE);
            case HEAVENS_THUNDER -> scatter(level,s,p,size,time,ParticleTypes.ELECTRIC_SPARK,SpellParticles.particle(Spell.Theme.THUNDER));
            case RIFT_COLLISION -> scatter(level,s,p,size,time,ParticleTypes.PORTAL,ParticleTypes.REVERSE_PORTAL);
            default -> throw new IllegalArgumentException("Missing cataclysm effect: "+s);
        }
        return true;
    }
    private static void ritual(ClientLevel level,Spell s,Vec3 p,double size,double time){
        double progress=Math.clamp((size-5)/2,0,1),radius=5+progress*2;
        int color=s==Spell.DOOM?0x9b1028:s.theme.color;
        var dust=new DustParticleOptions(new Vector3f(((color>>16)&255)/255f,((color>>8)&255)/255f,(color&255)/255f),.7f);
        // Two ground circles and a few runic ticks. The empty centre preserves first-person sight.
        for(int ring=0;ring<2;ring++)for(int i=0;i<96;i++){
            double a=i*Math.PI/48,r=radius-(ring==1?.38:0);
            boundary(level,dust,p.add(Math.cos(a)*r,.04,Math.sin(a)*r),12);
        }
        for(int rune=0;rune<12;rune++)for(int j=0;j<4;j++){
            double a=rune*Math.PI/6+time*.025,r=radius-.7+j*.14;
            boundary(level,dust,p.add(Math.cos(a)*r,.04,Math.sin(a)*r),12);
        }
        int n=18+(int)(progress*64);
        for(int i=0;i<n;i++){
            double a=i*2.39996+time,t=(i+.5)/n,r=radius+.35+t*3;
            Vec3 off=new Vec3(Math.cos(a)*r,.15+(i%5)*.13,Math.sin(a)*r);
            Vec3 velocity=new Vec3(-Math.cos(a)*(.045+progress*.055),-.005,-Math.sin(a)*(.045+progress*.055));
            point(level,themed(s,i),p.add(off),velocity,14);
        }
    }
    private static void doom(ClientLevel l,SpellVisuals.Stage stage,Vec3 p,double size,double time){
        if(stage==SpellVisuals.Stage.HIT)return;
        if(stage==SpellVisuals.Stage.IMPACT){for(int ring=0;ring<3;ring++)doomRing(l,p,size*(.35+ring*.25),time,ring*.6,80,true);return;}
        double core=Math.clamp(size*.17,.6,2.1),outer=Math.max(3,size);
        // Co-centred, perpendicular dark rings replace the opaque orb.
        for(int ring=0;ring<3;ring++)doomRing(l,p,core*(1+ring*.08),time,ring*Math.PI/3,64,false);
        var red=new DustColorTransitionOptions(new Vector3f(1,.015f,.035f),new Vector3f(.14f,0,.005f),1.05f);
        for(int arm=0;arm<4;arm++)for(int i=0;i<48;i++){
            double t=(i/48.0+time*.18)%1,r=core+(outer-core)*(1-t),a=arm*Math.PI/2+t*7+time;
            double tilt=arm%2==0?.35:-.35;
            Vec3 off=new Vec3(Math.cos(a)*r,Math.sin(a)*Math.sin(tilt)*r,Math.sin(a)*Math.cos(tilt)*r);
            point(l,red,p.add(off),off.normalize().scale(-.08),12);
        }
    }
    private static void doomRing(ClientLevel l,Vec3 p,double r,double time,double tilt,int n,boolean red){
        var dust=new DustParticleOptions(red?new Vector3f(.95f,.005f,.02f):new Vector3f(.025f,.005f,.012f),red?1.05f:1.4f);
        for(int i=0;i<n;i++){double a=i*Math.PI*2/n+time;Vec3 off=new Vec3(Math.cos(a)*r,Math.sin(a)*Math.sin(tilt)*r,Math.sin(a)*Math.cos(tilt)*r);point(l,dust,p.add(off),red?off.scale(.025):Vec3.ZERO,12);}
    }
    private static void scatter(ClientLevel l,Spell s,Vec3 p,double size,double time,ParticleOptions a,ParticleOptions b){
        for(int i=0;i<110;i++){double t=(i+.5)/110,angle=i*2.39996+time,r=size*Math.sqrt(t);Vec3 q=p.add(Math.cos(angle)*r,.15+(i%10)*.5,Math.sin(angle)*r);point(l,i%3==0?a:b,q,new Vec3(0,.035,0),20);}
    }
    static ParticleOptions themed(Spell s,int i){
        if(s==Spell.WORLDROOT_CATASTROPHE)return switch(i%5){
            case 0->new BlockParticleOption(ParticleTypes.BLOCK,Blocks.MOSS_BLOCK.defaultBlockState());
            case 1->new BlockParticleOption(ParticleTypes.BLOCK,Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState());
            case 2->ParticleTypes.SPORE_BLOSSOM_AIR;case 3->ParticleTypes.HAPPY_VILLAGER;default->ParticleTypes.CHERRY_LEAVES;
        };
        if(s==Spell.DOOM)return new DustParticleOptions(new Vector3f(.8f,.015f,.03f),.9f);
        if(s==Spell.VOID_CORONATION)return i%2==0?ParticleTypes.SMOKE:ParticleTypes.DRAGON_BREATH;
        if(s==Spell.SOUL_REQUIEM)return i%2==0?ParticleTypes.SCULK_SOUL:ParticleTypes.SOUL_FIRE_FLAME;
        if(s==Spell.ETERNAL_WINTER)return ParticleTypes.SNOWFLAKE;
        if(s==Spell.EXPLOSION)return i%3==0?ParticleTypes.SMOKE:ParticleTypes.FLAME;
        return SpellParticles.particle(s.theme);
    }
    private static void point(ClientLevel l,ParticleOptions o,Vec3 p,Vec3 v,int life){NativeSpellEffects.point(l,o,p,v,life,false);}
    private static void boundary(ClientLevel l,ParticleOptions o,Vec3 p,int life){NativeSpellEffects.point(l,o,p,Vec3.ZERO,life,true);}
}
