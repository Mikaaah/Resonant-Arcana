package nl.townsandkingdoms.resonantarcana;

import java.util.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Vector3f;

/** Bounded God-tier choreography. Purple lightning is visual geometry, never a damaging vanilla bolt. */
@EventBusSubscriber(modid=ResonantArcana.ID,value=Dist.CLIENT)
final class GodEffects {
    private record Lightning(Vec3 ground,Vec3 top,long seed,long end){}
    private static final List<Lightning> LIGHTNING=new ArrayList<>();private static ClientLevel world;
    static void tick(){var level=Minecraft.getInstance().level;if(level!=world){LIGHTNING.clear();world=level;}if(level!=null)LIGHTNING.removeIf(b->b.end()<level.getGameTime());}
    private static int color(Spell s){return switch(s){case THUNDER_OF_THE_HEAVENS->0xb066ff;case HELVAR_FINAL_EDICT->0xff5c21;case TREMORZILLA_CATACLYSM->0xe590ff;default->s.theme.color;};}
    private static ParticleOptions dust(Spell s){int c=color(s);return new DustParticleOptions(new Vector3f((c>>16&255)/255f,(c>>8&255)/255f,(c&255)/255f),1.25f);}
    private static void point(ClientLevel l,ParticleOptions particle,Vec3 p,Vec3 velocity,int life,boolean boundary){NativeSpellEffects.point(l,particle,p,velocity,life,boundary);}
    private static void ring(ClientLevel l,Spell s,Vec3 p,double radius,boolean boundary,double angle){int count=(int)Math.clamp(radius*7,32,168);for(int i=0;i<count;i++){double a=i*Math.PI*2/count+angle;point(l,dust(s),p.add(Math.cos(a)*radius,.05,Math.sin(a)*radius),Vec3.ZERO,12,boundary);}}
    static void visual(ClientLevel l,Spell s,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double size){
        double time=l.getGameTime()*.12;var primary=dust(s);
        if(stage==SpellVisuals.Stage.RITUAL_END)return;
        if(stage==SpellVisuals.Stage.RITUAL_TARGET){ring(l,s,p,size,true,0);for(int i=0;i<12;i++){double a=i*Math.PI/6;for(int j=0;j<4;j++)point(l,primary,p.add(Math.cos(a)*(size-.8+j*.25),.06,Math.sin(a)*(size-.8+j*.25)),Vec3.ZERO,14,true);}return;}
        if(stage==SpellVisuals.Stage.CHARGE){
            ring(l,s,p,size,true,time*.025);ring(l,s,p,size*.92,true,-time*.025);
            for(int arm=0;arm<6;arm++)for(int i=0;i<24;i++){double t=i/24.0,a=time+arm*Math.PI/3+t*8,r=(1-t)*size*.65+.5;Vec3 off=new Vec3(Math.cos(a)*r,(1-t)*12,Math.sin(a)*r);point(l,i%8==0?accent(s):primary,p.add(off),off.multiply(-.025,-.04,-.025),18,false);}
            for(int i=0;i<20;i++){Vec3 q=p.add(0,1,0).lerp(end.add(0,3,0),i/20.0);point(l,primary,q,new Vec3(0,-.025,0),12,false);}return;
        }
        if(stage==SpellVisuals.Stage.STRIKE&&s==Spell.THUNDER_OF_THE_HEAVENS){if(LIGHTNING.size()<48)LIGHTNING.add(new Lightning(p,end,Double.doubleToLongBits(p.x+p.z)+l.getGameTime(),l.getGameTime()+7));for(int i=0;i<28;i++){double a=i*2.39996;point(l,i%4==0?ParticleTypes.END_ROD:primary,p.add(Math.cos(a)*2,.2,Math.sin(a)*2),new Vec3(Math.cos(a)*.1,.08,Math.sin(a)*.1),14,false);}return;}
        if(stage==SpellVisuals.Stage.LINK){if(s==Spell.TREMORZILLA_CATACLYSM)return;for(int i=0;i<60;i++){double t=i/60.0;point(l,i%6==0?accent(s):primary,p.lerp(end,t),end.subtract(p).normalize().scale(.07),12,false);}return;}
        if(stage==SpellVisuals.Stage.STRIKE){for(int i=0;i<96;i++){double t=i/96.0;Vec3 q=p.lerp(end,t);point(l,i%4==0?accent(s):primary,q,new Vec3(0,.04,0),16,false);}return;}
        if(stage==SpellVisuals.Stage.AREA){
            if(s==Spell.TREMORZILLA_CATACLYSM)return;ring(l,s,p,size,true,0);
            if(s==Spell.ARCANE_CREATION){
                Vec3 previous=null,first=null;for(int star=0;star<6;star++){double a=star*Math.PI/3+time*.05;Vec3 center=p.add(Math.cos(a)*12,4+Math.sin(a+time)*.5,Math.sin(a)*12);if(first==null)first=center;
                    for(int ray=0;ray<6;ray++){double angle=ray*Math.PI/3;for(int j=0;j<8;j++)point(l,j==7?ParticleTypes.END_ROD:primary,center.add(Math.cos(angle)*j*.23,Math.sin(angle)*j*.23,0),Vec3.ZERO,12,false);}
                    if(previous!=null)for(int i=0;i<12;i++)point(l,primary,previous.lerp(center,i/12.0),Vec3.ZERO,12,false);previous=center;
                }if(previous!=null&&first!=null)for(int i=0;i<12;i++)point(l,primary,previous.lerp(first,i/12.0),Vec3.ZERO,12,false);return;
            }
            if(s==Spell.SOLAR_SUPERNOVA){double r=Math.min(8,2+(end.y-p.y)*12);for(int i=0;i<128;i++){double a=i*2.39996,y=1-2*(i+.5)/128,rr=Math.sqrt(1-y*y);point(l,i%4==0?ParticleTypes.FLAME:primary,p.add(Math.cos(a)*rr*r,6+y*r,Math.sin(a)*rr*r),Vec3.ZERO,12,false);}return;}
            if(s==Spell.PLANETARY_POLARITY){for(int sign:new int[]{-1,1}){Vec3 pole=p.add(Math.cos(time*.5)*10*sign,2,Math.sin(time*.5)*10*sign);for(int i=0;i<20;i++){double a=time+i*Math.PI/10;var options=net.minecraft.core.registries.BuiltInRegistries.PARTICLE_TYPE.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("alexscaves",sign<0?"scarlet_magnetic_flow":"azure_magnetic_flow"));if(options instanceof SimpleParticleType particle)NativeSpellEffects.nativeParticle(particle,pole.add(Math.cos(a)*2,Math.sin(a)*2,0),pole,false);}}return;}
            for(int arm=0;arm<6;arm++)for(int i=0;i<12;i++){double t=i/12.0,a=time*.3+arm*Math.PI/3+t*3,r=size*(.2+.7*t),y=.2+Math.sin(t*Math.PI)*2;Vec3 q=p.add(Math.cos(a)*r,y,Math.sin(a)*r);point(l,i%3==0?accent(s):primary,q,new Vec3(-Math.sin(a)*.05,.025,Math.cos(a)*.05),14,false);}
            if(s==Spell.FORSAKEN_ECLIPSE&&l.getGameTime()%8==0){for(int i=0;i<4;i++){double a=i*Math.PI/2;var type=net.minecraft.core.registries.BuiltInRegistries.PARTICLE_TYPE.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("alexscaves","forsaken_sonar_large"));if(type instanceof SimpleParticleType particle)NativeSpellEffects.nativeParticle(particle,p.add(Math.cos(a)*14,2,Math.sin(a)*14),new Vec3(-1,0,Math.toDegrees(a)+90),false);}}
            return;
        }
        if(stage==SpellVisuals.Stage.IMPACT||stage==SpellVisuals.Stage.HIT){
            ring(l,s,p,Math.max(.5,size),false,time);
            for(int i=0;i<80;i++){double a=i*2.39996,y=1-2*(i+.5)/80,r=Math.sqrt(1-y*y);Vec3 v=new Vec3(Math.cos(a)*r,y*.5,Math.sin(a)*r);point(l,i%4==0?accent(s):primary,p.add(v.scale(Math.min(size*.35,5))),v.scale(.2),18,false);}
            if(CavesCompat.optional(s)){String id=switch(s.definition.area()){case "toxic_caves"->"tremorzilla_retro_explosion";case "abyssal_chasm"->"big_splash";case "forlorn_hollows"->"underzealot_explosion";case "candy_cavity"->"purple_witch_explosion";case "primordial_caves"->"amber_explosion";default->"galena_debris";};var type=net.minecraft.core.registries.BuiltInRegistries.PARTICLE_TYPE.get(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("alexscaves",id));if(type instanceof SimpleParticleType particle)NativeSpellEffects.nativeParticle(particle,p,id.equals("big_splash")?new Vec3(1.2,0,0):Vec3.ZERO,false);}
        }
    }
    private static ParticleOptions accent(Spell s){return switch(s){case VERDANT_AWAKENING,PRIMORDIAL_SOVEREIGN->new BlockParticleOption(ParticleTypes.BLOCK,net.minecraft.world.level.block.Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState());case SOLAR_SUPERNOVA,DRAGON_PRIMORDIAL_INFERNO,HELVAR_FINAL_EDICT->ParticleTypes.FLAME;case ABSOLUTE_GLACIATION,YETI_WORLD_HEART->ParticleTypes.SNOWFLAKE;case SOUL_ASCENDANCY->ParticleTypes.SOUL_FIRE_FLAME;case ABYSSAL_WORLD_TIDE,KRAKEN_WORLD_DROWN->ParticleTypes.SPLASH;default->ParticleTypes.END_ROD;};}
    @SubscribeEvent public static void render(RenderLevelStageEvent event){
        if(event.getStage()!=RenderLevelStageEvent.Stage.AFTER_PARTICLES||LIGHTNING.isEmpty())return;var mc=Minecraft.getInstance();if(mc.level!=world)return;var buffers=mc.renderBuffers().bufferSource();var type=RenderType.lightning();var out=buffers.getBuffer(type);Vec3 camera=event.getCamera().getPosition();var pose=new PoseStack();
        for(var bolt:LIGHTNING){var random=new Random(bolt.seed());Vec3 previous=bolt.top().subtract(camera);for(int i=1;i<=12;i++){double t=i/12.0;Vec3 next=bolt.top().lerp(bolt.ground(),t).add(i==12?0:(random.nextDouble()-.5)*3,0,i==12?0:(random.nextDouble()-.5)*3).subtract(camera);
            Vec3 axis=next.subtract(previous).normalize(),side=axis.cross(new Vec3(0,1,0)).normalize();if(side.lengthSqr()<.01)side=new Vec3(1,0,0);Vec3 up=side.cross(axis);
            for(int layer=0;layer<3;layer++){double width=layer==0?.3:layer==1?.12:.035;int alpha=layer==0?65:layer==1?155:255;
                for(int face=0;face<4;face++){double a=face*Math.PI/2,b=(face+1)*Math.PI/2;Vec3 o=side.scale(Math.cos(a)*width).add(up.scale(Math.sin(a)*width)),n=side.scale(Math.cos(b)*width).add(up.scale(Math.sin(b)*width));
                    for(Vec3 v:new Vec3[]{previous.add(o),next.add(o),next.add(n),previous.add(n)})out.addVertex(pose.last().pose(),(float)v.x,(float)v.y,(float)v.z).setColor(layer==2?245:170,layer==2?220:65,255,alpha);
                }
            }previous=next;}
        }buffers.endBatch(type);
    }
    private GodEffects(){}
}
