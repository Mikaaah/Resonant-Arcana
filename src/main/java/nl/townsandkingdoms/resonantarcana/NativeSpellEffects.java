package nl.townsandkingdoms.resonantarcana;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.*;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/** Local native-particle choreography: one bounded network message, many visible particles.
 * Range outlines have a separate allowance so decorative effects cannot starve them.
 * No particles in this class affect collision, damage, allegiance or spell lifetime. */
final class NativeSpellEffects {
    private record Ripple(ClientLevel level,Spell spell,Vec3 center,double radius,int age){}
    private static final ArrayList<Ripple> RIPPLES=new ArrayList<>();
    private static ClientLevel world;
    private static int detail,outline;
    private static final ParticleOptions MOSS=new BlockParticleOption(ParticleTypes.BLOCK,net.minecraft.world.level.block.Blocks.MOSS_BLOCK.defaultBlockState());
    private static final ParticleOptions[][] DUST=new ParticleOptions[Spell.Theme.values().length][2];
    static {for(var theme:Spell.Theme.values())for(int i=0;i<2;i++){int c=theme.color;DUST[theme.ordinal()][i]=new DustParticleOptions(new Vector3f(((c>>16)&255)/255f,((c>>8)&255)/255f,(c&255)/255f),i==0?.9f:1.1f);}}
    private NativeSpellEffects(){}
    static void tick(){
        var level=Minecraft.getInstance().level;
        if(level!=world){RIPPLES.clear();world=level;}
        detail=ArcanaConfig.CLIENT_NATIVE_BUDGET.get();outline=detail/3;
        if(Minecraft.getInstance().isPaused())return;
        for(int i=RIPPLES.size()-1;i>=0;i--){
            var r=RIPPLES.get(i);if(r.level()!=level||r.age()>=12){RIPPLES.remove(i);continue;}
            ring(level,r.spell(),r.center(),r.radius()*(r.age()+1)/12.0,true,0,1);
            RIPPLES.set(i,new Ripple(level,r.spell(),r.center(),r.radius(),r.age()+1));
        }
    }
    static void visual(WandNetwork.Visual msg){
        if(!msg.valid()||(detail<=0&&outline<=0))return;
        var mc=Minecraft.getInstance();var level=mc.level;
        if(level==null||mc.player==null)return;
        Vec3 p=new Vec3(msg.x(),msg.y(),msg.z()),end=new Vec3(msg.endX(),msg.endY(),msg.endZ());
        if(mc.player.position().distanceToSqr(p.lerp(end,.5))>96*96)return;
        Spell spell=Spell.values()[msg.spell()];var stage=SpellVisuals.Stage.values()[msg.stage()];
        double size=msg.size(),time=level.getGameTime()*.14;
        // Distant casts and crowded fights retain their outlines and simplify interior detail.
        int count=(int)Math.clamp(48-Math.sqrt(mc.player.distanceToSqr(p))*.4,16,48);
        if(spell==Spell.SERAPH_ASCENDANT&&stage==SpellVisuals.Stage.AREA){seraph(level,p,end,time);return;}
        if(CavesCompat.optional(spell)){if(CavesCompat.available())CavesClientEffects.visual(level,spell,stage,p,end,size);return;}
        if(spell==Spell.VOID_DOMAIN){VoidDomainEffects.visual(level,stage,p,end,size);return;}
        if(spell==Spell.VOID_EXILE){VoidExileEffects.visual(level,stage,p,end,size);return;}
        if(spell==Spell.I_AM_ATOMIC){AtomicEffects.visual(level,stage,p,end,size);return;}
        if(spell==Spell.DOOM_BEAM){DoomBeamEffects.visual(level,stage,p,end,size);return;}
        if(CataclysmEffects.visual(level,spell,stage,p,end,size))return;
        switch(stage){
            case RITUAL_TARGET -> ring(level,spell,p,size*.7,true,time*.1,1);
            case RITUAL_END -> {}
            case AREA -> area(level,spell,p,end,size,time,count);
            case LINK,STRIKE -> beam(level,spell,p,end,size,time,count);
            case FLIGHT -> flight(level,spell,p,end,size,time,count);
            case CAST,CHARGE -> charge(level,spell,p,end,size,time,count);
            case HIT,IMPACT,TRAIL_BURST -> impact(level,spell,p,size,stage==SpellVisuals.Stage.IMPACT,count);
        }
    }
    private static void seraph(ClientLevel level,Vec3 p,Vec3 end,double time){
        Vec3 forward=end.subtract(p).multiply(1,0,1).normalize();if(forward.lengthSqr()<.01)forward=new Vec3(0,0,1);
        Vec3 side=new Vec3(-forward.z,0,forward.x),shoulder=p.subtract(forward.scale(.45)).add(0,1.35,0);
        for(int sign:new int[]{-1,1})for(int feather=0;feather<10;feather++){
            double f=feather/9.0,spread=.8+f*2.3,height=1.7-f*.9;
            Vec3 tip=shoulder.add(side.scale(sign*spread)).add(forward.scale(-.35-f*.25+Math.sin(time)*.15)).add(0,height,0);
            Vec3 base=shoulder.add(side.scale(sign*(.15+f*.35))).add(0,.4-f*.4,0);
            for(int i=0;i<9;i++)point(level,i%4==0?ParticleTypes.END_ROD:new DustParticleOptions(new Vector3f(1,.9f,.58f),.8f),base.lerp(tip,i/8.0),Vec3.ZERO,11,false);
        }
    }
    private static void area(ClientLevel level,Spell s,Vec3 p,Vec3 end,double radius,double time,int count){
        var shape=SpellVisuals.shape(s,SpellVisuals.Stage.AREA);
        switch(shape){
            case SLASH -> {
                Vec3 dir=end.subtract(p).multiply(1,0,1).normalize();if(dir.lengthSqr()<.01)dir=new Vec3(0,0,1);
                Vec3 side=new Vec3(-dir.z,0,dir.x);
                for(int i=0;i<count*2;i++){double a=-Math.PI/3+2*Math.PI/3*i/(count*2-1);
                    Vec3 q=p.add(dir.scale(Math.cos(a)*radius)).add(side.scale(Math.sin(a)*radius));
                    point(level,primary(s),q,new Vec3(0,.015,0),12,false);
                    if(i%3==0)point(level,accent(s),q.add(0,.12,0),dir.scale(.03),10,false);
                }
            }
            case CAGE,CURSE -> {
                for(int arm=0;arm<3;arm++)for(int i=0;i<count/2;i++){
                    double a=time+arm*Math.PI*2/3+i*.15,y=i*2.5/(count/2);
                    Vec3 q=p.add(Math.cos(a)*.85,y-.7,Math.sin(a)*.85);
                    point(level,i%4==0?accent(s):primary(s),q,Vec3.ZERO,12,false);
                }
                ring(level,s,p.add(0,-.65,0),Math.min(radius,1.2),false,time,1);
            }
            case ORBIT,CHARGE -> flight(level,s,p,end,Math.min(radius,1),time,count);
            case PULSE -> {
                ring(level,s,p,radius,true,time,1);
                if(s==Spell.WARSCREAM&&RIPPLES.size()<32)RIPPLES.add(new Ripple(level,s,p,radius,0));
            }
            case RAIN -> {
                ring(level,s,p,radius,true,time,1);
                for(int i=0;i<count;i++){
                    double a=i*2.39996+time,r=radius*Math.sqrt((i+.5)/count);
                    Vec3 q=p.add(Math.cos(a)*r,3.2+(i%4)*.22,Math.sin(a)*r);
                    point(level,i%3==0?ParticleTypes.CLOUD:accent(s),q,new Vec3(0,-.025,0),16,false);
                    point(level,primary(s),q.add(0,-.6,0),new Vec3(0,-.32,0),14,false);
                }
            }
            case FLAME -> {
                for(int i=0;i<count;i++){double a=i*2.39996+time,r=radius*.6*Math.sqrt((i+.5)/count);
                    point(level,i%3==0?accent(s):primary(s),p.add(Math.cos(a)*r,(i%6)*.2,Math.sin(a)*r),new Vec3(0,.07,0),14,false);}
            }
            default -> {
                ring(level,s,p,radius,true,time,1);
                boolean inward=shape==SpellVisuals.Shape.VORTEX||shape==SpellVisuals.Shape.HORIZON_WELL||shape==SpellVisuals.Shape.DOOM_WELL;
                for(int i=0;i<count;i++){
                    double t=(i+.5)/count,a=i*2.39996+time,r=radius*(inward?(1-t*.8):Math.sqrt(t)*.8);
                    double height=inward?t*2.1:(s.kind==Spell.Kind.TOTEM?t*1.8:.15+Math.sin(a*3)*.08);
                    Vec3 radial=new Vec3(Math.cos(a)*r,0,Math.sin(a)*r);
                    Vec3 v=inward?radial.normalize().scale(-.075).add(0,.012,0):new Vec3(-Math.sin(a)*.025,.02,Math.cos(a)*.025);
                    point(level,i%3==0?accent(s):primary(s),p.add(radial).add(0,height,0),v,14,false);
                }
                if(s==Spell.LAST_STAND||s==Spell.CONSECRATION||s==Spell.SANCTUARY){
                    for(int arm=0;arm<4;arm++)for(int i=1;i<=6;i++){
                        double a=arm*Math.PI/2;point(level,ParticleTypes.END_ROD,p.add(Math.cos(a)*radius*i/7,.12,Math.sin(a)*radius*i/7),new Vec3(0,.02,0),15,false);
                    }
                }
                if(s==Spell.ABSOLUTE_ZERO)for(int arm=0;arm<6;arm++)for(int i=1;i<=5;i++){
                    double a=arm*Math.PI/3;Vec3 q=p.add(Math.cos(a)*radius*i/6,.1,Math.sin(a)*radius*i/6);
                    point(level,ParticleTypes.SNOWFLAKE,q,Vec3.ZERO,16,false);
                }
                if(s==Spell.WISP_LANTERN)for(int arm=0;arm<3;arm++)for(int i=0;i<8;i++){
                    double a=time+arm*Math.PI*2/3+i*.3;
                    point(level,ParticleTypes.SOUL_FIRE_FLAME,p.add(Math.cos(a)*1.1,.7+i*.14,Math.sin(a)*1.1),new Vec3(0,.018,0),16,false);
                }
            }
        }
    }
    private static void ring(ClientLevel level,Spell s,Vec3 p,double radius,boolean ground,double angle,double scale){
        int n=(int)Math.clamp(radius*15,32,128);
        if(!RIPPLES.isEmpty())n=Math.min(n,Math.max(16,ArcanaConfig.CLIENT_NATIVE_BUDGET.get()/3/(RIPPLES.size()+2)));
        var dust=dust(s,.9f);
        for(int i=0;i<n;i++){
            double a=angle+i*Math.PI*2/n;Vec3 q=p.add(Math.cos(a)*radius,0,Math.sin(a)*radius);
            if(ground)q=surface(level,q,p.y);
            point(level,i%5==0?accent(s):dust,q,Vec3.ZERO,ground?11:9,true);
        }
    }
    private static Vec3 surface(ClientLevel level,Vec3 q,double centerY){
        var pos=BlockPos.containing(q.x,centerY+.5,q.z);
        if(!level.hasChunkAt(pos))return q.add(0,.08,0);
        for(int i=0;i<7;i++,pos=pos.below()){
            var state=level.getBlockState(pos);var shape=state.getCollisionShape(level,pos);
            if(!shape.isEmpty()){double top=pos.getY()+shape.max(net.minecraft.core.Direction.Axis.Y);
                if(top<=centerY+.75)return new Vec3(q.x,top+.06,q.z);}
        }
        return q.add(0,.08,0);
    }
    private static void beam(ClientLevel level,Spell s,Vec3 p,Vec3 end,double size,double time,int count){
        if(s==Spell.ARCANE_DEVASTATOR){
            Vec3 d=end.subtract(p).normalize(),x=side(d),y=x.cross(d).normalize();
            for(int i=0;i<140;i++){
                double t=i/139.0,a=time+t*22;Vec3 center=p.lerp(end,t);
                point(level,new DustParticleOptions(new Vector3f(.85f,.55f,1),1.7f),center,Vec3.ZERO,9,false);
                for(int arm=0;arm<3;arm++){double angle=a+arm*Math.PI*2/3;Vec3 q=center.add(x.scale(Math.cos(angle)*1.7)).add(y.scale(Math.sin(angle)*1.7));point(level,arm==0?ParticleTypes.END_ROD:SpellParticles.particle(Spell.Theme.MAGIC),q,d.scale(.03),9,false);}
            }
            return;
        }
        Vec3 dir=end.subtract(p).normalize();if(dir.lengthSqr()<.001)return;Vec3 side=side(dir),up=side.cross(dir).normalize();
        int n=(int)Math.clamp(p.distanceTo(end)*7,20,160);
        boolean lightning=s.theme==Spell.Theme.THUNDER;
        for(int i=0;i<n;i++){
            double t=(i+.5)/n,a=time+t*18;
            double r=lightning?Math.sin(i*1.7+time)*.25:Math.min(.5,size*.16);
            Vec3 q=p.lerp(end,t).add(side.scale(Math.cos(a)*r)).add(up.scale(Math.sin(a)*r));
            point(level,primary(s),q,dir.scale(.02),10,false);
            if(i%3==0)point(level,accent(s),p.lerp(end,t),dir.scale(.04),9,false);
            if(i%6==0)NativeSpellEffects.tinted(level,io.redspace.ironsspellbooks.registries.ParticleRegistry.WISP_PARTICLE.get(),q,dir.scale(.045),10,s.theme.color,.55f);
        }
    }
    private static void flight(ClientLevel level,Spell s,Vec3 p,Vec3 end,double size,double time,int count){
        Vec3 dir=end.subtract(p).normalize();if(dir.lengthSqr()<.01)dir=new Vec3(0,0,1);
        Vec3 side=side(dir),up=side.cross(dir).normalize();double radius=Math.min(2,size*.55);
        if(s==Spell.FROST_NEEDLE||s==Spell.LUMINOUS_DART||s.kind==Spell.Kind.FAN){
            for(int i=0;i<16;i++){
                Vec3 q=p.subtract(dir.scale(i*.075));
                point(level,i%4==0?accent(s):primary(s),q,dir.scale(.025),8,false);
                if(i%4==0)point(level,dust(s,1.1f),q.add(side.scale(Math.sin(time+i)*.08)),Vec3.ZERO,9,false);
            }return;
        }
        boolean wave=s.kind==Spell.Kind.WAVE||s.kind==Spell.Kind.FROSTFRONT;
        for(int i=0;i<count;i++){
            double t=(i+.5)/count,a=time+t*Math.PI*2;
            double ridge=switch(s.theme){
                case BLOOD -> (i%3==0?.55:.08);case FIRE -> .25+Math.sin(a*3)*.35;
                case POISON -> .1+Math.sin(a*2)*.08;case THUNDER -> (i%2==0?.45:-.15);
                case SOUL -> Math.sin(t*Math.PI*4+time)*.4;case DARK -> .2+Math.sin(t*Math.PI)*.9;
                default -> Math.sin(t*Math.PI)*.7;
            };
            Vec3 q=wave?p.add(side.scale((t*2-1)*size)).add(0,ridge,0)
                :p.add(side.scale(Math.cos(a)*radius)).add(up.scale(Math.sin(a)*radius));
            point(level,i%4==0?accent(s):primary(s),q,dir.scale(.035),10,false);
            if(i%2==0)point(level,dust(s,1.1f),p.subtract(dir.scale(t*1.9)),dir.scale(-.02),12,false);
        }
        if(s==Spell.PRISMATIC_RUIN)for(int arm=0;arm<2;arm++)for(int i=0;i<20;i++){
            double a=time+i*Math.PI/10+arm*Math.PI;
            var particle=arm==0?ParticleTypes.SOUL_FIRE_FLAME:ParticleTypes.WITCH;
            point(level,particle,p.add(side.scale(Math.cos(a)*1.4)).add(up.scale(Math.sin(a)*1.4)),dir.scale(-.02),13,false);
        }
        if(s==Spell.ICE_SERPENT)for(int i=0;i<8;i++){
            Vec3 q=p.subtract(dir.scale(i*.22)).add(side.scale(Math.sin(time-i*.5)*.3));
            point(level,ParticleTypes.SNOWFLAKE,q,Vec3.ZERO,13,false);
        }
    }
    private static void charge(ClientLevel level,Spell s,Vec3 p,Vec3 end,double size,double time,int count){
        Vec3 dir=end.subtract(p).normalize();if(dir.lengthSqr()<.01)dir=new Vec3(0,0,1);
        Vec3 side=side(dir),up=side.cross(dir).normalize();double r=Math.clamp(size*.5,.3,1.8);
        count=Math.clamp((int)(size*14),8,24);
        for(int i=0;i<count;i++){
            double t=(i+.5)/count,a=time+t*Math.PI*4;
            Vec3 off=side.scale(Math.cos(a)*r*(1-t*.6)).add(up.scale(Math.sin(a)*r*(1-t*.6))).subtract(dir.scale(t*.7));
            point(level,i%3==0?accent(s):primary(s),p.add(off),off.scale(-.04),12,false);
            if(i%4==0){
                Vec3 opposite=side.scale(-Math.cos(a)*r*.8).add(up.scale(-Math.sin(a)*r*.8)).add(dir.scale(.15));
                tinted(level,io.redspace.ironsspellbooks.registries.ParticleRegistry.WISP_PARTICLE.get(),p.add(opposite),opposite.scale(-.05),10,s.theme.color,.45f);
            }
        }
    }
    private static void impact(ClientLevel level,Spell s,Vec3 p,double size,boolean major,int count){
        if(s.category()==SpellCategory.MOBILITY&&!major)size=Math.min(size,.8);
        int n=major?count*3:count/2;
        for(int i=0;i<n;i++){
            double y=1-2*(i+.5)/n,r=Math.sqrt(1-y*y),a=i*2.399963;
            Vec3 v=new Vec3(Math.cos(a)*r,y*.65,Math.sin(a)*r).scale((major?.13:.06)*Math.min(size,4));
            point(level,i%3==0?accent(s):primary(s),p.add(v.scale(.5)),v,18+i%9,false);
        }
        if(major&&RIPPLES.size()<32)RIPPLES.add(new Ripple(level,s,p,Math.min(12,size),0));
    }
    private static Vec3 side(Vec3 dir){Vec3 v=dir.cross(new Vec3(0,1,0));return v.lengthSqr()<.01?new Vec3(1,0,0):v.normalize();}
    private static ParticleOptions dust(Spell s,float scale){return DUST[s.theme.ordinal()][scale<=1?0:1];}
    private static ParticleOptions primary(Spell s){return switch(s){
        case WARSCREAM -> ParticleTypes.POOF;
        case WISP_LANTERN,SOUL_CANNON -> ParticleTypes.SOUL_FIRE_FLAME;
        case DREAD_EFFIGY,NIGHT_BLOOM -> ParticleTypes.WITCH;
        case SPORE_TOTEM,VIPER_FANGS -> ParticleTypes.SPORE_BLOSSOM_AIR;
        case THORN_LASH,BRAMBLE_RING -> MOSS;
        case CORROSIVE_RAIN -> SpellParticles.particle(Spell.Theme.POISON);
        case HAILSTORM,ICE_SERPENT,CRYSTAL_BARRAGE -> ParticleTypes.SNOWFLAKE;
        default -> SpellParticles.particle(s.theme);
    };}
    private static ParticleOptions accent(Spell s){return switch(s){
        case WARSCREAM -> ParticleTypes.CRIT;
        case WISP_LANTERN,SOUL_CANNON -> ParticleTypes.SCULK_CHARGE_POP;
        case DREAD_EFFIGY,SHADOW_CAGE,SOUL_PRISON -> ParticleTypes.SMOKE;
        case SPORE_TOTEM,TOXIC_MOLT -> ParticleTypes.HAPPY_VILLAGER;
        case BRAMBLE_RING,THORN_LASH -> ParticleTypes.SPORE_BLOSSOM_AIR;
        case NIGHT_BLOOM,VEIL_OF_NIGHT,UMBRAL_STEP -> ParticleTypes.WITCH;
        case LAST_STAND,CONSECRATION,SANCTUARY,ABSOLUTION,RENEWAL_HYMN -> ParticleTypes.END_ROD;
        case PHANTOM_FORMATION,ORBITAL_BLADES,RIFT_DRAW,SPELLBREAKER -> ParticleTypes.ENCHANT;
        case HAILSTORM,GLACIAL_RUPTURE,ABSOLUTE_ZERO -> ParticleTypes.SNOWFLAKE;
        case DRAGON_BREATH,PHOENIX_RUSH,ASHEN_REBIRTH -> ParticleTypes.FLAME;
        case SONIC_SCREAM,FUNERAL_BELL,SHRIEKING_PULSE -> ParticleTypes.SCULK_SOUL;
        default -> switch(s.theme){
            case HOLY -> ParticleTypes.HAPPY_VILLAGER;case ICE -> ParticleTypes.SNOWFLAKE;
            case FIRE -> ParticleTypes.SMALL_FLAME;case BLOOD -> ParticleTypes.CRIMSON_SPORE;
            case POISON -> ParticleTypes.SPORE_BLOSSOM_AIR;case THUNDER -> ParticleTypes.ELECTRIC_SPARK;
            case DARK -> ParticleTypes.REVERSE_PORTAL;case GRAVITY -> ParticleTypes.PORTAL;
            case SOUL -> ParticleTypes.SCULK_SOUL;case MAGIC -> ParticleTypes.ENCHANT;
        };
    };}
    static void tinted(ClientLevel level,ParticleOptions particle,Vec3 p,Vec3 v,int life,int rgb,float scale){
        if(detail--<=0)return;
        var result=Minecraft.getInstance().particleEngine.createParticle(particle,p.x,p.y,p.z,v.x,v.y,v.z);
        if(result!=null){result.setColor(((rgb>>16)&255)/255f,((rgb>>8)&255)/255f,(rgb&255)/255f);result.setLifetime(life);result.scale(scale);}
    }
    /** Preserve specialized factory parameters and native animation lifetimes. */
    static net.minecraft.client.particle.Particle nativeParticle(ParticleOptions particle,Vec3 p,Vec3 parameters,boolean boundary){
        if(boundary){if(outline--<=0)return null;}else if(detail--<=0)return null;
        return Minecraft.getInstance().particleEngine.createParticle(particle,p.x,p.y,p.z,parameters.x,parameters.y,parameters.z);
    }
    static void point(ClientLevel level,ParticleOptions particle,Vec3 p,Vec3 v,int life,boolean boundary){
        if(boundary){if(outline--<=0)return;}else if(detail--<=0)return;
        var result=Minecraft.getInstance().particleEngine.createParticle(particle,p.x,p.y,p.z,v.x,v.y,v.z);
        if(result!=null)result.setLifetime(life);
    }
}
