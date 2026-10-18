package nl.townsandkingdoms.resonantarcana;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import java.util.*;

/** Native Alex's Caves effects. Parameterized factories retain their own lifetimes and semantics. */
final class CavesClientEffects {
    private static final Map<String,ParticleOptions> OPTIONS=new HashMap<>();
    private static final ArrayList<Long> CLOUDS=new ArrayList<>();
    private static ClientLevel world;
    private static ParticleOptions particle(String id){return OPTIONS.computeIfAbsent(id,key->{var type=BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.fromNamespaceAndPath(CavesCompat.MOD,key));return type instanceof SimpleParticleType simple?simple:ParticleTypes.END_ROD;});}
    private static void nativePoint(String id,Vec3 p,Vec3 parameters,boolean boundary){NativeSpellEffects.nativeParticle(particle(id),p,id.equals("proton")?p:parameters,boundary);}
    private static String main(Spell s){return switch(s.definition.area()){
        case "magnetic_caves"->"scarlet_magnetic_flow";case "primordial_caves"->s==Spell.LUX_EXTINCTION?"luxtructosaurus_ash":"amber_explosion";
        case "toxic_caves"->s==Spell.ACID_GLOB?"acid_bubble":"proton";case "abyssal_chasm"->"deep_one_magic";
        case "forlorn_hollows"->"underzealot_magic";default->"sugar_flake";
    };}
    static void visual(ClientLevel level,Spell s,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double radius){
        if(world!=level){world=level;CLOUDS.clear();OPTIONS.clear();}CLOUDS.removeIf(t->t<level.getGameTime());
        double time=level.getGameTime()*.13;Vec3 dir=end.subtract(p).normalize();if(dir.lengthSqr()<.01)dir=new Vec3(0,0,1);
        String id=main(s);boolean magnetic=s.definition.area().equals("magnetic_caves");
        if(stage==SpellVisuals.Stage.RITUAL_END)return;
        if(stage==SpellVisuals.Stage.RITUAL_TARGET||stage==SpellVisuals.Stage.AREA){
            ring(s,stage==SpellVisuals.Stage.AREA&&(s==Spell.EMP_SURGE||s==Spell.FORSAKEN_SONAR)?p.add(0,-.95,0):p,radius,time);
            if(stage==SpellVisuals.Stage.RITUAL_TARGET)return;
            if(s==Spell.NUCLEAR_STRIKE){for(int i=0;i<6;i++)nativePoint("nuclear_siren_sonar",p.add(0,1+i*.15,0),Vec3.ZERO,false);return;}
            if(s==Spell.ABYSSAL_DELUGE)for(int i=0;i<36;i++){
                double a=i*2.39996+time,r=radius*(.2+.8*i/36.0);Vec3 off=new Vec3(Math.cos(a)*r,i%5*.4,Math.sin(a)*r);
                nativePoint(i%3==0?"water_foam":"deep_one_magic",p.add(off),off.normalize().scale(-.12),false);
            }
            if(s==Spell.FROSTMINT_HEX||s==Spell.CANDY_CATASTROPHE)for(int i=0;i<24;i++){
                double a=i*Math.PI/12+time;Vec3 off=new Vec3(Math.cos(a)*radius*.7,1+Math.sin(time+i)*.5,Math.sin(a)*radius*.7);
                nativePoint(i%3==0?"purple_soda_fizz":"sugar_flake",p.add(off),new Vec3(0,.03,0),false);
            }
            return;
        }
        if(stage==SpellVisuals.Stage.CHARGE||stage==SpellVisuals.Stage.CAST){
            int arms=CavesCompat.cataclysm(s)?4:2,steps=CavesCompat.cataclysm(s)?20:10;
            for(int arm=0;arm<arms;arm++)for(int i=0;i<steps;i++){
                double t=i/(double)steps,a=time+arm*Math.PI*2/arms+t*7,r=(1-t)*(CavesCompat.cataclysm(s)?4:1.6)+.25;
                Vec3 q=p.add(Math.cos(a)*r,(1-t)*4-1,Math.sin(a)*r);
                if(!magnetic||i%4==0)nativePoint(magnetic?(i%2==0?"scarlet_magnetic_flow":"azure_magnetic_flow"):id,q,magnetic?p:p.subtract(q).normalize().scale(.08),false);
                else NativeSpellEffects.point(level,ParticleTypes.ELECTRIC_SPARK,q,p.subtract(q).normalize().scale(.05),12,false);
            }
            return;
        }
        if(stage==SpellVisuals.Stage.LINK){
            int steps=(int)Math.clamp(p.distanceTo(end)*1.6,16,96);Vec3 side=dir.cross(new Vec3(0,1,0)).normalize();if(side.lengthSqr()<.01)side=new Vec3(1,0,0);Vec3 up=side.cross(dir).normalize();
            for(int i=0;i<=steps;i++)for(int arm=0;arm<2;arm++){
                double t=i/(double)steps,a=time+t*30+arm*Math.PI;Vec3 q=p.lerp(end,t).add(side.scale(Math.cos(a)*radius*.5)).add(up.scale(Math.sin(a)*radius*.5));
                if(magnetic&&i%6!=0||!magnetic&&i%3!=0)continue;
                nativePoint(magnetic?(arm==0?"scarlet_magnetic_flow":"azure_magnetic_flow"):"proton",q,magnetic?p.lerp(end,Math.min(1,t+.08)):dir.scale(.2),false);
            }
            if(!magnetic){nativePoint("tremorzilla_explosion",end,Vec3.ZERO,false);}
            return;
        }
        if(stage==SpellVisuals.Stage.STRIKE&&s==Spell.FORSAKEN_SONAR){sonar(p,dir,true);return;}
        if(stage==SpellVisuals.Stage.FLIGHT){
            for(int i=0;i<12;i++){Vec3 q=p.subtract(dir.scale(i*.12)).add(Math.sin(time+i)*.12,Math.cos(time+i)*.12,0);nativePoint(magnetic?(i%2==0?"scarlet_magnetic_flow":"azure_magnetic_flow"):id,q,magnetic?p:dir.scale(-.025),false);}
            if(s==Spell.HULLBREAKER_SURGE){nativePoint("big_splash",p,new Vec3(.6,0,0),false);for(int i=0;i<6;i++)nativePoint("water_foam",p.add(i-3,.1,0),dir.scale(.15),false);}
            return;
        }
        if(stage!=SpellVisuals.Stage.IMPACT&&stage!=SpellVisuals.Stage.HIT&&stage!=SpellVisuals.Stage.TRAIL_BURST)return;
        if(s==Spell.NUCLEAR_STRIKE){if(stage==SpellVisuals.Stage.IMPACT&&CLOUDS.size()<2){nativePoint("mushroom_cloud",p,new Vec3(1.3,0,0),false);CLOUDS.add(level.getGameTime()+180);}return;}
        if(s==Spell.ABYSSAL_DELUGE&&stage==SpellVisuals.Stage.IMPACT)nativePoint("big_splash",p,new Vec3(1.5,0,0),false);
        if(s==Spell.FORLORN_RECKONING){for(int i=0;i<8;i++){double a=i*Math.PI/4;sonar(p.add(0,1,0),new Vec3(Math.cos(a),.05,Math.sin(a)),true);}}
        if(s==Spell.EMP_SURGE){for(int i=0;i<3;i++)nativePoint("magnet_lightning",p.add(Math.cos(i*2.1)*2,1,Math.sin(i*2.1)*2),Vec3.ZERO,false);}
        String burst=switch(s){case LUX_EXTINCTION->"tephra_small";case ACID_GLOB->"acid_drop";case ABYSSAL_DELUGE,BRINE_BUBBLE,HULLBREAKER_SURGE->"water_foam";case FORLORN_RECKONING,FORSAKEN_SONAR,DESOLATE_DAGGER->"underzealot_explosion";case FROSTMINT_HEX->"frostmint_explosion";case CANDY_CATASTROPHE,GUMBALL_VOLLEY->"purple_witch_explosion";case TREMORZILLA_BREATH->"tremorzilla_explosion";default->id;};
        int count=stage==SpellVisuals.Stage.HIT?12:48;
        for(int i=0;i<count;i++){double a=i*2.39996,y=1-2*(i+.5)/count,r=Math.sqrt(1-y*y);Vec3 off=new Vec3(Math.cos(a)*r,y*.5,Math.sin(a)*r);nativePoint(magnetic?(i%2==0?"scarlet_magnetic_flow":"azure_magnetic_flow"):burst,p.add(off.scale(Math.max(.5,radius*.5))),magnetic?p:off.scale(.18),false);}
    }
    private static void sonar(Vec3 point,Vec3 dir,boolean large){double yaw=Math.toDegrees(Math.atan2(-dir.x,dir.z)),pitch=Math.toDegrees(-Math.asin(Math.clamp(dir.y,-1,1)));nativePoint(large?"forsaken_sonar_large":"forsaken_sonar",point,new Vec3(-1,pitch,yaw),false);}
    private static void ring(Spell s,Vec3 p,double radius,double time){
        int n=(int)Math.clamp(radius*12,32,128);boolean magnetic=s.definition.area().equals("magnetic_caves");
        for(int i=0;i<n;i++){double a=i*Math.PI*2/n;Vec3 q=p.add(Math.cos(a)*radius,.04,Math.sin(a)*radius);
            if(magnetic){if(i%8==0)nativePoint(i%16==0?"scarlet_magnetic_flow":"azure_magnetic_flow",q,q.add(Math.cos(a+.1)*.12,0,Math.sin(a+.1)*.12),true);
                else NativeSpellEffects.point(world,new DustParticleOptions(new org.joml.Vector3f(i%2==0?1:.2f,.4f,i%2==0?.3f:1),.9f),q,Vec3.ZERO,10,true);}
            else {
                int color=switch(s.definition.area()){case "primordial_caves"->0xffb955;case "toxic_caves"->0x9cff35;case "abyssal_chasm"->0x54dfe7;case "forlorn_hollows"->0xba83e8;default->0xffa2df;};
                NativeSpellEffects.point(world,new DustParticleOptions(new org.joml.Vector3f((color>>16&255)/255f,(color>>8&255)/255f,(color&255)/255f),.9f),q,Vec3.ZERO,10,true);
            }
        }
    }
    private CavesClientEffects(){}
}
