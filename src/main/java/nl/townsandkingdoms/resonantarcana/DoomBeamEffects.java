package nl.townsandkingdoms.resonantarcana;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.*;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/** Native emerald plasma: a singularity ignition, white core and branching corona. */
final class DoomBeamEffects {
    private static DustParticleOptions dust(float r,float g,float b,float size){return new DustParticleOptions(new Vector3f(r,g,b),size);}
    private static final ParticleOptions GREEN=dust(.12f,1,.015f,1.5f),WHITE=dust(.8f,1,.65f,1.4f),DARK=dust(.008f,.012f,.006f,1.8f),GOLD=dust(1,.65f,.18f,1);
    static boolean green(double progress){return progress>=.5;}
    static void visual(ClientLevel l,SpellVisuals.Stage stage,Vec3 p,Vec3 end,double size){
        double time=l.getGameTime()*.12;
        Vec3 axis=end.subtract(p).normalize();if(axis.lengthSqr()<.01)axis=new Vec3(0,0,1);
        Vec3 side=axis.cross(new Vec3(0,1,0)).normalize();if(side.lengthSqr()<.01)side=new Vec3(1,0,0);
        Vec3 up=side.cross(axis).normalize();
        if(stage==SpellVisuals.Stage.RITUAL_TARGET){
            for(int ring=0;ring<2;ring++)for(int i=0;i<100;i++){
                double a=i*Math.PI/50,r=size-ring*.25;
                NativeSpellEffects.point(l,GREEN,p.add(Math.cos(a)*r,.03,Math.sin(a)*r),Vec3.ZERO,12,true);
            }
            return;
        }
        if(stage==SpellVisuals.Stage.CHARGE){
            double progress=Math.clamp((size-5)/2,0,1),radius=.45+progress*.8;
            boolean charged=green(progress);ParticleOptions rim=charged?GREEN:GOLD;
            SingularityEffects.refresh(l,p,radius*.8);
            // Opaque-looking native dark motes surrounded by a luminous photon rim.
            for(int i=0;i<120;i++){
                double y=1-2*(i+.5)/120,a=i*2.39996,r=Math.sqrt(1-y*y)*radius*.82;
                point(l,DARK,p.add(Math.cos(a)*r,y*radius*.82,Math.sin(a)*r),Vec3.ZERO);
            }
            for(int i=0;i<144;i++){
                double a=i*Math.PI/72+time;
                Vec3 off=side.scale(Math.cos(a)*radius).add(up.scale(Math.sin(a)*radius));
                point(l,rim,p.add(off),Vec3.ZERO);
            }
            for(int arm=0;arm<5;arm++)for(int i=0;i<36;i++){
                double t=i/36.0,a=arm*Math.PI*2/5+time+t*9,r=radius+(1-t)*4;
                Vec3 off=side.scale(Math.cos(a)*r).add(up.scale(Math.sin(a)*r)).add(axis.scale((1-t)*2));
                point(l,charged&&i%5==0?WHITE:rim,p.add(off),off.scale(-.08));
            }
            return;
        }
        if(stage==SpellVisuals.Stage.LINK){
            // Spacing is independent of range, with three luminous concentric layers.
            int n=(int)Math.clamp(p.distanceTo(end)*6,32,384);
            for(int i=0;i<=n;i++){
                double t=i/(double)n,a=t*48-time*2;
                Vec3 q=p.lerp(end,t);
                point(l,WHITE,q,Vec3.ZERO);
                for(int arm=0;arm<2;arm++){
                    double angle=a+arm*Math.PI,r=.65+.25*Math.sin(t*16+time);
                    Vec3 off=side.scale(Math.cos(angle)*r).add(up.scale(Math.sin(angle)*r));
                    point(l,GREEN,q.add(off),off.scale(.015));
                }
            }
            // Jagged branching lightning hugs the outer plasma envelope.
            for(int branch=0;branch<4;branch++){
                Vec3 previous=p;
                for(int i=1;i<=36;i++){
                    double t=i/36.0,a=branch*Math.PI/2+t*17+time;
                    double r=1.3+.65*Math.sin(i*2.7+time*3+branch);
                    Vec3 q=p.lerp(end,t).add(side.scale(Math.cos(a)*r)).add(up.scale(Math.sin(a)*r));
                    for(int j=0;j<2;j++)point(l,j==0?WHITE:GREEN,previous.lerp(q,j/2.0),Vec3.ZERO);
                    previous=q;
                }
            }
            // Expanding muzzle rings give the beam a dense, turbulent source.
            for(int ring=0;ring<3;ring++)for(int i=0;i<72;i++){
                double a=i*Math.PI/36+time,r=1.4+ring*.65;
                Vec3 off=side.scale(Math.cos(a)*r).add(up.scale(Math.sin(a)*r));
                point(l,GREEN,p.add(axis.scale(.6+ring*.5)).add(off),off.scale(.025));
            }
        }else if(stage==SpellVisuals.Stage.IMPACT||stage==SpellVisuals.Stage.HIT){
            for(int i=0;i<160;i++){
                double a=i*2.39996,y=1-2*(i+.5)/160,r=Math.sqrt(1-y*y);
                Vec3 off=new Vec3(Math.cos(a)*r,y,Math.sin(a)*r);
                point(l,i%4==0?WHITE:GREEN,p.add(off.scale(1.5)),off.scale(.25));
            }
            point(l,ParticleTypes.FLASH,p,Vec3.ZERO);
        }
    }
    private static void point(ClientLevel l,ParticleOptions o,Vec3 p,Vec3 v){NativeSpellEffects.point(l,o,p,v,12,false);}
}
