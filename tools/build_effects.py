"""Author the wand effects and export them with Effekseer 1.80.7 (Pillow required)."""
import argparse
from concurrent.futures import ThreadPoolExecutor, as_completed
import math
import json
from pathlib import Path
import random
import shutil
import subprocess
import xml.etree.ElementTree as ET
from PIL import Image, ImageDraw, ImageFilter

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / 'src/main/resources/assets/resonant_arcana/effeks'
PALETTES = {
    'magic': (0xB349F5, 0xFFD57D), 'blood': (0xD91C42, 0xFF6280),
    'poison': (0x68DB39, 0xCEE95D), 'fire': (0xFF6515, 0xFFD35C),
    'thunder': (0x52BFFF, 0xD8F6FF), 'dark': (0x7033B8, 0xBD74EE),
    'gravity': (0xCF58E8, 0xF6A5FF), 'soul': (0x24D7CB, 0xAA70EF),
    'holy': (0xFFE5A1, 0xFFFBEA), 'ice': (0x81D9FF, 0xE4FAFF),
}
SHAPES = 'field ward domain trap doom_well horizon_well'.split()


SPECIAL_THEMES = {
    'slash': ('blood','fire','dark','thunder','gravity','holy'),
    'orbit': ('gravity',), 'totem': ('dark','soul','poison','holy'),
    'rain': ('poison','ice'), 'trap': ('dark',), 'serpent': ('ice',),
    'blessing': ('blood','dark','soul','poison','holy','ice'), 'domain': ('ice',),
}
def packaged(shape, theme):
    dedicated={'cannon_':'soul','pyre_':'fire','tempest_':'thunder','horizon_':'gravity','scream_':'soul','doom_':'dark','sanctuary_':'holy','glacier_':'ice','fang_':'magic','fusion_':'gravity'}
    for prefix, school in dedicated.items():
        if shape.startswith(prefix): return theme == school
    return theme in SPECIAL_THEMES[shape] if shape in SPECIAL_THEMES else True


def element(parent, name, text=None):
    e = ET.SubElement(parent, name)
    if text is not None:
        e.text = str(text)
    return e


def fixed(parent, name, value):
    e = element(parent, name)
    for key in ('Center', 'Max', 'Min'):
        element(e, key, value)
    return e


def vector(parent, name, values, randomized=False):
    e = element(parent, name)
    for axis, value in zip('XYZ', values):
        (fixed if randomized else element)(e, axis, round(value, 6))
    return e


def color(parent, name, value, alpha=255):
    e = element(parent, name)
    for key, channel in zip('RGBA', [(value >> 16) & 255, (value >> 8) & 255, value & 255, alpha]):
        element(e, key, channel)


def textures():
    folder = ASSETS / 'textures'
    folder.mkdir(parents=True, exist_ok=True)
    for kind in ('glow', 'star', 'smoke', 'flame'):
        im = Image.new('RGBA', (128, 128))
        pixels = []
        for y in range(128):
            for x in range(128):
                u, v = (x-63.5)/63.5, (y-63.5)/63.5
                r = math.hypot(u, v)
                if kind == 'glow':
                    a = math.exp(-r*r*5) * max(0, 1-r)**0.4
                elif kind == 'star':
                    a = max(math.exp(-r*r*18), math.exp(-abs(u)*36-abs(v)*2.8), math.exp(-abs(v)*36-abs(u)*2.8)) * max(0, 1-r)
                elif kind == 'flame':
                    width = max(0.08, (v+1)*0.38)
                    a = math.exp(-((u+math.sin(v*7)*0.08)/width)**2*2) * max(0, 1-abs(v))**0.7
                else:
                    a = math.exp(-r*r*4) * max(0, 1-r) * (0.65+0.2*math.sin(u*17+math.sin(v*9))*math.cos(v*13))
                pixels.append((255, 255, 255, int(min(1, a)*255)))
        im.putdata(pixels)
        im.save(folder / f'{kind}.png')
    for kind in ('sigil', 'arc', 'bolt', 'shard', 'halo'):
        im = Image.new('RGBA', (256, 256)); d = ImageDraw.Draw(im)
        white = (255, 255, 255, 255)
        if kind == 'halo':
            d.ellipse((16,16,240,240),outline=white,width=5)
        elif kind == 'sigil':
            for radius in (72, 94, 112):
                d.ellipse((128-radius, 128-radius, 128+radius, 128+radius), outline=white, width=2)
            for i in range(12):
                a = i*math.tau/12
                points = [(128+math.cos(a+offset)*r, 128+math.sin(a+offset)*r) for offset, r in ((-.035, 98), (0, 108), (.035, 98))]
                d.line(points, fill=white, width=3)
            for i in range(6):
                a = i*math.tau/6
                d.line([(128+math.cos(a)*70, 128+math.sin(a)*70), (128+math.cos(a+math.tau/3)*70, 128+math.sin(a+math.tau/3)*70)], fill=white, width=2)
        elif kind == 'arc':
            for r, width in ((109, 8), (94, 3), (81, 2)):
                d.arc((128-r, 128-r, 128+r, 128+r), 202, 338, fill=white, width=width)
        elif kind == 'bolt':
            rng = random.Random(27)
            points = [(128, 2)] + [(128+rng.randrange(-38, 39), y) for y in range(20, 245, 20)] + [(128, 254)]
            d.line(points, fill=white, width=5)
            for i in (3, 6, 9):
                x, y = points[i]; d.line([(x, y), (x+35, y-18), (x+48, y-40)], fill=white, width=2)
        else:
            d.polygon([(128, 8), (164, 130), (128, 248), (92, 130)], fill=white)
            d.line([(128, 8), (128, 248)], fill=(180, 180, 180, 255), width=4)
        # A crisp centre with a faint bloom baked into the texture.
        glow = im.filter(ImageFilter.GaussianBlur(3))
        Image.alpha_composite(glow, im).resize((128, 128), Image.Resampling.LANCZOS).save(folder / f'{kind}.png')


def node(parent, name, c, life=14, pos=(0, 0, 0), velocity=(0, 0, 0),
         size=(1, 1, 1), growth=(0, 0, 0), rotation=(0, 0, 0), spin=(0, 0, 0),
         texture='glow', billboard=0, alpha=255, blend=2):
    n = element(parent, 'Node'); element(n, 'Name', name)
    common = element(n, 'CommonValues'); fixed(common, 'Life', life)
    location = element(n, 'LocationValues'); element(location, 'Type', 1)
    pva = element(location, 'PVA'); vector(pva, 'Location', pos, True); vector(pva, 'Velocity', velocity, True)
    scaling = element(n, 'ScalingValues'); element(scaling, 'Type', 1)
    pva = element(scaling, 'PVA'); vector(pva, 'Scale', size, True); vector(pva, 'Velocity', growth, True)
    rot = element(n, 'RotationValues'); element(rot, 'Type', 1)
    pva = element(rot, 'PVA'); vector(pva, 'Rotation', rotation, True); vector(pva, 'Velocity', spin, True)
    common = element(n, 'RendererCommonValues')
    element(common, 'ColorTexture', f'textures/{texture}.png'); element(common, 'AlphaBlend', blend)
    element(common, 'FadeInType', 1); fade = element(common, 'FadeIn'); element(fade, 'Frame', 1)
    element(common, 'FadeOutType', 1); fade = element(common, 'FadeOut'); element(fade, 'Frame', max(3, life-3))
    draw = element(n, 'DrawingValues'); element(draw, 'Type', 2)
    color(element(draw, 'ColorAll'), 'Fixed', c, alpha)
    sprite = element(draw, 'Sprite'); element(sprite, 'Billboard', billboard)
    element(n, 'Children')
    return n


def ring(parent, c, radius=1, y=0, life=14, spin=4, alpha=230, vertical=False):
    return node(parent, 'Runic circle', c, life, pos=(0, y, 0),
                size=(radius*2.25, radius*2.25, 1), rotation=(0 if vertical else 90, 0, 0),
                spin=(0, 0, spin), texture='sigil', billboard=2, alpha=alpha)


def author(shape, theme):
    c, accent = PALETTES[theme]
    project = ET.Element('EffekseerProject'); root = element(project, 'Root'); element(root, 'Name', 'Root')
    children = element(root, 'Children')
    seed = random.Random(37 + list(PALETTES).index(theme)*71 + SHAPES.index(shape)*317)
    def sprite(name, col=c, **kwargs): return node(children, name, col, **kwargs)
    def motes(count=14, radius=1, life=16, outward=.025, y=0, shards=False):
        for i in range(count):
            a = i*math.tau/count; h = seed.uniform(-.4, .6)
            sprite('Flying ember' if theme=='fire' else 'Magic mote', accent if i%3==0 else c,
                   life=life, pos=(math.cos(a)*radius, y+h*radius, math.sin(a)*radius),
                   velocity=(math.cos(a)*outward, .012+h*.02, math.sin(a)*outward),
                   size=(.11 if shards else .18, .33 if shards else .18, .18),
                   rotation=(0, 0, seed.randrange(360)), spin=(0, 0, 5),
                   texture='shard' if shards else ('flame' if theme=='fire' else 'star'))
    def school_profile():
        # Schools use different silhouettes and motion; shape still describes delivery.
        charge=shape=='charge'; impact=shape in ('impact','nova','pulse'); field=shape=='field'
        travelling=shape in ('orb','comet'); wave=shape=='wave'
        count=28 if impact else 18
        for i in range(count):
            t=i/max(1,count-1);a=t*math.tau
            r=.25+t*.45
            x,y,z=math.cos(a)*r,math.sin(a)*r,0
            if travelling:z=-t*(2.7 if shape=='comet' else 1.5)
            if wave:x=(t-.5)*2.3;y=.2*math.cos(t*math.pi*2);z=-abs(t-.5)*.4
            if field:x=math.cos(a)*r;y=.1;z=math.sin(a)*r
            vel=(-x*.045,-y*.045,-.015) if charge else (x*.055,.025,z*.025)
            if theme=='fire':
                sprite('Rising flame tongue',accent if i%3==0 else c,life=22 if impact else 14,
                       pos=(x,y-.3,z),velocity=(x*.03,.07+t*.045,-.05 if travelling else .015),
                       size=(.23,.65 if impact else .4,.23),growth=(.006,.015,.006),texture='flame')
                if i%4==0:sprite('Ash veil',0x5B3427,life=26,pos=(x,.2,z),velocity=(0,.04,0),
                                 size=(.65,.65,.65),texture='smoke',blend=1,alpha=115)
            elif theme=='blood':
                sprite('Falling crimson ribbon',accent if i%5==0 else c,life=24 if impact else 16,
                       pos=(x*seed.uniform(.65,1.3),y*seed.uniform(.3,1.2),z),velocity=(vel[0],-.045-t*.025,vel[2]),size=(.08+seed.random()*.08,.22+seed.random()*.3,.13),
                       rotation=(0,0,x*35),texture='flame',blend=1,alpha=240)
                if impact or field:
                    sprite('Blood splash',0x610B24,life=28,pos=(x*.8,-.3,z if field else math.sin(a)*r),
                           size=(.5,.3,.5),growth=(.008,0,.008),texture='smoke',blend=1,alpha=180)
            elif theme=='poison':
                sprite('Low toxic billow',c,life=28,pos=(x,y*.25-.2,z),velocity=(vel[0]*.25,.008,vel[2]*.25),
                       size=(.65,.48,.65),growth=(.008,.008,.008),texture='smoke',blend=1,alpha=105)
                if i%2==0:sprite('Rising acid bubble',accent,life=22,pos=(x,y*.2,z),velocity=(0,.035,0),
                                 size=(.10,.10,.10),texture='glow',alpha=200)
            elif theme=='thunder':
                sprite('Forked electric splinter',0xDFFFFF if i%2 else c,life=8+(i%3)*2,
                       pos=(x*1.25,y*1.25,z),velocity=vel,size=(.42,1.05,.42),
                       rotation=(0,0,(i%5)*47-90),texture='bolt',billboard=2)
                if i%5==0:sprite('Lightning core flash',accent,life=7,pos=(x*.3,y*.3,z),size=(.4,.4,.4),texture='star')
            elif theme=='dark':
                sprite('Trailing shadow veil',0x251136,life=26,pos=(x,y,z),velocity=(vel[0]*.3,.015,-.035),
                       size=(.7,.45,.7),growth=(.01,.008,.01),texture='smoke',blend=1,alpha=190)
                if i%4==0:sprite('Phantom claw',accent,life=16,pos=(x,y,z),velocity=vel,
                                 size=(.45,.75,1),rotation=(0,0,i*40),texture='arc',billboard=2,alpha=190)
            elif theme=='gravity':
                # Inward debris on tilted planes, rather than outgoing fireworks.
                pos=(x,y,z if travelling else math.sin(a*2)*.35)
                sprite('Infalling rift shard',accent if i%3==0 else c,life=22,pos=pos,
                       velocity=tuple(-v*.055 for v in pos),size=(.08,.28,.08),texture='shard',spin=(0,0,12))
                if i%9==0:sprite('Tilted accretion crescent',c,life=20,size=(1.6,1.6,1),
                                 rotation=(55,i*31,20),spin=(0,0,7),texture='arc',billboard=2)
            elif theme=='soul':
                sprite('Resonant crystal fragment',accent if i%4==0 else c,life=20,pos=(x,y,z),velocity=vel,
                       size=(.08,.3,.08),rotation=(0,0,i*31),spin=(0,0,6),texture='shard')
                if i%8==0:sprite('Echo crescent',c,life=18,pos=(0,0,-i*.025),size=(1.5,1.5,1),
                                 growth=(.018,.018,0),rotation=(0,0,i*20),texture='arc',billboard=2,alpha=160)
            elif theme=='holy':
                sprite('Ascending feather of light',accent if i%3==0 else c,life=24,
                       pos=(x,y*.6,z),velocity=(vel[0]*.5,.035+t*.035,vel[2]),
                       size=(.14,.48,.14),rotation=(0,0,35+math.sin(a)*40),spin=(0,0,2),texture='shard')
                if i%4==0:sprite('Dawn star',accent,life=18,pos=(x,y,z),size=(.32,.32,.32),texture='star')
            elif theme=='ice':
                sprite('Fractured frost crystal',accent if i%3==0 else c,life=24,
                       pos=(x,y,z),velocity=(vel[0],-.018,vel[2]),size=(.14,.48,.14),
                       rotation=(0,0,i*37),spin=(0,0,4),texture='shard')
                if i%6==0:sprite('Cold mist',c,life=28,pos=(x,-.2,z),size=(.7,.35,.7),
                                 velocity=(0,.008,-.012),texture='smoke',alpha=85)
            else:
                sprite('Orbiting arcane diamond',accent if i%3==0 else c,life=18,pos=(x,y,z),velocity=vel,
                       size=(.16,.22,.16),rotation=(0,0,45),spin=(0,0,4),texture='shard')
                if i%9==0:sprite('Swept arcane ribbon',c,life=15,pos=(0,0,z),size=(1.25,1.25,1),
                                 rotation=(0,0,i*40),spin=(0,0,8),texture='arc',billboard=2)
        if theme=='gravity':sprite('Light eating core',0x100518,life=22,size=(.7,.7,.7),texture='smoke',blend=1,alpha=245)
    if shape.startswith('fusion_'):
        charge=shape=='fusion_charge';impact=shape=='fusion_impact'
        for side,col in ((-1,0x538DFF),(1,0xFF4267)):
            pos=(side*.65,0,0) if charge else (0,0,0)
            sprite('Opposed energy nucleus',col,life=24,pos=pos,size=(.65,.65,.65),texture='glow',alpha=240)
            for i in range(20):
                a=i*math.tau/20;radius=.65 if charge else 1.1
                sprite('Braided fusion stream',col,life=24,pos=(math.cos(a)*radius,math.sin(a)*radius,-i*.06),
                       velocity=(-math.cos(a)*.025,-math.sin(a)*.025,.02),size=(.16,.28,.16),texture='shard',spin=(0,0,8*side))
        sprite('Violet singularity',0xECBEFF,life=22,size=(1.25,1.25,1.25),texture='star')
        for i in range(3):sprite('Spatial fracture crescent',c,life=26,size=(1.8+i*.2,1.8+i*.2,1),
                                 growth=(.035,.035,0) if impact else (0,0,0),rotation=(i*35,0,i*100),texture='arc',billboard=2)
    elif shape=='slash':
        for i in range(4):
            sprite('Sweeping cutting edge',accent if i==0 else c,life=15+i*2,
                   size=(2.1-i*.18,2.1-i*.18,1),rotation=(0,0,15+i*7),texture='arc',billboard=2,alpha=240)
        for i in range(22):
            a=(i/21-.5)*2.5
            sprite('Cutting wake',accent if i%4==0 else c,life=18,pos=(math.sin(a),math.cos(a)*.7,0),
                   velocity=(math.sin(a)*.045,math.cos(a)*.025,-.02),size=(.12,.35,.12),texture='flame' if theme=='fire' else 'shard')
    elif shape=='orbit':
        sprite('Orbiting spectral blade',accent,life=16,size=(.18,1.6,.18),rotation=(0,0,35),texture='shard')
        sprite('Blade motion crescent',c,life=16,size=(1.4,1.4,1),rotation=(0,0,40),texture='arc',billboard=2)
        motes(12,radius=.4,life=18,outward=.02,shards=True)
    elif shape=='totem':
        for i in range(5):
            sprite('Suspended lantern crystal',accent if i%2 else c,life=26,pos=(0,.15+i*.22,0),
                   size=(.18,.4,.18),rotation=(0,0,i*45),spin=(0,0,4),texture='shard')
        for i in range(24):
            a=i*math.tau/24
            sprite('Spiralling aura wisp',c,life=26,pos=(math.cos(a)*.7,.05,math.sin(a)*.7),
                   velocity=(-math.sin(a)*.015,.02,math.cos(a)*.015),size=(.18,.18,.18),texture='star')
    elif shape=='rain':
        for i in range(32):
            a=i*2.39996;r=math.sqrt((i+.5)/32)*.85
            sprite('Falling hail shard' if theme=='ice' else 'Falling acid drop',accent if i%4==0 else c,
                   life=24,pos=(math.cos(a)*r,3+(i%4)*.3,math.sin(a)*r),velocity=(0,-.15,0),
                   size=(.11,.4,.11),texture='shard' if theme=='ice' else 'flame')
        motes(18,radius=.8,life=24,outward=.015,shards=theme=='ice')
    elif shape=='trap':
        for i in range(12):
            a=i*math.tau/12
            sprite('Closed night petal',accent if i%3==0 else c,life=26,pos=(math.cos(a)*.6,0,math.sin(a)*.6),
                   size=(.2,.65,.2),rotation=(0,0,30*math.sin(a)),texture='flame',alpha=200)
        sprite('Dormant shadow seed',0x1C082F,life=28,size=(.6,.6,.6),texture='smoke',blend=1)
    elif shape=='serpent':
        for i in range(26):
            t=i/25
            sprite('Weaving frost spine',accent if i%4==0 else c,life=22,pos=(math.sin(t*7)*.25,0,-t*2),
                   size=(.16,.5*(1-t*.5),.16),rotation=(0,0,30*math.cos(t*7)),texture='shard')
        sprite('Ice serpent crown',accent,life=18,size=(.55,.55,.55),texture='star')
    elif shape=='blessing':
        for i in range(30):
            a=i*2.39996;y=(i%10)/10
            sprite('Rising protective ribbon',accent if i%4==0 else c,life=28,pos=(math.cos(a)*.6,y-.6,math.sin(a)*.6),
                   velocity=(-math.sin(a)*.012,.025,math.cos(a)*.012),size=(.12,.35,.12),texture='shard')
        for i in range(5):sprite('Blessing star',accent,life=26,pos=(math.sin(i*3)*.7,i*.3-.5,math.cos(i*3)*.7),size=(.3,.3,.3),texture='star')
    elif shape=='domain':
        for i in range(32):
            a=i*math.tau/32
            sprite('Rising permafrost boundary',accent if i%4==0 else c,life=28,pos=(math.cos(a)*.85,-.2,math.sin(a)*.85),
                   size=(.12,.65+(i%3)*.25,.12),texture='shard')
        for i in range(12):sprite('Polar drifting mist',c,life=30,pos=(math.sin(i*3)*.6,.1,math.cos(i*3)*.6),
                                 size=(.7,.35,.7),velocity=(.005,.006,0),texture='smoke',alpha=95)
    elif shape.startswith('scream_'):
        for i in range(4):
            sprite('Sonic pressure crescent',0xCFFFF9 if i%2 else c,life=20,
                   pos=(0,0,-i*.08),size=(1.9-i*.15,1.9-i*.15,1),growth=(.025,.025,0),
                   rotation=(0,0,i*85),texture='arc',billboard=2,alpha=210)
        for i in range(26):
            a=i*math.tau/26
            sprite('Shattered scream fragment',accent if i%4==0 else c,life=22,
                   pos=(math.cos(a)*.8,math.sin(a)*.8,0),velocity=(math.cos(a)*.025,math.sin(a)*.025,.01),
                   size=(.045,.17,.045),texture='shard',spin=(0,0,8))
    elif shape.startswith('doom_'):
        charge=shape=='doom_charge';impact=shape=='doom_impact'
        sprite('Opaque void sphere',0x030105,life=32,size=(1.9,1.9,1.9),growth=(-.04,-.04,-.04) if impact else (0,0,0),texture='glow',blend=1,alpha=255)
        sprite('Black hole photon rim',0xF4E7FF,life=30,size=(1.94,1.94,1),texture='halo',alpha=240)
        if not charge:
            for i in range(2):
                sprite('Accretion disc',0xCB9FEE if i else 0xFFDFBD,life=30,size=(2.8,1.0,1),
                       rotation=(60,i*30,12),spin=(0,0,5 if i else -4),texture='halo',billboard=2,alpha=200)
        for i in range(48 if not charge else 24):
            a=i*2.39996;y=1-2*(i+.5)/(48 if not charge else 24);r=math.sqrt(max(0,1-y*y))
            pos=(math.cos(a)*r*1.4,y*1.4,math.sin(a)*r*1.4)
            sprite('Devoured light stream',0xD7C0FF if i%3 else 0xFFD8B0,life=28,pos=pos,
                   velocity=tuple(v*(.055 if impact else -.028) for v in pos),size=(.035,.16,.035),texture='shard')
        if impact:sprite('Collapse flash',0xEFDCFF,life=9,size=(2.4,2.4,2.4),texture='star')
    elif shape.startswith('sanctuary_'):
        field=shape=='sanctuary_field'
        for i in range(28):
            a=i*2.39996;r=.2+(i%5)*.15
            sprite('Ascending blessing',accent if i%3 else c,life=30,
                   pos=(math.cos(a)*r,0 if field else -.4,math.sin(a)*r),velocity=(0,.035+(i%4)*.008,0),
                   size=(.09,.5 if field else .35,.09),texture='shard',spin=(0,0,3))
        for side in (-1,1):
            for i in range(6):
                sprite('Golden feather sweep',accent,life=26,pos=(side*(.2+i*.13),.4-i*.07,0),
                       velocity=(side*.012,.018,0),size=(.14,.6,.14),rotation=(0,0,side*(20+i*8)),texture='shard')
        sprite('Warm healing bloom',c,life=28,size=(1.4,.6,1.4),texture='glow',alpha=150)
        for i in range(8):
            a=i*math.tau/8
            sprite('Radiant healing star',0xFFF8D5,life=24,pos=(math.cos(a)*.6,.15,math.sin(a)*.6),
                   velocity=(0,.025,0),size=(.22,.22,.22),texture='star')
    elif shape.startswith('glacier_'):
        front=shape=='glacier_front';impact=shape=='glacier_impact'
        for i in range(9 if front else 15):
            x=(i-4)*.24 if front else math.cos(i*2.4)*.6;z=math.sin(i*2.4)*.2
            sprite('Erupting ice spire',accent if i%3==0 else c,life=28,pos=(x,-.1,z),
                   velocity=(0,.018,0),size=(.18,.7+(i%3)*.25,.18),growth=(0,.028,0),
                   rotation=(0,0,x*-8),texture='shard',billboard=1,blend=1,alpha=245)
        for i in range(16):
            a=i*2.4
            sprite('Chipped ice fragment',accent,life=25,pos=(math.cos(a)*.4,.3,math.sin(a)*.4),
                   velocity=(math.cos(a)*.04,.012,math.sin(a)*.04),size=(.035,.12,.035),texture='shard')
        sprite('Cold ground mist',c,life=30,pos=(0,-.15,0),size=(2,.3,1.2),texture='smoke',alpha=90,blend=1)
    elif shape.startswith('fang_'):
        row=shape=='fang_row'
        for pair in range(3 if row else 2):
            for side in (-1,1):
                sprite('Spectral evoker fang',0xE5E7CD,life=24,pos=(side*(.12+pair*.14),.2,pair*.22),
                       velocity=(0,.025,0),size=(.22,1.0+pair*.18,.22),growth=(0,.025,0),
                       rotation=(0,0,side*-16),texture='shard',billboard=2,blend=1,alpha=250)
        for i in range(18):
            a=i*2.4
            sprite('Evocation soul splinter',0x6FCEB0,life=22,pos=(math.cos(a)*.6,.05,math.sin(a)*.6),
                   velocity=(0,.025,0),size=(.04,.2,.04),texture='shard')
        sprite('Fang emergence shadow',0x17231F,life=26,pos=(0,-.1,0),size=(1.6,.4,1.2),texture='smoke',blend=1,alpha=190)
    elif shape.startswith('pyre_'):
        charge=shape=='pyre_charge';fall=shape=='pyre_fall'
        sprite('Solar heart',0xFFF2AA,life=16,size=(.8,.8,.8) if fall else (.35,.35,.35),texture='star')
        for i in range(38 if not charge else 24):
            a=i*2.39996;r=.15+(i%7)*.11
            pos=(math.cos(a)*r,math.sin(a)*r,-(i%9)*.22) if fall else (math.cos(a)*r,-.7+(i%6)*.12,math.sin(a)*r)
            sprite('Meteor fire tongue',accent if i%3 else c,life=24 if not charge else 18,pos=pos,
                   velocity=(math.cos(a)*.03,.065 if not fall else 0,-.12 if fall else math.sin(a)*.03),
                   size=(.28,.8 if not fall else .45,.28),growth=(.006,.012,.006),texture='flame')
            if i%5==0:sprite('Rolling ash plume',0x543A32,life=34,pos=pos,velocity=(0,.055,0),
                             size=(.75,.75,.75),growth=(.025,.025,.025),texture='smoke',blend=1,alpha=160)
        if shape=='pyre_impact':
            for i in range(10):
                a=i*math.tau/10
                sprite('Ground fire fan',c,life=28,pos=(math.cos(a)*.2,-.2,math.sin(a)*.2),
                       velocity=(math.cos(a)*.08,.025,math.sin(a)*.08),size=(.25,.7,.25),texture='flame')
    elif shape.startswith('tempest_'):
        beam=shape=='tempest_beam';charge=shape=='tempest_charge'
        for i in range(28):
            a=i*2.39996;t=i/27
            pos=(math.sin(a)*(.4 if charge else .7),math.cos(a)*(.4 if charge else .7),t if beam else -.3)
            sprite('Splitting storm branch',accent if i%3 else c,life=10 if beam else 15,pos=pos,
                   velocity=(0,0,-.03 if charge else 0 if beam else .03),size=(.45,1.15,.45),
                   rotation=(90 if beam else 0,i%3*60,i*53),texture='bolt',billboard=2)
        if beam:
            for i in range(18):
                sprite('Continuous storm plasma',0xB7F7FF,life=16,pos=(0,0,i/17),size=(.3,.3,.3),texture='glow',alpha=230)
            for roll in (0,90):sprite('White storm spine',0xE8FFFF,life=14,pos=(0,0,.5),size=(.32,1,1),
                                      rotation=(90,roll,0),texture='glow',billboard=2)
        else:sprite('Condensed thunder spark',accent,life=12,size=(.65,.65,.65),texture='star')
    elif shape.startswith('horizon_'):
        impact=shape=='horizon_impact';charge=shape=='horizon_charge'
        sprite('Event horizon core',0x090311,life=30,size=(1.15,1.15,1.15),growth=(-.025,-.025,-.025) if impact else (0,0,0),texture='smoke',blend=1,alpha=250)
        for i in range(1 if impact else 2 if charge else 3):
            sprite('Tilted accretion plane',accent if i==1 else c,life=26,size=(1.9,1.9,1),
                   rotation=(65,i*60,15),spin=(0,0,10-i*7),texture='arc',billboard=2,alpha=200)
        for i in range(36):
            a=i*2.39996;y=1-2*(i+.5)/36;r=math.sqrt(1-y*y)
            pos=(math.cos(a)*r,y,math.sin(a)*r)
            sprite('Crushed spatial fragment',accent if i%4==0 else c,life=28,pos=pos,
                   velocity=tuple(v*(.055 if impact else -.035) for v in pos),
                   size=(.07,.3,.07),rotation=(0,0,i*37),spin=(0,0,10),texture='shard')
    elif shape=='drain':
        # Local +Z points from the drained target to the caster. Gravity Pull uses
        # the reversed link, so its motes flow toward local zero instead.
        for i in range(24):
            t=i/23;a=i*2.39996
            z=.25+t*.7 if theme=='gravity' else t*.72
            sprite('Siphoned fragment',accent if i%4==0 else c,life=18,
                   pos=(math.cos(a)*.18,math.sin(a)*.18,z),velocity=(0,0,-.012 if theme=='gravity' else .012),
                   size=(.1,.25,.1),texture='flame' if theme=='blood' else 'shard',spin=(0,0,5))
    elif shape in ('cast','charge','orb','comet','wave','impact','nova','pulse','field'):
        school_profile()
    elif shape=='cannon_charge':
        # Dark centre, counter-rotating seals and three dimensional inward streams.
        sprite('Void heart',0x090413,life=24,size=(1.0,1.0,1.0),texture='smoke',blend=1,alpha=240)
        sprite('Condensed soul core',accent,life=21,size=(.38,.38,.38),growth=(.015,.015,.015),texture='star')
        for radius,spin in ((.65,9),(1.0,-6)):
            sprite('Collapsing void crescent',c,life=24,size=(radius*2,radius*2,1),growth=(-.015,-.015,0),
                   rotation=(0,0,radius*130),spin=(0,0,spin),texture='arc',billboard=2)
        for i in range(42):
            a=i*2.39996; y=1-2*(i+.5)/42; r=math.sqrt(1-y*y); radius=1.5+(i%4)*.3
            pos=(math.cos(a)*r*radius,y*radius,math.sin(a)*r*radius)
            sprite('Absorbed void fragment',0x713ABE if i%3 else c,life=24,pos=pos,
                   velocity=tuple(-v/22 for v in pos),size=(.10,.24,.1),texture='shard',spin=(0,0,12))
    elif shape=='cannon_head':
        sprite('Echo pressure bloom',c,life=12,size=(2.2,2.2,2.2),alpha=145)
        sprite('White hot soul core',0xCBFFF4,life=10,size=(.85,.85,.85),texture='star')
        for i in range(4):
            n=sprite('Travelling echo crescent',accent if i%2 else c,life=14,size=(2-i*.22,2-i*.22,1),
                     rotation=(0,0,i*90),spin=(0,0,8 if i%2 else -8),texture='arc',billboard=2)
            # Ring quads face forward along local +Z.
            loc=n.find('LocationValues/PVA/Location/Z')
            for key in ('Center','Max','Min'):loc.find(key).text=str(-i*.5)
        for i in range(32):
            a=i*.8;r=.6+(i%3)*.1
            sprite('Helical echo wake',accent if i%4==0 else c,life=15,
                   pos=(math.cos(a)*r,math.sin(a)*r,-i*.075),velocity=(0,0,-.025),
                   size=(.16,.32,.16),texture='shard',spin=(0,0,8))
    elif shape in ('cannon_burst','cannon_impact'):
        big=shape=='cannon_impact'
        sprite('Echo detonation',c,life=24,size=(1.5,1.5,1.5),growth=(.06,.06,.06),alpha=160)
        sprite('Detonation flash',0xDFFFF9,life=8,size=(2,2,2),texture='star')
        for i in range(3 if big else 1):
            ring(children,accent if i==1 else c,radius=.65+i*.15,life=32 if big else 20,spin=3-i*3)
        if big:
            for roll in (0,60,120):
                sprite('Expanding echo halo',c,life=30,size=(1.5,1.5,1),growth=(.045,.045,0),
                       rotation=(0,roll,roll),texture='sigil',billboard=2,alpha=180)
            sprite('Receding void cloud',0x251040,life=36,size=(1.3,1.3,1.3),growth=(.03,.03,.03),texture='smoke',blend=1,alpha=140)
        count=66 if big else 26
        for i in range(count):
            a=i*2.39996;y=1-2*(i+.5)/count;r=math.sqrt(1-y*y);v=.045 if big else .065
            sprite('Soul firework shard',accent if i%3==0 else c,life=36 if big else 24,
                   velocity=(math.cos(a)*r*v,y*v,math.sin(a)*r*v),size=(.045,.13,.045) if big else (.10,.22,.1),
                   texture='shard' if i%3 else 'star',spin=(0,0,9))
    elif shape in ('vortex', 'ward', 'cage', 'curse'):
        if shape=='vortex':
            for i in range(5):
                r = .25+i*.17 if theme=='fire' else 1-i*.15
                ring(children, accent if i%2 else c, radius=r, y=i*.23, life=15, spin=(9 if theme=='fire' else -9)+i, alpha=200)
            for i in range(28):
                a=i*.76; h=i/28; r=.25+h*.6 if theme=='fire' else 1-h*.75
                sprite('Vortex stream', accent if i%4==0 else c, life=15, pos=(math.cos(a)*r,h,math.sin(a)*r),
                       velocity=(-math.sin(a)*.025,.008,math.cos(a)*.025), size=(.12,.22,.12),texture='flame' if theme=='fire' else 'star')
            if theme=='gravity':
                sprite('Gravity core',0x10051D,life=15,pos=(0,.1,0),size=(1.2,1.2,1.2),alpha=230,blend=1)
        elif shape=='ward' and theme in ('blood','soul'):
            for i in range(24):
                a=i*2.39996;y=1-2*(i+.5)/24;r=math.sqrt(1-y*y)
                sprite('Blood membrane' if theme=='blood' else 'Orbiting echo crystal',accent if i%5==0 else c,
                       life=18,pos=(math.cos(a)*r,y,math.sin(a)*r),
                       size=(.4,.65,.4) if theme=='blood' else (.09,.4,.09),
                       rotation=(0,0,i*37),spin=(0,0,3),texture='flame' if theme=='blood' else 'shard',
                       blend=1 if theme=='blood' else 2,alpha=180 if theme=='blood' else 240)
        elif shape=='ward':
            for i, rot in enumerate(((90,0,0),(0,0,0),(0,90,0))):
                sprite('Crossing shield sigil',accent if i==1 else c,life=12,size=(2.25,2.25,1),rotation=rot,spin=(0,0,4 if i%2 else -4),texture='sigil',billboard=2,alpha=170)
            motes(18,radius=.92,life=12,outward=0,shards=theme=='soul')
        elif shape in ('cage','curse') and theme=='soul':
            for i in range(16):
                a=i*math.tau/8;y=-.65 if i<8 else .65
                sprite('Sculk crystal restraint',accent if i%3==0 else c,life=18,
                       pos=(math.cos(a)*.7,y,math.sin(a)*.7),size=(.14,.85,.14),texture='shard',spin=(0,0,2))
        elif shape in ('cage','curse'):
            for y in (-.8,.8):ring(children,c,radius=.9,y=y,life=15,spin=5 if y>0 else -5)
            for i in range(8):
                a=i*math.tau/8
                sprite('Restraint pillar',accent if i%3==0 else c,life=15,pos=(math.cos(a)*.8,0,math.sin(a)*.8),size=(.11,2.0,1),texture='bolt' if shape=='curse' else 'glow',billboard=1,alpha=220)
            motes(12,radius=.8,life=15,outward=0,shards=theme=='soul')
    elif shape in ('beam','lightning','flame'):
        if shape in ('beam','lightning'):
            # Crossed quads occupy local +Z from 0 to 1; runtime stretches only Z.
            for roll in (0,90):
                sprite('Energy filament',accent,life=13,size=(.65,1,1),pos=(0,0,.5),rotation=(90,roll,0),texture='bolt' if shape=='lightning' else 'glow',billboard=2)
        for i in range(25):
            t=i/24; a=i*2.4; spread=.18 if shape=='beam' else .35
            px=math.cos(a)*spread; py=math.sin(a)*spread
            sprite('Flowing energy',accent if i%4==0 else c,life=17 if shape=='flame' else 13,
                   pos=(px,py,t),velocity=(0,0,.02),
                   size=(.75,.9,.7) if shape=='flame' else (.32,.32,.32),
                   texture='flame' if shape=='flame' else 'star' if shape=='lightning' else 'glow',alpha=220)
    else:
        raise ValueError(shape)
    element(project,'ToolVersion','1.80');element(project,'Version',3)
    element(project,'StartFrame',0);element(project,'EndFrame',40);element(project,'IsLoop','False')
    return ET.ElementTree(project)


def main():
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--jobs',type=int,default=1,help='Independent headless export workers (1 to 8)')
    parser.add_argument('--editor',type=Path,required=True,help='Effekseer 1.80.7 executable (Tool/bin/Effekseer on Linux)')
    parser.add_argument('--work',type=Path,default=ROOT/'build/effekseer')
    parser.add_argument('--theme',choices=list(PALETTES),nargs='+',default=list(PALETTES))
    parser.add_argument('--shape',choices=SHAPES,nargs='+',default=SHAPES)
    args=parser.parse_args();work=args.work.resolve();work.mkdir(parents=True,exist_ok=True)
    ASSETS.mkdir(parents=True,exist_ok=True);textures()
    shutil.copytree(ASSETS/'textures',work/'textures',dirs_exist_ok=True)
    editor=args.editor.resolve()
    def export(theme, shape):
        project=author(shape,theme);ET.indent(project,space='  ')
        source=work/f'{shape}_{theme}.efkproj';project.write(source,encoding='utf-8',xml_declaration=True)
        # Effekseer rebases texture paths relative to its output file. Export beside
        # the project, then copy the finished asset into Minecraft's resource tree.
        exported=work/f'{shape}_{theme}.efkefc'
        exported.unlink(missing_ok=True)
        subprocess.run([str(editor),'-cui','-in',str(source),'-o',str(exported)],cwd=editor.parent,check=True,stdout=subprocess.DEVNULL)
        if not exported.exists() or exported.read_bytes()[:4]!=b'EFKE':raise RuntimeError(f'Export failed: {exported}')
        shutil.copy2(exported,ASSETS/exported.name)
        return theme
    manifest={'schemaVersion':1,'effects':sorted(f'{shape}_{theme}.efkefc' for theme in PALETTES for shape in SHAPES if packaged(shape,theme))}
    for stale in ASSETS.glob('*.efkefc'):
        if stale.name not in manifest['effects']:stale.unlink()
    (ASSETS/'manifest.json').write_text(json.dumps(manifest,indent=2)+'\n')
    completed={theme:0 for theme in args.theme}
    with ThreadPoolExecutor(max_workers=max(1,min(8,args.jobs))) as pool:
        tasks=[pool.submit(export,theme,shape) for theme in args.theme for shape in args.shape if packaged(shape,theme)]
        for task in as_completed(tasks):
            theme=task.result();completed[theme]+=1
            expected=sum(packaged(shape,theme) for shape in args.shape)
            if completed[theme]==expected:print(f'{theme}: {expected} effects exported',flush=True)


if __name__=='__main__':main()
