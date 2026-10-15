"""Draw the original spellbook icons from the spell catalogue (Pillow required)."""
from pathlib import Path
import re,json,math,random
from PIL import Image,ImageDraw,ImageFilter
ROOT=Path(__file__).resolve().parents[1]
PALETTES={'MAGIC':'#c570ff','BLOOD':'#ee365d','POISON':'#87e745','FIRE':'#ff8b32','THUNDER':'#7de8ff','DARK':'#9b6be8','GRAVITY':'#ed8aff','SOUL':'#48e9d9'}

def main():
    source=(ROOT/'src/main/java/nl/townsandkingdoms/kingdomwands/Spell.java').read_text()
    spells=re.findall(r'^    (\w+)\("([^"]+)", Kind\.(\w+), Theme\.(\w+)',source,re.M)
    folder=ROOT/'src/main/resources/assets/kingdomwands/textures/gui/spell_icons';folder.mkdir(parents=True,exist_ok=True)
    langfile=ROOT/'src/main/resources/assets/kingdomwands/lang/en_us.json';lang=json.loads(langfile.read_text())
    for index,(name,title,kind,theme) in enumerate(spells):
        ident={'EMPIRE_SPARK':'arcane_strike','EMPIRE_COMET':'astral_comet','EMPIRE_PULSE':'arcane_repulse'}.get(name,name.lower())
        im=Image.new('RGBA',(128,128));d=ImageDraw.Draw(im);c=PALETTES[theme];white='#edfff8';rng=random.Random(index+911)
        def line(points,width=7,col=c):d.line(points,fill=col,width=width,joint='curve')
        if kind in ('BOLT','COMET','SEEKER','VOLLEY','CANNON','LANCE','SONIC','CHANNEL','SIPHON'):
            for i in range(3 if kind in ('VOLLEY','CANNON') else 2):
                off=(i-1)*18
                line([(17,104+off),(54,78+off),(103,29+off)],9 if kind=='CANNON' else 6)
            d.ellipse((79,17,111,49),fill=c);d.ellipse((89,24,103,38),fill=white)
            if kind=='CANNON':
                for x,y in ((46,78),(69,55)):d.ellipse((x-8,y-8,x+8,y+8),outline=white,width=4)
        elif kind in ('WAVE','PULSE','PUSH','PULL','BURST','LIGHTNING','SMITE','CHAIN','STORM'):
            if kind in ('LIGHTNING','SMITE','CHAIN','STORM'):
                d.polygon([(75,12),(35,66),(59,65),(46,116),(100,53),(73,53)],fill=c)
                line([(75,22),(49,60),(66,59)],4,white)
            else:
                for i in range(3):d.arc((14+i*12,17+i*8,113-i*3,117-i*4),203,341,fill=c,width=7)
                line([(41,57),(65,39),(89,57)],4,white)
        elif kind in ('WARD','CAPTURE','CHOKE','SUSPEND','MARK','STUN','CONFUSE'):
            d.polygon([(64,13),(105,36),(100,81),(64,114),(28,81),(23,36)],outline=c,width=8)
            line([(64,36),(64,84)],9);d.ellipse((58,92,70,104),fill=white)
        elif kind in ('AURA','FIELD','WELL','TWISTER'):
            points=[]
            for i in range(76):
                angle=i*.22;radius=4+i*.5;points.append((64+math.cos(angle)*radius,64+math.sin(angle)*radius))
            line(points,7);d.ellipse((56,56,72,72),fill=white)
        elif kind in ('LEAP','LAUNCH'):
            line([(30,106),(64,29),(97,59)],9);line([(46,44),(64,25),(84,28)],8,white)
        else:
            d.polygon([(64,13),(89,51),(113,64),(83,84),(64,116),(43,84),(15,64),(43,43)],fill=c)
            d.ellipse((53,53,75,75),fill=white)
        for i in range(5):
            x,y=rng.randint(15,112),rng.randint(18,110);d.ellipse((x,y,x+4,y+4),fill=c)
        glow=im.filter(ImageFilter.GaussianBlur(4));im=Image.alpha_composite(glow,im)
        im.resize((32,32),Image.Resampling.LANCZOS).save(folder/(ident+'.png'))
        lang['spell.kingdomwands.'+ident]=title
    lang['subtitles.kingdomwands.cast']='Magic rushes'
    lang['subtitles.kingdomwands.impact']='Spell strikes'
    langfile.write_text(json.dumps(lang,indent=2)+'\n');print('Generated',len(spells),'spell icons and translations.')

if __name__=='__main__':main()
