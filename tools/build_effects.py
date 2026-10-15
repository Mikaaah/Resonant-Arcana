"""Author the wand effects and export them with Effekseer 1.80.7 (Pillow required)."""
import argparse
import math
from pathlib import Path
import random
import shutil
import subprocess
import xml.etree.ElementTree as ET
from PIL import Image, ImageDraw, ImageFilter

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / 'src/main/resources/assets/kingdomwands/effeks'
PALETTES = {
    'magic': (0xB349F5, 0xFFD57D), 'blood': (0xD91C42, 0xFF6280),
    'poison': (0x68DB39, 0xCEE95D), 'fire': (0xFF6515, 0xFFD35C),
    'thunder': (0x52BFFF, 0xD8F6FF), 'dark': (0x7033B8, 0xBD74EE),
    'gravity': (0xCF58E8, 0xF6A5FF), 'soul': (0x24D7CB, 0xAA70EF),
}
SHAPES = 'cast orb comet wave impact nova pulse field vortex ward cage curse beam drain lightning flame charge'.split()


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
    for kind in ('sigil', 'arc', 'bolt', 'shard'):
        im = Image.new('RGBA', (256, 256)); d = ImageDraw.Draw(im)
        white = (255, 255, 255, 255)
        if kind == 'sigil':
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
    if shape in ('impact', 'nova'):
        r = .8 if shape=='nova' else .65
        sprite('Impact bloom', life=22, size=(1.8, 1.8, 1.8), growth=(.045, .045, .045), alpha=190)
        sprite('Bright flash', accent, life=9, size=(2.6, 2.6, 2.6), texture='star')
        for i in range(3):
            ring(children, accent if i==1 else c, radius=r+i*.2, y=i*.08, life=26, spin=(i-1)*3, alpha=210)
        motes(30 if shape=='nova' else 18, radius=.3, life=28, outward=.045, shards=theme in ('soul', 'blood'))
        if theme in ('poison', 'dark', 'blood'):
            for i in range(6): sprite('Lingering coloured mist', life=28, pos=(math.cos(i)*.6, .2, math.sin(i)*.6), size=(1.1, 1.1, 1.1), growth=(.02,.02,.02), texture='smoke', alpha=125, blend=1)
    elif shape in ('cast', 'charge'):
        ring(children, c, radius=.65 if shape=='cast' else .4, life=15, spin=8, vertical=True)
        sprite('Casting glow', life=12, size=(1.6, 1.6, 1.6), alpha=150)
        sprite('Casting star', accent, life=10, size=(1.1, 1.1, 1.1), texture='star')
        motes(10, radius=.5, life=15, outward=-.02, shards=theme=='soul')
    elif shape in ('orb', 'comet'):
        big = shape=='comet'
        sprite('Projectile aura', life=11, size=(2.4 if big else 1.6,)*3, alpha=180)
        sprite('Projectile core', accent, life=10, size=(1.1 if big else .7,)*3, texture='flame' if theme=='fire' else 'star')
        for i in range(12 if big else 8):
            a = i*2.39996
            sprite('Spiralling wake', accent if i%3==0 else c, life=13, pos=(math.sin(a)*.32, math.cos(a)*.32, -i*.14),
                   velocity=(math.cos(a)*.015,math.sin(a)*.015,-.025), size=(.24,.24,.24), texture='shard' if theme in ('soul','blood') else 'glow')
        if big: ring(children, c, radius=.7, life=11, spin=14, vertical=True)
    elif shape=='wave':
        for i in range(3):
            sprite('Sweeping crescent', accent if i==1 else c, life=11, pos=(0, 0, -i*.18), size=(2.6-i*.16, 2.6-i*.16, 1), texture='arc', billboard=2)
        motes(16, radius=.8, life=12, outward=.01, shards=theme=='soul')
    elif shape in ('pulse', 'field', 'vortex', 'ward', 'cage', 'curse'):
        if shape=='pulse':
            ring(children,c,radius=1,life=9,spin=3)
            ring(children,accent,radius=.88,y=.15,life=9,spin=-4)
            motes(18,radius=.95,life=9,outward=.005)
        elif shape=='vortex':
            for i in range(5):
                r = .25+i*.17 if theme=='fire' else 1-i*.15
                ring(children, accent if i%2 else c, radius=r, y=i*.23, life=15, spin=(9 if theme=='fire' else -9)+i, alpha=200)
            for i in range(28):
                a=i*.76; h=i/28; r=.25+h*.6 if theme=='fire' else 1-h*.75
                sprite('Vortex stream', accent if i%4==0 else c, life=15, pos=(math.cos(a)*r,h,math.sin(a)*r),
                       velocity=(-math.sin(a)*.025,.008,math.cos(a)*.025), size=(.12,.22,.12),texture='flame' if theme=='fire' else 'star')
            if theme=='gravity':
                sprite('Gravity core',0x10051D,life=15,pos=(0,.1,0),size=(1.2,1.2,1.2),alpha=230,blend=1)
        elif shape=='ward':
            for i, rot in enumerate(((90,0,0),(0,0,0),(0,90,0))):
                sprite('Crossing shield sigil',accent if i==1 else c,life=12,size=(2.25,2.25,1),rotation=rot,spin=(0,0,4 if i%2 else -4),texture='sigil',billboard=2,alpha=170)
            motes(18,radius=.92,life=12,outward=0,shards=theme=='soul')
        elif shape in ('cage','curse'):
            for y in (-.8,.8):ring(children,c,radius=.9,y=y,life=15,spin=5 if y>0 else -5)
            for i in range(8):
                a=i*math.tau/8
                sprite('Restraint pillar',accent if i%3==0 else c,life=15,pos=(math.cos(a)*.8,0,math.sin(a)*.8),size=(.11,2.0,1),texture='bolt' if shape=='curse' else 'glow',billboard=1,alpha=220)
            motes(12,radius=.8,life=15,outward=0,shards=theme=='soul')
        else:
            ring(children,c,radius=1,life=15,spin=2,alpha=210)
            ring(children,accent,radius=.65,y=.05,life=15,spin=-4,alpha=170)
            for i in range(10):
                a=i*2.4; r=seed.uniform(.2,.85)
                sprite('Drifting field mist',life=16,pos=(math.cos(a)*r,seed.uniform(.1,.6),math.sin(a)*r),velocity=(0,.008,0),size=(.5,.45,.5),growth=(.004,.004,.004),texture='smoke',alpha=100,blend=1)
            motes(16,radius=.8,life=15,outward=0)
            if theme=='thunder':
                for i in range(8):
                    a=i*math.tau/8
                    sprite('Storm cloud',0x7988A8,life=16,pos=(math.cos(a)*.55,2.8,math.sin(a)*.55),size=(.95,.55,.95),texture='smoke',alpha=150,blend=1)
                for i in range(4):
                    a=i*math.tau/4
                    sprite('Storm flicker',accent,life=12,pos=(math.cos(a)*.4,2.5,math.sin(a)*.4),size=(.12,.7,.12),texture='bolt')
    elif shape in ('beam','lightning','flame','drain'):
        if shape in ('beam','lightning'):
            # Crossed quads occupy local +Z from 0 to 1; runtime stretches only Z.
            for roll in (0,90):
                sprite('Energy filament',accent,life=13,size=(.65,1,1),pos=(0,0,.5),rotation=(90,roll,0),texture='bolt' if shape=='lightning' else 'glow',billboard=2)
        for i in range(25):
            t=i/24*(.65 if shape=='drain' else 1); a=i*2.4; spread=.18 if shape=='beam' else .35
            px=math.cos(a)*spread; py=math.sin(a)*spread
            sprite('Flowing energy',accent if i%4==0 else c,life=17 if shape=='flame' else 13,
                   pos=(px,py,t),velocity=(0,0,.025 if shape=='drain' else 0),
                   size=(.75,.9,.7) if shape=='flame' else (.32,.32,.32),
                   texture='flame' if shape=='flame' else 'star' if shape=='lightning' else 'glow',alpha=220)
            if shape=='drain' and i%3==0:
                sprite('Drained soul fragment',accent,life=14,pos=(px,py,t),velocity=(0,0,.02),size=(.18,.38,.18),texture='shard')
    else:
        raise ValueError(shape)
    element(project,'ToolVersion','1.80');element(project,'Version',3)
    element(project,'StartFrame',0);element(project,'EndFrame',40);element(project,'IsLoop','False')
    return ET.ElementTree(project)


def main():
    parser=argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--editor',type=Path,required=True,help='Effekseer 1.80.7 executable (Tool/bin/Effekseer on Linux)')
    parser.add_argument('--work',type=Path,default=ROOT/'build/effekseer')
    parser.add_argument('--theme',choices=list(PALETTES),nargs='+',default=list(PALETTES))
    parser.add_argument('--shape',choices=SHAPES,nargs='+',default=SHAPES)
    args=parser.parse_args();work=args.work.resolve();work.mkdir(parents=True,exist_ok=True)
    ASSETS.mkdir(parents=True,exist_ok=True);textures()
    shutil.copytree(ASSETS/'textures',work/'textures',dirs_exist_ok=True)
    editor=args.editor.resolve()
    for theme in args.theme:
        for shape in args.shape:
            project=author(shape,theme);ET.indent(project,space='  ')
            source=work/f'{shape}_{theme}.efkproj';project.write(source,encoding='utf-8',xml_declaration=True)
            dest=ASSETS/f'{shape}_{theme}.efkefc'
            dest.unlink(missing_ok=True)
            subprocess.run([str(editor),'-cui','-in',str(source),'-o',str(dest)],cwd=editor.parent,check=True,stdout=subprocess.DEVNULL)
            if not dest.exists() or dest.read_bytes()[:4]!=b'EFKE':raise RuntimeError(f'Export failed: {dest}')
        print(f'{theme}: {len(args.shape)} effects exported',flush=True)


if __name__=='__main__':main()
