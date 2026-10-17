package nl.townsandkingdoms.resonantarcana;

import java.util.ArrayList;
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

/** Native particle choreography and an opaque, depth-tested event-horizon mesh. */
@EventBusSubscriber(modid=ResonantArcana.ID,value=Dist.CLIENT)
final class CataclysmEffects {
    private record Horizon(ClientLevel level,Vec3 pos,double radius,long expires){}
    private static final ArrayList<Horizon> HOLES=new ArrayList<>();
    private CataclysmEffects(){}
    static boolean visual(ClientLevel level,Spell s,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double size){
        if(s!=Spell.DOOM&&s.kind!=Spell.Kind.CATACLYSM)return false;
        double time=level.getGameTime()*.09;
        boolean charge=stage==SpellVisuals.Stage.CAST||stage==SpellVisuals.Stage.CHARGE;
        if(s==Spell.DOOM){doom(level,stage,p,size,time,charge);return true;}
        double radius=charge?Math.max(.6,size):size;
        if(stage==SpellVisuals.Stage.LINK||stage==SpellVisuals.Stage.STRIKE)return false;
        if(stage==SpellVisuals.Stage.HIT)return true;
        if(stage==SpellVisuals.Stage.IMPACT){
            for(int i=0;i<180;i++){
                double a=i*2.39996,y=1-2*(i+.5)/180,r=Math.sqrt(1-y*y);
                Vec3 dir=new Vec3(Math.cos(a)*r,y*.8,Math.sin(a)*r);
                point(level,i%3==0?accent(s):SpellParticles.particle(s.theme),p.add(dir.scale(radius*.3)),dir.scale(.3),26);
            }
            circle(level,s,p,radius,time,0,140);return true;
        }
        if(charge){
            // Two counter-rotating orbits and rising columns visibly gather the long windup.
            for(int arm=0;arm<3;arm++)for(int i=0;i<40;i++){
                double a=time*(arm%2==0?1:-1)+i*Math.PI/20,r=radius*(1+arm*.35);
                Vec3 off=arm==0?new Vec3(Math.cos(a)*r,.15,Math.sin(a)*r):new Vec3(Math.cos(a)*r,Math.sin(a)*r,Math.sin(a*.5)*r*.35);
                point(level,i%3==0?accent(s):SpellParticles.particle(s.theme),p.add(off),off.scale(-.018),12);
            }
            return true;
        }
        circle(level,s,p,radius,time,.03,120);
        switch(s){
            case ASTRAL_APOCALYPSE -> {
                for(int star=0;star<3;star++){
                    double a=time+star*Math.PI*2/3;Vec3 q=p.add(Math.cos(a)*5,7+Math.sin(time)*2,Math.sin(a)*5);
                    star(level,s,q,1.1,time);line(level,s,q,p,22);
                }
                circle(level,s,p.add(0,6,0),radius*.55,-time,.55,100);
            }
            case CRIMSON_ECLIPSE -> {
                circle(level,s,p.add(0,7,0),4.2,time,Math.PI/2,150);
                for(int i=0;i<90;i++){
                    double a=i*2.39996+time,r=radius*Math.sqrt((i+.5)/90);
                    Vec3 q=p.add(Math.cos(a)*r,Math.sin(time+i)*.3,Math.sin(a)*r);
                    point(level,SpellParticles.particle(s.theme),q,p.add(0,7,0).subtract(q).normalize().scale(.12),16);
                }
            }
            case WORLD_PYRE -> {
                circle(level,s,p.add(0,10,0),radius*.7,-time,.1,130);
                for(int i=0;i<100;i++){
                    double a=i*2.39996+time,r=radius*Math.sqrt((i+.5)/100);
                    Vec3 q=p.add(Math.cos(a)*r,9+(i%7),Math.sin(a)*r);
                    point(level,i%4==0?ParticleTypes.LAVA:ParticleTypes.FLAME,q,new Vec3(0,-.25,0),20);
                }
            }
            case VOID_CORONATION -> {
                for(int spire=0;spire<5;spire++){
                    double a=spire*Math.PI*2/5;Vec3 base=p.add(Math.cos(a)*7,0,Math.sin(a)*7);
                    for(int i=0;i<35;i++){
                        double h=i*.23,w=(1-i/38.0)*.65,b=time+i*.4;
                        point(level,i%5==0?ParticleTypes.WITCH:ParticleTypes.REVERSE_PORTAL,base.add(Math.cos(b)*w,h,Math.sin(b)*w),new Vec3(0,.015,0),14);
                    }
                    line(level,s,base.add(0,8,0),p.add(0,4,0),18);
                }
            }
            case HEAVENS_THUNDER -> {
                circle(level,s,p.add(0,12,0),radius*.7,time,.2,100);
                for(int bolt=0;bolt<6;bolt++){
                    double a=bolt*Math.PI/3+time*.08;Vec3 base=p.add(Math.cos(a)*4,0,Math.sin(a)*4);
                    for(int i=0;i<25;i++)point(level,ParticleTypes.ELECTRIC_SPARK,base.add(Math.sin(i*2+time)*.5,i*.5,Math.cos(i*3+time)*.5),Vec3.ZERO,9);
                }
            }
            case RIFT_COLLISION -> {
                for(int side=-1;side<=1;side+=2){
                    Vec3 center=p.add(side*6,4,0);circle(level,s,center,4,time*side,Math.PI/2,110);
                    for(int i=0;i<32;i++){
                        double t=i/31.0;Vec3 q=center.lerp(p.add(0,3,0),t).add(0,Math.sin(t*9+time),Math.cos(t*9+time));
                        point(level,i%3==0?ParticleTypes.END_ROD:ParticleTypes.PORTAL,q,p.subtract(q).normalize().scale(.08),12);
                    }
                }
            }
            case SOUL_REQUIEM -> {
                for(int shell=0;shell<3;shell++){
                    double r=2+((time*3+shell*4)%12);circle(level,s,p,r,time,shell*.55,90);
                }
                for(int i=0;i<60;i++){
                    double a=i*2.39996+time;point(level,ParticleTypes.SOUL_FIRE_FLAME,p.add(Math.cos(a)*5,(i%10)*.6,Math.sin(a)*5),new Vec3(0,.04,0),18);
                }
            }
            case WORLDROOT_CATASTROPHE -> {
                for(int root=0;root<8;root++)for(int i=0;i<24;i++){
                    double t=i/23.0,a=root*Math.PI/4+Math.sin(t*5+time*.3)*.2;
                    Vec3 q=p.add(Math.cos(a)*radius*t,Math.sin(t*Math.PI)*2.5,Math.sin(a)*radius*t);
                    point(level,i%4==0?ParticleTypes.SPORE_BLOSSOM_AIR:SpellParticles.particle(s.theme),q,new Vec3(0,.025,0),18);
                    if(i%6==0)star(level,s,q,.4,time);
                }
            }
            case FINAL_JUDGEMENT -> {
                for(int i=0;i<6;i++){
                    double a=i*Math.PI/3;Vec3 q=p.add(Math.cos(a)*7,0,Math.sin(a)*7);
                    line(level,s,q,q.add(0,12,0),34);star(level,s,q.add(0,11,0),1.2,time);
                }
                circle(level,s,p.add(0,11,0),8,-time,0,150);
                star(level,s,p.add(0,7,0),3,time*.3);
            }
            case ETERNAL_WINTER -> {
                for(int arm=0;arm<6;arm++)for(int i=0;i<28;i++){
                    double t=i/27.0,a=arm*Math.PI/3,r=radius*t;
                    Vec3 q=p.add(Math.cos(a)*r,.1+Math.sin(t*Math.PI)*3,Math.sin(a)*r);
                    point(level,i%3==0?ParticleTypes.END_ROD:ParticleTypes.SNOWFLAKE,q,new Vec3(0,.015,0),18);
                    if(i%5==0)for(int sign:new int[]{-1,1}){
                        Vec3 branch=q.add(Math.cos(a+sign*.7)*1.4,.3,Math.sin(a+sign*.7)*1.4);
                        line(level,s,q,branch,5);
                    }
                }
                for(int i=0;i<55;i++){double a=i*2.39996+time;point(level,ParticleTypes.SNOWFLAKE,p.add(Math.cos(a)*radius,4+i%8,Math.sin(a)*radius),new Vec3(-Math.sin(a)*.1,-.12,Math.cos(a)*.1),24);}
            }
            default -> throw new IllegalArgumentException("Missing cataclysm visual");
        }
        return true;
    }
    private static void doom(ClientLevel level,SpellVisuals.Stage stage,Vec3 p,double size,double time,boolean charge){
        if(stage==SpellVisuals.Stage.HIT)return;
        if(stage==SpellVisuals.Stage.IMPACT){
            HOLES.removeIf(h->h.level()==level&&h.pos().distanceToSqr(p)<4);
            for(int ring=0;ring<4;ring++)doomRing(level,p,size*(.3+ring*.22),time,ring*.55,100,.04);
            return;
        }
        double core=charge?.2+size*.35:Math.clamp(size*.17,.6,2.1),outer=charge?size*1.6:Math.max(3,size);
        HOLES.removeIf(h->h.level()!=level||h.expires()<level.getGameTime()||h.pos().distanceToSqr(p)<.5);
        if(HOLES.size()<16)HOLES.add(new Horizon(level,p,core,level.getGameTime()+7));
        for(int arm=0;arm<4;arm++)for(int i=0;i<64;i++){
            double t=((i/64.0+time*.18)%1),r=core+(outer-core)*(1-t),a=arm*Math.PI/2+t*7+time;
            double tilt=(arm%2==0?.3:-.4);
            Vec3 off=new Vec3(Math.cos(a)*r,Math.sin(a)*r*tilt,Math.sin(a)*r);
            var particle=new DustColorTransitionOptions(new Vector3f(1,.015f,.035f),new Vector3f(.14f,0,.005f),1.35f);
            Vec3 velocity=off.normalize().scale(-.1).add(-Math.sin(a)*.035,0,Math.cos(a)*.035);
            point(level,particle,p.add(off),velocity,10);
            if(i%8==0)point(level,ParticleTypes.SMOKE,p.add(off.scale(.85)),off.scale(-.03),12);
        }
        doomRing(level,p,core*1.04,time,Math.PI/2,80,.005);
    }
    private static void doomRing(ClientLevel l,Vec3 p,double r,double time,double tilt,int n,double speed){
        var red=new DustParticleOptions(new Vector3f(.95f,.005f,.02f),1.4f);
        for(int i=0;i<n;i++){double a=i*Math.PI*2/n+time;Vec3 off=new Vec3(Math.cos(a)*r,Math.sin(a)*Math.sin(tilt)*r,Math.sin(a)*Math.cos(tilt)*r);point(l,red,p.add(off),off.scale(speed),14);}
    }
    private static void circle(ClientLevel l,Spell s,Vec3 p,double r,double time,double tilt,int n){
        for(int i=0;i<n;i++){
            double a=time+i*Math.PI*2/n;Vec3 off=new Vec3(Math.cos(a)*r,Math.sin(a)*Math.sin(tilt)*r,Math.sin(a)*Math.cos(tilt)*r);
            point(l,i%4==0?accent(s):SpellParticles.particle(s.theme),p.add(off),Vec3.ZERO,13);
        }
    }
    private static void line(ClientLevel l,Spell s,Vec3 a,Vec3 b,int n){for(int i=0;i<n;i++)point(l,accent(s),a.lerp(b,i/(double)n),Vec3.ZERO,12);}
    private static void star(ClientLevel l,Spell s,Vec3 p,double size,double time){
        for(int i=0;i<6;i++){double a=i*Math.PI/3+time;line(l,s,p,p.add(Math.cos(a)*size,Math.sin(a)*size,0),5);}
    }
    private static ParticleOptions accent(Spell s){return switch(s.theme){case FIRE->ParticleTypes.FLAME;case BLOOD->ParticleTypes.CRIMSON_SPORE;case DARK->ParticleTypes.WITCH;case THUNDER->ParticleTypes.ELECTRIC_SPARK;case SOUL->ParticleTypes.SCULK_SOUL;case POISON->ParticleTypes.HAPPY_VILLAGER;case ICE->ParticleTypes.SNOWFLAKE;default->ParticleTypes.END_ROD;};}
    private static void point(ClientLevel l,ParticleOptions particle,Vec3 p,Vec3 v,int life){NativeSpellEffects.point(l,particle,p,v,life,false);}
    private static void sphereVertex(com.mojang.blaze3d.vertex.VertexConsumer v,org.joml.Matrix4f m,double lat,double lon,double r){
        double ring=Math.cos(lat)*r;v.addVertex(m,(float)(Math.cos(lon)*ring),(float)(Math.sin(lat)*r),(float)(Math.sin(lon)*ring)).setColor(0,0,0,255);
    }
    @SubscribeEvent public static void render(RenderLevelStageEvent event){
        if(event.getStage()!=RenderLevelStageEvent.Stage.AFTER_PARTICLES)return;
        var mc=Minecraft.getInstance();if(mc.level==null){HOLES.clear();return;}
        HOLES.removeIf(h->h.level()!=mc.level||h.expires()<mc.level.getGameTime());if(HOLES.isEmpty())return;
        var buffers=mc.renderBuffers().bufferSource();var type=RenderType.debugQuads();var vertex=buffers.getBuffer(type);
        Vec3 camera=event.getCamera().getPosition();var pose=new PoseStack();pose.mulPose(event.getModelViewMatrix());
        for(var hole:HOLES){
            pose.pushPose();pose.translate(hole.pos().x-camera.x,hole.pos().y-camera.y,hole.pos().z-camera.z);
            var matrix=pose.last().pose();double r=hole.radius();
            for(int lat=0;lat<12;lat++)for(int lon=0;lon<24;lon++){
                double a=-Math.PI/2+lat*Math.PI/12,b=a+Math.PI/12,c=lon*Math.PI/12,d=c+Math.PI/12;
                sphereVertex(vertex,matrix,a,c,r);sphereVertex(vertex,matrix,b,c,r);
                sphereVertex(vertex,matrix,b,d,r);sphereVertex(vertex,matrix,a,d,r);
            }
            pose.popPose();
        }
        buffers.endBatch(type);
    }
}
