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
    private static int color(Spell s){return switch(s){case THUNDER_OF_THE_HEAVENS->0xb066ff;case HELVAR_FINAL_EDICT,DRAGON_PRIMORDIAL_INFERNO->0xff5c21;case SANDWORM_WORLD_DEVOURER->0xe1bc79;case PRIMORDIAL_SOVEREIGN->0xffbc4a;case ABYSSAL_WORLD_TIDE,KRAKEN_WORLD_DROWN->0x42dcd7;case CONFECTION_APOCALYPSE->0xff9cdd;case TREMORZILLA_CATACLYSM->0xe590ff;default->s.theme.color;};}
    private static ParticleOptions dust(Spell s){int c=color(s);return new DustParticleOptions(new Vector3f((c>>16&255)/255f,(c>>8&255)/255f,(c&255)/255f),1.25f);}
    private static void point(ClientLevel l,ParticleOptions particle,Vec3 p,Vec3 velocity,int life,boolean boundary){NativeSpellEffects.point(l,particle,p,velocity,life,boundary);}
    private static void ring(ClientLevel l,Spell s,Vec3 p,double radius,boolean boundary,double angle){int count=(int)Math.clamp(radius*7,32,168);for(int i=0;i<count;i++){double a=i*Math.PI*2/count+angle;point(l,dust(s),p.add(Math.cos(a)*radius,.05,Math.sin(a)*radius),Vec3.ZERO,12,boundary);}}
    static void visual(ClientLevel l,Spell s,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double size){
        double time=l.getGameTime()*.12;var primary=dust(s);
        if(stage==SpellVisuals.Stage.RITUAL_END)return;
        if(stage==SpellVisuals.Stage.RITUAL_TARGET){ring(l,s,p,size,true,0);for(int i=0;i<12;i++){double a=i*Math.PI/6;for(int j=0;j<4;j++)point(l,primary,p.add(Math.cos(a)*(size-.8+j*.25),.06,Math.sin(a)*(size-.8+j*.25)),Vec3.ZERO,14,true);}return;}
        if(stage==SpellVisuals.Stage.CHARGE){
            double progress=Math.clamp((size-5)/3,0,1);Vec3 focus=p.add(0,2.4+progress,0);
            ring(l,s,p,3+progress*2,true,time*.04);
            globe(l,s,focus,.4+progress*1.5,56,time*.2);
            for(int arm=0;arm<6;arm++)for(int i=0;i<22;i++){double t=i/21.0,a=arm*Math.PI/3+time*.5+t*7,r=(1-t)*(4+progress*2)+.3;Vec3 off=new Vec3(Math.cos(a)*r,(1-t)*5,Math.sin(a)*r);spark(l,s,focus.add(off),off.scale(-.075),i);}
            if(l.getGameTime()%8==0)point(l,ParticleTypes.FLASH,focus,Vec3.ZERO,4,false);
            return;
        }
        if(stage==SpellVisuals.Stage.STRIKE&&s==Spell.THUNDER_OF_THE_HEAVENS){if(LIGHTNING.size()<48)LIGHTNING.add(new Lightning(p,end,Double.doubleToLongBits(p.x+p.z)+l.getGameTime(),l.getGameTime()+7));for(int i=0;i<28;i++){double a=i*2.39996;point(l,i%4==0?ParticleTypes.END_ROD:primary,p.add(Math.cos(a)*2,.2,Math.sin(a)*2),new Vec3(Math.cos(a)*.1,.08,Math.sin(a)*.1),14,false);}return;}
        if(stage==SpellVisuals.Stage.LINK){if(s==Spell.TREMORZILLA_CATACLYSM)return;for(int i=0;i<60;i++){double t=i/60.0;point(l,i%6==0?accent(s):primary,p.lerp(end,t),end.subtract(p).normalize().scale(.07),12,false);}return;}
        if(stage==SpellVisuals.Stage.STRIKE){for(int i=0;i<96;i++){double t=i/96.0;Vec3 q=p.lerp(end,t);point(l,i%4==0?accent(s):primary,q,new Vec3(0,.04,0),16,false);}return;}
        if(stage==SpellVisuals.Stage.AREA){
            if(s==Spell.TREMORZILLA_CATACLYSM)return;ring(l,s,p,size,true,0);
            if(scene(l,s,p,size,time))return;
            if(s==Spell.ARCANE_CREATION){
                globe(l,s,p.add(0,6,0),3,96,time*.1);
                Vec3 previous=null,first=null;for(int star=0;star<6;star++){double a=star*Math.PI/3+time*.05;Vec3 center=p.add(Math.cos(a)*12,4+Math.sin(a+time)*.5,Math.sin(a)*12);if(first==null)first=center;
                    for(int ray=0;ray<6;ray++){double angle=ray*Math.PI/3;for(int j=0;j<8;j++)spark(l,s,center.add(Math.cos(angle)*j*.23,Math.sin(angle)*j*.23,0),Vec3.ZERO,j);}
                    if(previous!=null)for(int i=0;i<12;i++)point(l,primary,previous.lerp(center,i/12.0),Vec3.ZERO,12,false);previous=center;
                }if(previous!=null&&first!=null)for(int i=0;i<12;i++)point(l,primary,previous.lerp(first,i/12.0),Vec3.ZERO,12,false);return;
            }
            if(s==Spell.SOLAR_SUPERNOVA){double r=Math.min(8,2+(end.y-p.y)*12);for(int i=0;i<128;i++){double a=i*2.39996,y=1-2*(i+.5)/128,rr=Math.sqrt(1-y*y);spark(l,s,p.add(Math.cos(a)*rr*r,9+y*r,Math.sin(a)*rr*r),new Vec3(0,.02,0),i);}return;}
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
    private static void spark(ClientLevel l,Spell s,Vec3 p,Vec3 v,int index){
        if(index%4==0)point(l,accent(s),p,v,16,false);
        else NativeSpellEffects.tinted(l,io.redspace.ironsspellbooks.registries.ParticleRegistry.WISP_PARTICLE.get(),p,v,14,color(s),.85f);
    }
    private static void stroke(ClientLevel l,Spell s,Vec3 a,Vec3 b,int steps){for(int i=0;i<=steps;i++)spark(l,s,a.lerp(b,i/(double)steps),Vec3.ZERO,i);}
    private static void globe(ClientLevel l,Spell s,Vec3 center,double radius,int count,double rotation){
        for(int i=0;i<count;i++){double y=1-2*(i+.5)/count,a=i*2.39996+rotation,r=Math.sqrt(1-y*y);Vec3 off=new Vec3(Math.cos(a)*r,y,Math.sin(a)*r).scale(radius);spark(l,s,center.add(off),off.normalize().scale(.025),i);}
    }
    /** Every field has a readable silhouette; these are budgeted client particles, not entities. */
    private static boolean scene(ClientLevel l,Spell s,Vec3 p,double size,double time){
        switch(s){
            case CRIMSON_APOTHEOSIS,EVENTIDE_HORIZON->{
                boolean blood=s==Spell.CRIMSON_APOTHEOSIS;Vec3 center=p.add(0,blood?9:6,0);double radius=blood?5:3;
                for(int i=0;i<128;i++){double a=i*Math.PI/64+time*.15;Vec3 off=blood?new Vec3(Math.cos(a)*radius,Math.sin(a)*radius,Math.sin(a)*.3):new Vec3(Math.cos(a)*radius*2,Math.sin(a*2)*.5,Math.sin(a)*radius);spark(l,s,center.add(off),off.scale(-.01),i);}
                for(int arm=0;arm<5;arm++)for(int i=0;i<18;i++){double t=i/18.0,a=arm*Math.PI*2/5+time+t*5,r=2+(1-t)*size*.65;spark(l,s,p.add(Math.cos(a)*r,.4+t*5,Math.sin(a)*r),new Vec3(-Math.cos(a)*.08,.05,-Math.sin(a)*.08),i);}
                return true;
            }
            case ABSOLUTE_GLACIATION,YETI_WORLD_HEART->{
                for(int k=0;k<10;k++){double a=k*Math.PI/5,r=size*.65;Vec3 base=p.add(Math.cos(a)*r,0,Math.sin(a)*r),tip=base.add(Math.cos(a)*1.5,5+(k%3)*2,Math.sin(a)*1.5);
                    Vec3 left=base.add(-Math.sin(a)*1.3,0,Math.cos(a)*1.3),right=base.add(Math.sin(a)*1.3,0,-Math.cos(a)*1.3);stroke(l,s,left,tip,13);stroke(l,s,right,tip,13);stroke(l,s,left,right,6);
                    point(l,ParticleTypes.SNOWFLAKE,tip,new Vec3(0,-.1,0),20,false);
                }return true;
            }
            case VERDANT_AWAKENING,PRIMORDIAL_SOVEREIGN->{
                for(int root=0;root<7;root++)for(int i=0;i<32;i++){double t=i/31.0,a=root*Math.PI*2/7+Math.sin(t*4+time*.2)*.3,r=size*.7*(1-t)+1;
                    Vec3 q=p.add(Math.cos(a)*r,Math.sin(t*Math.PI/2)*9,Math.sin(a)*r);spark(l,s,q,new Vec3(0,.025,0),i);
                    if(i>20&&i%3==0)point(l,accent(s),q.add(Math.cos(i)*2,.4,Math.sin(i)*2),new Vec3(0,-.03,0),22,false);
                }globe(l,s,p.add(0,9,0),4,72,time*.1);return true;
            }
            case HEAVENS_COVENANT->{
                for(int arm=0;arm<8;arm++)for(int i=0;i<26;i++){double t=i/25.0,a=arm*Math.PI/4+time*.025,r=size*.8*Math.cos(t*Math.PI/2);Vec3 q=p.add(Math.cos(a)*r,Math.sin(t*Math.PI/2)*12,Math.sin(a)*r);spark(l,s,q,new Vec3(0,.035,0),i);}
                Vec3 cross=p.add(0,13,0);stroke(l,s,cross.add(0,-2,0),cross.add(0,3,0),20);stroke(l,s,cross.add(-2,1,0),cross.add(2,1,0),16);return true;
            }
            case SOUL_ASCENDANCY->{
                for(int soul=0;soul<7;soul++){double a=soul*Math.PI*2/7+time*.06,r=size*.55;Vec3 center=p.add(Math.cos(a)*r,3+Math.sin(time+soul)*1.2,Math.sin(a)*r);globe(l,s,center,.8,16,time);
                    for(int i=0;i<18;i++){double t=i/18.0;spark(l,s,center.add(Math.sin(t*6+time)*t,-t*4,Math.cos(t*6+time)*t),new Vec3(0,.08,0),i);}
                }return true;
            }
            case ABYSSAL_WORLD_TIDE,KRAKEN_WORLD_DROWN->{
                for(int crest=0;crest<8;crest++)for(int i=0;i<28;i++){double t=i/27.0,a=crest*Math.PI/4+time*.045+t*.4,r=size*(.35+.4*Math.sin(t*Math.PI));Vec3 q=p.add(Math.cos(a)*r,t*10,Math.sin(a)*r);spark(l,s,q,new Vec3(-Math.cos(a)*.06,-.04,-Math.sin(a)*.06),i);
                    if(i%4==0)point(l,ParticleTypes.SPLASH,q,new Vec3(0,.1,0),12,false);
                }return true;
            }
            case FORSAKEN_ECLIPSE->{
                for(int arch=0;arch<4;arch++)for(int i=0;i<40;i++){double t=i/39.0,a=arch*Math.PI/2+t*Math.PI,r=size*.7;Vec3 q=p.add(Math.cos(a)*r,1+Math.sin(t*Math.PI)*10,Math.sin(a)*r);spark(l,s,q,new Vec3(0,-.025,0),i);}
                globe(l,s,p.add(0,8,0),3,64,-time*.2);return false; // Retain native sonar accents below.
            }
            case CONFECTION_APOCALYPSE->{
                for(int candy=0;candy<6;candy++){double a=candy*Math.PI/3+time*.08;Vec3 center=p.add(Math.cos(a)*size*.6,4+Math.sin(a*2+time)*2,Math.sin(a)*size*.6);
                    for(int i=0;i<28;i++){double y=1-2*(i+.5)/28,b=i*2.39996,r=Math.sqrt(1-y*y);int rgb=i%2==0?0xff9cdf:0x9effed;NativeSpellEffects.tinted(l,io.redspace.ironsspellbooks.registries.ParticleRegistry.WISP_PARTICLE.get(),center.add(Math.cos(b)*r*1.8,y*1.8,Math.sin(b)*r*1.8),Vec3.ZERO,16,rgb,.85f);}
                }return true;
            }
            case HELVAR_FINAL_EDICT->{
                for(int cut=0;cut<8;cut++){double a=cut*Math.PI/4;Vec3 outer=p.add(Math.cos(a)*size*.85,.12,Math.sin(a)*size*.85);stroke(l,s,p.add(0,.12,0),outer,25);point(l,ParticleTypes.LAVA,outer,new Vec3(0,.04,0),16,false);}return true;
            }
            case SANDWORM_WORLD_DEVOURER->{
                for(int arm=0;arm<5;arm++)for(int i=0;i<34;i++){double t=i/33.0,a=time*.4+arm*Math.PI*2/5+t*9,r=2+t*size*.5;Vec3 q=p.add(Math.cos(a)*r,t*11,Math.sin(a)*r);spark(l,s,q,new Vec3(-Math.sin(a)*.1,.04,Math.cos(a)*.1),i);
                    if(i%4==0)point(l,new BlockParticleOption(ParticleTypes.BLOCK,net.minecraft.world.level.block.Blocks.SAND.defaultBlockState()),q,new Vec3(0,.05,0),18,false);
                }return true;
            }
            case DRAGON_PRIMORDIAL_INFERNO->{
                for(int wing:new int[]{-1,1})for(int feather=0;feather<8;feather++){Vec3 base=p.add(wing*2,5,0),tip=p.add(wing*(5+feather*1.6),10-feather*.5,Math.sin(time*.5)*2-feather*.7);stroke(l,s,base,tip,20);}
                for(int i=0;i<48;i++){double t=i/48.0,a=t*Math.PI*6+time;Vec3 q=p.add(Math.cos(a)*3,t*13,Math.sin(a)*3);spark(l,s,q,new Vec3(0,.08,0),i);}return true;
            }
            default->{return false;}
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
