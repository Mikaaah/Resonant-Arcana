package nl.townsandkingdoms.resonantarcana;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/** Dense native choreography complements the deliberately limited optional AAA seals. */
final class CataclysmEffects {
    private CataclysmEffects(){}
    static boolean visual(ClientLevel level,Spell s,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double size){
        double time=level.getGameTime()*.08;
        if(s==Spell.EXPLOSION){ExplosionEffects.visual(level,stage,p,end,size);return true;}
        if(stage==SpellVisuals.Stage.RITUAL_TARGET){
            if(s==Spell.EXPLOSION)explosionTarget(level,p,end,Math.clamp((size-5)/2,0,1),time);
            else groundSeal(level,s,p,size,time);
            return true;
        }
        if(s!=Spell.DOOM&&s.kind!=Spell.Kind.CATACLYSM)return false;
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
                point(level,ParticleTypes.FLASH,p.add(0,2,0),Vec3.ZERO,4);
                point(level,ParticleTypes.EXPLOSION_EMITTER,p,Vec3.ZERO,12);
                for(int ring=0;ring<4;ring++)for(int i=0;i<100;i++){
                    double a=i*Math.PI/50,r=size*(.3+ring*.22);
                    point(level,ring%2==0?ParticleTypes.FLAME:ParticleTypes.CLOUD,p.add(Math.cos(a)*r,.2+ring*.3,Math.sin(a)*r),new Vec3(Math.cos(a)*.28,.04,Math.sin(a)*.28),32);
                }
                for(int i=0;i<90;i++){
                    double a=i*2.39996,h=i/90.0*15,r=2+(h>9?(h-9)*1.4:h*.15);
                    point(level,i%3==0?ParticleTypes.CAMPFIRE_COSY_SMOKE:ParticleTypes.FLAME,p.add(Math.cos(a)*r,h,Math.sin(a)*r),new Vec3(Math.cos(a)*.05,.14,Math.sin(a)*.05),50);
                }
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
        int color=s==Spell.DOOM?0x9b1028:s==Spell.EXPLOSION?0xe81b39:s.theme.color;
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
        // Six descending corkscrews gather from well above the caster into the ground seal.
        // Dense outside the centre, with just a few smaller streams entering the player's hands.
        int n=30+(int)(progress*26);
        for(int arm=0;arm<6;arm++)for(int i=0;i<n;i++){
            double t=(i+.5)/n,a=arm*Math.PI/3+time*.8+t*Math.PI*3.5;
            double r=radius*(.35+.85*t),height=.2+t*(8+progress*5);
            Vec3 q=p.add(Math.cos(a)*r,height,Math.sin(a)*r);
            Vec3 tangent=new Vec3(-Math.sin(a)*.13-Math.cos(a)*.04,-.2-progress*.14,Math.cos(a)*.13-Math.sin(a)*.04);
            point(level,i%5==0?dust:themed(s,i),q,tangent,26);
        }
        for(int i=0;i<14;i++){
            double a=i*2.39996+time,r=1.6+(i%3)*.25;
            Vec3 q=p.add(Math.cos(a)*r,2.8+i*.12,Math.sin(a)*r);
            Vec3 destination=p.add(0,1.05,0);
            point(level,themed(s,i),q,destination.subtract(q).scale(.07),18);
        }
    }
    private static void groundSeal(ClientLevel level,Spell s,Vec3 p,double radius,double time){
        var dust=new DustParticleOptions(color(s.theme.color),.7f);
        for(int i=0;i<96;i++){double a=i*Math.PI/48;boundary(level,dust,p.add(Math.cos(a)*radius,.03,Math.sin(a)*radius),12);}
        for(int arm=0;arm<3;arm++)for(int i=0;i<24;i++){
            double t=(i+.5)/24,a=arm*Math.PI*2/3+time+t*8,r=radius*(.4+.6*t);
            Vec3 q=p.add(Math.cos(a)*r,.15+t*5.5,Math.sin(a)*r);
            point(level,themed(s,i),q,new Vec3(-Math.sin(a)*.12-Math.cos(a)*.025,-.22,Math.cos(a)*.12-Math.sin(a)*.025),24);
        }
        for(int i=0;i<5;i++){double a=i*2.39996+time;Vec3 off=new Vec3(Math.cos(a)*1.1,1.9,Math.sin(a)*1.1);point(level,themed(s,i),p.add(off),new Vec3(-off.x*.07,-.065,-off.z*.07),16);}
    }
    static int explosionRings(double progress){return (int)Math.ceil(Math.clamp((progress-.1)/.65,0,1)*5);}
    private static Vector3f color(int c){return new Vector3f(((c>>16)&255)/255f,((c>>8)&255)/255f,(c&255)/255f);}
    private static void explosionTarget(ClientLevel l,Vec3 p,Vec3 top,double progress,double time){
        Vec3 axis=top.subtract(p).normalize();if(axis.lengthSqr()<.01)return;
        Vec3 side=axis.cross(new Vec3(0,0,1)).normalize();if(side.lengthSqr()<.01)side=new Vec3(1,0,0);
        Vec3 up=axis.cross(side).normalize();
        var core=new DustParticleOptions(new Vector3f(1,.86f,.78f),1.1f);
        var red=new DustParticleOptions(new Vector3f(.95f,.015f,.11f),1.35f);
        var amber=new DustColorTransitionOptions(new Vector3f(1,.5f,.06f),new Vector3f(.85f,.015f,.07f),1.1f);
        // The beacon is present from the very first charge tick. Five seals appear in order.
        int segments=(int)Math.clamp(p.distanceTo(top)*7,32,196);
        for(int i=0;i<=segments;i++){
            double t=i/(double)segments,a=t*24-time*2;
            Vec3 q=p.lerp(top,t),off=side.scale(Math.cos(a)*(.12+progress*.16)).add(up.scale(Math.sin(a)*(.12+progress*.16)));
            point(l,red,q,Vec3.ZERO,10);
            if(progress>.55)point(l,core,q,Vec3.ZERO,9);
            point(l,amber,q.add(off),off.normalize().scale(-.015),12);
        }
        int rings=explosionRings(progress);
        for(int ring=0;ring<rings;ring++){
            Vec3 center=p.lerp(top,.12+ring*.17);double radius=2.4+ring*.3;
            // A native rim stays visible when AAA is absent. Geometry is perpendicular to the beam.
            for(int j=0;j<112;j++){
                double a=j*Math.PI/56+time*(ring%2==0?.3:-.3);
                Vec3 off=side.scale(Math.cos(a)*radius).add(up.scale(Math.sin(a)*radius));
                point(l,ring%2==0?red:amber,center.add(off),Vec3.ZERO,12);
            }
            // Counter-rotating ember ribbons feed each seal, then tighten into the beam.
            for(int arm=0;arm<2;arm++)for(int j=0;j<24;j++){
                double t=j/24.0,a=arm*Math.PI+time*(ring%2==0?1.6:-1.6)+t*6.5;
                double r=radius*(1.65-t*.9);
                Vec3 off=side.scale(Math.cos(a)*r).add(up.scale(Math.sin(a)*r)).add(axis.scale((1-t)*2));
                Vec3 tangent=side.scale(-Math.sin(a)*.14-Math.cos(a)*.06).add(up.scale(Math.cos(a)*.14-Math.sin(a)*.06)).add(axis.scale(-.07));
                point(l,j%5==0?core:j%3==0?amber:red,center.add(off),tangent,22);
            }
        }
    }
    private static void doom(ClientLevel l,SpellVisuals.Stage stage,Vec3 p,double size,double time){
        if(stage==SpellVisuals.Stage.HIT)return;
        if(stage==SpellVisuals.Stage.IMPACT){for(int ring=0;ring<3;ring++)doomRing(l,p,size*(.35+ring*.25),time,ring*.6,80,true);return;}
        double core=Math.clamp(size*.17,.6,2.1),outer=Math.max(3,size);
        SingularityEffects.refresh(l,p,core*.88);
        // Gold photon rim and a flattened, co-centred accretion disc match the reference.
        var gold=new DustColorTransitionOptions(new Vector3f(1,.85f,.4f),new Vector3f(1,.19f,.015f),1.1f);
        // Vertical photon ring, then bright lensing arcs over the horizontal disc.
        for(int layer=0;layer<2;layer++)for(int i=0;i<128;i++){
            double a=i*Math.PI/64,r=core*(1+layer*.09);
            point(l,gold,p.add(Math.cos(a)*r,Math.sin(a)*r,0),Vec3.ZERO,12);
        }
        for(int lane=0;lane<5;lane++)for(int i=0;i<96;i++){
            double a=i*Math.PI/48+time*.6,r=core*1.15+lane*(outer-core)*.18;
            double lens=Math.max(0,Math.sin(a))*core*.75*Math.exp(-lane*.5);
            Vec3 off=new Vec3(Math.cos(a)*r,lens+Math.sin(a)*.12*r,Math.sin(a)*r);
            point(l,lane<2?gold:new DustParticleOptions(new Vector3f(1,.04f,.008f),.85f),p.add(off),Vec3.ZERO,12);
        }
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
        if(s==Spell.EXPLOSION)return i%5==0?ParticleTypes.FLAME:new DustColorTransitionOptions(new Vector3f(.95f,.015f,.11f),new Vector3f(1,.42f,.04f),1.1f);
        return SpellParticles.particle(s.theme);
    }
    private static void point(ClientLevel l,ParticleOptions o,Vec3 p,Vec3 v,int life){NativeSpellEffects.point(l,o,p,v,life,false);}
    private static void boundary(ClientLevel l,ParticleOptions o,Vec3 p,int life){NativeSpellEffects.point(l,o,p,Vec3.ZERO,life,true);}
}
