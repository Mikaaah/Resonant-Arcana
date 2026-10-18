package nl.townsandkingdoms.resonantarcana;

import java.util.ArrayList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.render.RenderHelper;
import org.joml.Vector3f;

/** Reference-inspired golden seals, coloured mana sparks, ignition dome and rising fireball. */
@EventBusSubscriber(modid=ResonantArcana.ID,value=Dist.CLIENT)
final class ExplosionEffects {
    private record Bloom(ClientLevel level,Vec3 center,Vec3 ground,double radius,long started){}
    private static final ArrayList<Bloom> BLOOMS=new ArrayList<>();
    private static final ParticleOptions GOLD=new DustColorTransitionOptions(new Vector3f(1,.85f,.32f),new Vector3f(1,.22f,.015f),1.15f);
    private static final ParticleOptions WHITE=new DustParticleOptions(new Vector3f(1,.98f,.8f),1.25f);
    private static final int[] SPARKS={0x78eaff,0xf190ff,0x85ffc2,0xc7a6ff,0xffe28a};
    static void visual(ClientLevel l,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double size){
        double time=l.getGameTime()*.12;
        if(stage==SpellVisuals.Stage.RITUAL_TARGET){target(l,p,end,Math.clamp((size-5)/2,0,1),time);return;}
        if(stage==SpellVisuals.Stage.CHARGE){charge(l,p,Math.clamp((size-5)/2,0,1),time);return;}
        if(stage==SpellVisuals.Stage.IMPACT){
            BLOOMS.removeIf(b->b.level()!=l||b.center().distanceToSqr(p)<1);
            var ground=l.clip(new net.minecraft.world.level.ClipContext(p.add(0,.2,0),p.add(0,-16,0),net.minecraft.world.level.ClipContext.Block.COLLIDER,net.minecraft.world.level.ClipContext.Fluid.NONE,net.minecraft.world.phys.shapes.CollisionContext.empty()));
            if(BLOOMS.size()<8)BLOOMS.add(new Bloom(l,p,ground.getType()==net.minecraft.world.phys.HitResult.Type.BLOCK?ground.getLocation():null,size,l.getGameTime()));
            point(l,ParticleTypes.FLASH,p.add(0,1,0),Vec3.ZERO,4);
            for(int i=0;i<120;i++){
                double a=i*2.39996,y=1-2*(i+.5)/120,r=Math.sqrt(1-y*y);
                Vec3 v=new Vec3(Math.cos(a)*r,y*.5,Math.sin(a)*r);
                NativeSpellEffects.tinted(l,ParticleRegistry.EMBER_PARTICLE.get(),p.add(v),v.scale(.55),28,0xffa326,1.4f);
            }
        }
    }
    private static void charge(ClientLevel l,Vec3 p,double progress,double time){
        for(int ring=0;ring<2;ring++)for(int i=0;i<80;i++){
            double a=i*Math.PI/40,r=5.3+progress-ring*.3;
            NativeSpellEffects.point(l,GOLD,p.add(Math.cos(a)*r,.04,Math.sin(a)*r),Vec3.ZERO,10,true);
        }
        for(int i=0;i<80;i++){
            double a=i*Math.PI/40+time*.3,r=4.2+progress;
            point(l,GOLD,p.add(Math.cos(a)*r,5.5,Math.sin(a)*r),Vec3.ZERO,10);
        }
        for(int arm=0;arm<4;arm++)for(int i=0;i<28;i++){
            double t=i/28.0,a=arm*Math.PI/2+time+t*9,r=2.5+t*3;
            Vec3 q=p.add(Math.cos(a)*r,.3+t*7,Math.sin(a)*r);
            NativeSpellEffects.tinted(l,ParticleRegistry.WISP_PARTICLE.get(),q,new Vec3(-Math.sin(a)*.12,-.16,Math.cos(a)*.12),16,SPARKS[(i+arm)%SPARKS.length],.6f);
        }
        for(int i=0;i<14;i++){
            double a=i*2.39996+time,r=2+i%3;
            NativeSpellEffects.tinted(l,ParticleTypes.FIREWORK,p.add(Math.cos(a)*r,1+i%5,Math.sin(a)*r),new Vec3(0,.015,0),18,SPARKS[i%SPARKS.length],.8f);
        }
    }
    private static void target(ClientLevel l,Vec3 p,Vec3 top,double progress,double time){
        Vec3 axis=top.subtract(p).normalize();if(axis.lengthSqr()<.01)return;
        Vec3 side=axis.cross(new Vec3(0,0,1)).normalize();if(side.lengthSqr()<.01)side=new Vec3(1,0,0);
        Vec3 up=axis.cross(side).normalize();
        int segments=(int)Math.clamp(p.distanceTo(top)*5,24,128);
        for(int i=0;i<=segments;i++){
            double t=i/(double)segments,a=t*25-time*2;
            Vec3 q=p.lerp(top,t),off=side.scale(Math.cos(a)*.25).add(up.scale(Math.sin(a)*.25));
            point(l,i%3==0?WHITE:GOLD,q,Vec3.ZERO,10);point(l,GOLD,q.add(off),Vec3.ZERO,10);
        }
        int rings=CataclysmEffects.explosionRings(progress);
        for(int ring=0;ring<rings;ring++){
            Vec3 center=p.lerp(top,.12+ring*.17);double radius=2.6+ring*.45;
            for(int i=0;i<96;i++){
                double a=i*Math.PI/48+time*(ring%2==0?.15:-.15);
                Vec3 off=side.scale(Math.cos(a)*radius).add(up.scale(Math.sin(a)*radius));
                point(l,i%8==0?WHITE:GOLD,center.add(off),Vec3.ZERO,10);
                if(i%8==0)point(l,WHITE,center.add(off.scale(.82)),Vec3.ZERO,10);
            }
            for(int arm=0;arm<2;arm++)for(int i=0;i<18;i++){
                double t=i/18.0,a=arm*Math.PI+time+t*7,r=radius*(1.8-t);
                Vec3 off=side.scale(Math.cos(a)*r).add(up.scale(Math.sin(a)*r)).add(axis.scale(2*(1-t)));
                NativeSpellEffects.tinted(l,ParticleRegistry.WISP_PARTICLE.get(),center.add(off),off.scale(-.06),14,0xffb42b,.7f);
            }
        }
    }
    static void tick(){
        var level=Minecraft.getInstance().level;
        BLOOMS.removeIf(b->b.level()!=level||level.getGameTime()-b.started()>90);
        if(level==null||level.getGameTime()%2!=0)return;
        for(var b:BLOOMS){
            double age=level.getGameTime()-b.started(),r=b.radius()*Math.min(1,age/32.0);
            if(age<40&&b.ground()!=null)for(int ring=0;ring<2;ring++)for(int i=0;i<96;i++){
                double a=i*Math.PI/48,radius=r*(1+ring*.18);
                Vec3 q=b.ground().add(Math.cos(a)*radius,.12+ring*.18,Math.sin(a)*radius);
                NativeSpellEffects.tinted(level,ParticleRegistry.RING_SMOKE_PARTICLE.get(),q,new Vec3(Math.cos(a)*.08,.015,Math.sin(a)*.08),12,0xffcb78,.7f);
            }
            for(int i=0;i<70;i++){
                double a=i*2.39996+age*.05,y=(i+.5)/70,radius=Math.sqrt(1-y*y)*r;
                Vec3 q=b.center().add(Math.cos(a)*radius,y*r+Math.max(0,age-30)*.13,Math.sin(a)*radius);
                NativeSpellEffects.tinted(level,i%4==0?ParticleRegistry.WISP_PARTICLE.get():ParticleRegistry.EMBER_PARTICLE.get(),q,new Vec3(0,.08,0),18,i%4==0?0xffec9a:0xff7116,1.1f);
            }
        }
    }
    @SubscribeEvent public static void render(RenderLevelStageEvent e){
        if(e.getStage()!=RenderLevelStageEvent.Stage.AFTER_PARTICLES||BLOOMS.isEmpty())return;
        var mc=Minecraft.getInstance();var buffers=mc.renderBuffers().bufferSource();var type=RenderHelper.CustomerRenderType.magicNoCull(PlasmaAssets.FIRE);
        Vec3 camera=e.getCamera().getPosition();float partial=e.getPartialTick().getGameTimeDeltaPartialTick(false);
        for(var b:BLOOMS){
            if(b.level()!=mc.level)continue;
            double age=mc.level.getGameTime()-b.started()+partial;if(age<0||age>90)continue;
            double radius=b.radius()*(.08+.92*Math.min(1,age/36.0));double rise=Math.max(0,age-30)*.13;
            int alpha=(int)(230*Math.clamp((90-age)/25,0,1));
            var pose=new PoseStack();pose.translate(b.center().x-camera.x,b.center().y-camera.y+rise,b.center().z-camera.z);
            var v=buffers.getBuffer(type);
            for(int lat=0;lat<20;lat++)for(int lon=0;lon<48;lon++){
                double a=-Math.PI/2+lat*Math.PI/20,c=lon*Math.PI/24;
                vertex(v,pose,a,c,radius,age,alpha);vertex(v,pose,a+Math.PI/20,c,radius,age,alpha);
                vertex(v,pose,a+Math.PI/20,c+Math.PI/24,radius,age,alpha);vertex(v,pose,a,c+Math.PI/24,radius,age,alpha);
            }
        }
        buffers.endBatch(type);
    }
    private static void vertex(VertexConsumer v,PoseStack pose,double lat,double lon,double radius,double age,int alpha){
        double ripple=1+.025*Math.sin(lon*9+lat*7+age*.35),r=Math.cos(lat)*radius*ripple;
        int green=(int)(155+85*Math.max(0,Math.sin(lon*4+lat*3-age*.1))),blue=age<10?170:35;
        v.addVertex(pose.last().pose(),(float)(Math.cos(lon)*r),(float)(Math.sin(lat)*radius*ripple),(float)(Math.sin(lon)*r))
            .setColor(255,green,blue,alpha).setUv((float)(lon/Math.PI/2),(float)(lat/Math.PI+.5-age*.006))
            .setOverlay(net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY).setLight(0xF000F0).setNormal(0,1,0);
    }
    private static void point(ClientLevel l,ParticleOptions o,Vec3 p,Vec3 v,int life){NativeSpellEffects.point(l,o,p,v,life,false);}
}
