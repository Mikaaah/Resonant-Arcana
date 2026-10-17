package nl.townsandkingdoms.resonantarcana;

import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/** Shaped native effects, shared particle budget, no optional renderer dependency. */
final class BossSpellEffects {
    private static final ParticleOptions WATER=dust(.05f,.5f,.85f,.5f,.95f,1);
    private static final ParticleOptions FROST=dust(.2f,.55f,1,.9f,1,1);
    private static final ParticleOptions RIFT=dust(.35f,.05f,.7f,.8f,.6f,1);
    private static final ParticleOptions SAND=dust(.6f,.32f,.08f,1,.8f,.3f);
    private static final ParticleOptions FIRE=dust(1,.12f,.01f,1,.85f,.18f);
    private BossSpellEffects(){}
    private static ParticleOptions dust(float r,float g,float b,float x,float y,float z){return new DustColorTransitionOptions(new Vector3f(r,g,b),new Vector3f(x,y,z),1.25f);}
    private static void point(ServerLevel level,ParticleOptions particle,Vec3 pos){SpellParticles.point(level,particle,pos,Vec3.ZERO);}
    static void ring(ServerLevel level,Vec3 pos,double radius,ParticleOptions particle,int count,double phase){
        for(int i=0;i<count;i++){
            double a=phase+i*Math.PI*2/count;
            point(level,particle,pos.add(Math.cos(a)*radius,0,Math.sin(a)*radius));
        }
    }
    static void warning(SpellEngine.Cast c,Vec3 pos,double radius){
        ParticleOptions color=switch(c.spell.kind){
            case BOSS_ICE->FROST;case BOSS_RIFT,BOSS_SWORD->RIFT;
            case BOSS_SAND->SAND;case BOSS_DRAGON->FIRE;default->WATER;
        };
        ring(c.level,pos.add(0,.12,0),radius,color,18,c.age*.08);
    }
    static void water(SpellEngine.Cast c,double progress){
        for(int arm=0;arm<2;arm++)for(int i=0;i<7;i++){
            double t=i/7.0,a=c.age*.18+arm*Math.PI+t*2.6,r=(c.spell.radius+.6)*(1-.65*t);
            Vec3 p=c.pos.add(Math.cos(a)*r,.12+t*.65,Math.sin(a)*r);
            point(c.level,WATER,p);
            if(i%3==0)SpellParticles.point(c.level,ParticleTypes.SPLASH,p,new Vec3(-Math.cos(a)*.05,.035,-Math.sin(a)*.05));
        }
    }
    static void ice(SpellEngine.Cast c,Vec3 pos,boolean impact){
        ring(c.level,pos.add(0,.12,0),impact?1.7:1.1,FROST,12,c.age*.1);
        if(impact)for(int i=0;i<8;i++){
            double a=i*Math.PI/4;Vec3 v=new Vec3(Math.cos(a)*.1,.15,Math.sin(a)*.1);
            SpellParticles.point(c.level,ParticleTypes.SNOWFLAKE,pos.add(0,.4,0),v);
            SpellParticles.point(c.level,ParticleRegistry.SNOWFLAKE_PARTICLE.get(),pos.add(Math.cos(a)*.4,.7,Math.sin(a)*.4),v);
        }
    }
    static void rift(SpellEngine.Cast c,Vec3 pos,Vec3 dir,boolean impact){
        if(impact){
            for(int ring=0;ring<2;ring++)ring(c.level,pos.add(0,.15+ring*.5,0),1.1+ring*.9,RIFT,16,c.age*.12);
            return;
        }
        Vec3 side=dir.cross(new Vec3(0,1,0));if(side.lengthSqr()<.01)side=new Vec3(1,0,0);side=side.normalize();
        Vec3 up=side.cross(dir).normalize();
        for(int i=0;i<8;i++){
            double a=c.age*.45+i*Math.PI/4;
            Vec3 radial=side.scale(Math.cos(a)*.45).add(up.scale(Math.sin(a)*.45));
            Vec3 p=pos.add(radial).subtract(dir.scale(i*.13));point(c.level,RIFT,p);
            if(i%4==0)SpellParticles.point(c.level,ParticleTypes.END_ROD,p,radial.scale(.035));
        }
    }
    static void sand(SpellEngine.Cast c,boolean impact){
        if(impact){ring(c.level,c.pos.add(0,.15,0),c.spell.radius,SAND,24,c.age*.13);return;}
        for(int arm=0;arm<2;arm++)for(int i=0;i<8;i++){
            double t=i/8.0,a=c.age*.15+arm*Math.PI+t*3.2,r=c.spell.radius*(1-.7*t);
            Vec3 p=c.pos.add(Math.cos(a)*r,.15+t*1.6,Math.sin(a)*r);point(c.level,SAND,p);
            if(i%4==0){
                var state=c.level.getBlockState(BlockPos.containing(c.pos).below());
                SpellParticles.point(c.level,new BlockParticleOption(ParticleTypes.BLOCK,state),p,new Vec3(-Math.cos(a)*.05,.04,-Math.sin(a)*.05));
            }
        }
    }
    static void fire(SpellEngine.Cast c,Vec3 pos,Vec3 dir,boolean charged){
        Vec3 side=dir.cross(new Vec3(0,1,0));if(side.lengthSqr()<.01)side=new Vec3(1,0,0);side=side.normalize();
        Vec3 up=side.cross(dir).normalize();
        for(int i=0;i<10;i++){
            double a=c.age*.35+i*Math.PI/5,r=charged?.7:.35;
            Vec3 p=pos.add(side.scale(Math.cos(a)*r)).add(up.scale(Math.sin(a)*r));
            point(c.level,i%3==0?ParticleRegistry.EMBER_PARTICLE.get():FIRE,p);
        }
        SpellParticles.point(c.level,ParticleTypes.FLAME,pos,dir.scale(-.05));
        SpellParticles.point(c.level,ParticleTypes.SMOKE,pos.subtract(dir.scale(.3)),dir.scale(-.03));
    }
    static void fireImpact(SpellEngine.Cast c,Vec3 pos,int age){
        double r=Math.min(c.spell.radius+2,.6+age*.45);
        ring(c.level,pos.add(0,.18,0),r,FIRE,24,age*.15);
        for(int i=0;i<8;i++){
            double a=i*Math.PI/4;
            SpellParticles.point(c.level,ParticleRegistry.EMBER_PARTICLE.get(),pos.add(Math.cos(a)*r,.4,Math.sin(a)*r),new Vec3(Math.cos(a)*.07,.08,Math.sin(a)*.07));
        }
    }
}
